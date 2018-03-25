package ie.gmit.sw.ai;

import java.security.SecureRandom;
import java.util.Random;

import ie.gmit.sw.ai.cipher.PlayfairCipher;
import ie.gmit.sw.ai.keygen.IKeyGenerator;
import ie.gmit.sw.ai.keygen.KeyGenerator;
import ie.gmit.sw.ai.simulated_annealing.FitnessCalculator;

public class CipherBreaker {
	
    public static void main( String[] args ) {
    	long time = System.currentTimeMillis();
    	String message = "HFZQLYVEDWNITIQPQDUVHYLGXZHFNYBKPACAZQHFVQIQCUUVYCBXABQZQZURHQDZHBKDMVZQHXRGURLQHTXZQVDFYXZHRGGWHBYEGXNYYEGKYVHFLQDBWDVQIZEAUCAHHPQIBRRVBREZNYYQAHPUQDUVHYZXGNRDEOZWQFKCLZZHXVRDEOFEINQZZKZPKDYDCAMEEQUDBCLDBKPAEDUVYCHFZQQEUMSVPBUMURLQHTXZXZCUHTVTPHMDLDRGMDLDVBHCMGUVYCQVPVDMSZXQCPDIQZLQKDUBEMTCYDDBCQGDFEUKQZVPCYUHKDIABDFVFEETGKIDOZEFURLQUVYCKDPTACYQUCFUPVVBBREZZXDTZPWCMEDILYTHZHADMUDBGQHBKIFEMDEWIZRGVQHTKCNWIEGNHCPLLUDPCOFTQGDPNWBYHCHFQZITQVGKUVYCHFBDQVHVHCHFDIYXHFBRUMLZKDZDFQFHNYLGSAPLQCCAZQHCPCBODITCVBMUHFDIYXHFBRUMLZKDLULIDLIDDLQRKWZQACYQUZBHZBDUBHQZUKUZEDGWTVBXABQZQZBUFEUFFTQVEKZQINAHMEPTDFNYFBIZEXBRRVBREZTCILEVFBEDHUBRWDLYTHFHIZNYCPOVBDLIZQHFQPQDUVHYLGCUNYOKDMPCHTXZPCGCHFDYLQDBLTHPQEKCGKTIQIBRVQHBQNDBRXBZEFRFVUEDQYNYMZCPBDHYLKCUXF";
    	System.out.println(message.length());
    	KeyGenerator keygen = new KeyGenerator();
        String parent = keygen.generateKey();
        System.out.println(parent);
        System.out.println(parent.length());
        
        keygen.printCipherMatrix(parent);
        System.out.println();
        parent = keygen.keySwapCols(parent, 0, 1);
        keygen.printCipherMatrix(parent);
        System.out.println();

        parent = keygen.keySwapChars(parent, "A", "B");
        keygen.printCipherMatrix(parent);
        
    	PlayfairCipher playfair = new PlayfairCipher();
    	String[] digrams = playfair.createDigrams(message);
    	String decrypted = playfair.decrypt(digrams, parent);

    	FitnessCalculator fitness = new FitnessCalculator();
    	System.out.println(fitness.logProbability(decrypted));
    	double parentScore = fitness.logProbability(decrypted);
    	  	
    	String bestKey = "";
    	String decMessage = "";
    	String childKey;
    	double childScore;
    	double dF;
    	Random rand = new SecureRandom();
    	double bestScore = parentScore;
    	
    	for(int temp = 20; temp > 0; temp = temp - 1) {
    		for(int trans = 50000; trans >= 0; trans--) {
    			childKey = keygen.shuffleKey(parent);
    			//System.out.println(child);
    			decrypted = playfair.decrypt(digrams, childKey);
    			
    			childScore = fitness.logProbability(decrypted);

    			dF = childScore - parentScore;
    			//System.out.println("dF = " + dF);
    			
    			if(dF > 0) {
    				parentScore = childScore;
    				parent = childKey;
    			} else if(dF < 0) {
    				
    				double prob = (Math.exp((dF / temp)));
    				//System.out.println("Probability = " + prob);
    				if(prob > rand.nextDouble()) {
    					parentScore = childScore;
    					parent = childKey;
    				}
    			}
    			
    			if(parentScore > bestScore) {
    				bestScore = parentScore;
    				bestKey = parent;
    				decMessage = decrypted;
    				System.out.println("Best Key: " + bestKey);
    		    	System.out.println("Decrypted Message: " + decMessage);
    			}
    		}
    	}
    	
    	System.out.println("Key: " + bestKey);
    	System.out.println("Decrypted Message: " + decMessage);
    	
 
       	//System.out.println(fitness.logProbability("HAPPYDAYS"));
        /*
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
        */
        long end = System.currentTimeMillis() - time;
        System.out.println(end + "ms");
    }
}
