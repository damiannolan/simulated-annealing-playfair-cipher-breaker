package ie.gmit.sw.ai;

import java.security.SecureRandom;
import java.util.Random;

import ie.gmit.sw.ai.cipher.PlayfairCipher;
import ie.gmit.sw.ai.keys.IKeyGenerator;
import ie.gmit.sw.ai.keys.KeyGenerator;
import ie.gmit.sw.ai.keys.KeyShuffler;
import ie.gmit.sw.ai.simulated_annealing.FitnessCalculator;

public class TestRunner {
	
    public static void main( String[] args ) {
    	long time = System.currentTimeMillis();
    	// Add a file parser for reading in cipherText
    	String cipherText = "HFZQLYVEDWNITIQPQDUVHYLGXZHFNYBKPACAZQHFVQIQCUUVYCBXABQZQZURHQDZHBKDMVZQHXRGURLQHTXZQVDFYXZHRGGWHBYEGXNYYEGKYVHFLQDBWDVQIZEAUCAHHPQIBRRVBREZNYYQAHPUQDUVHYZXGNRDEOZWQFKCLZZHXVRDEOFEINQZZKZPKDYDCAMEEQUDBCLDBKPAEDUVYCHFZQQEUMSVPBUMURLQHTXZXZCUHTVTPHMDLDRGMDLDVBHCMGUVYCQVPVDMSZXQCPDIQZLQKDUBEMTCYDDBCQGDFEUKQZVPCYUHKDIABDFVFEETGKIDOZEFURLQUVYCKDPTACYQUCFUPVVBBREZZXDTZPWCMEDILYTHZHADMUDBGQHBKIFEMDEWIZRGVQHTKCNWIEGNHCPLLUDPCOFTQGDPNWBYHCHFQZITQVGKUVYCHFBDQVHVHCHFDIYXHFBRUMLZKDZDFQFHNYLGSAPLQCCAZQHCPCBODITCVBMUHFDIYXHFBRUMLZKDLULIDLIDDLQRKWZQACYQUZBHZBDUBHQZUKUZEDGWTVBXABQZQZBUFEUFFTQVEKZQINAHMEPTDFNYFBIZEXBRRVBREZTCILEVFBEDHUBRWDLYTHFHIZNYCPOVBDLIZQHFQPQDUVHYLGCUNYOKDMPCHTXZPCGCHFDYLQDBLTHPQEKCGKTIQIBRVQHBQNDBRXBZEFRFVUEDQYNYMZCPBDHYLKCUXF";

    	IKeyGenerator keygen = KeyGenerator.getInstance();
    	FitnessCalculator fitness = new FitnessCalculator();
    	PlayfairCipher pf = new PlayfairCipher(cipherText);

//    	SimulatedAnnealing simulation = new SimulatedAnnealing(pf, 20);
//    	simulation.start();
    	
    	Random rand = new SecureRandom();
    	String parent = keygen.generateKey();    	
    	String decrypted = pf.decrypt(parent);
    	double parentScore = fitness.logProbability(decrypted);
       	double bestScore = parentScore;
       	    	
    	for(int temp = 20; temp > 0; temp = temp - 1) {    		
    		for(int trans = 50000; trans > 0; trans = trans - 1) {
    			String childKey = KeyShuffler.shuffleKey(parent);
    			decrypted = pf.decrypt(childKey);
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
    		    	System.out.printf("With Temperature: %d and Transitions: %d\n", temp, trans);
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