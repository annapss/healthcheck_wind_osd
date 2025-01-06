package com.example.demo.dataGenerator.nmeaSentences.rmc;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.LocalDateTime;  
import java.util.Random;

public class RmcGenerator {
	private String sentence;
	private long timestamp;
	
	public void generateSentence()
	{
		int option = (int)generateValue(1,5);
		//option = 4; //-> usei para testar as opções separadamente. Vou deixar aqui para caso precise novamente
		if(option == 1)
			generateCorrectSentence();
		else if(option == 2)
			generateSentenceFormatError();
		else if(option == 3)
			generateOldSentence();
		else
			generateSentenceIncorrectChecksum();
		//Usados para teste
		/*System.out.println(option);
		System.out.println(sentence);
		System.out.println(timestamp);*/
	}
	private void generateSentenceFormatError()
	{
		timestamp = System.currentTimeMillis();
		this.sentence =  "$GPRMC," + geraHorarioUTC() + ",A,,,,,," + "?,S,'',3*2";
	}
	private void generateOldSentence()
	{
		timestamp = System.currentTimeMillis() - (60 * 60000);
		String sentence =  "$GPRMC," + geraHorarioUTC() + ",A," + generateLatLon() + generateShipSpeed() + generateShipHeading() + generateDate() + generateMagneticVariation() + "*";
		String checksum = generateChecksum(sentence);
		sentence += checksum;
		this.sentence = sentence;
	}
	private void generateSentenceIncorrectChecksum()
	{
		timestamp = System.currentTimeMillis();
		String sentence =  "$GPRMC," + geraHorarioUTC() + ",A," + generateLatLon() + generateShipSpeed() + generateShipHeading() + generateDate() + generateMagneticVariation() + "*";
		String checksum = "-1";
		sentence += checksum;
		this.sentence = sentence;
	}
	private void generateCorrectSentence()
	{
		timestamp = System.currentTimeMillis();
		String sentence =  "$GPRMC," + geraHorarioUTC() + ",A," + generateLatLon() + generateShipSpeed() + generateShipHeading() + generateDate() + generateMagneticVariation() + "*";
		String checksum = generateChecksum(sentence);
		sentence += checksum;
		this.sentence = sentence;
	}
	private String geraHorarioUTC()
	{
		OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);
		return Integer.toString(now.getHour()) + Integer.toString(now.getMinute()) + Float.toString(now.getSecond()); 
	}
	private float generateValue(float min, float max)
	{
		Random rand = new Random();
		float midpoint = max/2 + min/2;
		float half_range = max/2 - min/2;
		int plus_minus = rand.nextBoolean() ? 1 : -1;
		float value = midpoint + plus_minus * rand.nextFloat() * half_range;
		return (float) ((int)(value * 10.0)/10.0);
	}
	//Latitude e Longitude vai ser gerada dentro de um intervalo que pega o litoral do Brasil
	private String generateLatLon()
	{
		float latitude = generateValue((float)-24.23, (float)-1.37);
		float longitude = generateValue((float)-49.57, (float)-29.80);
		float latMinutes = (float) ((int)(((latitude - (int)latitude) * -60) * 10000.0)/10000.0);
		String latitudeDMM = Integer.toString((int)latitude * -1) + Float.toString(latMinutes) + ",S,";
		float lonMinutes = (float) ((int)(((longitude - (int)longitude) * -60) * 10000.0)/10000.0);
		String longitudeDMM = Integer.toString((int)longitude * -1) + Float.toString(lonMinutes) + ",W,";
		return latitudeDMM + longitudeDMM;
		
	}
	private String generateShipSpeed()
	{
		return Float.toString(generateValue(10, 20)) + ",";
	}
	
	private String generateShipHeading()
	{
		return Float.toString(generateValue(0,359)) + ","; //Depois vou verificar esse intervalo, vou diminuir!
	}
	
	private String generateDate()
	{
		LocalDateTime now = LocalDateTime.now();
		String day, month, year;
		if(now.getDayOfMonth() < 10)
			day = "0" + Integer.toString(now.getDayOfMonth());
		else
			day = Integer.toString(now.getDayOfMonth());

		if(now.getMonthValue() < 10)
			month = "0" + Integer.toString(now.getMonthValue());
		else
			month = Integer.toString(now.getMonthValue());

		year = Integer.toString(now.getYear() % 100);

		return day + month + year + ",";
	}
	private String generateMagneticVariation()
	{
		return generateValue(0, 180) + ",W";
	}
	private String generateChecksum(String sentence)
	{
		int checksum = 0;
		for(int i = 1; i < sentence.length() - 1; i++) {
			  checksum = checksum ^ sentence.charAt(i);
		}
		return String.format("%02X", checksum);
	}
	public String getSentence() {
		return sentence;
	}
	public long getTimestamp() {
		return timestamp;
	}
	// Essa main foi criada somente para testes
	/*public static void main(String[] args) {
		RmcGenerator rmc = new RmcGenerator();
		rmc.generateSentence();
	}*/
}
