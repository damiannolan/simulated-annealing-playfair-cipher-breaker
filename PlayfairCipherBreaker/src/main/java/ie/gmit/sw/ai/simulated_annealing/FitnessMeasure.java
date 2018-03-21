package ie.gmit.sw.ai.simulated_annealing;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FitnessMeasure {
	private static final String TEXTFILE = "4grams.txt"; 
	private Map<String, Integer> dictionary;
	
	public FitnessMeasure() {
		try {
			this.dictionary = parseText(TEXTFILE);
		} catch (Exception e) {
			System.out.println("Error occurred while parsing text" + TEXTFILE);
			e.printStackTrace();
		}
		
	}
	
	public HashMap<String, Integer> parseText(String textfile) throws IOException {
		HashMap<String, Integer> quadgrams = new HashMap<String, Integer>();
		
		BufferedReader reader = new BufferedReader(new FileReader(textfile));
		String next = reader.readLine();
		
		while(next != null) {
			quadgrams.put(next.split(" ")[0], Integer.parseInt(next.split(" ")[1]));
			
			next = reader.readLine();
		}
		
		reader.close();
		return quadgrams;
	}
	
	public void test() {
		System.out.println(dictionary.get("NTHE"));
	}
}
