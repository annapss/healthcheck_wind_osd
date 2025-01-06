package com.example.demo.dataGenerator.nmeaSentences.mwv;

public class FieldsGenerator {
    public static double windAngleGen() {
        return Math.round(Math.random() * 3600) / 10.0;
    }
    public static String referenceGen(){
        return (Math.random() < 0.5) ? "R" : "T";
    }
    public static double windSpeedGen(){
        return Math.round((Math.random() * 15 + 5) * 10) / 10.0;
    }
    public static String windUnitGen(){
        return "K";
    }
    public static String statusGen(){
        return "A";
    }
}
