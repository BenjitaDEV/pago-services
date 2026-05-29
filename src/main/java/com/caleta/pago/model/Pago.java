package com.caleta.pago.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "pagos")
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pago")
    private Long id;

    @Column(name = "lote_id", nullable = false)
    private Long loteId;

    @Column(name = "total", nullable = false)
    private Double total;

    @Column(name = "comision", nullable = false)
    private Double comision;

    @Column(name = "montofinal", nullable = false)
    private Double montofinal;

    public Pago() {  
    }

    public Pago(Long id, Long loteId, Double total, Double comision, Double montofinal) {
        this.id = id;
        this.loteId = loteId;
        this.total = total;
        this.comision = comision;
        this.montofinal = montofinal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLoteId() {
        return loteId;
    }

    public void setLoteId(Long loteId) {
        this.loteId = loteId;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getComision() {
        return comision;
    }

    public void setComision(Double comision) {
        this.comision = comision;
    }

    public Double getMontofinal() {
        return montofinal;
    }

    public void setMontofinal(Double montofinal) {
        this.montofinal = montofinal;
    }


}
