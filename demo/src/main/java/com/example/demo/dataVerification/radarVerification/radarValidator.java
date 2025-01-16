package com.example.demo.dataVerification.radarVerification;
import com.example.demo.dataGenerator.radar.RadarGenerator;

public class radarValidator {

    public static String verificaFormatoCorreto(RadarGenerator radar) {
        String resultadoVerificacoes = "";
        if (!verificaArquivosRecentes(radar)) resultadoVerificacoes += "Arquivos antigos\n";
        if (verificaPingMaquinaRadar(radar)) resultadoVerificacoes +=  "Ping nao funcionou\n";
        if (!verificaSoftwareRadar(radar)) resultadoVerificacoes += "Software do radar desligado\n";    
        if (resultadoVerificacoes.equals("")) return "OK";
        return resultadoVerificacoes;
    }

    private static boolean verificaArquivosRecentes(RadarGenerator radar) {
        long agora = System.currentTimeMillis();
        long umaHoraMillis = 60 * 60 * 100;

        return (agora - radar.getArquivoA_timestamp() <= umaHoraMillis) &&
               (agora - radar.getArquivoB_timestamp() <= umaHoraMillis) &&
               (agora - radar.getArquivoC_timestamp() <= umaHoraMillis);
    }

    private static boolean verificaPingMaquinaRadar(RadarGenerator radar) {
        String ping = radar.getPing_maquina_miros();
        // Verifica se contém resposta de ping
        return ping.contains("Esgotado o tempo limite do pedido");
    }

    private static boolean verificaSoftwareRadar(RadarGenerator radar) {
        long agora = System.currentTimeMillis();
        long umaHoraMillis = 60 * 60 * 1000;

        if (agora - radar.getSoftware_radar_timestamp() > umaHoraMillis) {
            return false;
        }
        // "OK,ProgramaA,"MirAdm01 - Old Data Disposal",300100,,15351808,6549504,128974
		// OK,ProgramaB,"MirSip24 - NMEA Interface",2400,,18743296,7651328,124336
		// OK,ProgramaC,"MirSip12 - Wind Sensor Interface",1200,,18685952,7557120,128964
		// OK,ProgramaD,"MirSip30 - Marine Radar Interface",3000,,23289856,12017664,128959
		// OK,ProgramaE,"MirAdm14 - Application Runner",301400,,24883200,16928768,128954
		// OK,ProgramaF,"MirRap05 - History File Generator",100500,,15646720,7540736,128948"
		
        // Verifica se o software radar segue um formato esperado
        String software = radar.getSoftware_radar();
        String regex = "\\s*OK,Programa[A-Z],\"[^\"]+\",\\d+,,\\d+,\\d+,\\d+\\s*";
        String[] linhas = software.split("\r\n");
        
        for (String linha : linhas) {
            //System.out.println(linha);
            if (!linha.matches(regex)) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        RadarGenerator radar = new RadarGenerator();
        radar.geraDadosRadar();

        String formatoCorreto = verificaFormatoCorreto(radar);
        System.out.println(formatoCorreto);
    }
}
