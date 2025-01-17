package com.example.demo.dataVerification.rov;

import java.util.Random;
import java.util.regex.Pattern;

import com.example.demo.dataGenerator.rov.RovSentence;

//TODO Deixar explicito qual erro que a sentenca apresenta
public class ValidadeRovSentences {

    // Padrões para cada campo da sentença
    private static final String VERSION_PATTERN = "1";
    private static final String DATE_TIME_PATTERN = "\\d{2}\\.\\d{2}\\.\\d{4} \\d{2}:\\d{2}:\\d{2}";
    private static final String LAT_LON_PATTERN = "-?\\d{1,3}\\.\\d{6}";
    private static final String DEGREE_PATTERN = "\\d{1,3}\\.\\d{2}";
    private static final String KNOTS_PATTERN = "\\d{1,2}\\.\\d{2}";
    private static final String METERS_PATTERN = "\\d{1,4}\\.\\d{2}";
    private static final String ANGLE_PATTERN = "-?\\d{1,2}\\.\\d{2}";
    private static final String DEPTH_TMS = "\\d{1,5}\\.\\d{2}";

    public static String isValidSentence(String sentence, long timestamp) {
        // Dividir a sentença pelos campos separados por vírgula
        String[] fields = sentence.split(",");

        // for(int i = 1; i < fields.length; i++){
        //     System.out.println(fields[i]);
        // }
        
        long tolerance = 30000;
        long now = System.currentTimeMillis();
        if (now - timestamp > tolerance) return "ERRO - Dado antigo";
        if (fields.length != 17) {
           
            return "ERRO - Numero errado de campos"; // Número errado de campos
        }
        
        try {
            

            // Verificar cada campo individualmente
            if (!fields[0].equals(VERSION_PATTERN)) return "ERRO - Formatação incorreta";
            // System.out.println(1);
            if (!Pattern.matches(DATE_TIME_PATTERN, fields[1])) return "ERRO - Formatação incorreta";
            // System.out.println(2);
            if (!Pattern.matches(LAT_LON_PATTERN, fields[2])) return "ERRO - Formatação incorreta"; // SHIP LAT
            // System.out.println(3);
            if (!Pattern.matches(LAT_LON_PATTERN, fields[3])) return "ERRO - Formatação incorreta"; // SHIP LONG
            // System.out.println(4);
            if (!Pattern.matches(DEGREE_PATTERN, fields[4])) return "ERRO - Formatação incorreta"; // SHIP GYRO
            // System.out.println(5);
            if (!Pattern.matches(DEGREE_PATTERN, fields[5])) return "ERRO - Formatação incorreta"; // SHIP COG
            // System.out.println(6);
            if (!Pattern.matches(KNOTS_PATTERN, fields[6])) return "ERRO - Formatação incorreta"; // SHIP SOG
            // System.out.println(7);
            if (!Pattern.matches(LAT_LON_PATTERN, fields[7])) return "ERRO - Formatação incorreta"; // LAT ROV
            // System.out.println(8);
            if (!Pattern.matches(LAT_LON_PATTERN, fields[8])) return "ERRO - Formatação incorreta"; // LONG ROV
            // System.out.println(9);
            if (!Pattern.matches(METERS_PATTERN, fields[9])) return "ERRO - Formatação incorreta"; // DEPTH ROV
            // System.out.println(10);
            if (!Pattern.matches(KNOTS_PATTERN, fields[10])) return "ERRO - Formatação incorreta"; // ALT ROV
            // System.out.println(11);
            if (!Pattern.matches(DEGREE_PATTERN, fields[11])) return "ERRO - Formatação incorreta"; // GYRO ROV
            // System.out.println(12);
            if (!Pattern.matches(ANGLE_PATTERN, fields[12])) return "ERRO - Formatação incorreta"; // PITCH ROV
            // System.out.println(13);
            if (!Pattern.matches(ANGLE_PATTERN, fields[13])) return "ERRO - Formatação incorreta"; // ROLL ROV
            // System.out.println(14);
            if (!Pattern.matches(LAT_LON_PATTERN, fields[14])) return "ERRO - Formatação incorreta"; // LAT TMS
            // System.out.println(15);
            if (!Pattern.matches(LAT_LON_PATTERN, fields[15])) return "ERRO - Formatação incorreta"; // LONG TMS
            // System.out.println(16);
            if (!Pattern.matches(DEPTH_TMS, fields[16])) return "ERRO - Formatação incorreta"; // DEPTH TMS


        } catch (Exception e) {
            
            return "ERRO - Sentença com erro Inesperado"; // Captura erros inesperados
        }
        return "OK";
    }
}