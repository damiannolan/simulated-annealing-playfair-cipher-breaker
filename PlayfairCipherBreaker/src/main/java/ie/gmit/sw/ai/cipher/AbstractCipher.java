package ie.gmit.sw.ai.cipher;

public abstract class AbstractCipher {
	private String key;
	
	public AbstractCipher() {
		super();
	}
	
	public abstract String encrypt(String[] digrams, String key);
	
	public abstract String decrypt(String[] digrams, String key);
	
	public String getKey() {
		return this.key;
	}
	
	public void setKey(String key) {
		this.key = key;
	}
}
