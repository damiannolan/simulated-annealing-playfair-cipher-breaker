package ie.gmit.sw.ai;

import ie.gmit.sw.ai.cipher.IKeyGenerator;
import ie.gmit.sw.ai.cipher.KeyGenerator;
import ie.gmit.sw.ai.cipher.PlayfairCipher;

public class CipherBreaker 
{
	static final String PLAYFAIR_ALPHABET = "ABCDEFGHIKLMNOPQRSTUVWXYZ";
	
    public static void main( String[] args )
    {
    	String message = "Hello World";
    	String encMessage = "GCIZHQCWTIBZ";
        
        IKeyGenerator keygen = new KeyGenerator();
        String key = keygen.generateKey();
        System.out.println(key);
        System.out.println(key.length());
        
        key = keygen.createKey("secretkey");
        System.out.println(key);
        System.out.println(key.length());
        
        PlayfairCipher playfair = new PlayfairCipher(key);
        String[] digrams = playfair.createDigrams(message);
        
        for(int i = 0; i < digrams.length; i++) {
        	System.out.println(digrams[i]);
        }
        
       digrams = playfair.createDigrams(encMessage.toUpperCase());
       String plaintext = playfair.decrypt(digrams, playfair.getKey());
       System.out.println(plaintext);

    }
}
