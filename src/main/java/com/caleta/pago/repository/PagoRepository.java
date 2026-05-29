package com.caleta.pago.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.caleta.pago.model.Pago;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {

    @Query(value = "SELECT * FROM pagos WHERE lote_id = :loteId", nativeQuery = true)
    List<Pago> selectByLoteId(@Param("loteId") Long loteId);

}
