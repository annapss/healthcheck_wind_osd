package com.example.demo.relatorioGenerator;

import java.util.stream.Collectors;
import java.util.*;
import com.example.demo.databaseTables.RelatorioRepository;
import com.example.demo.databaseTables.Status;
import com.example.demo.databaseTables.StatusRepository;
import org.springframework.stereotype.Service;

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
        Date fiveMinutesAgo = new Date(currentDate.getTime() - 3000000);
        List<Status> statuses = statusRepository.findByDataAfter(fiveMinutesAgo);

        // Mapa final do relatorio
        Map<String, Map<String, Map<String, Double>>> relatorio = new HashMap<>();

        // Processa os dados para agrupar por nome embarcacao e calcular porcentagens
        statuses.forEach(status -> {
            String nomeEmbarcacao = status.getEmbarcacao().getNome();

            // Inicializa as embarcacoes, se nao existirem
            relatorio.putIfAbsent(nomeEmbarcacao, new HashMap<>());

            processarErro(relatorio.get(nomeEmbarcacao), "MWV", status.getSentenca_mwv());
            processarErro(relatorio.get(nomeEmbarcacao), "RMC", status.getSentenca_rmc());
        });

        // Calcula porcentagens
        relatorio.forEach((embarcacao, sentencas) -> {
            int totalSentencas = statuses.size();
            sentencas.forEach((sentenca, qtdErros) -> {
                qtdErros.replaceAll((erro, count) -> {
                    double porcentagem = (count / (double) totalSentencas) * 100;
                    // Formata a porcentagem para ter 1 casa decimal
                    // Substituir vírgula por ponto antes de converter porque tava dando erro
                    String porcentagemStr = String.format("%.1f", porcentagem).replace(",", ".");
                    return Double.valueOf(porcentagemStr);
                });
            });
        });

        return relatorio;
    }

    public Map<String, Map<String, Map<String, Double>>> getRelatorioCameraRadarRov(Date currentDate) {
        // Pega dados dos últimos 5 minutos
        Date fiveMinutesAgo = new Date(currentDate.getTime() - 3000000);
        List<Status> statuses = statusRepository.findByDataAfter(fiveMinutesAgo);
    
        // Mapa final do relatorio
        Map<String, Map<String, Map<String, Double>>> relatorio = new HashMap<>();
    
        // Processa os dados para agrupar por nome embarcacao e calcular porcentagens
        statuses.forEach(status -> {
            String nomeEmbarcacao = status.getEmbarcacao().getNome();
    
            // Inicializa as embarcacoes, se nao existirem
            relatorio.putIfAbsent(nomeEmbarcacao, new HashMap<>());
    
            processarErro(relatorio.get(nomeEmbarcacao), "Radar", status.getRadar());
            processarErro(relatorio.get(nomeEmbarcacao), "ROV", status.getSentenca_rov());
            processarErro(relatorio.get(nomeEmbarcacao), "Camera", status.getCamera());
            processarErro(relatorio.get(nomeEmbarcacao), "StatusVideo", status.getServico_video());
        });
    
        // Calcula porcentagens
        relatorio.forEach((embarcacao, sentencas) -> {
            int totalSentencas = statuses.size();
            sentencas.forEach((sentenca, qtdErros) -> {
                qtdErros.replaceAll((erro, count) -> {
                    double porcentagem = (count / (double) totalSentencas) * 100;
                    // Formata a porcentagem para ter 1 casa decimal
                    // Substituir vírgula por ponto antes de converter porque tava dando erro
                    String porcentagemStr = String.format("%.1f", porcentagem).replace(",", ".");
                    return Double.valueOf(porcentagemStr);
                });
            });
        });
    
        return relatorio;
    }

    public String formatarRelatorio(Map<String, Map<String, Map<String, Double>>> relatorio) {
        StringBuilder sb = new StringBuilder();
        relatorio.forEach((embarcacao, sentencas) -> {
            sb.append("Embarcação: ").append(embarcacao).append("\n");
            sentencas.forEach((tipoSentenca, erros) -> {
                sb.append("  ").append(tipoSentenca).append(":\n");
                erros.forEach((erro, porcentagem) -> {
                    sb.append("    ").append(erro).append(": ").append(porcentagem).append("%\n");
                });
            });
            sb.append("\n");
        });
        return sb.toString();
    }

private void processarErro(Map<String, Map<String, Double>> embarcacao, String tipoSentenca, String sentenca) {
    // Ignora valores nulos ou vazios
    if (sentenca == null || sentenca.isEmpty()) return;

    // Inicializa o result da sentenca, se nao existir
    embarcacao.putIfAbsent(tipoSentenca, new HashMap<>());

    // Incrementa a contagem do erro
    if (sentenca.contains("ERRO")) {
        embarcacao.get(tipoSentenca).merge(sentenca, 1.0, Double::sum);
    }
}
}
