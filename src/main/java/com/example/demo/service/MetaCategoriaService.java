package com.example.demo.service;

import com.example.demo.component.Messages;
import com.example.demo.dto.CategoriaDTO;
import com.example.demo.dto.FaturaDTO;
import com.example.demo.dto.MetaCategoriaDTO;
import com.example.demo.entity.Categoria;
import com.example.demo.entity.Fatura;
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
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MetaCategoriaService extends BaseService {

    @Autowired
    private final MetaCategoriaRepository metaCategoriaRepository;
    private static final String METACATEGORIA = "MetaCategoria";

    @Autowired
    private final CategoriaRepository categoriaRepository;

    public void verificaLimite(List<FaturaDTO> faturas, FaturaDTO dto, Categoria cat) {
        Categoria categoria = new Categoria();
        float somaFaturasDeMetaCategoria = 0.0F;
        for (FaturaDTO faturaDTO : faturas) {
            categoria.setId(faturaDTO.getCategoria().getId());
            categoria.setNome(faturaDTO.getCategoria().getNome());
            if (categoria.equals(cat)) {
                somaFaturasDeMetaCategoria += faturaDTO.getValorTotal()/faturaDTO.getParcelas();
            }
        }
        for (MetaCategoriaDTO metaCategoriaDTO: this.listar()) {
            categoria.setId(metaCategoriaDTO.getCategoria().getId());
            categoria.setNome(metaCategoriaDTO.getCategoria().getNome());
            if (categoria.equals(cat)) {
                if ((somaFaturasDeMetaCategoria + (dto.getValorTotal()/ dto.getParcelas())) > metaCategoriaDTO.getLimite()) {
                    metaCategoriaDTO.setControle(false); // false indica que o limite foi excedido com a fatura recem inserida
                    this.atualizar(metaCategoriaDTO);
                }
            }
        }
    }

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

    public Double filtrarOrcamento() {
        return metaCategoriaRepository.findAll().stream().map(MetaCategoria::getLimite).reduce(0.0, Double::sum);
    }

    public Double filtrarOrcamentoPorCategoria(Long id) {
        Optional<Categoria> categoria = categoriaRepository.findById(id);
        return categoria.map(value -> metaCategoriaRepository.findAll().stream().filter(metaCategoria -> metaCategoria.getCategoria() == value).map(MetaCategoria::getLimite).reduce(0.0, Double::sum)).orElse(0.0);
    }
}
