package com.example.demo.service;

import com.example.demo.component.Messages;
import com.example.demo.dto.TransacaoDTO;
import com.example.demo.entity.Transacao;
import com.example.demo.repository.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
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
}
