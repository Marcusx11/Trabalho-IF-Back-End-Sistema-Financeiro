package com.example.demo.repository;

import com.example.demo.entity.Categoria;
import com.example.demo.entity.MetaCategoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MetaCategoriaRepository extends JpaRepository<MetaCategoria, Long> {

    MetaCategoria findByCategoria(Categoria categoria);
}
