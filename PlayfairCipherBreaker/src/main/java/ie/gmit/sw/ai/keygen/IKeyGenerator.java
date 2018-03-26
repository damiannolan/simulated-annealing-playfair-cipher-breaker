package ie.gmit.sw.ai.keygen;

public interface IKeyGenerator {
	
	public String generateKey();
	
	public String generateKey(String textString);
	
	public String shuffleKey(String key);
}
