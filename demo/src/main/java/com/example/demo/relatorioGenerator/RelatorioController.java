package com.example.demo.relatorioGenerator;

import com.example.demo.databaseTables.Relatorio;
import com.example.demo.databaseTables.Status;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "api/v1/relatorios")
public class RelatorioController {

    private final RelatorioService relatorioService;

    public RelatorioController(RelatorioService relatorioService) {
        this.relatorioService = relatorioService;
    }

    @GetMapping
    public Map<String, Map<String, Map<String, Double>>> getRelatorios() {
        Date currentDate = new Date();
        return relatorioService.getRelatorio(currentDate);
    }
}
