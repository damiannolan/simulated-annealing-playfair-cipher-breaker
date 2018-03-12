package ie.gmit.sw.ai.cipher;

public class Playfair {
	private String key;
	
	public Playfair(String key) {
		this.key = key;
	}
	
	public String getKey() {
		return this.key;
	}
	
	public void setKey(String key) {
		this.key = key;
	}
}
