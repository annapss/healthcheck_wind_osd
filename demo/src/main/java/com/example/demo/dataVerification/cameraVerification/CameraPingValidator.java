package com.example.demo.dataVerification.cameraVerification;
import com.example.demo.dataGenerator.camera.Camera;

public class CameraPingValidator {
    public String verificaPingCamera(String ip) {
        Camera camera = new Camera();
        String pingOutput = camera.ping(ip);

        // Verifica se o ping foi bem-sucedido
        boolean pingMalSucedido = pingOutput.contains("0 received");
        

        if (pingMalSucedido) {
            return "Falha no ping para a câmera IP: " + ip;
        } else {
            return "Ping bem-sucedido para a câmera IP: " + ip;
        }
    }  
    public static void main(String[] args) {
        CameraPingValidator cameraPingValidator = new CameraPingValidator();
        String[] ip = Camera.generateIPs(1);
        System.out.println(cameraPingValidator.verificaPingCamera(ip[0]));
    }
}
