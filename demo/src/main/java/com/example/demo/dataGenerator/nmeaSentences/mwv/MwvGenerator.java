package com.example.demo.dataGenerator.nmeaSentences.mwv;
import java.util.Random;

public class MwvGenerator {

    private String sentence;
    private long timestamp;
    public void generateSentence(boolean correct)
	{
        if(correct)
        {
            generateCorrectSentence();
            return;
        }
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

    private void generateCorrectSentence(){
        double angle = FieldsGenerator.windAngleGen();
        String reference = FieldsGenerator.referenceGen();
        double speed = FieldsGenerator.windSpeedGen();
        String unit = FieldsGenerator.windUnitGen();
        String status = FieldsGenerator.statusGen();

        sentence = "$IIMWV," + angle + "," + reference + "," + speed + "," + unit + ',' + status + "*";
        sentence += generateChecksum(sentence);
        timestamp = System.currentTimeMillis();
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
		sentence =  "$IIMWV,??????.00,,K,,V*";
        sentence += generateChecksum(sentence);
	}

    private String generateChecksum(String sentence)
	{
		long checksum = 0;
        for (int i = 0; i < sentence.length(); i++) {
            char ch = sentence.charAt(i);
            if(ch != '$' && ch != '*'){
                checksum = checksum ^ sentence.charAt(i);
            }
        }

        String hexChecksum = String.format("%02X", checksum);

		return hexChecksum;
	}

    private void generateOldSentence()
	{
        generateCorrectSentence();
		timestamp = System.currentTimeMillis() - (60 * 60000);
	}

    private void generateSentenceIncorrectChecksum()
	{
		timestamp = System.currentTimeMillis();
        double angle = FieldsGenerator.windAngleGen();
        String reference = FieldsGenerator.referenceGen();
        double speed = FieldsGenerator.windSpeedGen();
        String unit = FieldsGenerator.windUnitGen();
        String status = FieldsGenerator.statusGen();
		sentence = "$IIMWV," + angle + "," + reference + "," + speed + "," + unit + ',' + status + "*-1";
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
