package ie.gmit.sw.ai;

import ie.gmit.sw.ai.cipher.IKeyGenerator;
import ie.gmit.sw.ai.cipher.KeyGenerator;

public class CipherBreaker 
{
	static final String PLAYFAIR_ALPHABET = "ABCDEFGHIKLMNOPQRSTUVWXYZ";
	
    public static void main( String[] args )
    {
        System.out.println("Hello World!");
        
        IKeyGenerator keygen = new KeyGenerator();
        String key = keygen.generateKey();
        System.out.println(key);
        System.out.println(key.length());
        
        key = keygen.createKey("secretkey");
        System.out.println(key);
        System.out.println(key.length());
    }
}
