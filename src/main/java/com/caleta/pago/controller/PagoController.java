package com.caleta.pago.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.caleta.pago.dto.CreatePagoRequest;
import com.caleta.pago.dto.UpdatePagoRequest;
import com.caleta.pago.exception.ResourceNotFoundException;
import com.caleta.pago.model.Pago;
import com.caleta.pago.service.PagoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/pagos")
public class PagoController {

    private final PagoService pagoService;

    public PagoController(PagoService pagoService) {
        this.pagoService = pagoService;
    }

    @GetMapping
    public ResponseEntity<List<Pago>> listar(){
        return ResponseEntity.ok(pagoService.getPagos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pago> obtenerPorId(@PathVariable Long id){

        Pago pago = pagoService.getPagoById(id);
        if (pago == null){
            throw new ResourceNotFoundException("Pago no encontrado");
        }

        return ResponseEntity.ok(pagoService.getPagoById(id));
    }

    @GetMapping("/lote/{id}")
    public ResponseEntity<List<Pago>> obtenerPorLoteId(@PathVariable Long id){
        return ResponseEntity.ok(pagoService.getPagosByLote(id));
    }

    @PostMapping
    public ResponseEntity<Pago> crearPago(@Valid @RequestBody CreatePagoRequest request){
        Pago nuevoPago = pagoService.crearPago(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPago);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pago> actualizarPago(@PathVariable Long id, @Valid @RequestBody UpdatePagoRequest request){
        Pago pagoActualizado = pagoService.actualizarPago(id, request);
        
        if(pagoActualizado == null){
            throw new ResourceNotFoundException("Pago no encontraodo con id:" + id);
        }
        return ResponseEntity.ok(pagoActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPago(@PathVariable Long id){
        pagoService.eliminarPago(id);
        return ResponseEntity.noContent().build();
    }
}
