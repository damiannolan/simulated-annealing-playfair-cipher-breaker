package ie.gmit.sw.ai.simulated_annealing;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class FitnessMeasure {
	private static final String TEXTFILE = "4grams.txt"; 
	private Map<String, Integer> dictionary;
	private long totalQuadgrams;
	
	public FitnessMeasure() {
		try {
			this.dictionary = parse(TEXTFILE);
			this.totalQuadgrams = getTotalQuadgrams();
		} catch (Exception e) {
			System.out.println("Error occurred while parsing text" + TEXTFILE);
			e.printStackTrace();
		}	
	}
	
	private Map<String, Integer> parse(String fileName) throws IOException {		
		Stream<String> lines = Files.lines(Paths.get(fileName));
		
		Map<String, Integer> quadgrams = 
				lines.map(line -> line.split(" "))
				.collect(Collectors.toMap(line -> line[0], line -> Integer.parseInt(line[1])));
		
		lines.close();

		return quadgrams;
	}
	
	private long getTotalQuadgrams() {
		return dictionary.values().stream().mapToLong(i->i).sum();
	}
	
	private double nGramProbability(String key) {
//		double temp = (double) dictionary.get(key.toUpperCase()) / this.totalQuadgrams;
//		return Math.log10(temp);
		
		return Math.log10((double) dictionary.get(key.toUpperCase()) / this.totalQuadgrams);
	}
	
	public double logProbability(String cipherText) {					
		return IntStream.range(0, (cipherText.length() - 4 + 1))
				.mapToObj(i -> new String(cipherText.toCharArray(), i, 4))
				.mapToDouble(ngram -> nGramProbability(ngram)).sum();
	}
	
	public int getNGramFrequencyCount(String key) {
		return this.dictionary.get(key.toUpperCase());
	}
	
}
