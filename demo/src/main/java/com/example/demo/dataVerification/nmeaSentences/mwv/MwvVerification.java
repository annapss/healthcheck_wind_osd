package com.example.demo.dataVerification.nmeaSentences.mwv;

import java.util.regex.Pattern;

public class MwvVerification {

    public static final String MWV_PATTERN = "\\$IIMWV";
    public static final String ANGLE_PATTERN = "\\d{1,3}(\\.\\d{1,4})?";
    public static final String REFERENCE_PATTERN = "[RT]";
    public static final String SPEED_PATTERN = "\\d{1,3}(\\.\\d{1,4})?";
    public static final String UNIT_PATTERN = "K";
    public static final String STATUS_PATTERN = "A";

    // Método para calcular o checksum
    public static String calculateChecksum(String sentence) {
        int checksum = 0;
        for (int i = 0; i < sentence.length() && sentence.charAt(i) != '*'; i++) {
            char ch = sentence.charAt(i);
            if (ch != '$' && ch != '*') {
                checksum ^= ch;
            }
            
        }
        return String.format("%02X", checksum);
    }

    // Método para verificar a validade de uma sentença MWV
    public static String isMwvSentenceValid(String sentence, long timestamp) {
        int tolerance = 30000;
        String[] fieldsAndChecksum = sentence.split("\\*");
        if (fieldsAndChecksum.length != 2) {
            return "ERRO - Número errado de campos ou ausência de checksum";
        }

        String[] fields = fieldsAndChecksum[0].split(",");
        String checksum = fieldsAndChecksum[1];

        // Verifica se há exatamente 6 campos antes do checksum
        if (fields.length != 6) {
            return "ERRO - Número de campos incorreto";
        }

        // Verifica cada campo individualmente
        if (!Pattern.matches(MWV_PATTERN, fields[0])) return "ERRO - Formatação incorreta";
        if (!Pattern.matches(ANGLE_PATTERN, fields[1])) return "ERRO - Formatação incorreta";
        if (!Pattern.matches(REFERENCE_PATTERN, fields[2])) return "ERRO - Formatação incorreta";
        if (!Pattern.matches(SPEED_PATTERN, fields[3])) return "ERRO - Formatação incorreta";
        if (!Pattern.matches(UNIT_PATTERN, fields[4])) return "ERRO - Formatação incorreta";
        if (!Pattern.matches(STATUS_PATTERN, fields[5])) return "ERRO - Formatação incorreta";

        // Verifica o checksum
        String calculatedChecksum = calculateChecksum(fieldsAndChecksum[0]);
        if (!calculatedChecksum.equalsIgnoreCase(checksum)) {
            return "ERRO - Checksum incorreto";
        }
        long now = System.currentTimeMillis();
        if(now - timestamp > tolerance)
            return "ERRO - Dado antigo";
        return "OK";
    }

    // Método principal para testar
    /*public static void main(String[] args) {
        // Exemplo de sentença válida gerada
        MwvGenerator mwv = new MwvGenerator();
        for(int i = 0; i < 5; i++)
        {
            mwv.generateSentence();
            String result = MwvVerification.isMwvSentenceValid(mwv.getSentence(), mwv.getTimestamp());
            System.out.println(result);
        }
    }*/
}

