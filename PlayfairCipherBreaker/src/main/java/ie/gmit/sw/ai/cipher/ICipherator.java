package ie.gmit.sw.ai.cipher;

public interface ICipherator {
	
	public String encrypt(String[] digrams, String key);
	
	public String decrypt(String[] digrams, String key);
}
