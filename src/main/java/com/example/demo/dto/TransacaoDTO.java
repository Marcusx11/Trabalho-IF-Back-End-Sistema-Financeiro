package com.example.demo.dto;

import com.example.demo.entity.Transacao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransacaoDTO {

    private Long id;
    private Double valor;
    private Date data;
    private Integer parcela;
    private Date dataPagamento;
    private Date dataVencimento;

    public TransacaoDTO(Transacao transacao) {
        if (transacao == null) return;

        setId(transacao.getId());
        setValor(transacao.getValor());
        setData(transacao.getData());
        setParcela(transacao.getParcela());
        setDataPagamento(transacao.getDataPagamento());
        setDataVencimento(transacao.getDataVencimento());
    }
}
