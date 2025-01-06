package com.example.demo.dataGenerator.nmeaSentences.vtg;
import java.util.Random;

public class VtgGenerator {
    String sentence;
    long timestamp;
    /*public static void main(String[] args) {
        VtgGenerator vtg = new VtgGenerator();
        vtg.generateSentence();
    }*/
    public void generateSentence() {
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

    private float generateValue(float min, float max)
	{
		Random rand = new Random();
		float midpoint = max/2 + min/2;
		float half_range = max/2 - min/2;
		int plus_minus = rand.nextBoolean() ? 1 : -1;
		float value = midpoint + plus_minus * rand.nextFloat() * half_range;
		return (float) ((int)(value * 10.0)/10.0);
	}

    private void generateCorrectSentence()
    {
        Random rand = new Random();

        float cog_true = (float) ((rand.nextFloat() * 1000) % 360.0);
        char cog_ref_t = 'T';
        float cog_mag = (float) ((rand.nextFloat() * 1000) % 360.0);
        char cog_ref_m = 'M';
        float sog_knot = (float) ((rand.nextFloat() * 1000) % 50.0);
        char sog_unit_n = 'N';
        float sog_kph = (float) (sog_knot * 1.852);
        char sog_unit_k = 'K';
        char mode = 'A';
        sentence = "$GPVTG," + roundTwoDecimals(cog_true) + "," + cog_ref_t +
                ","+ roundTwoDecimals(cog_mag) + "," + cog_ref_m + "," + roundTwoDecimals(sog_knot) + "," + sog_unit_n + ","
                + roundTwoDecimals(sog_kph) + "," + sog_unit_k + "," + mode + "*";
      
        sentence += generateChecksum(sentence);
        timestamp = System.currentTimeMillis();
    }

    private String generateChecksum(String sentence)
    {
        int checkSum = 0;
        for (int i = 0; i < sentence.length(); i++) {
            char cur = sentence.charAt(i);
            if (cur == '$' || cur == '*') continue;
            checkSum ^= sentence.charAt(i);

        }
        return String.format("%02X", checkSum);
    }
    private static float roundTwoDecimals(float d)
    {
        int big = (int) (d * 10);
        return (float) big / 10;
    }

    private void generateSentenceFormatError()
	{
		timestamp = System.currentTimeMillis();
		sentence =  "$GPVTG,?????,T,362.00,M,1.33,N,0.60,K,D*07";
	}

    private void generateOldSentence()
    {
        generateCorrectSentence();
        timestamp = System.currentTimeMillis() - (60 * 60000);
    }

    private void generateSentenceIncorrectChecksum()
	{
        Random rand = new Random();
		timestamp = System.currentTimeMillis();
		float cog_true = (float) ((rand.nextFloat() * 1000) % 360.0);
        char cog_ref_t = 'T';
        float cog_mag = (float) ((rand.nextFloat() * 1000) % 360.0);
        char cog_ref_m = 'M';
        float sog_knot = (float) ((rand.nextFloat() * 1000) % 50.0);
        char sog_unit_n = 'N';
        float sog_kph = (float) (sog_knot * 1.852);
        char sog_unit_k = 'K';
        char mode = 'A';
        sentence = "$GPVTG," + roundTwoDecimals(cog_true) + "," + cog_ref_t +
                ","+ roundTwoDecimals(cog_mag) + "," + cog_ref_m + "," + roundTwoDecimals(sog_knot) + "," + sog_unit_n + ","
                + roundTwoDecimals(sog_kph) + "," + sog_unit_k + "," + mode + "*";
      
		String checksum = "1";
		sentence += checksum;
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
