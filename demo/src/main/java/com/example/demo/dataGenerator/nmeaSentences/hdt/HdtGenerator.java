package com.example.demo.dataGenerator.nmeaSentences.hdt;
import java.util.Random;

public class HdtGenerator {
    private long timestamp;
    private String sentence;

    public void generateSentence(boolean correct)
	{
		if(correct)
		{
			generateCorrectSentence(0, 359);
			return;
		}
		int option = (int)generateValue(1,5);
		//option = 4; //-> usei para testar as opções separadamente. Vou deixar aqui para caso precise novamente
		if(option == 1)
            generateCorrectSentence(0,359);
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

    private void generateCorrectSentence(int minValue, int maxValue) {
        double degrees = Math.random() * maxValue;

        degrees = (double) Math.round(degrees * 100) / 100;
        String sentence = "$HEHDT," + degrees + ",T*";

        String checksum =generateChecksum(sentence);
        this.sentence = sentence + checksum;
        this.timestamp = System.currentTimeMillis();
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

    private void generateSentenceFormatError()
	{
		timestamp = System.currentTimeMillis();
		sentence =  "$HEHDT,361.00,T*";
        sentence += generateChecksum(sentence);
	}

    private String generateChecksum(String sentenca_atual)
	{
		int checksum = 0;
		for(int i = 1; i < sentenca_atual.length() - 1; i++) {
			  checksum = checksum ^ sentenca_atual.charAt(i);
		}
		String checksum_str = Integer.toHexString(checksum);
		checksum_str = checksum_str.toUpperCase();
		return checksum_str;
	}

    private void generateOldSentence()
	{
        generateCorrectSentence(0, 359);
		timestamp = System.currentTimeMillis() - (60 * 60000);
	}

    private void generateSentenceIncorrectChecksum()
	{
		timestamp = System.currentTimeMillis();
		sentence =  "$HEHDT,350.00,T*-1";
	}

    public String getSentence()
    {
        return sentence;
    }

    public long getTimestamp()
    {
        return timestamp;
    }
}