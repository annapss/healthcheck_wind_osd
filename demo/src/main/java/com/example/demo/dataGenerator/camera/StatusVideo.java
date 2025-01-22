package com.example.demo.dataGenerator.camera;

public class StatusVideo {
    public String generateStatusVideo(boolean correct){
        if(correct)
        {
            return "oceanlive service status\n" +
            "\tLoaded: Loaded\n" + //
            "\tActive: Active";
        }
        boolean status = Math.random() < 0.5;
        if (status) {
            return "oceanlive service status\n" +
                    "\tLoaded: Loaded\n" + //
                    "\tActive: Active";
        } else {
            return "oceanlive service status\n" + //
                    "\tLoaded: Loaded";
        }
    }
}
