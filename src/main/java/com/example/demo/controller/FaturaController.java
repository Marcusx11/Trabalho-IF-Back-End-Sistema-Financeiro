package com.example.demo.controller;

import com.example.demo.dto.FaturaDTO;
import com.example.demo.service.FaturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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
}
