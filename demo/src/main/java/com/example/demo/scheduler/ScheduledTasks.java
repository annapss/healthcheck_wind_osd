package com.example.demo.scheduler;

import com.example.demo.relatorioGenerator.RelatorioService;
import com.example.demo.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public class ScheduledTasks {

    @Autowired
    private RelatorioService relatorioService;

    @Autowired
    private EmailService emailService;

    @Scheduled(cron = "0 35 15 * * ?") // Todos os dias às 8h
    public void sendRelatorioGeral() {
        Date currentDate = new Date();
        Map<String, Map<String, Map<String, Double>>> relatorio = relatorioService.getRelatorio(currentDate);
        String emailContent = relatorioService.formatarRelatorio(relatorio); // Formate o conteúdo do e-mail
        emailService.sendEmail("leopranzl07@gmail.com", "Relatório Geral Diário", emailContent);
    }

    @Scheduled(cron = "0 35 15 * * ?") // Todos os dias às 9h
    public void sendRelatorioCameraRadarRov() {
        Date currentDate = new Date();
        Map<String, Map<String, Map<String, Double>>> relatorio = relatorioService.getRelatorioCameraRadarRov(currentDate);
        String emailContent = relatorioService.formatarRelatorio(relatorio); // Formate o conteúdo do e-mail
        emailService.sendEmail("leopranzl07@gmail.com", "Relatório Camera Radar ROV Diário", emailContent);
    }
}