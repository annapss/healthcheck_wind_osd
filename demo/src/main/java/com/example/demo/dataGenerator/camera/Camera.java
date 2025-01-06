package com.example.demo.dataGenerator.camera;

import java.util.Random;

import static java.lang.Math.abs;

public class Camera{

    // Main apenas para facilitar o chamado
    public static void main(String[] args) {
        String[] ips = generateIPs(1);
        for (String s : ips) {
            ping(s);
        }
    }

    // Função opcional para gerar ips- apenas utilizar para testes
    public static String[] generateIPs(int amount) {
        String [] ips = new String[amount];
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

    // Função a chamar para verificar a conexão IP
    public static void ping(String ip) {
        double availabilityChance = 0.3;

        Random rand = new Random();
        boolean isAvailable = rand.nextFloat() > availabilityChance;

        System.out.println("PING " + ip + " (" + ip + ") 56(84) bytes of data.");
        double [] totalTime = ping(ip, isAvailable);
        System.out.println("\n--- " + ip + " ping statistics ---");
        if (isAvailable) System.out.println("2 packets transmitted, 2 received, 0% packet loss, time " + roundThreeDecimals(totalTime[3]) + "ms");
        else System.out.println("4 packets transmitted, 0 received, 100% packet loss, time " + roundThreeDecimals(totalTime[3]) + "ms");

        // rtt min/avg/max/mdev = 0.444/0.567/0.690/0.123 ms
        if (isAvailable) System.out.println("rtt min/avg/max/mdev = " + roundThreeDecimals(totalTime[0]) + "/" + roundThreeDecimals(totalTime[1]) + "/" + roundThreeDecimals(totalTime[2]) + "/" + roundThreeDecimals(totalTime[1] - totalTime[0]));
    }

    // Função usada para simular o requerimento do IP
    private static double[] ping(String ip, boolean status) {
        Random rand = new Random();
        int amount = status ? 2 : 4;

        double [] times = new double[4];

        double pingTime = 0;
        times[0] = 10;

        for (int i = 0; i < amount; i++) {
            if (!status) pingTime = 2;
            else pingTime = rand.nextFloat() % 2;

            wait((int)pingTime * 1000);

            if (status) System.out.println("64 bytes from " + ip + ": icmp_seq=" + (i + 1) + " ttl=64 time=" + roundThreeDecimals(pingTime) + " ms");
            else System.out.println("Packet time limit exceeded.");

            if (times[0] > pingTime) times[0] = pingTime;
            if (times[2] < pingTime) times[2] = pingTime;
            times[3] += pingTime;
        }

        times[1] = times[3] / amount;

        return times;
    }

    // Função usada para aguardar resposta
    public static void wait(int ms)
    {
        try
        {
            Thread.sleep(ms);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
    }

    // Função usada para arredondar
    private static double roundThreeDecimals(double d)
    {
        int big = (int) (d * 1000);
        return (double) big / 1000;
    }
}
