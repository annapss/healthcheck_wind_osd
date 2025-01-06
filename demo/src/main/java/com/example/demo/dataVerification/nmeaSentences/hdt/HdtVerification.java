package com.example.demo.dataVerification.nmeaSentences.hdt;

import java.util.regex.Pattern;

public class HdtVerification {

    // Padrões para validar a sentença HDT
    public static final String HDT_PATTERN = "\\$HEHDT";
    public static final String ANGLE_PATTERN = "\\d{1,3}(\\.\\d{1,2})?"; // 0-359 com até 2 casas decimais
    public static final String TERMINATOR_PATTERN = "T";

    // Método para calcular o checksum
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

    // Método para verificar se a sentença HDT está correta
    public static String isHdtSentenceValid(String sentence, long timestamp) {
        long tolerance = 30000;

        // Dividir a sentença pelo caractere '*'
        String[] fieldsAndChecksum = sentence.split("\\*");
        if (fieldsAndChecksum.length != 2)
            return "ERRO - Número errado de campos ou ausência de checksum";

        String[] fields = fieldsAndChecksum[0].split(",");
        String checksum = fieldsAndChecksum[1];
        // Validar o número de campos
        if (fields.length != 3) return "ERRO - Número de campos incorreto";
        // Validar os campos individualmente
        if (!Pattern.matches(HDT_PATTERN, fields[0])) return "ERRO - Formatação";
        if (!Pattern.matches(ANGLE_PATTERN, fields[1])) return "ERRO - Valor com formatação incorreta";
        if (!Pattern.matches(TERMINATOR_PATTERN, fields[2])) return "ERRO - Formatação";

        // Verificar o intervalo do ângulo
        double angle = Double.parseDouble(fields[1]);
        if (angle < 0 || angle > 359) return "ERRO - Valor fora do intervalo";

        // Verificar o checksum
        String calculatedChecksum = calculateChecksum(fieldsAndChecksum[0]);
        if (!calculatedChecksum.equalsIgnoreCase(checksum))
            return "ERRO - Checksum incorreto";

        // Verificar o tempo
        long now = System.currentTimeMillis();
        if (now - timestamp > tolerance) return "ERRO - Dado antigo";

        return "OK";
    }

    /*public static void main(String[] args) {
        // Exemplo de teste
        HdtGenerator hdtGen = new HdtGenerator();

        for (int i = 0; i < 5; i++) {
            hdtGen.generateSentence();
            String sentence = hdtGen.getSentence();
            long timestamp = hdtGen.getTimestamp();
            String status = HdtVerification.isHdtSentenceValid(sentence, timestamp);
            System.out.println(status);
        }
    }*/
}
