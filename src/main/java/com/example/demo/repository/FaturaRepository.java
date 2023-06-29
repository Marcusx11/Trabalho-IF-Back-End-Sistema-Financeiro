package com.example.demo.repository;

import com.example.demo.entity.Fatura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface FaturaRepository extends JpaRepository<Fatura, Long> {

    List<Fatura> findAllByFaturadoEquals(Boolean faturado);

    @Query("SELECT DISTINCT f FROM Fatura f LEFT JOIN f.transacoes t WHERE f.faturado IS TRUE AND t.dataPagamento IS NOT NULL ")
    List<Fatura> encontrarPagamentosEfetuados();
}
