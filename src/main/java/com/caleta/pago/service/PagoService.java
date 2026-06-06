package com.caleta.pago.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.caleta.pago.dto.CapturaResponse;
import com.caleta.pago.dto.CreatePagoRequest;
import com.caleta.pago.dto.LoteResponse;
import com.caleta.pago.dto.UpdatePagoRequest;
import com.caleta.pago.model.Pago;
import com.caleta.pago.repository.PagoRepository;

@Service
public class PagoService {

    private final PagoRepository pagoRepository;
    private final WebClient loteWebClient;
    private final WebClient capturaWebClient;

    public PagoService(
            PagoRepository pagoRepository,
            @Qualifier("loteWebClient") WebClient loteWebClient,
            @Qualifier("capturaWebClient") WebClient capturaWebClient) {
        this.pagoRepository = pagoRepository;
        this.loteWebClient = loteWebClient;
        this.capturaWebClient = capturaWebClient;
    }

    public List<Pago> getPagos(){
        return pagoRepository.findAll();
    }

    public Pago getPagoById(Long id){
        return pagoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Pago no encontrado"));
    }

    public List<Pago> getPagosByLote(Long loteId){
        return pagoRepository.selectByLoteId(loteId);
    }

    public Pago crearPago(CreatePagoRequest request){
        
        Long loteId = request.loteId();

        //evitar pagos dupicados
        if (!pagoRepository.selectByLoteId(loteId).isEmpty()){
            throw new RuntimeException("Este lote ya tiene un pago registrado");
        }

        //obtener lote
        LoteResponse lote = loteWebClient.get()
            .uri("/{id}", loteId)
            .retrieve()
            .bodyToMono(LoteResponse.class)
            .block();
        
        if (lote == null){
            throw new RuntimeException("Lote no encontrado");
        }

        if (!"VENDIDO".equalsIgnoreCase(lote.getEstado())){
            throw new RuntimeException("El lote no está vendido, no se puede crear el pago");
        }

        //obtener captura
        CapturaResponse captura = capturaWebClient.get()
            .uri("/{id}", lote.getCapturaId())
            .retrieve()
            .bodyToMono(CapturaResponse.class)
            .block();

        if (captura == null){
            throw new RuntimeException("Captura no encontrada");
        }

        if (captura.getKilos() <= 0){
            throw new RuntimeException("La captura no tiene kilos registrados");
        }

        //calcular comision y monto final
        double total = captura.getKilos() * lote.getPrecioBase();
        double comision = total * 0.08; // 8% de comision
        double montoFinal = total - comision;

        Pago pago = new Pago();
        pago.setLoteId(loteId);
        pago.setTotal(total);
        pago.setComision(comision);
        pago.setMontofinal(montoFinal);

        return pagoRepository.save(pago);
    }

    public String eliminarPago(Long id){
        Pago pago = pagoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Pago no encontrado"));

        pagoRepository.delete(pago);
        return "Pago " + id + " eliminado";
    }

    public Pago actualizarPago(Long id, UpdatePagoRequest request){
        
        Pago pago = pagoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Pago no encontrado"));

        if (request.total() != 0){
            throw new RuntimeException("Total invalido");
        }

        if (request.comision() < 0){
            throw new RuntimeException("Comision invalida");
        }
        
        pago.setLoteId(request.loteId());
        pago.setTotal(request.total());
        pago.setComision(request.comision());
        pago.setMontofinal(request.montofinal());

        return pagoRepository.save(pago);
    }

}
