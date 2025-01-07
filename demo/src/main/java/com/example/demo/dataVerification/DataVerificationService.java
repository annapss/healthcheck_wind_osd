package com.example.demo.dataVerification;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.example.demo.dataVerification.nmeaSentences.mwv.MwvVerification;
import com.example.demo.dataVerification.nmeaSentences.rmc.RmcVerification;

@Service
public class DataVerificationService {
    public String checkData() throws MalformedURLException, IOException
    {
		RestClient client = RestClient.create();
		Map<String, String> json = client.get().uri("http://localhost:8080/api/v1/dataGenerator").retrieve().body(Map.class);
		String windSentence = json.get("sentencaWind");
		long windTimestamp = Long.parseLong(json.get("sentencaWindTimestamp"));
		String gnssSentence = json.get("sentencaGnss");
		long gnssTimestamp = Long.parseLong(json.get("sentencaGnssTimestamp"));
		String gyroSentence = json.get("sentencaGyro");
		long gyroTimestamp = Long.parseLong(json.get("sentencaGyroTimestamp"));

		String wind_status = MwvVerification.isMwvSentenceValid(windSentence, windTimestamp);
		String gnss_status = RmcVerification.isRmcSentenceValid(gnssSentence, gnssTimestamp);
		String gyro_status = RmcVerification.isRmcSentenceValid(gyroSentence, gyroTimestamp);
		//TODO - falta colocar essas informações no banco junto com o mmsi. Elas vao ser inseridas na tabela de Status
		return "";
    }
}
