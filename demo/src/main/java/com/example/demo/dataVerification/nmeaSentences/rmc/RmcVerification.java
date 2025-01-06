package com.example.demo.dataVerification.nmeaSentences.rmc;

import java.util.regex.Pattern;
//import src.dataGenerator.nmeaSentences.rmc.*;
public class RmcVerification {

    // Padrões para validar a sentença RMC
    public static final String RMC_PATTERN = "\\$GPRMC";
    public static final String TIME_PATTERN = "\\d{4,6}(\\.\\d+)?"; // hhmmss.ss
    public static final String STATUS_PATTERN = "A"; // Ativo (válido)
    public static final String LATITUDE_PATTERN = "\\d{2,4}\\.\\d{1,4},[N|S]"; // ddmm.mmmm,N ou ddmm.mmmm,S
    public static final String LONGITUDE_PATTERN = "\\d{2,4}\\.\\d{1,4},[E|W]"; // dddmm.mmmm,E ou dddmm.mmmm,W
    public static final String SPEED_PATTERN = "\\d+(\\.\\d+)?"; // Velocidade em nós
    public static final String COURSE_PATTERN = "\\d+(\\.\\d+)?"; // Curso sobre o solo
    public static final String DATE_PATTERN = "\\d{6}"; // ddmmyy
    public static final String VARIATION_PATTERN = "\\d+(\\.\\d+)?,[E|W]"; // Variância magnética

    public static String calculateChecksum(String sentence) {
        int checksum = 0;
        for (int i = 1; i < sentence.length() && sentence.charAt(i) != '*'; i++) {
            checksum ^= sentence.charAt(i);
        }
        return String.format("%02X", checksum);
    }

    public static String isRmcSentenceValid(String sentence, long timestamp) {
        // Dividir sentença pelo caractere '*'
        String[] fieldsAndChecksum = sentence.split("\\*");
        if (fieldsAndChecksum.length != 2) 
            return "ERRO - Número errado de campos ou ausência de checksum"; // Deve conter a parte da sentença e o checksum

        String[] fields = fieldsAndChecksum[0].split(",");
        String checksum = fieldsAndChecksum[1];

        // Validar o número de campos
        if (fields.length != 12) return "ERRO - Número de campos incorreto";

        // Validar os campos individualmente
        if (!Pattern.matches(RMC_PATTERN, fields[0])) return "ERRO - Formatação incorreta";
        if (!Pattern.matches(TIME_PATTERN, fields[1])) return "ERRO - Formatação incorreta";
        if (!Pattern.matches(STATUS_PATTERN, fields[2])) return "ERRO - Formatação incorreta";
        if (!Pattern.matches(LATITUDE_PATTERN, fields[3] + "," + fields[4])) return "ERRO - Formatação incorreta";
        if (!Pattern.matches(LONGITUDE_PATTERN, fields[5] + "," + fields[6])) return "ERRO - Formatação incorreta";
        if (!Pattern.matches(SPEED_PATTERN, fields[7])) return "ERRO - Formatação incorreta";
        if (!Pattern.matches(COURSE_PATTERN, fields[8])) return "ERRO - Formatação incorreta";
        if (!Pattern.matches(DATE_PATTERN, fields[9])) return "ERRO - Formatação incorreta";
        if (!Pattern.matches(VARIATION_PATTERN, fields[10] + "," + fields[11].substring(0, 1))) return "ERRO - Formatação incorreta";

        // Verificar o checksum
        String calculatedChecksum = calculateChecksum(fieldsAndChecksum[0]);
        if (!calculatedChecksum.equalsIgnoreCase(checksum)) 
            return "ERRO - Checksum incorreto";
        
        long now = System.currentTimeMillis();
        if(now - timestamp > 30000)
            return "ERRO - Dado antigo";

        return "OK";
    }

    /*public static void main(String[] args) {
        System.out.println();
        RmcGenerator rmc = new RmcGenerator();
        for(int i = 0; i < 5; i++)
        {
            rmc.generateSentence();
            System.out.println(RmcVerification.isRmcSentenceValid(rmc.getSentence(), rmc.getTimestamp()));
        }
    }*/
}
