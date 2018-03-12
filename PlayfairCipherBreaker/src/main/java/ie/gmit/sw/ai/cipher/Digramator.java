package ie.gmit.sw.ai.cipher;

public class Digramator implements IDigramator {
	
	public Digramator() { }
	
	public String[] createDigrams(String text) {
		text = primeText(text);
		
		String digrams[] = new String[text.length() / 2];
		int j = 0;
		
		for(int i = 0; i < text.length(); i = i + 2) {
			digrams[j] = text.substring(i, i + 2);
			j++;
		}
		
		return digrams;
	}
	
	private String primeText(String text) {
		// Regex \W replaces anything that isn't a word character (including punctuation)
		text = text.replaceAll("\\W", "");
		
		StringBuilder builder = new StringBuilder(text.toUpperCase());
		
		for(int i = 0; i < text.length() - 1; i += 2) {
			if(builder.charAt(i) == builder.charAt(i + 1)) {
				builder.insert(i + 1, "X");
			}
		}
		
		if(builder.length() % 2 == 1) {
			builder.append("X");
		}
		
		return builder.toString();
	}
}
