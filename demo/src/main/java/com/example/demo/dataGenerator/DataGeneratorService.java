package com.example.demo.dataGenerator;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.example.demo.dataGenerator.nmeaSentences.hdt.HdtGenerator;
import com.example.demo.dataGenerator.nmeaSentences.mwv.MwvGenerator;
import com.example.demo.dataGenerator.nmeaSentences.rmc.RmcGenerator;

@Service
public class DataGeneratorService {
    public Map<String, String> generateData()
    {
        MwvGenerator mwv = new MwvGenerator();
        RmcGenerator rmc = new RmcGenerator();
        HdtGenerator hdt = new HdtGenerator();
        mwv.generateSentence();
        rmc.generateSentence();
        hdt.generateSentence();
        String windSentence = mwv.getSentence();
        String windTimestamp = Long.toString(mwv.getTimestamp());
        String gnssSentence = rmc.getSentence();
        String gnssTimestamp = Long.toString(rmc.getTimestamp());
        String gyroSentence = hdt.getSentence();
        String gyroTimestamp = Long.toString(hdt.getTimestamp());
         

        Random random = new Random();
        long numberMmsi = 100000000 + random.nextInt(900000000);
        String mmsi = String.valueOf(numberMmsi);

        Map<String, String> json = new HashMap<>();
        json.put("mmsi", mmsi);
        json.put("sentencaWind", windSentence);
        json.put("sentencaGnss", gnssSentence);
        json.put("sentencaGyro", gyroSentence);
        json.put("sentencaWindTimestamp", windTimestamp);
        json.put("sentencaGnssTimestamp", gnssTimestamp);
        json.put("sentencaGyroTimestamp", gyroTimestamp);

        /*String json = "{"
        + "\"mmsi\":\"" + mmsi + "\","
        + "\"sentencaWind\":\"" + windSentence + "\","
        + "\"sentencaGnss\":\"" + gnssSentence + "\","
        + "\"sentencaGyro\":\"" + gyroSentence + "\","
        + "\"sentencaWindTimestamp\":\"" + windTimestamp + "\","
        + "\"sentencaGnssTimestamp\":\"" + gnssTimestamp + "\","
        + "\"sentencaGyroTimestamp\":\"" + gyroTimestamp + "\""
        + "}";*/
        // Retorna o JSON
        return json;
    }
}
