package com.example.demo.query;

import com.example.demo.component.Messages;
import com.example.demo.entity.Fatura;
import com.example.demo.repository.FaturaRepository;
import com.example.demo.utility.enums.EnumFiltroFatura;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class FaturaQuery {

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    private FaturaRepository repository;

    @Autowired
    private Messages messages;

    public List<Fatura> filtrarFaturas(EnumFiltroFatura filtro) {
        switch (filtro) {
            case FILTRO_PENDENTE:
                return repository.findAllByFaturadoEquals(Boolean.FALSE);
            case FILTRO_DATAS_PAGAMENTOS_PENDENTES:
                return repository.findAll();
            case FILTRO_PAGAMENTOS_EFETUADOS:
                return repository.encontrarPagamentosEfetuados();
            default:
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, messages.get("filtro.busca"));
        }
    }
}
