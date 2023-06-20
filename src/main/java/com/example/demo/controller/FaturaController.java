package com.example.demo.controller;

import com.example.demo.dto.FaturaDTO;
import com.example.demo.service.FaturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/faturas")
@RestController
public class FaturaController {

    @Autowired
    private FaturaService service;

    @GetMapping("/listar")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<FaturaDTO> listar() {
        return service.listar();
    }

    @GetMapping("/buscar-por-id/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public FaturaDTO retornarPorId(@PathVariable(name = "id") Long id) {
        return service.retornarPorId(id);
    }

    @PostMapping(value = "/salvar", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public String salvar(@RequestBody FaturaDTO dto) {
        return service.salvar(dto);
    }

    @PutMapping(value = "/atualizar", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String atualizar(@RequestBody FaturaDTO dto) {
        return service.atualizar(dto);
    }

    @DeleteMapping(value = "/deletar/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String deletar(@PathVariable(name = "id") Long id) {
        return service.deletar(id);
    }
}
