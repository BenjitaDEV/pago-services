package com.caleta.pago.dto;

public class LoteResponse {

    private Long id;
    private Long capturaId;
    private double precioBase;
    private String estado;

    public LoteResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCapturaId() {
        return capturaId;
    }

    public void setCapturaId(Long capturaId) {
        this.capturaId = capturaId;
    }

    public double getPrecioBase() {
        return precioBase;
    }

    public void setPrecioBase(double precioBase) {
        this.precioBase = precioBase;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    

}
