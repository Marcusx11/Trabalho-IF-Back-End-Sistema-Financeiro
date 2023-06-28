package com.example.demo.service;

import com.example.demo.component.Messages;
import com.example.demo.dto.FaturaDTO;
import com.example.demo.dto.TransacaoDTO;
import com.example.demo.entity.Fatura;
import com.example.demo.entity.Transacao;
import com.example.demo.repository.TransacaoRepository;
import com.example.demo.utility.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class TransacaoService extends BaseService {

    @Autowired
    private TransacaoRepository repository;

    private static final String TRANSACAO = "Transação";

    public Transacao buscarValidar(Long id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        messages.getAndReplace("entidade.nao.encontrada", TRANSACAO)));
    }

    public Transacao retornarTransacaoDataPagamentoMaisRecente(List<Transacao> transacoes) {
        return transacoes.stream().filter(t -> t.getDataPagamento() == null).findFirst().orElse(null);
    }

    public String pagarTransacaoMaisRecente(List<Transacao> transacaos) {
        Transacao transacaoMaisRecente = retornarTransacaoDataPagamentoMaisRecente(transacaos);
        if (transacaoMaisRecente == null) {
            return messages.get("fatura.paga");
        }
        transacaoMaisRecente.setDataPagamento(new Date());
        repository.save(transacaoMaisRecente);

        return messages.get("parcela.paga");
    }

    public List<Transacao> passarDadosTransacoes(Fatura fatura, FaturaDTO dto) {
        List<Transacao> entidades = new ArrayList<>();
        double valorParcela = dto.getValorTotal() / dto.getParcelas();
        Calendar c = Calendar.getInstance();
        Transacao transacao;

        if (dto.getParcelas() == 1) {
            transacao = passarDadosTransacao(fatura, dto, c.getTime(), 1, valorParcela);
            if (Boolean.TRUE.equals(dto.getFaturado())) {
                transacao.setDataPagamento(new Date());
            }
            entidades.add(transacao);
        } else {
            for (int parc = 1; parc <= dto.getParcelas(); parc++) {
                c.add(Calendar.MONTH, 1);
                transacao = passarDadosTransacao(fatura, dto, c.getTime(), parc, valorParcela);
                entidades.add(transacao);
            }
        }

        return entidades;
    }

    private Transacao passarDadosTransacao(Fatura fatura,
                                           FaturaDTO dto,
                                           Date proxDataPagamento,
                                           int parc,
                                           double valorParcela) {
        Transacao transacao = new Transacao();

        transacao.setValor(Utils.retornaValorNegativoSeEhDespesa(valorParcela, dto.getEhDespesa()));
        transacao.setData(new Date());
        transacao.setParcela(parc);
        transacao.setDataVencimento(proxDataPagamento);
        transacao.setFatura(fatura);

        return transacao;
    }
}
