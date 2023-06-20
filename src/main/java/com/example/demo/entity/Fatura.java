package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "fatura")
public class Fatura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "valor_total")
    private Double valorTotal;

    @Column(name = "parcelas")
    private Integer parcelas;

    @Column(name = "faturado")
    private Boolean faturado;

    // TODO -> Colocar referencia da Categoria

    @OneToMany(mappedBy = "fatura", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transacao> transacoes;
}
