package com.example.demo.entity;

import com.example.demo.dto.CategoriaDTO;
import com.example.demo.dto.FaturaDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "categoria")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Transient
    public void transformer(CategoriaDTO dto) {
        setNome(dto.getNome());
    }
}
