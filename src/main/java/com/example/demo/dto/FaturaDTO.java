package com.example.demo.dto;

import com.example.demo.entity.Fatura;
import com.example.demo.entity.Transacao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FaturaDTO {

    private Long id;
    private Double valorTotal;
    private Integer parcelas;
    private Boolean faturado;
    private List<TransacaoDTO> transacoes;

    public FaturaDTO(Fatura fatura) {
        if (fatura == null) return;

        setId(fatura.getId());
        setValorTotal(fatura.getValorTotal());
        setParcelas(fatura.getParcelas());
        setFaturado(fatura.getFaturado());

        if (fatura.getTransacoes() != null) {
            setTransacoes(new ArrayList<>());
            for (Transacao t : fatura.getTransacoes()) {
                getTransacoes().add(new TransacaoDTO(t));
            }
        }
    }
}
