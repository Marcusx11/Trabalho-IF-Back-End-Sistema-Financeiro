package com.example.demo.query;

import com.example.demo.component.Messages;
import com.example.demo.dto.FaturaDTO;
import com.example.demo.entity.Fatura;
import com.example.demo.repository.FaturaRepository;
import com.example.demo.utility.enums.EnumFiltroFatura;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

@Repository
public class FaturaQuery {

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    private FaturaRepository repository;

    @Autowired
    private Messages messages;

    public List<Fatura> filtrarFaturas(FaturaDTO filtro) {
        switch (filtro.getFiltro()) {
            case FILTRO_PENDENTE:
                return repository.findAllByFaturadoEquals(Boolean.FALSE);
            case FILTRO_DATAS_PAGAMENTOS_PENDENTES:
                return repository.encontrarFaturasPendentes(filtro.getDataPeriodoInicio(), filtro.getDataPeriodoFim());
            case FILTRO_PAGAMENTOS_EFETUADOS:
                return repository.encontrarPagamentosEfetuados();
            default:
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, messages.get("filtro.busca"));
        }
    }

    private List<Fatura> encontrarFaturasPendentes(Date dataPeriodoInicio, Date dataPeriodoFim) {
        String sql = " SELECT f FROM Fatura f LEFT JOIN f.transacoes t ";

        if (dataPeriodoInicio != null && !dataPeriodoInicio.equals("")) {
            sql += " WHERE t.dataVencimento >= DATE(" + dataPeriodoInicio + ") ";
            if (dataPeriodoFim != null && !dataPeriodoFim.equals("")) {
                sql += " AND t.dataVencimento <= DATE(" + dataPeriodoFim + ") ";
            }
        } else if (dataPeriodoFim != null && !dataPeriodoFim.equals("")) {
            sql += " WHERE t.dataVencimento <= DATE(" + dataPeriodoFim + ") ";
        }

        return manager.createQuery(sql, Fatura.class).getResultList();
    }
}
