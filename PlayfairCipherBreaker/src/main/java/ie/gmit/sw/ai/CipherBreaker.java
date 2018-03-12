package ie.gmit.sw.ai;

import ie.gmit.sw.ai.cipher.PlayfairCipher;
import ie.gmit.sw.keygen.IKeyGenerator;
import ie.gmit.sw.keygen.KeyGenerator;

public class CipherBreaker {
	
    public static void main( String[] args ) {
        IKeyGenerator keygen = new KeyGenerator();
        String key = keygen.generateKey();
        System.out.println(key);
        System.out.println(key.length());
        
        key = keygen.createKey("secretkey");
        System.out.println(key);
        System.out.println(key.length());
        
        PlayfairCipher playfair = new PlayfairCipher(key);
        
        String[] digrams = playfair.createDigrams("Hello World, my guys!");
        String encrypted = playfair.encrypt(digrams, playfair.getKey());
        System.out.println("Encrypted: " + encrypted);
        
        digrams = playfair.createDigrams(encrypted);
        String decrypted = playfair.decrypt(digrams, playfair.getKey());
        System.out.println("Decrypted: " + decrypted);
    }
}
