package com.example.demo.entity;

import com.example.demo.dto.FaturaDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    @OneToMany(mappedBy = "fatura", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transacao> transacoes;

    @Transient
    public void transformer(FaturaDTO dto, Categoria categoria, List<Transacao> transacoes) {
        setValorTotal(dto.getValorTotal());
        setParcelas(dto.getParcelas());
        setFaturado(dto.getFaturado());
        setCategoria(categoria);
        setTransacoes(transacoes);

        if (getTransacoes() == null) {
            setTransacoes(new ArrayList<>());
        }
        getTransacoes().clear();
        getTransacoes().addAll(transacoes);
    }
}
