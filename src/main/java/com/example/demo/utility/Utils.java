package com.example.demo.utility;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Utils {

    public static final double retornaValorNegativoSeEhDespesa(Double valor, Boolean ehDespesa) {
        if (valor == null) {
            return 0.0;
        }
        if (valor == Math.abs(valor)) {
            return Boolean.TRUE.equals(ehDespesa) ? -1 * valor : valor;
        }
        return Boolean.TRUE.equals(ehDespesa) ? valor : -1 * valor;
    }
}
