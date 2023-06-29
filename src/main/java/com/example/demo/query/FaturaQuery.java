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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

    private static final DateFormat dbDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public List<Fatura> filtrarFaturas(FaturaDTO filtro) {
        switch (filtro.getFiltro()) {
            case FILTRO_PENDENTE:
                return repository.findAllByFaturadoEquals(Boolean.FALSE);
            case FILTRO_DATAS_PAGAMENTOS_PENDENTES:
                return encontrarFaturasPendentes(filtro.getDataPeriodoInicio(), filtro.getDataPeriodoFim());
            case FILTRO_PAGAMENTOS_EFETUADOS:
                return repository.encontrarPagamentosEfetuados();
            default:
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, messages.get("filtro.busca"));
        }
    }

    private List<Fatura> encontrarFaturasPendentes(Date dataPeriodoInicio, Date dataPeriodoFim) {
        String sql = " SELECT DISTINCT f FROM Fatura f LEFT JOIN f.transacoes t ON t.fatura = f ";
        String dataInicioDB = dateToStrDatabase(dataPeriodoInicio);
        String dataFimDB = dateToStrDatabase(dataPeriodoFim);

        sql += " WHERE f.faturado IS FALSE ";

        if (dataInicioDB != null && !dataInicioDB.equals("")) {
            sql += " AND ( t.dataVencimento >= '" + dataInicioDB + "' ";
            if (dataFimDB != null && !dataFimDB.equals("")) {
                sql += " AND t.dataVencimento <= '" + dataFimDB + "' ) ";
            } else {
                sql += " ) ";
            }
        } else if (dataFimDB != null && !dataFimDB.equals("")) {
            sql += " AND t.dataVencimento <= '" + dataFimDB + "' ";
        }

        return manager.createQuery(sql, Fatura.class).getResultList();
    }

    private String dateToStrDatabase(Date date){
        return date != null ? dbDateFormat.format(date) : null;
    }
}
