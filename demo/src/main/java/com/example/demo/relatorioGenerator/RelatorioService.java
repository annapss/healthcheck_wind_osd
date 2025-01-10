package com.example.demo.relatorioGenerator;

import java.util.stream.Collectors;
import java.util.*;
import com.example.demo.databaseTables.RelatorioRepository;
import com.example.demo.databaseTables.Status;
import com.example.demo.databaseTables.StatusRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class RelatorioService {

    private final RelatorioRepository relatorioRepository;
    private final StatusRepository statusRepository;

    public RelatorioService(RelatorioRepository relatorioRepository, StatusRepository statusRepository) {
        this.relatorioRepository = relatorioRepository;
        this.statusRepository = statusRepository;
    }

    public Map<String, Map<String, Map<String, Double>>> getRelatorio(Date currentDate) {
        // Pega dados dos últimos 5 minutos
        Date fiveMinutesAgo = new Date(currentDate.getTime() - 300000);
        List<Status> statuses = statusRepository.findByDataAfter(fiveMinutesAgo);

        // Mapa final do relatorio
        Map<String, Map<String, Map<String, Double>>> relatorio = new HashMap<>();

        // Processa os dados para agrupar por nome embarcacao e calcular porcentagens
        statuses.forEach(status -> {
            String nomeEmbarcacao = status.getEmbarcacao().getNome();

            // Inicializa as embarcacoes, se necessário
            relatorio.putIfAbsent(nomeEmbarcacao, new HashMap<>());

            processarErro(relatorio.get(nomeEmbarcacao), "MWV", status.getSentenca_mwv());
            processarErro(relatorio.get(nomeEmbarcacao), "RMC", status.getSentenca_rmc());
        });

        // Calcula porcentagens
        relatorio.forEach((embarcacao, sentencas) -> sentencas.forEach((sentenca, erros) -> {
            int totalErros = erros.values().stream().mapToInt(Double::intValue).sum();
            erros.replaceAll((erro, count) -> {
                double porcentagem = (count / totalErros) * 100;
                // Formata a porcentagem para ter 1 casa decimal
                // Substituir vírgula por ponto antes de converter porque tava dando erro
                String porcentagemStr = String.format("%.1f", porcentagem).replace(",", ".");
                return Double.valueOf(porcentagemStr);
            });
        }));


        return relatorio;
    }

    private void processarErro(Map<String, Map<String, Double>> sentencas, String tipoSentenca, String erro) {
        // Ignora valores nulos ou vazios
        if (erro == null || erro.isEmpty()) return;

        // Inicializa a contagem de erros para a sentença, se necessário
        sentencas.putIfAbsent(tipoSentenca, new HashMap<>());

        // Incrementa a contagem do erro
        sentencas.get(tipoSentenca).merge(erro, 1.0, Double::sum);
    }
}
