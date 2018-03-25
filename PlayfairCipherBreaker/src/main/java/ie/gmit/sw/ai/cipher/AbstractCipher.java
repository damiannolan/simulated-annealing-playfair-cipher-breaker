package ie.gmit.sw.ai.cipher;

public abstract class AbstractCipher implements ICipher {
	
	public AbstractCipher() {
		super();
	}
	
	public abstract String encrypt(String[] digrams, String key);
	
	public abstract String decrypt(String[] digrams, String key);
	
}
