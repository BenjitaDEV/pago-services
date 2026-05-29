package com.caleta.pago.mapper;

import com.caleta.pago.dto.CreatePagoRequest;
import com.caleta.pago.dto.UpdatePagoRequest;
import com.caleta.pago.model.Pago;

public class PagoMapper {

    //CREATE
    public static Pago toModel(CreatePagoRequest request){
        return new Pago(
            0L,
            request.loteId(),
            request.total(),
            request.comision(),
            request.montofinal()
        );
    }

    //UPDATE
    public static Pago toModel(Long id, UpdatePagoRequest request){
        return new Pago(
            id,
            request.loteId(),
            request.total(),
            request.comision(),
            request.montofinal()
        );
    }

}
