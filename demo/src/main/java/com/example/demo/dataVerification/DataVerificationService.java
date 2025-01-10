package com.example.demo.dataVerification;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.Calendar;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.example.demo.dataVerification.nmeaSentences.hdt.HdtVerification;
import com.example.demo.dataVerification.nmeaSentences.mwv.MwvVerification;
import com.example.demo.dataVerification.nmeaSentences.rmc.RmcVerification;
import com.example.demo.databaseTables.StatusRepository;
import com.example.demo.databaseTables.Relatorio;
import com.example.demo.databaseTables.RelatorioRepository;

import com.example.demo.databaseTables.Embarcacao;
import com.example.demo.databaseTables.EmbarcacaoRepository;
import com.example.demo.databaseTables.Organizacao;
import com.example.demo.databaseTables.OrganizacaoRepository;
import com.example.demo.databaseTables.Status;

@Service
public class DataVerificationService {

    private final StatusRepository statusRepository;
    private final OrganizacaoRepository organizacaoRepository;
    private final EmbarcacaoRepository embarcacaoRepository;
    private final RelatorioRepository relatorioRepository;

    @Autowired
    public DataVerificationService(StatusRepository statusRepository, OrganizacaoRepository organizacaoRepository, EmbarcacaoRepository embarcacaoRepository, RelatorioRepository relatorioRepository) {
        this.statusRepository = statusRepository;
        this.organizacaoRepository = organizacaoRepository;
        this.embarcacaoRepository = embarcacaoRepository;
        this.relatorioRepository = relatorioRepository;
    }

    public Status checkData() throws MalformedURLException, IOException {
        System.out.println("testeeeeeeeeeeee");
        //chama o serviço de geração de dados
        RestClient client = RestClient.create();
        Map<String, String> json = client.get().uri("http://localhost:8080/api/v1/dataGenerator").retrieve().body(Map.class);
        String mmsi = json.get("mmsi");

        String windSentence = json.get("sentencaWind");
        long windTimestamp = Long.parseLong(json.get("sentencaWindTimestamp"));
        String gnssSentence = json.get("sentencaGnss");
        long gnssTimestamp = Long.parseLong(json.get("sentencaGnssTimestamp"));
        String gyroSentence = json.get("sentencaGyro");
        long gyroTimestamp = Long.parseLong(json.get("sentencaGyroTimestamp"));

        String wind_status = MwvVerification.isMwvSentenceValid(windSentence, windTimestamp);
        String gnss_status = RmcVerification.isRmcSentenceValid(gnssSentence, gnssTimestamp);
        String gyro_status = HdtVerification.isHdtSentenceValid(gyroSentence, gyroTimestamp);
        Calendar instancia = Calendar.getInstance();
        Date data_atual = instancia.getTime();
        //DateFormat formataData = DateFormat.getInstance();
        //String data_atual_str = formataData.format(data_atual);
        //TODO - falta colocar essas informações no banco junto com o mmsi. Elas vao ser inseridas na tabela de Status

        Organizacao org = organizacaoRepository.findById(1);
        Embarcacao embarcacao = embarcacaoRepository.findById(1);

		if (org == null) {
            org = new Organizacao("OceanPact");
            organizacaoRepository.updateOrInsert(org);
        }
        if (embarcacao == null) {
            embarcacao = new Embarcacao(org, mmsi, "SkySea Fishing", null, null);
            embarcacaoRepository.updateOrInsert(embarcacao);
        }
        System.out.println(embarcacao);
        String descricao = "";
        if(!wind_status.equals("OK")){
            descricao += wind_status + "\n";
        }
        if(!gnss_status.equals("OK")){
            descricao += gnss_status + "\n";
        }
        if(!gyro_status.equals("OK")){
            descricao += gyro_status + "\n";
        }
        
        Relatorio novoRelatorio = new Relatorio(data_atual, descricao, embarcacao, "NAVTI");
        System.out.println(novoRelatorio);
        if (!descricao.equals("")) relatorioRepository.updateOrInsert(novoRelatorio);

        Status status_atual = new Status(embarcacao, wind_status, gnss_status, gyro_status, null, null, null, null, null, data_atual);
        return statusRepository.updateOrInsert(status_atual);
    }
}
