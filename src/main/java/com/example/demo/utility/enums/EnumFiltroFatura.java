package com.example.demo.utility.enums;

import lombok.Getter;

@Getter
public enum EnumFiltroFatura {
    FILTRO_PENDENTE("FILTRO_PENDENTE"), FILTRO_DATAS_PAGAMENTOS_PENDENTES("FILTRO_DATAS_PAGAMENTOS_PENDENTES"),
    FILTRO_PAGAMENTOS_EFETUADOS("FILTRO_PAGAMENTOS_EFETUADOS");

    private final String filtroFatura;

    EnumFiltroFatura(String filtroFatura) {
        this.filtroFatura = filtroFatura;
    }
}
