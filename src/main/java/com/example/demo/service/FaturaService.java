package com.example.demo.service;

import com.example.demo.component.Messages;
import com.example.demo.dto.FaturaDTO;
import com.example.demo.dto.MetaCategoriaDTO;
import com.example.demo.entity.Categoria;
import com.example.demo.entity.Fatura;
import com.example.demo.entity.MetaCategoria;
import com.example.demo.query.FaturaQuery;
import com.example.demo.repository.FaturaRepository;
import com.example.demo.utility.enums.EnumFiltroFatura;
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
public class FaturaService extends BaseService {

    @Autowired
    private FaturaRepository repository;

    @Autowired
    private FaturaQuery query;

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private TransacaoService transacaoService;

    @Autowired
    private MetaCategoriaService metaCategoriaService;

    private static final String FATURA = "Fatura";


    @Transactional(readOnly = true)
    public List<FaturaDTO> listar() {
        List<Fatura> entidades = repository.findAll();

        return entidades.stream().map(FaturaDTO::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<FaturaDTO> filtrarFaturas(FaturaDTO filtro) {
        List<Fatura> entidades = query.filtrarFaturas(filtro);

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
        Categoria categoria = categoriaService.buscarValidar(dto.getCategoria().getId());

        fatura.transformer(dto, categoria, transacaoService.passarDadosTransacoes(fatura, dto));

        metaCategoriaService.verificaLimite(repository.findAllByCategoria(categoria), dto, categoria);
        repository.save(fatura);

        return messages.getAndReplace("entidade.salva", FATURA);
    }

    @Transactional(rollbackFor = Throwable.class, propagation = Propagation.REQUIRES_NEW)
    public String atualizar(FaturaDTO dto) {
        Categoria categoria = categoriaService.buscarValidar(dto.getCategoria().getId());
        metaCategoriaService.verificaLimite(repository.findAllByCategoria(categoria), dto, categoria);

        Fatura fatura = buscarValidar(dto.getId());
        fatura.transformer(dto, categoria, transacaoService.passarDadosTransacoes(fatura, dto));

        repository.save(fatura);

        return messages.getAndReplace("entidade.atualizada", FATURA);
    }

    @Transactional(rollbackFor = Throwable.class, propagation = Propagation.REQUIRES_NEW)
    public String pagarParcela(Long id) {
        Fatura fatura = buscarValidar(id);
        String mensagemRetorno = transacaoService.pagarTransacaoMaisRecente(fatura.getTransacoes());

        if (transacaoService.retornarTransacaoDataPagamentoMaisRecente(fatura.getTransacoes()) == null) {
            fatura.setFaturado(Boolean.TRUE);
            repository.save(fatura);
        }

        return mensagemRetorno;
    }

    @Transactional(rollbackFor = Throwable.class, propagation = Propagation.REQUIRES_NEW)
    public String deletar(Long id) {
        Fatura fatura = buscarValidar(id);
        repository.delete(fatura);

        return messages.getAndReplace("entidade.deletada", FATURA);
    }
}
