package com.example.demo.dataVerification;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.List;
import java.util.Calendar;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.example.demo.dataVerification.cameraVerification.CameraPingValidator;
import com.example.demo.dataVerification.cameraVerification.StatusVideoValidator;
import com.example.demo.dataVerification.nmeaSentences.hdt.HdtVerification;
import com.example.demo.dataVerification.nmeaSentences.mwv.MwvVerification;
import com.example.demo.dataVerification.nmeaSentences.rmc.RmcVerification;
import com.example.demo.dataVerification.radarVerification.radarValidator;
import com.example.demo.dataVerification.rov.ValidadeRovSentences;
import com.example.demo.databaseTables.StatusRepository;

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

    @Autowired
    public DataVerificationService(StatusRepository statusRepository, OrganizacaoRepository organizacaoRepository, EmbarcacaoRepository embarcacaoRepository) {
        this.statusRepository = statusRepository;
        this.organizacaoRepository = organizacaoRepository;
        this.embarcacaoRepository = embarcacaoRepository;
    }

    public Status checkData(String shipName) throws MalformedURLException, IOException {

        //chama o serviço de geração de dados
        RestClient client = RestClient.create();
        Map<String, String> json;
        if(shipName.equals("Loreto"))
        {
            json = client.get().uri("http://localhost:8080/api/v1/dataGenerator/1").retrieve().body(Map.class);
        }
        else
        {
            json = client.get().uri("http://localhost:8080/api/v1/dataGenerator/0").retrieve().body(Map.class);
        }
        //Coleta dados de vento gerados
        String windSentence = json.get("sentencaWind");
        long windTimestamp = Long.parseLong(json.get("sentencaWindTimestamp"));
        String gnssSentence = json.get("sentencaGnss");
        long gnssTimestamp = Long.parseLong(json.get("sentencaGnssTimestamp"));
        String gyroSentence = json.get("sentencaGyro");
        long gyroTimestamp = Long.parseLong(json.get("sentencaGyroTimestamp"));

        //Coleta dados do ROV
        String rovSentence = json.get("sentencaRov");
        long rovSentenceTimestamp = Long.parseLong(json.get("sentencaRovTimestamp"));

        //Coleta dados do radar
        long radarTimestampArquivoA = Long.parseLong(json.get("radarTimestampArquivoA"));
        long radarTimestampArquivoB = Long.parseLong(json.get("radarTimestampArquivoB"));
        long radarTimestampArquivoC = Long.parseLong(json.get("radarTimestampArquivoC"));
        String softwareRadar = json.get("softwareRadar");
        long softwareRadarTimestamp = Long.parseLong(json.get("softwareRadarTimestamp"));
        String pingComputadorMiros = json.get("pingComputadorMiros");
        String camera = json.get("camera");
        String video = json.get("video");

        //Verificação de todos os dados gerados
        String wind_status = MwvVerification.isMwvSentenceValid(windSentence, windTimestamp);
        String gnss_status = RmcVerification.isRmcSentenceValid(gnssSentence, gnssTimestamp);
        String gyro_status = HdtVerification.isHdtSentenceValid(gyroSentence, gyroTimestamp);
        String rov_status = ValidadeRovSentences.isValidSentence(rovSentence, rovSentenceTimestamp);
        String radar_status = radarValidator.verificaFormatoCorreto(radarTimestampArquivoA, radarTimestampArquivoB, radarTimestampArquivoC, softwareRadar, softwareRadarTimestamp, pingComputadorMiros);
        String camera_status = CameraPingValidator.verificaPingCamera(camera);
        String video_status = StatusVideoValidator.validateStatusVideo(video);
        //String radar_status = radarValidator.verificaFormatoCorreto()
        
        Calendar instancia = Calendar.getInstance();
        Date data_atual = instancia.getTime();
        
        Organizacao queryOrg = organizacaoRepository.findById(1);
        if (queryOrg == null) {
            queryOrg = new Organizacao("OceanPact");
            organizacaoRepository.updateOrInsert(queryOrg);
        }

        Embarcacao embarcacao = createShip(shipName, queryOrg);
        List<Embarcacao> queryEmbarcacao = embarcacaoRepository.findByMmsi(embarcacao.getMmsi());
        if (queryEmbarcacao.size() == 0){
            embarcacaoRepository.updateOrInsert(embarcacao);
        }
        else
        {
            embarcacao = queryEmbarcacao.get(0);
        }

        Status status_atual = new Status(embarcacao, wind_status, gnss_status, gyro_status, rov_status, radar_status, camera_status, video_status, null, data_atual);
        return statusRepository.updateOrInsert(status_atual);
    }

    private Embarcacao createShip(String shipName, Organizacao org)
    {
        System.out.println(shipName);
        Calendar inicioContrato = Calendar.getInstance();
        Calendar fimContrato = Calendar.getInstance();
        Date inicioContratoFormato = inicioContrato.getTime();
        Date fimContratoFormato = fimContrato.getTime();
        String mmsi;
        if(shipName.equals("IlhaSantana"))
        {
            mmsi = "123456789";
            shipName = "Ilha de Santana";
            inicioContrato.set(Calendar.YEAR, 2023);
            inicioContrato.set(Calendar.MONTH, Calendar.MARCH);
            inicioContrato.set(Calendar.DAY_OF_MONTH, 20);

            fimContrato.set(Calendar.YEAR, 2025);
            fimContrato.set(Calendar.MONTH, Calendar.FEBRUARY);
            fimContrato.set(Calendar.DAY_OF_MONTH, 20);
        }
        else if(shipName.equals("IlhaFlechas"))
        {
            mmsi = "987654321";
            shipName = "Ilha das Flechas";

            inicioContrato.set(Calendar.YEAR, 2023);
            inicioContrato.set(Calendar.MONTH, Calendar.MARCH);
            inicioContrato.set(Calendar.DAY_OF_MONTH, 20);

            fimContrato.set(Calendar.YEAR, 2025);
            fimContrato.set(Calendar.MONTH, Calendar.APRIL);
            fimContrato.set(Calendar.DAY_OF_MONTH, 20);
        }
        else
        {
            System.out.println(shipName);
            mmsi = "543219876";
            shipName = "NS Loreto";

            inicioContrato.set(Calendar.YEAR, 2023);
            inicioContrato.set(Calendar.MONTH, Calendar.MARCH);
            inicioContrato.set(Calendar.DAY_OF_MONTH, 20);

            fimContrato.set(Calendar.YEAR, 2025);
            fimContrato.set(Calendar.MONTH, Calendar.MARCH);
            fimContrato.set(Calendar.DAY_OF_MONTH, 20);
        }
        System.out.println(shipName);
        Embarcacao embarcacao = new Embarcacao(org, mmsi, shipName, inicioContratoFormato, fimContratoFormato);
        return embarcacao;
    }
}
