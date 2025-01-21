package com.example.demo.dataVerification.radarVerification;

public class radarValidator {
    public static String verificaFormatoCorreto(Long arquivoA_timestamp, Long arquivoB_timestamp, Long arquivoC_timestamp, String software_radar, Long software_radar_timestamp, String ping_miros) {
        String resultadoVerificacoes = "";
        if (verificaPingMaquinaRadar(ping_miros)) resultadoVerificacoes =  "Ping não funcionou";
        if (!verificaSoftwareRadar(software_radar_timestamp, software_radar)) resultadoVerificacoes = "Software do radar desligado";    
        if (!verificaArquivosRecentes(arquivoA_timestamp, arquivoB_timestamp, arquivoC_timestamp)) resultadoVerificacoes = "Radar desligado";
        if (resultadoVerificacoes.equals("")) return "OK";
        return resultadoVerificacoes;
    }

    private static boolean verificaArquivosRecentes(Long arquivoA_timestamp, Long arquivoB_timestamp, Long arquivoC_timestamp) {
        long agora = System.currentTimeMillis();
        long umaHoraMillis = 60 * 60 * 100;

        return (agora - arquivoA_timestamp <= umaHoraMillis) &&
               (agora - arquivoB_timestamp <= umaHoraMillis) &&
               (agora - arquivoC_timestamp <= umaHoraMillis);
    }

    private static boolean verificaPingMaquinaRadar(String ping_miros) {
        // Verifica se contém resposta de ping
        return ping_miros.contains("Esgotado o tempo limite do pedido");
    }

    private static boolean verificaSoftwareRadar(Long software_radar_timestamp, String software_radar) {
        long agora = System.currentTimeMillis();
        long umaHoraMillis = 60 * 60 * 1000;

        if (agora - software_radar_timestamp > umaHoraMillis) {
            return false;
        }
        // "OK,ProgramaA,"MirAdm01 - Old Data Disposal",300100,,15351808,6549504,128974
		// OK,ProgramaB,"MirSip24 - NMEA Interface",2400,,18743296,7651328,124336
		// OK,ProgramaC,"MirSip12 - Wind Sensor Interface",1200,,18685952,7557120,128964
		// OK,ProgramaD,"MirSip30 - Marine Radar Interface",3000,,23289856,12017664,128959
		// OK,ProgramaE,"MirAdm14 - Application Runner",301400,,24883200,16928768,128954
		// OK,ProgramaF,"MirRap05 - History File Generator",100500,,15646720,7540736,128948"
		
        // Verifica se o software radar segue um formato esperado
        /*String regex = "OK,Programa[A-Z],\\\"[^\\\"]+\\\",\\d+,,\\d+,\\d+,\\d+\\s*";
        String[] linhas = software_radar.split("\r\n");
        
        for (String linha : linhas) {
            //System.out.println(linha);
            if (!linha.matches(regex)) {
                return false;
            }
        }*/
        return true;
    }

    /*public static void main(String[] args) {
        RadarGenerator radar = new RadarGenerator();
        radar.geraDadosRadar();

        String formatoCorreto = verificaFormatoCorreto(radar);
        System.out.println(formatoCorreto);
    }*/
}
