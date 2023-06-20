package com.example.demo.service;

import com.example.demo.dto.FaturaDTO;
import com.example.demo.entity.Fatura;
import com.example.demo.repository.FaturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FaturaService {

    @Autowired
    private FaturaRepository repository;

    public List<FaturaDTO> listar() {
        List<Fatura> entidades = repository.findAll();

        return entidades.stream().map(FaturaDTO::new).collect(Collectors.toList());
    }
}
