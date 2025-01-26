package com.example.demo.dataGenerator;

import java.io.ObjectInputFilter.Status;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.example.demo.dataGenerator.camera.Camera;
import com.example.demo.dataGenerator.camera.StatusVideo;
import com.example.demo.dataGenerator.nmeaSentences.hdt.HdtGenerator;
import com.example.demo.dataGenerator.nmeaSentences.mwv.MwvGenerator;
import com.example.demo.dataGenerator.nmeaSentences.rmc.RmcGenerator;
import com.example.demo.dataGenerator.radar.RadarGenerator;
import com.example.demo.dataGenerator.rov.RovSentence;

@Service
public class DataGeneratorService {
    public Map<String, String> generateData(boolean correct)
    {
        MwvGenerator mwv = new MwvGenerator();
        RmcGenerator rmc = new RmcGenerator();
        HdtGenerator hdt = new HdtGenerator();
        RovSentence rov = new RovSentence();
        StatusVideo video = new StatusVideo();
        RadarGenerator radar = new RadarGenerator();
        Camera camera = new Camera();

        mwv.generateSentence(correct);
        rmc.generateSentence(correct);
        hdt.generateSentence(correct);
        radar.geraDadosRadar(correct);
        rov.generateRandomSentence(correct);

        String windSentence = mwv.getSentence();
        String windTimestamp = Long.toString(mwv.getTimestamp());
        String gnssSentence = rmc.getSentence();
        String gnssTimestamp = Long.toString(rmc.getTimestamp());
        String gyroSentence = hdt.getSentence();
        String gyroTimestamp = Long.toString(hdt.getTimestamp());
        String rovSentence = rov.getSentence();
        String rovTimestamp = Long.toString(rov.getTimeStamp());
        String video_status = video.generateStatusVideo(correct);
        String timestampFileARadar = Long.toString(radar.getArquivoA_timestamp());
        String timestampFileBRadar = Long.toString(radar.getArquivoB_timestamp());
        String timestampFileCRadar = Long.toString(radar.getArquivoC_timestamp());
        String softwareRadar = radar.getSoftware_radar();
        String software_radar_timestamp = Long.toString(radar.getSoftware_radar_timestamp());
        String pingMirosComputer = radar.getPing_maquina_miros();
        String pingCamera = camera.ping("192.168.115.41", correct); //Esse da erro
        String servicoVideo =  video.generateStatusVideo(correct);

        Map<String, String> json = new HashMap<>();
        json.put("sentencaWind", windSentence);
        json.put("sentencaGnss", gnssSentence);
        json.put("sentencaGyro", gyroSentence);
        json.put("sentencaWindTimestamp", windTimestamp);
        json.put("sentencaGnssTimestamp", gnssTimestamp);
        json.put("sentencaGyroTimestamp", gyroTimestamp);
        json.put("sentencaRov", rovSentence);
        json.put("sentencaRovTimestamp", rovTimestamp);
        json.put("servicoVideo", video_status);
        json.put("radarTimestampArquivoA", timestampFileARadar);
        json.put("radarTimestampArquivoB", timestampFileBRadar);
        json.put("radarTimestampArquivoC", timestampFileCRadar);
        json.put("softwareRadar", softwareRadar);
        json.put("softwareRadarTimestamp", software_radar_timestamp);
        json.put("pingComputadorMiros", pingMirosComputer);
        json.put("camera", pingCamera);
        json.put("video", servicoVideo);
        

        return json;
    }
}
