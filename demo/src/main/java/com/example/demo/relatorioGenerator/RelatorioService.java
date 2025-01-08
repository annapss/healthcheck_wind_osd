package com.example.demo.relatorioGenerator;

import com.example.demo.databaseTables.Relatorio;
import com.example.demo.databaseTables.RelatorioRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class RelatorioService {

    private final RelatorioRepository relatorioRepository;

    public RelatorioService(RelatorioRepository relatorioRepository) {
        this.relatorioRepository = relatorioRepository;
    }

    public List<Relatorio> getRelatorios(Date currentDate) {
        //pega relatorios dos ultimos 5 mins
        Date fiveMinutesAgo = new Date(currentDate.getTime() - 300000);

        return relatorioRepository.findByDataAfter(fiveMinutesAgo);
    }
}
