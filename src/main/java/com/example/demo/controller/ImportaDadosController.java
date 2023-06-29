package com.example.demo.controller;

import com.example.demo.utility.Extrato;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/sistema/importa-dados")
@RestController
@RequiredArgsConstructor
public class ImportaDadosController {

    @PostMapping(value = "/extrato-xml", consumes = MediaType.APPLICATION_XML_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity importaXml(@RequestBody String textoDoExtrato) {
        Extrato extrato;
        try {
            extrato = new Extrato(textoDoExtrato);
            extrato.importarDados();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Não foi possível importar o XML informado...");
        }

        List<String> datas = extrato.getDatas();
        List<String> descricoes = extrato.getDescricoes();
        List<String> valores = extrato.getValores();

        return ResponseEntity.ok("XML importado com sucesso!");
    }
}
