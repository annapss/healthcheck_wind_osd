package com.example.demo.dataGenerator.radar;

import java.util.Random;

public class RadarGenerator {
	private long arquivoA_timestamp;
	private long arquivoB_timestamp;
	private long arquivoC_timestamp;
	private String software_radar;
	private long software_radar_timestamp;
	private String ping_maquina_miros;
	
	public void geraDadosRadar(boolean correct)
	{
		if(correct)
		{
			geraPingMaquinaRadarCorreto();
			geraArquivosRecentes();
			geraDadoSoftwareRadarAtualizado();
			return;
		}
		int opcao = (int)geraValorEmIntervaloUmaCasaDecimal(1,5);
		//opcao = 4; -> usei para testar as op��es separadamente. Vou deixar aqui para caso precise novamente
		System.out.println("opcao: " + opcao);
		if(opcao == 1) //todos os dados do radar est�o corretos
		{
			geraPingMaquinaRadarCorreto();
			geraArquivosRecentes();
			geraDadoSoftwareRadarAtualizado();
		}
		else if(opcao == 2) //pelo menos um dos arquivos nao � recente, ping maquina n�o funciona e dado do software do radar n�o � recente
		{
			geraArquivosDesatualizados();
			geraPingMaquinaRadarIncorreto();
			geraDadoSoftwareRadarDesatualizado();
		}
		else if(opcao == 3) //arquivos n�o s�o recentes, dado do software nao � recente e o ping da maquina funciona
		{
			geraArquivosDesatualizados();
			geraPingMaquinaRadarCorreto();
			geraDadoSoftwareRadarDesatualizado();
		}
		else //arquivos n�o s�o recentes, dado do software � recente e o ping da m�quina funciona
		{
			geraArquivosDesatualizados();
			geraPingMaquinaRadarCorreto();
			geraDadoSoftwareRadarAtualizado();
		}
		/* Usado para testes
		System.out.println("arquivoA: " + arquivoA_timestamp + "\n");
		System.out.println("arquivoB: " + arquivoB_timestamp + "\n");
		System.out.println("arquivoC: " + arquivoC_timestamp + "\n");
		System.out.println("Software: " + software_radar + "\n");
		System.out.println("Software timestamp: " + software_radar_timestamp + "\n");
		System.out.println("Maquina Miros: " + ping_maquina_miros + "\n");
		*/
	}
	
	private void geraPingMaquinaRadarCorreto()
	{
		ping_maquina_miros = "PING 192.168.123.2 (192.168.123.2) 56(84) bytes of data\n"
				+ "64 bytes from 192.168.123.2: icmp_seq=1 ttl=64 time=0.444 ms\n"
				+ "64 bytes from 192.168.123.2: icmp_seq=2 ttl=64 time=0.690 ms\n"
				+ " --- 192.168.123.12 ping statistics ---\n"
				+ "2 packets transmitted, 2 received, 0% packet loss, time 1003ms\n"
				+ "rtt min/avg/max/mdev = 0.444/0.567/0.690/0.123 ms";
	}
	private void geraArquivosRecentes()
	{
		arquivoA_timestamp = System.currentTimeMillis();
		arquivoB_timestamp = System.currentTimeMillis();
		arquivoC_timestamp = System.currentTimeMillis();
	}
	private void geraDadoSoftwareRadarAtualizado()
	{
		software_radar = "OK,ProgramaA,\"MirAdm01 - Old Data Disposal\",300100,,15351808,6549504,128974\r\n"
				+ "OK,ProgramaB,\"MirSip24 - NMEA Interface\",2400,,18743296,7651328,124336\r\n"
				+ "OK,ProgramaC,\"MirSip12 - Wind Sensor Interface\",1200,,18685952,7557120,128964\r\n\""
				+ "OK,ProgramaD,\"MirSip30 - Marine Radar Interface\",3000,,23289856,12017664,128959\r\n"
				+ "OK,ProgramaE,\"MirAdm14 - Application Runner\",301400,,24883200,16928768,128954\r\n"
				+ "OK,ProgramaF,\"MirRap05 - History File Generator\",100500,,15646720,7540736,128948\n";
		software_radar_timestamp = System.currentTimeMillis();
	}
	private void geraPingMaquinaRadarIncorreto()
	{
		ping_maquina_miros = "PING 192.168.123.2 (192.168.123.2) 56(84) bytes of data\n"
				+ "Esgotado o tempo limite do pedido.\n"
				+ "Esgotado o tempo limite do pedido.\n"
				+ "Esgotado o tempo limite do pedido.\n"
				+ "Esgotado o tempo limite do pedido.";
	}
	private void geraArquivosDesatualizados()
	{
		arquivoA_timestamp = System.currentTimeMillis() - (60 * 60000);
		arquivoB_timestamp = System.currentTimeMillis() - (60 * 60000);
		arquivoC_timestamp = System.currentTimeMillis() - (60 * 60000);
	}
	private void geraDadoSoftwareRadarDesatualizado()
	{
		software_radar = "OK,ProgramaA,\"MirAdm01 - Old Data Disposal\",300100,,15351808,6549504,128974\r\n"
				+ "OK,ProgramaB,\"MirSip24 - NMEA Interface\",2400,,18743296,7651328,124336\r\n"
				+ "OK,ProgramaC,\"MirSip12 - Wind Sensor Interface\",1200,,18685952,7557120,128964\r\n\""
				+ "OK,ProgramaD,\"MirSip30 - Marine Radar Interface\",3000,,23289856,12017664,128959\r\n"
				+ "OK,ProgramaE,\"MirAdm14 - Application Runner\",301400,,24883200,16928768,128954\r\n"
				+ "OK,ProgramaF,\"MirRap05 - History File Generator\",100500,,15646720,7540736,128948\n";
		software_radar_timestamp = System.currentTimeMillis() - (60 * 60000);
	}
	private float geraValorEmIntervaloUmaCasaDecimal(float min, float max)
	{
		Random rand = new Random();
		float midpoint = max/2 + min/2;
		float half_range = max/2 - min/2;
		int plus_minus = rand.nextBoolean() ? 1 : -1;
		float value = midpoint + plus_minus * rand.nextFloat() * half_range;
		return (float) ((int)(value * 10.0)/10.0);
	}

	public long getArquivoA_timestamp() {
		return arquivoA_timestamp;
	}

	public long getArquivoB_timestamp() {
		return arquivoB_timestamp;
	}

	public long getArquivoC_timestamp() {
		return arquivoC_timestamp;
	}

	public String getSoftware_radar() {
		return software_radar;
	}

	public long getSoftware_radar_timestamp() {
		return software_radar_timestamp;
	}

	public String getPing_maquina_miros() {
		return ping_maquina_miros;
	}	
}
