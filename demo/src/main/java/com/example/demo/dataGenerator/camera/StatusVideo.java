package com.example.demo.dataGenerator.camera;

public class StatusVideo {
    public static String main(String[] args) {
        boolean status = Math.random() < 0.5;
        if (status) {
            System.out.println("oceanlive service status\n" + //
                    "\tLoaded: Loaded\n" + //
                    "\tActive: Active");

            return "oceanlive service status\n" +
                    "\tLoaded: Loaded\n" + //
                    "\tActive: Active";
        } else {
            System.out.println("oceanlive service status\n" + //
                    "\tLoaded: Loaded");

            return "oceanlive service status\n" + //
                    "\tLoaded: Loaded";
        }
    }
}
