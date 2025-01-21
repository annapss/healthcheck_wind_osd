package com.example.demo.dataVerification.cameraVerification;

public class StatusVideoValidator {
    public static String validateStatusVideo(String statusVideo){
        if (!statusVideo.contains("Active")) {
            return "Serviço de video inativo";
        }
        return "OK";
    }
    /*public static void main(String[] args) {
        StatusVideoValidator statusVideoValidator = new StatusVideoValidator();
        StatusVideo statusVideo = new StatusVideo();
        String videoService = statusVideo.generateStatusVideo();
        System.out.println(statusVideoValidator.validateStatusVideo(videoService));
    }*/
}
