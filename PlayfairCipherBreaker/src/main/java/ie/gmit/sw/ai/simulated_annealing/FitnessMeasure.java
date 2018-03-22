package ie.gmit.sw.ai.simulated_annealing;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FitnessMeasure {
	private static final String TEXTFILE = "4grams.txt"; 
	private Map<String, Integer> dictionary;
	private long totalQuadgrams;
	
	public FitnessMeasure() {
		try {
			this.dictionary = parse(TEXTFILE);
			this.totalQuadgrams = dictionary.values().stream().mapToLong(i->i).sum();
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
	
	public Map<String, Integer> parse(String fileName) throws IOException {		
		Stream<String> lines = Files.lines(Paths.get(fileName));
		
		Map<String, Integer> quadgrams = 
				lines.map(line -> line.split(" "))
				.collect(Collectors.toMap(line -> line[0], line -> Integer.parseInt(line[1])));
		
		lines.close();

		return quadgrams;
	}
	
	public double logProbability(String key) {
		double temp = (double) dictionary.get(key.toUpperCase()) / this.totalQuadgrams;
		return Math.log10(temp);
	}
	
	public int getCount(String key) {
		return this.dictionary.get(key.toUpperCase());
	}

	public long getTotalQuadgrams() {
		return totalQuadgrams;
	}
}
