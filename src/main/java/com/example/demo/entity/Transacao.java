package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "transacao")
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "valor")
    private Double valor;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data")
    private Date data;

    @Column(name = "parcela")
    private Integer parcela;

    @Temporal(TemporalType.DATE)
    @Column(name = "data_pagamento")
    private Date dataPagamento;

    @Temporal(TemporalType.DATE)
    @Column(name = "data_vencimento")
    private Date dataVencimento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fatura_id")
    private Fatura fatura;
}
