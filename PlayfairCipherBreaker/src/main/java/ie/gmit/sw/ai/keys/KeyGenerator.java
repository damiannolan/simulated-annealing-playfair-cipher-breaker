package ie.gmit.sw.ai.keys;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class KeyGenerator implements IKeyGenerator {
	private static final String PLAYFAIR_ALPHABET = "ABCDEFGHIKLMNOPQRSTUVWXYZ";
	private static IKeyGenerator keygen;
	private Random rng;

	private KeyGenerator() {
		this.rng = new SecureRandom();
	}
	
	public static IKeyGenerator getInstance() {
		return keygen == null ? keygen = new KeyGenerator() : keygen;
	}

	public String generateKey() {
		List<Character> sequence = new ArrayList<Character>();
		
		for (char ch : PLAYFAIR_ALPHABET.toCharArray()) {
			sequence.add(ch);
		}

		Collections.shuffle(sequence, this.rng);
		StringBuilder builder = new StringBuilder(sequence.size());

		for (Character ch : sequence) {
			builder.append(ch);
		}

		return builder.toString();
	}

	public String generateKey(String secret) {
		String key = secret.toUpperCase().replace("J", "I") + PLAYFAIR_ALPHABET;

		return removeDuplicates(key);
	}

	private static String removeDuplicates(String str) {
		// https://codereview.stackexchange.com/questions/46777/eliminate-duplicates-from-strings
		boolean seen[] = new boolean[256];
		StringBuilder sb = new StringBuilder(seen.length);

		for (int i = 0; i < str.length(); i++) {
			char ch = str.charAt(i);
			if (!seen[ch]) {
				seen[ch] = true;
				sb.append(ch);
			}
		}

		return sb.toString();
	}
}
