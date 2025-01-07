package com.example.demo.dataVerification;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DateFormat;
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
	public DataVerificationService(StatusRepository statusRepository, OrganizacaoRepository organizacaoRepository, EmbarcacaoRepository embarcacaoRepository)
	{
		this.statusRepository = statusRepository;
		this.organizacaoRepository = organizacaoRepository;
		this.embarcacaoRepository = embarcacaoRepository;
	}

    public Status checkData() throws MalformedURLException, IOException
    {
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
		
		Organizacao nova_organizacao = new Organizacao("OceanPact");
		organizacaoRepository.updateOrInsert(nova_organizacao);

		Embarcacao nova_embarcacao = new Embarcacao(nova_organizacao, mmsi, "ABC", null, null);
		embarcacaoRepository.updateOrInsert(nova_embarcacao);

		Status status_atual = new Status(nova_embarcacao, wind_status, gnss_status, gyro_status, null, null, null, null, null, data_atual);
		return statusRepository.updateOrInsert(status_atual);
    }
}
