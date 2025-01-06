package com.example.demo.dataVerification.nmeaSentences.vtg;
import java.util.regex.Pattern;
//import src.dataGenerator.nmeaSentences.vtg.*;
public class VtgVerification {
    
    public static final String DEGREES_PATTERN = "\\d{1,3}\\.\\d{1,4}";
    public static final String SPEED_PATTERN = "\\d{1,3}\\.\\d{1,4}";
    public static final String TRUE_PATTERN = "T";
    public static final String MAGNETIC_DEGREES_PATTERN = "\\d{1,3}\\.\\d{1,4}";
    public static final String MAGNETIC_PATTERN = "M";
    public static final String SPEED_OVER_GROUND_MEASURE_KNOTS = "N";
    public static final String SPEED_OVER_GROUND_MEASURE_KILOMETERS = "K";
    public static final String FAA_MODE = "A";
    public static final String VTG_PATTERN = "\\$GPVTG";

    public static String calculateChecksum(String sentence) {
        int checksum = 0;
        for (int i = 0; i < sentence.length() && sentence.charAt(i) != '*'; i++) {
            char ch = sentence.charAt(i);
            if (ch != 'I' && ch != '*' && ch != '$') {
                checksum ^= ch;
            }
        }
        return String.format("%02X", checksum);
    }

    public static String isVtgSentenceValid(String sentence,long timestamp){
        String[] fieldsAndChecksum = sentence.split("\\*");
        if (fieldsAndChecksum.length != 2)
            return "ERRO - Número de campos errado ou ausência de checksum"; // Número errado de campos
        
        String[] fields = fieldsAndChecksum[0].split(",");
        String checksum = fieldsAndChecksum[1];
        if(fields.length != 10)
            return "ERRO - Número incorreto de campos";

        if (!Pattern.matches(VTG_PATTERN, fields[0])) return "ERRO - Formatação incorreta";
        if (!Pattern.matches(DEGREES_PATTERN, fields[1])) return "ERRO - Formatação incorreta";
        if (!Pattern.matches(TRUE_PATTERN, fields[2])) return "ERRO - Formatação incorreta"; // Verifica se é T
        if (!Pattern.matches( MAGNETIC_DEGREES_PATTERN, fields[3])) return "ERRO - Formatação incorreta"; // verifica se é m
        if (!Pattern.matches(MAGNETIC_PATTERN, fields[4])) return "ERRO - Formatação incorreta"; // verifica se é m
        if (!Pattern.matches(SPEED_PATTERN, fields[5])) return "ERRO - Formatação incorreta"; // verifica se a velocidade tem 4 casas decimais
        if (!Pattern.matches(SPEED_OVER_GROUND_MEASURE_KNOTS, fields[6])) return "ERRO - Formatação incorreta"; // verifica se a medida é em nós
        if (!Pattern.matches(SPEED_PATTERN, fields[7])) return "ERRO - Formatação incorreta"; // verifica se a velocidade tem 4 casas decimais
        if (!Pattern.matches(SPEED_OVER_GROUND_MEASURE_KILOMETERS, fields[8])) return "ERRO - Formatação incorreta"; // verifica se a medida é em kilometros
        if (!Pattern.matches(FAA_MODE, fields[9])) return "ERRO - Formatação incorreta"; // verifica se o FAA Mode é A
        if (!calculateChecksum(fieldsAndChecksum[0]).equalsIgnoreCase(checksum)) return "ERRO - Checksum incorreto";
        
        long now = System.currentTimeMillis();
        if(now - timestamp > 30000)
            return "ERRO - Dado antigo";
        
        return "OK";
    }

    /*public static void main(String[] args) {
        VTG_Gen vtg = new VTG_Gen();
        for(int i = 0; i < 5; i++)
        {
            vtg.generateSentence();
            System.out.println(VtgVerification.isVtgSentenceValid(vtg.getSentence(), vtg.getTimestamp()));
        }
    }*/
}
