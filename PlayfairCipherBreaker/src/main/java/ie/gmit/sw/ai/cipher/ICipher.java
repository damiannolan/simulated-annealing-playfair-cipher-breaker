package ie.gmit.sw.ai.cipher;

public interface ICipher {
	
	public String encrypt(String[] digrams, String key);
	
	public String decrypt(String[] digrams, String key);
}
