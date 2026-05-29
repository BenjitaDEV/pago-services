package com.caleta.pago.dto;

import jakarta.validation.constraints.NotNull;

public record UpdatePagoRequest (
    @NotNull (message = "loteId no puede estar vacío") Long loteId,
    @NotNull (message = "total no puede estar vacío") Double total,
    @NotNull (message = "comision no puede estar vacío") Double comision,
    @NotNull (message = "montofinal no puede estar vacío") Double montofinal
){

}
