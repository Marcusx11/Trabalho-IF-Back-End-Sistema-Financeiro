package com.example.demo.service;

import com.example.demo.component.Messages;
import com.example.demo.dto.CategoriaDTO;
import com.example.demo.dto.FaturaDTO;
import com.example.demo.entity.Categoria;
import com.example.demo.entity.Fatura;
import com.example.demo.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoriaService extends BaseService {

    @Autowired
    private final CategoriaRepository categoriaRepository;
    private static final String CATEGORIA = "Categoria";

    @Autowired
    private final MetaCategoriaService metaCategoriaService;

    @Transactional(readOnly = true)
    public List<CategoriaDTO> listar() {
        List<Categoria> entidades = categoriaRepository.findAll();
        return entidades.stream().map(CategoriaDTO::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CategoriaDTO retornarPorId(Long id) {
        Categoria categoria = buscarValidar(id);
        return new CategoriaDTO(categoria);
    }

    public Categoria buscarValidar(Long id) {
        return categoriaRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        messages.getAndReplace("entidade.nao.encontrada", CATEGORIA)));
    }

    @Transactional(rollbackFor = Throwable.class, propagation = Propagation.REQUIRES_NEW)
    public String salvar(CategoriaDTO dto) {
        Categoria categoria = new Categoria();
        categoria.transformer(dto);
        categoriaRepository.save(categoria);

        return messages.getAndReplace("entidade.salva", CATEGORIA);
    }

    @Transactional(rollbackFor = Throwable.class, propagation = Propagation.REQUIRES_NEW)
    public String atualizar(CategoriaDTO dto) {
        Categoria categoria = buscarValidar(dto.getId());
        categoria.transformer(dto);
        categoriaRepository.save(categoria);

        return messages.getAndReplace("entidade.atualizada", CATEGORIA);
    }

    @Transactional(rollbackFor = Throwable.class, propagation = Propagation.REQUIRES_NEW)
    public String deletar(Long id) {
        Categoria categoria = buscarValidar(id);
        categoriaRepository.delete(categoria);

        return messages.getAndReplace("entidade.deletada", CATEGORIA);
    }
}
