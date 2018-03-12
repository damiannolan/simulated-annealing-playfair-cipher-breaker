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
}
