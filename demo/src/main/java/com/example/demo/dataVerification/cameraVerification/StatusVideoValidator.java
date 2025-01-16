package com.example.demo.dataVerification.cameraVerification;
import com.example.demo.dataGenerator.camera.StatusVideo;

public class StatusVideoValidator {
    public String validateStatusVideo(String statusVideo){
        if (!statusVideo.contains("Active")) {
            return "Serviço de video inativo";
        }
        return "Serviço de video ok";
    }
    public static void main(String[] args) {
        StatusVideoValidator statusVideoValidator = new StatusVideoValidator();
        StatusVideo statusVideo = new StatusVideo();
        String videoService = statusVideo.generateStatusVideo();
        System.out.println(statusVideoValidator.validateStatusVideo(videoService));
    }
}
