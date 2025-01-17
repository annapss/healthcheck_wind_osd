package com.example.demo.dataGenerator.rov;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class RovSentence {

    private String sentence;
    private long timeStamp;
    
    private void generateSentence() {
        Random random = new Random();

        // Configurar DecimalFormat para usar ponto como separador decimal
        DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance(Locale.US);
        DecimalFormat sixDecimals = new DecimalFormat("00.000000", symbols);
        DecimalFormat threeDigitstwoDecimals = new DecimalFormat("000.00", symbols);
        DecimalFormat twoDecimals = new DecimalFormat("00.00", symbols);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        String dateTime = dateFormat.format(new Date());

        double shipLat = random.nextDouble() * 180 - 90;
        double shipLong = random.nextDouble() * 360 - 180;
        double shipGyro = random.nextDouble() * 360;
        double shipCog = random.nextDouble() * 360;
        double shipSog = random.nextDouble() * 50;
        double latRov = random.nextDouble() * 180 - 90;
        double longRov = random.nextDouble() * 360 - 180;
        double depthRov = random.nextDouble() * 10000;
        double altRov = random.nextDouble() * 100;
        double gyroRov = random.nextDouble() * 360;
        double pitchRov = random.nextDouble() * 90 - 45;
        double rollRov = random.nextDouble() * 90 - 45;
        double latTms = random.nextDouble() * 180 - 90;
        double longTms = random.nextDouble() * 360 - 180;
        double depthTms = random.nextDouble() * 10000;

        // Montar a sentença no formato esperado
        sentence =  String.format(
                "1,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s",
                dateTime,
                sixDecimals.format(shipLat),
                sixDecimals.format(shipLong),
                twoDecimals.format(shipGyro),
                twoDecimals.format(shipCog),
                twoDecimals.format(shipSog),
                sixDecimals.format(latRov),
                sixDecimals.format(longRov),
                threeDigitstwoDecimals.format(depthRov),
                twoDecimals.format(altRov),
                threeDigitstwoDecimals.format(gyroRov),
                twoDecimals.format(pitchRov),
                twoDecimals.format(rollRov),
                sixDecimals.format(latTms),
                sixDecimals.format(longTms),
                twoDecimals.format(depthTms));
    }

    private void generateIncorrectSentence() {
        Random random = new Random();
        DecimalFormat sixDecimals = new DecimalFormat("00.0000");
        DecimalFormat threeDigitstwoDecimals = new DecimalFormat("000.00");
        DecimalFormat twoDecimals = new DecimalFormat("00.00");

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd,MM,yyyy HH:mm:ss");
        String dateTime = dateFormat.format(new Date());

        double shipLat = random.nextDouble() * 180 - 90;
        double shipLong = random.nextDouble() * 360 - 180;
        double shipGyro = random.nextDouble() * 360;
        double shipCog = random.nextDouble() * 360;
        double shipSog = random.nextDouble() * 50;
        double latRov = random.nextDouble() * 180 - 90;
        double longRov = random.nextDouble() * 360 - 180;
        double depthRov = random.nextDouble() * 10000;
        double altRov = random.nextDouble() * 100;
        double gyroRov = random.nextDouble() * 360;
        double pitchRov = random.nextDouble() * 90 - 45;
        double rollRov = random.nextDouble() * 90 - 45;
        double latTms = random.nextDouble() * 180 - 90;
        double longTms = random.nextDouble() * 360 - 180;

        // Montar a sentença no formato esperado
        sentence =  String.format(
                "1,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s",
                dateTime,
                sixDecimals.format(shipLat),
                sixDecimals.format(shipLong),
                twoDecimals.format(shipGyro),
                twoDecimals.format(shipCog),
                twoDecimals.format(shipSog),
                threeDigitstwoDecimals.format(latRov),
                sixDecimals.format(longRov),
                threeDigitstwoDecimals.format(depthRov),
                twoDecimals.format(altRov),
                threeDigitstwoDecimals.format(gyroRov),
                twoDecimals.format(pitchRov),
                twoDecimals.format(rollRov),
                threeDigitstwoDecimals.format(latTms),
                threeDigitstwoDecimals.format(longTms));
    }
    
    public void generateRandomSentence() {
        Random random = new Random();
        timeStamp = System.currentTimeMillis();
        if (random.nextInt(10) == 0) {
            generateIncorrectSentence();
            return;
        }
        generateSentence();
    }

    public String getSentence() {
        return sentence;
    }

    public long getTimeStamp() {
        return timeStamp;
    }
     
}