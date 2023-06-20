package com.example.demo.service;

import com.example.demo.component.Messages;
import com.example.demo.dto.CategoriaDTO;
import com.example.demo.dto.MetaCategoriaDTO;
import com.example.demo.entity.MetaCategoria;
import com.example.demo.repository.CategoriaRepository;
import com.example.demo.repository.MetaCategoriaRepository;
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
public class MetaCategoriaService {

    @Autowired
    private Messages messages;
    private final MetaCategoriaRepository metaCategoriaRepository;
    private static final String METACATEGORIA = "MetaCategoria";

    private final CategoriaRepository categoriaRepository;

    @Transactional(readOnly = true)
    public List<MetaCategoriaDTO> listar() {
        List<MetaCategoria> entidades = metaCategoriaRepository.findAll();
        return entidades.stream().map(MetaCategoriaDTO::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public MetaCategoriaDTO retornarPorId(Long id) {
        MetaCategoria metaCategoria = buscarValidar(id);
        return new MetaCategoriaDTO(metaCategoria);
    }

    public MetaCategoria buscarValidar(Long id) {
        return metaCategoriaRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        messages.getAndReplace("entidade.nao.encontrada", METACATEGORIA)));
    }

    @Transactional(rollbackFor = Throwable.class, propagation = Propagation.REQUIRES_NEW)
    public String salvar(MetaCategoriaDTO dto) {
        MetaCategoria metaCategoria = new MetaCategoria();
        metaCategoria.transformer(dto, categoriaRepository
                .findById(dto.getCategoria().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        messages.getAndReplace("entidade.nao.encontrada", METACATEGORIA))));
        metaCategoriaRepository.save(metaCategoria);

        return messages.getAndReplace("entidade.salva", METACATEGORIA);
    }

    @Transactional(rollbackFor = Throwable.class, propagation = Propagation.REQUIRES_NEW)
    public String atualizar(MetaCategoriaDTO dto) {
        MetaCategoria metaCategoria = buscarValidar(dto.getId());
        metaCategoria.transformer(dto, categoriaRepository
                .findById(dto.getCategoria().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        messages.getAndReplace("entidade.nao.encontrada", METACATEGORIA))));
        metaCategoriaRepository.save(metaCategoria);

        return messages.getAndReplace("entidade.atualizada", METACATEGORIA);
    }

    @Transactional(rollbackFor = Throwable.class, propagation = Propagation.REQUIRES_NEW)
    public String deletar(Long id) {
        MetaCategoria metaCategoria = buscarValidar(id);
        metaCategoriaRepository.delete(metaCategoria);

        return messages.getAndReplace("entidade.deletada", METACATEGORIA);
    }
}
