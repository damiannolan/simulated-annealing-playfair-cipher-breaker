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
    	
    	Random rand = new SecureRandom();

    	IKeyGenerator keygen = new KeyGenerator();
    	PlayfairCipher playfair = new PlayfairCipher();
    	FitnessCalculator fitness = new FitnessCalculator();
    	
    	String parent = keygen.generateKey();        
    	
    	String[] digrams = playfair.createDigrams(message);
    	String decrypted = playfair.decrypt(digrams, parent);

    	double parentScore = fitness.logProbability(decrypted);
       	double bestScore = parentScore;
       	    	
    	for(int temp = 20; temp > 0; temp = temp - 1) {
    		
    		for(int trans = 50000; trans > 0; trans = trans - 1) {
    			String childKey = keygen.shuffleKey(parent);
    			decrypted = playfair.decrypt(digrams, childKey);    			
    			double childScore = fitness.logProbability(decrypted);

    			double dF = childScore - parentScore;
    			
    			if(dF > 0) {
    				parentScore = childScore;
    				parent = childKey;
    			} else {    				
    				double prob = (Math.exp((dF / temp)));

    				if(prob > rand.nextDouble()) {
    					parentScore = childScore;
    					parent = childKey;
    				}
    			}
    			
    			if(parentScore > bestScore) {
    				bestScore = parentScore;
    				String bestKey = parent;
    				System.out.printf("\nBest Key: %s With Score: %.5f\n", bestKey, bestScore);
    		    	System.out.println("Decrypted Message: " + decrypted);
    		    	System.out.printf("With Temperature: %d and Transitions: %d\n", temp, trans);;
    			}
    			
    			
    		} // end transitions
    		
    		if(parentScore == bestScore) {
    			break;
    		}
    		
    	} // end temperature
    	
        long end = System.currentTimeMillis() - time;
        System.out.println(end + "ms");
    }
}
