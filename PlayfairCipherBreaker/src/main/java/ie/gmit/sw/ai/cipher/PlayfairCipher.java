package ie.gmit.sw.ai.cipher;

import ie.gmit.sw.ai.cipher.digrams.Digramator;
import ie.gmit.sw.ai.keys.IKeyGenerator;
import ie.gmit.sw.ai.keys.KeyGenerator;

public class PlayfairCipher extends AbstractCipher {
	private IKeyGenerator keygen;
	private String key;
	private String[] digrams;
	
	public PlayfairCipher() {
		super();
	}

	public PlayfairCipher(String text) {
		super();
		this.digrams = createDigrams(text);
		this.keygen = KeyGenerator.getInstance();
	}

	public PlayfairCipher(String text, String secret) {
		super();
		this.digrams = createDigrams(text);
		this.keygen = KeyGenerator.getInstance();
		this.key = keygen.generateKey(secret);
	}
	
	public String[] createDigrams(String text) {
		return new Digramator().createDigrams(text);
	}
	
	public String getKey() {
		return key == null ? this.key = this.keygen.generateKey() : key;
	}
	
	/*
	 * Inline comments show the result below
	 * For example - take the digram GC with the matrix key
	 * - SECRTKYABDFGHILMNOPQUVWXZ
	 */
	public String decrypt(String key) {
		StringBuilder plainText = new StringBuilder();

		for (String ngram : this.digrams) {
			int row1 = key.indexOf(ngram.charAt(0)) / 5; // G - 11 / 5 = row 2
			int col1 = key.indexOf(ngram.charAt(0)) % 5; // G - 11 % 2 = column 1
			int row2 = key.indexOf(ngram.charAt(1)) / 5; // C - 2 / 5 = row 0
			int col2 = key.indexOf(ngram.charAt(1)) % 5; // C - 2 % 5 = column 2

			char chr1;
			char chr2;

			if (col1 == col2) { // same column Rule 3
				chr1 = key.charAt((row1 + 4) % 5 * 5 + col1);
				chr2 = key.charAt((row2 + 4) % 5 * 5 + col2);
			} else if (row1 == row2) { // same row Rule 2
				chr1 = key.charAt(row1 * 5 + (col1 + 4) % 5);
				chr2 = key.charAt(row2 * 5 + (col2 + 4) % 5);
			} else { // different rows & columns Rule 1
				chr1 = key.charAt(row1 * 5 + col2); // 2 * 5 + 2 = 12 = H
				chr2 = key.charAt(row2 * 5 + col1); // 0 * 5 + 1 = 1 = E
			}

			plainText.append(Character.toString(chr1) + Character.toString(chr2));
		}

		return plainText.toString();
	}

	/*
	 * Inline comments show the result below
	 * For example - take the digram HE with the matrix key
	 * - SECRTKYABDFGHILMNOPQUVWXZ
	 */
	public String encrypt(String key) {
		StringBuilder cipherText = new StringBuilder();

		for (String ngram : this.digrams) {
			int row1 = key.indexOf(ngram.charAt(0)) / 5; // H - 12 / 5 = 2
			int col1 = key.indexOf(ngram.charAt(0)) % 5; // H - 12 % 5 = 2
			int row2 = key.indexOf(ngram.charAt(1)) / 5; // E - 1 / 5 = 0
			int col2 = key.indexOf(ngram.charAt(1)) % 5; // E - 1 % 5 = 1
			
			char chr1;
			char chr2;
			
			if (col1 == col2) { // Rule 3
				chr2 = key.charAt(((row2 + 1) % 5 * 5 + col2));
				chr1 = key.charAt(((row1 + 1) % 5 * 5 + col1));
			} else if (row1 == row2) { // Rule 2
				chr1 = key.charAt(row1 * 5 + ((col1 + 1) % 5));
				chr2 = key.charAt(row2 * 5 + ((col2 + 1) % 5));
			} else { // Rule 1
				chr1 = key.charAt(row1 * 5 + col2); // 2 * 5 + 1 = 11 = G
				chr2 = key.charAt(row2 * 5 + col1); // 0 * 5 + 2 = 2 = C
			}

			cipherText.append(Character.toString(chr1) + Character.toString(chr2));
		}

		return cipherText.toString();
	}
}
