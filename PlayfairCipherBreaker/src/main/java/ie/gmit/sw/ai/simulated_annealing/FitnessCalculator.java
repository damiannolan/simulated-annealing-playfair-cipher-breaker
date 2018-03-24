package ie.gmit.sw.ai.simulated_annealing;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class FitnessCalculator {
	private static final String TEXTFILE = "4grams.txt"; 
	private Map<String, Integer> dictionary;
	private long totalQuadgrams;
	
	public FitnessCalculator() {
		try {
			this.dictionary = parse(TEXTFILE);
			this.totalQuadgrams = getTotalQuadgrams();
		} catch (Exception e) {
			System.out.println("Error occurred while parsing file: " + TEXTFILE);
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
	
	private double quadGramProbability(String key) {		
		return Math.log10((double) dictionary.get(key.toUpperCase()) / this.totalQuadgrams);
	}
	
	public double logProbability(String textString) {
		String text = textString.replace(" ",  "");
		
		return IntStream.range(0, (text.length() - 4 + 1))
				.mapToObj(i -> new String(text.toCharArray(), i, 4))
				.mapToDouble(ngram -> quadGramProbability(ngram)).sum();
	}
	
	public int getNGramFrequencyCount(String key) {
		// NOT BEING USED - COULD GET RID FOR FINAL
		return this.dictionary.get(key.toUpperCase());
	}
	
}
