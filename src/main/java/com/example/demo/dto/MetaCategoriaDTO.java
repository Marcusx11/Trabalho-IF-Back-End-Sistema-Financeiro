package com.example.demo.dto;

import com.example.demo.entity.MetaCategoria;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MetaCategoriaDTO {

    private Long id;
    private Double limite;
    private Boolean controle;

    public MetaCategoriaDTO(MetaCategoria metaCategoria) {
        if (metaCategoria == null) return;

        setId(metaCategoria.getId());
        setLimite(metaCategoria.getLimite());
        setControle(metaCategoria.getControle());
    }
}
