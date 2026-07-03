package com.caleta.pago.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.caleta.pago.dto.CreatePagoRequest;
import com.caleta.pago.dto.UpdatePagoRequest;
import com.caleta.pago.exception.ResourceNotFoundException;
import com.caleta.pago.model.Pago;
import com.caleta.pago.service.PagoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/pagos")
@Tag(name = "Pagos", description = "API para gestionar pagos")
public class PagoController {

    private final PagoService pagoService;

    public PagoController(PagoService pagoService) {
        this.pagoService = pagoService;
    }

    @Operation(
            summary = "Listar pagos",
            description = "Obtiene todos los pagos registrados"
    )
    @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente")
    @GetMapping
    public ResponseEntity<List<Pago>> listar() {
        return ResponseEntity.ok(pagoService.getPagos());
    }

    @Operation(
            summary = "Buscar pago por ID",
            description = "Obtiene un pago mediante su ID"
    )
    @ApiResponse(responseCode = "200", description = "Pago encontrado")
    @ApiResponse(responseCode = "404", description = "Pago no encontrado")
    @GetMapping("/{id}")
    public ResponseEntity<Pago> obtenerPorId(@PathVariable Long id) {

        Pago pago = pagoService.getPagoById(id);

        if (pago == null) {
            throw new ResourceNotFoundException("Pago no encontrado");
        }

        return ResponseEntity.ok(pago);
    }

    @Operation(
            summary = "Buscar pagos por lote",
            description = "Obtiene todos los pagos asociados a un lote"
    )
    @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente")
    @GetMapping("/lote/{id}")
    public ResponseEntity<List<Pago>> obtenerPorLoteId(@PathVariable Long id) {
        return ResponseEntity.ok(pagoService.getPagosByLote(id));
    }

    @Operation(
            summary = "Crear pago",
            description = "Registra un nuevo pago"
    )
    @ApiResponse(responseCode = "201", description = "Pago creado correctamente")
    @PostMapping
    public ResponseEntity<Pago> crearPago(

            @Valid
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos para registrar un pago",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Ejemplo de creación",
                                    summary = "Crear pago",
                                    value = """
                                    {
                                      "loteId": 1,
                                      "total": 150000.0,
                                      "comision": 15000.0,
                                      "montofinal": 135000.0
                                    }
                                    """
                            )
                    )
            )
            @org.springframework.web.bind.annotation.RequestBody CreatePagoRequest request) {

        Pago nuevoPago = pagoService.crearPago(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPago);
    }

    @Operation(
            summary = "Actualizar pago",
            description = "Actualiza la información de un pago"
    )
    @ApiResponse(responseCode = "200", description = "Pago actualizado correctamente")
    @ApiResponse(responseCode = "404", description = "Pago no encontrado")
    @PutMapping("/{id}")
    public ResponseEntity<Pago> actualizarPago(

            @PathVariable Long id,
            @Valid
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos para actualizar un pago",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Ejemplo de actualización",
                                    summary = "Actualizar pago",
                                    value = """
                                    {
                                      "loteId": 2,
                                      "total": 200000.0,
                                      "comision": 20000.0,
                                      "montofinal": 180000.0
                                    }
                                    """
                            )
                    )
            )
            @org.springframework.web.bind.annotation.RequestBody UpdatePagoRequest request) {

        Pago pagoActualizado = pagoService.actualizarPago(id, request);

        if (pagoActualizado == null) {
            throw new ResourceNotFoundException("Pago no encontrado con id: " + id);
        }

        return ResponseEntity.ok(pagoActualizado);
    }

    @Operation(
            summary = "Eliminar pago",
            description = "Elimina un pago por su ID"
    )
    @ApiResponse(responseCode = "204", description = "Pago eliminado correctamente")
    @ApiResponse(responseCode = "404", description = "Pago no encontrado")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPago(@PathVariable Long id) {

        pagoService.eliminarPago(id);

        return ResponseEntity.noContent().build();
    }
}