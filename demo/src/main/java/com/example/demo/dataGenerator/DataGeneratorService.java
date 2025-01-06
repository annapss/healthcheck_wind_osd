package com.example.demo.dataGenerator;

import java.util.Random;

import org.springframework.stereotype.Service;

import com.example.demo.dataGenerator.nmeaSentences.hdt.HdtGenerator;
import com.example.demo.dataGenerator.nmeaSentences.mwv.MwvGenerator;
import com.example.demo.dataGenerator.nmeaSentences.rmc.RmcGenerator;

@Service
public class DataGeneratorService {
    public String generateData()
    {
        MwvGenerator mwv = new MwvGenerator();
        RmcGenerator rmc = new RmcGenerator();
        HdtGenerator hdt = new HdtGenerator();
        mwv.generateSentence();
        rmc.generateSentence();
        hdt.generateSentence();
        String windSentence = mwv.getSentence();
        long windTimestamp = mwv.getTimestamp();
        String gnssSentence = rmc.getSentence();
        long gnssTimestamp = rmc.getTimestamp();
        String gyroSentence = hdt.getSentence();
        long gyroTimestamp = hdt.getTimestamp();
         

        Random random = new Random();
        long numberMmsi = 100000000 + random.nextInt(900000000);
        String mmsi = String.valueOf(numberMmsi);

        String json = "{"
        + "\"mmsi\":\"" + mmsi + "\","
        + "\"sentencaWind\":\"" + windSentence + "\","
        + "\"sentencaGnss\":\"" + gnssSentence + "\","
        + "\"sentencaGyro\":\"" + gyroSentence + "\","
        + "\"sentencaWindTimestamp\":\"" + windTimestamp + "\","
        + "\"sentencaGnssTimestamp\":\"" + gnssTimestamp + "\","
        + "\"sentencaGyroTimestamp\":\"" + gyroTimestamp + "\""
        + "}";
        // Retorna o JSON
        return json;
    }
}
