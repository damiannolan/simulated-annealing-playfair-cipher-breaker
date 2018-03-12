package ie.gmit.sw.ai.cipher;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class KeyGenerator implements IKeyGenerator {
	private String key;
	private Random rng;
	
	public KeyGenerator() {
		this.rng = new SecureRandom();
	}
	
	public String generateKey(String alphabet) {
		List<Character> sequence = new ArrayList<Character>();
		
		for(char ch : alphabet.toLowerCase().toCharArray()) {
			sequence.add(ch);
		}
		
		Collections.shuffle(sequence, this.rng);
		
		StringBuilder builder = new StringBuilder(sequence.size());
		
		for(Character ch : sequence) {
			builder.append(ch);
		}
		
		this.key = builder.toString();
		return this.key;
	}

}
