package com.example.demo.dto;

import com.example.demo.entity.Fatura;
import com.example.demo.entity.Transacao;
import com.example.demo.utility.enums.EnumFiltroFatura;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FaturaDTO {

    private Long id;
    private Double valorTotal;
    private Integer parcelas;
    private Boolean faturado;
    private Boolean ehDespesa;
    private CategoriaDTO categoria;
    private EnumFiltroFatura filtro;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dataPeriodoInicio;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dataPeriodoFim;

    private List<TransacaoDTO> transacoes;

    public FaturaDTO(Fatura fatura) {
        if (fatura == null) return;

        setId(fatura.getId());
        setValorTotal(fatura.getValorTotal());
        setEhDespesa(fatura.getValorTotal() != Math.abs(fatura.getValorTotal()));
        setParcelas(fatura.getParcelas());
        setFaturado(fatura.getFaturado());
        setCategoria(new CategoriaDTO(fatura.getCategoria()));
        setTransacoes(new ArrayList<>());

        if (fatura.getTransacoes() != null) {
            for (Transacao t : fatura.getTransacoes()) {
                getTransacoes().add(new TransacaoDTO(t));
            }
        }
    }
}
