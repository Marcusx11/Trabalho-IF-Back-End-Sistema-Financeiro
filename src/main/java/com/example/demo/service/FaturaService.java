package com.example.demo.service;

import com.example.demo.component.Messages;
import com.example.demo.dto.FaturaDTO;
import com.example.demo.entity.Fatura;
import com.example.demo.repository.FaturaRepository;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FaturaService {

    @Autowired
    private Messages messages;

    @Autowired
    private FaturaRepository repository;

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private TransacaoService transacaoService;

    private static final String FATURA = "Fatura";

    @Transactional(readOnly = true)
    public List<FaturaDTO> listar() {
        List<Fatura> entidades = repository.findAll();

        return entidades.stream().map(FaturaDTO::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public FaturaDTO retornarPorId(Long id) {
        Fatura fatura = buscarValidar(id);
        return new FaturaDTO(fatura);
    }

    public Fatura buscarValidar(Long id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        messages.getAndReplace("entidade.nao.encontrada", FATURA)));
    }

    @Transactional(rollbackFor = Throwable.class, propagation = Propagation.REQUIRES_NEW)
    public String salvar(FaturaDTO dto) {
        Fatura fatura = new Fatura();
        fatura.transformer(dto,
                categoriaService.buscarValidar(dto.getCategoria().getId()),
                transacaoService.retornarListaEntidade(dto.getTransacoes()));

        repository.save(fatura);

        return messages.getAndReplace("entidade.salva", FATURA);
    }

    @Transactional(rollbackFor = Throwable.class, propagation = Propagation.REQUIRES_NEW)
    public String atualizar(FaturaDTO dto) {
        Fatura fatura = buscarValidar(dto.getId());
        fatura.transformer(dto,
                categoriaService.buscarValidar(dto.getCategoria().getId()),
                transacaoService.retornarListaEntidade(dto.getTransacoes()));

        repository.save(fatura);

        return messages.getAndReplace("entidade.atualizada", FATURA);
    }

    @Transactional(rollbackFor = Throwable.class, propagation = Propagation.REQUIRES_NEW)
    public String deletar(Long id) {
        Fatura fatura = buscarValidar(id);
        repository.delete(fatura);

        return messages.getAndReplace("entidade.deletada", FATURA);
    }
}
