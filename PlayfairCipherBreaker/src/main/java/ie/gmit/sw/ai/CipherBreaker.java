package ie.gmit.sw.ai;

import ie.gmit.sw.ai.cipher.PlayfairCipher;
import ie.gmit.sw.ai.keygen.IKeyGenerator;
import ie.gmit.sw.ai.keygen.KeyGenerator;
import ie.gmit.sw.ai.simulated_annealing.FitnessMeasure;

public class CipherBreaker {
	
    public static void main( String[] args ) {
    	long time = System.currentTimeMillis();
   
    	FitnessMeasure fm = new FitnessMeasure();
       	System.out.println(fm.logProbability("HAPPYDAYS"));
    	
        IKeyGenerator keygen = new KeyGenerator();
        String key = keygen.generateKey();
        System.out.println(key);
        System.out.println(key.length());
        
        key = keygen.createKey("THEQUICKBROWNFXMPDVLAZYGS");
        System.out.println(key);
        System.out.println(key.length());
        
        PlayfairCipher playfair = new PlayfairCipher(key);
        
        String[] digrams = playfair.createDigrams("Hello World, my hell");
        //String[] digrams = playfair.createDigrams("HEQEFIRCHITZMHUKOTXEDKWLHKHQVDSIEAKOZTXMTKOEEQSBXTDYHEUKUDBMKYZTFIRCEOMIYOZAEAMKIUZNQHTWDUOBVUDUPNOIEHEQKDLYWXNWILAZDYYOFTWAGADTVUDXXIEKITLKGKSIUYYOYETWDUOCHEFWHEKOABOKHUIMAREMWNFWFWIUNTTIOIOZAZTWFBRCHEXMTYDYHETIDTYOZRMAXWIUOITZMYDHBXHKUMSKXTDYMNXIMRNKZMXIHEXMHDTOOEKYSBQUKDOCHEZATCDEEKVUMXFCISGUYRFWRCOEEQUNZIIAKMVMQHEQMN");
        String encrypted = playfair.encrypt(digrams, playfair.getKey());
        System.out.println("Encrypted: " + encrypted);
        
        digrams = playfair.createDigrams(encrypted);
        String decrypted = playfair.decrypt(digrams, playfair.getKey());
        System.out.println("Decrypted: " + decrypted);
        
        long end = System.currentTimeMillis() - time;
        System.out.println(end + "ms");
    }
}
