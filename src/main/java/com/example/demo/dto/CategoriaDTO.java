package com.example.demo.dto;

import com.example.demo.entity.Categoria;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaDTO {

    private Long id;
    private String nome;

    public CategoriaDTO(Categoria categoria) {
        if (categoria == null) return;

        setId(categoria.getId());
        setNome(categoria.getNome());
    }
}
