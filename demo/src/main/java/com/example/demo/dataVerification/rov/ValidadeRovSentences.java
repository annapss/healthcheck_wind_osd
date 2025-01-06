package com.example.demo.dataVerification.rov;

import java.util.regex.Pattern;

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

    public static boolean isValidSentence(String sentence) {
        // Dividir a sentença pelos campos separados por vírgula
        String[] fields = sentence.split(",");
        // for(int i = 1; i < fields.length; i++){
        //     System.out.println(fields[i]);
        // }
        if (fields.length != 17) {
            System.out.println(fields.length);
            System.out.println("numero de campos errado");
            return false; // Número errado de campos
        }

        try {
            

            // Verificar cada campo individualmente
            if (!fields[0].equals(VERSION_PATTERN)) return false;
            System.out.println(1);
            if (!Pattern.matches(DATE_TIME_PATTERN, fields[1])) return false;
            System.out.println(2);
            if (!Pattern.matches(LAT_LON_PATTERN, fields[2])) return false; // SHIP LAT
            System.out.println(3);
            if (!Pattern.matches(LAT_LON_PATTERN, fields[3])) return false; // SHIP LONG
            System.out.println(4);
            if (!Pattern.matches(DEGREE_PATTERN, fields[4])) return false; // SHIP GYRO
            System.out.println(5);
            if (!Pattern.matches(DEGREE_PATTERN, fields[5])) return false; // SHIP COG
            System.out.println(6);
            if (!Pattern.matches(KNOTS_PATTERN, fields[6])) return false; // SHIP SOG
            System.out.println(7);
            if (!Pattern.matches(LAT_LON_PATTERN, fields[7])) return false; // LAT ROV
            System.out.println(8);
            if (!Pattern.matches(LAT_LON_PATTERN, fields[8])) return false; // LONG ROV
            System.out.println(9);
            if (!Pattern.matches(METERS_PATTERN, fields[9])) return false; // DEPTH ROV
            System.out.println(10);
            if (!Pattern.matches(KNOTS_PATTERN, fields[10])) return false; // ALT ROV
            System.out.println(11);
            if (!Pattern.matches(DEGREE_PATTERN, fields[11])) return false; // GYRO ROV
            System.out.println(12);
            if (!Pattern.matches(ANGLE_PATTERN, fields[12])) return false; // PITCH ROV
            System.out.println(13);
            if (!Pattern.matches(ANGLE_PATTERN, fields[13])) return false; // ROLL ROV
            System.out.println(14);
            if (!Pattern.matches(LAT_LON_PATTERN, fields[14])) return false; // LAT TMS
            System.out.println(15);
            if (!Pattern.matches(LAT_LON_PATTERN, fields[15])) return false; // LONG TMS
            System.out.println(16);
            if (!Pattern.matches(DEPTH_TMS, fields[16])) return false; // DEPTH TMS

        } catch (Exception e) {
            
            return false; // Captura erros inesperados
        }

        return true;
    }


    public static void main(String[] args) {
        String validSentence = "1,15.12.2024 19:11:23,-05.490088,-111.570590,323.68,112.60,31.58,24.682921,-363.243308,6690.35,82.57,337.32,-30.65,08.83,-12.212252,-115.741137,6233.50";

        System.out.println("Valid Sentence: " + isValidSentence(validSentence));
    }
}
