package com.example.demo.controller;

import com.example.demo.dto.FaturaDTO;
import com.example.demo.dto.MetaCategoriaDTO;
import com.example.demo.dto.TransacaoDTO;
import com.example.demo.service.FaturaService;
import com.example.demo.service.MetaCategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping("/sistema/relatorio")
@RestController
@RequiredArgsConstructor
public class RelatorioController {
    @Autowired
    private MetaCategoriaService serviceMetaCategoria;
    @Autowired
    private FaturaService serviceFatura;

    private String printTable(int mesInicial, int mesFinal) {
        StringBuilder relatorio = new StringBuilder();
        Optional<TransacaoDTO> transacao;
        for (int i = mesInicial - 1; i < mesFinal; i++) {
            if (i == 0)
                relatorio.append("Relatório do mês atual:\n");
            else
                relatorio.append(String.format("Relatório do %do mês subsequente:\n", i));

            relatorio.append("-------------------------------------------------------------------------\n");
            relatorio.append(String.format("| %-22s | %8s | %15s | %15s |\n", "Categoria", "Meta", "Valor Total", "Parcela Atual"));
            float orcamento = 0;
            int parcela = i + 1;
            for (MetaCategoriaDTO dto : serviceMetaCategoria.listar()) {
                orcamento += dto.getLimite();
                relatorio.append(String.format("| %-22s | %8.2f |                 |                 |\n", dto.getCategoria().getNome(), dto.getLimite()));
                for (FaturaDTO fatura : serviceFatura.listar()) {
                    if (fatura.getCategoria().equals(dto.getCategoria())) {
                        int finalParcela = parcela;
                        transacao = fatura.getTransacoes().stream().filter(t -> t.getDataPagamento() == null && t.getParcela() == finalParcela).findFirst();
                        transacao.ifPresentOrElse(
                                (value) -> relatorio.append(String.format("| |-> %-18s |          | %15.2f | %10.2f (%dx) |\n", fatura.getId(), fatura.getValorTotal(), value.getValor(), fatura.getParcelas())),
                                () -> relatorio.append(""));
                    }
                }
            }
            relatorio.append("-------------------------------------------------------------------------\n");
            relatorio.append(String.format("  %-22s : %8.2f\n\n", "Orçamento", orcamento));
        }
        return String.valueOf(relatorio);
    }

    @GetMapping("/geral") // Gera relatorio geral
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public String geral() {
        return printTable(1, 1);
    }

    @GetMapping("/previsao-gastos/{x}") // Gera relatorio previsao de gastos pros proximos X meses
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public String previsaoGastos(@PathVariable(name = "x") int meses) {
        return printTable(2, meses+1);
    }

    @GetMapping("/contas-pendentes") // Gera relatorio de contas pendentes dentro de um período [inicial a final]
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public String contasPendentes(@RequestParam("inicial") int mesInicial, @RequestParam("final") int mesFinal) {
        return printTable(mesInicial, mesFinal+1);
    }
}
