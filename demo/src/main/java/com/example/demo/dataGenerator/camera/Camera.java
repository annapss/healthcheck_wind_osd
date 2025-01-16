package com.example.demo.dataGenerator.camera;

import java.util.Random;

import static java.lang.Math.abs;

public class Camera {

    // Main apenas para facilitar o chamado
    public static void main(String[] args) {
        String[] ips = generateIPs(1);
        Camera camera = new Camera();
        for (String s : ips) {
            String resultado = camera.ping(s);
            System.out.println(resultado);
        }
    }

    public static String[] generateIPs(int amount) {
        String[] ips = new String[amount];
        Random rand = new Random();

        while (--amount >= 0) {
            int ipNo1 = abs(rand.nextInt()) % 256;
            int ipNo2 = abs(rand.nextInt()) % 256;
            int ipNo3 = abs(rand.nextInt()) % 256;
            int ipNo4 = abs(rand.nextInt()) % 256;

            ips[amount] = ipNo1 + "." + ipNo2 + "." + ipNo3 + "." + ipNo4;
        }

        return ips;
    }

    public String ping(String ip) {
        StringBuilder resultado = new StringBuilder();
        double availabilityChance = 0.3;
        double errorChance = 0.1;

        Random rand = new Random();
        boolean isAvailable = rand.nextFloat() > availabilityChance;
        boolean hasError = rand.nextFloat() < errorChance;

        resultado.append("PING ").append(ip).append(" (").append(ip).append(") 56(84) bytes of data.\n");

        if (hasError) {
            resultado.append("Packet time limit exceeded.\n");
            resultado.append("Packet time limit exceeded.\n");
            resultado.append("Packet time limit exceeded.\n");
            resultado.append("Packet time limit exceeded.\n");
            resultado.append("\n--- ").append(ip).append(" ping statistics ---\n");
            resultado.append("4 packets transmitted, 0 received, 100% packet loss, time ")
                     .append(roundThreeDecimals(rand.nextFloat() * 10)).append("ms\n");
            return resultado.toString();
        }

        double[] totalTime = pingSimulado(ip, isAvailable, resultado);

        resultado.append("\n--- ").append(ip).append(" ping statistics ---\n");
        if (isAvailable) {
            resultado.append("2 packets transmitted, 2 received, 0% packet loss, time ")
                     .append(roundThreeDecimals(totalTime[3])).append("ms\n");
        } else {
            resultado.append("4 packets transmitted, 0 received, 100% packet loss, time ")
                     .append(roundThreeDecimals(totalTime[3])).append("ms\n");
        }

        if (isAvailable) {
            resultado.append("rtt min/avg/max/mdev = ")
                     .append(roundThreeDecimals(totalTime[0])).append("/")
                     .append(roundThreeDecimals(totalTime[1])).append("/")
                     .append(roundThreeDecimals(totalTime[2])).append("/")
                     .append(roundThreeDecimals(totalTime[1] - totalTime[0])).append(" ms");
        }

        return resultado.toString();
    }

    private static double[] pingSimulado(String ip, boolean status, StringBuilder resultado) {
        Random rand = new Random();
        int amount = status ? 2 : 4;

        double[] times = new double[4];
        double pingTime = 0;
        times[0] = 10;

        for (int i = 0; i < amount; i++) {
            if (!status) {
                pingTime = 2;
                resultado.append("Packet time limit exceeded.\n");
            } else {
                pingTime = rand.nextFloat() % 2;
                resultado.append("64 bytes from ").append(ip).append(": icmp_seq=").append(i + 1)
                        .append(" ttl=64 time=").append(roundThreeDecimals(pingTime)).append(" ms\n");
            }

            if (times[0] > pingTime) times[0] = pingTime;
            if (times[2] < pingTime) times[2] = pingTime;
            times[3] += pingTime;
        }

        times[1] = times[3] / amount;

        return times;
    }

    private static double roundThreeDecimals(double d) {
        int big = (int) (d * 1000);
        return (double) big / 1000;
    }
}
