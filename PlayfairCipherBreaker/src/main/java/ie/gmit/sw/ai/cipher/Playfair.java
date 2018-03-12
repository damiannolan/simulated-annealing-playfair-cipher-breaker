package ie.gmit.sw.ai.cipher;

public class Playfair {
	private String key;
	private Digramator digramator;
	
	public Playfair(String key) {
		this.key = key;
		this.digramator = new Digramator();
	}
	
	public String getKey() {
		return this.key;
	}
	
	public void setKey(String key) {
		this.key = key;
	}
	
	public String[] createDigrams(String text) {
		return digramator.createDigrams(text);
	}
	
	public String decrypt(String[] digrams, String key) {		
		StringBuilder plainText = new StringBuilder();
		System.out.println(key);
		
		for (String ngram : digrams) {
			// For example - take the digram GC with the matrix key SECRTKYABDFGHILMNOPQUVWXZ
			int row1 = (key.indexOf(ngram.charAt(0)) / 5); // G - 11 / 5 = row 2
            int col1 = (key.indexOf(ngram.charAt(0)) % 5); // G - 11 % 2 = column 1
            int row2 = (key.indexOf(ngram.charAt(1)) / 5); // C - 2 / 5 = row 0
            int col2 = (key.indexOf(ngram.charAt(1)) % 5); // C - 2 % 5 = column 2 
            
            char chr1;
            char chr2;
            
            if (col1 == col2) { // same column Rule 3
                chr1 = key.charAt((row1 + 4) % 5 * 5 + col1);
                chr2 = key.charAt((row2 + 4) % 5 * 5 + col2);
            } else if (row1 == row2) { // same row Rule 2
                chr1 = key.charAt(row1 * 5 +(col1 +4) % 5);
                chr2 = key.charAt(row2 * 5 +(col2 +4) % 5);
            } else { // different rows & columns Rule 1
                chr1 = key.charAt(row1 * 5 + col2); // 2 * 5 + 2 = 12 = H
                chr2 = key.charAt(row2 * 5 + col1); // 0 * 5 + 1 = 1 = E
            }
            
            plainText.append(Character.toString(chr1) + Character.toString(chr2));
		}
		
		return plainText.toString();
	}
}
