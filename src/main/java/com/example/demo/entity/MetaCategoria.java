package com.example.demo.entity;

import com.example.demo.dto.MetaCategoriaDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "meta_categoria")
public class MetaCategoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "limite")
    private Double limite;

    @Column(name = "controle")
    private Boolean controle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    @Transient
    public void transformer(MetaCategoriaDTO dto, Categoria categoria) {
        setLimite(dto.getLimite());
        setControle(dto.getControle());
        setCategoria(categoria);
    }

}
