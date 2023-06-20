package com.example.demo.service;

import com.example.demo.component.Messages;
import com.example.demo.dto.CategoriaDTO;
import com.example.demo.entity.Categoria;
import com.example.demo.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    @Autowired
    private Messages messages;
    private final CategoriaRepository categoriaRepository;
    private static final String CATEGORIA = "Categoria";

    @Transactional(readOnly = true)
    public List<CategoriaDTO> listar() {
        List<Categoria> entidades = categoriaRepository.findAll();
        return entidades.stream().map(CategoriaDTO::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CategoriaDTO retornarPorId(Long id) {
        Categoria metaCategoria = buscarValidar(id);
        return new CategoriaDTO(metaCategoria);
    }

    public Categoria buscarValidar(Long id) {
        return categoriaRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        messages.getAndReplace("entidade.nao.encontrada", CATEGORIA)));
    }

    @Transactional(rollbackFor = Throwable.class, propagation = Propagation.REQUIRES_NEW)
    public String salvar(CategoriaDTO dto) {
        Categoria metaCategoria = new Categoria();
        categoriaRepository.save(metaCategoria);

        return messages.getAndReplace("entidade.salva", CATEGORIA);
    }

    @Transactional(rollbackFor = Throwable.class, propagation = Propagation.REQUIRES_NEW)
    public String atualizar(CategoriaDTO dto) {
        Categoria metaCategoria = buscarValidar(dto.getId());

        categoriaRepository.save(metaCategoria);

        return messages.getAndReplace("entidade.atualizada", CATEGORIA);
    }

    @Transactional(rollbackFor = Throwable.class, propagation = Propagation.REQUIRES_NEW)
    public String deletar(Long id) {
        Categoria metaCategoria = buscarValidar(id);
        categoriaRepository.delete(metaCategoria);

        return messages.getAndReplace("entidade.deletada", CATEGORIA);
    }
}
