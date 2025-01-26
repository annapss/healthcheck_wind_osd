package com.example.demo.dataVerification.cameraVerification;

public class CameraPingValidator {
    public static String verificaPingCamera(String pingOutput) {
        // Verifica se o ping foi bem-sucedido
        boolean pingMalSucedido = pingOutput.contains("0 received");
        
        if (pingMalSucedido) {
            return "ERRO- Falha no ping para a câmera";
        } else {
            return "OK";
        }
    }  
}
