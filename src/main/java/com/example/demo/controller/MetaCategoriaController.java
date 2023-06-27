package com.example.demo.controller;

import com.example.demo.dto.CategoriaDTO;
import com.example.demo.dto.MetaCategoriaDTO;
import com.example.demo.entity.Categoria;
import com.example.demo.service.MetaCategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/metacategorias")
@RestController
@RequiredArgsConstructor
public class MetaCategoriaController {
    @Autowired
    private MetaCategoriaService service;

    @GetMapping("/listar")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<MetaCategoriaDTO> listar() {
        return service.listar();
    }

    @GetMapping("/buscar-por-id/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public MetaCategoriaDTO retornarPorId(@PathVariable(name = "id") Long id) {
        return service.retornarPorId(id);
    }

    @GetMapping("/filtrar-orcamento")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Double filtrarOrcamento() {
        return service.filtrarOrcamento();
    }

    @GetMapping("/filtrar-orcamento-por-categoria/{categoria_id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Double filtrarOrcamentoPorCategoria(@PathVariable(name = "categoria_id") Long id) {
        return service.filtrarOrcamentoPorCategoria(id);
    }

    @PostMapping(value = "/salvar", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public String salvar(@RequestBody MetaCategoriaDTO dto) {
        return service.salvar(dto);
    }

    @PutMapping(value = "/atualizar", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String atualizar(@RequestBody MetaCategoriaDTO dto) {
        return service.atualizar(dto);
    }

    @DeleteMapping(value = "/deletar/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String deletar(@PathVariable(name = "id") Long id) {
        return service.deletar(id);
    }
}
