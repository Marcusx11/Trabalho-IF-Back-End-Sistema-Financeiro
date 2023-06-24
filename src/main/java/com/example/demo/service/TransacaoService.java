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
public class TransacaoService {

    @Autowired
    private TransacaoRepository repository;

    @Autowired
    private Messages messages;

    private static final String TRANSACAO = "Transação";

    public Transacao buscarValidar(Long id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        messages.getAndReplace("entidade.nao.encontrada", TRANSACAO)));
    }

    public List<Transacao> retornarListaEntidade(List<TransacaoDTO> transacoes) {
        List<Transacao> entidades = new ArrayList<>();

        for (TransacaoDTO dto : transacoes) {
            entidades.add(buscarValidar(dto.getId()));
        }

        return entidades;
    }

    public List<Transacao> passarDadosTransacoes(Fatura fatura, FaturaDTO dto) {
        List<Transacao> entidades = new ArrayList<>();
        Double valorParcela = dto.getValorTotal() / dto.getParcelas();
        Calendar c = Calendar.getInstance();
        Transacao transacao;

        if (dto.getParcelas() == 1) {
            transacao = passarDadosTransacao(fatura, dto, c.getTime(), 1, valorParcela);
            entidades.add(transacao);
            // TODO -> Dúdidas sobre setar a data de pagamento mesmo para faturas únicas
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
