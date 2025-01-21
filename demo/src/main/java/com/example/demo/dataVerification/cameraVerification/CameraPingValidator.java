package com.example.demo.dataVerification.cameraVerification;
import com.example.demo.dataGenerator.camera.Camera;

public class CameraPingValidator {
    public static String verificaPingCamera(String ip) {
        Camera camera = new Camera();
        String pingOutput = camera.ping(ip);

        // Verifica se o ping foi bem-sucedido
        boolean pingMalSucedido = pingOutput.contains("0 received");
        

        if (pingMalSucedido) {
            return "Falha no ping para a câmera";
        } else {
            return "OK";
        }
    }  
}
