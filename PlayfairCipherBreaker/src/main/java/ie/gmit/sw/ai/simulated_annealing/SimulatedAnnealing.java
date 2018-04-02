package ie.gmit.sw.ai.simulated_annealing;

import java.security.SecureRandom;
import java.util.Random;

import ie.gmit.sw.ai.cipher.PlayfairCipher;
import ie.gmit.sw.ai.keys.KeyShuffler;

public class SimulatedAnnealing {
	private FitnessCalculator fitness;
	private Random rand;
	
	public SimulatedAnnealing() {
		super();
		this.fitness = new FitnessCalculator();
		this.rand = new SecureRandom();
	}
	
	public SAResult solve(PlayfairCipher cipher, int temperature, boolean debug) {
		String parentKey = cipher.getKey();
		String bestKey = parentKey;
		String decipheredText = cipher.decrypt(parentKey);
		String bestText = decipheredText;
		double parentScore = fitness.logProbability(decipheredText);
		double bestScore = parentScore;
		
		
		for (int temp = temperature; temp > 0; temp--) {
			for (int trans = 50000; trans > 0; trans--) {
				String childKey = KeyShuffler.shuffleKey(parentKey);
				decipheredText = cipher.decrypt(childKey);
				double childScore = fitness.logProbability(decipheredText);

				double dF = childScore - parentScore;

				if (dF > 0) {
					parentScore = childScore;
					parentKey = childKey;
				} else {
					double prob = (Math.exp((dF / temp)));
					if (prob > rand.nextDouble()) {
						parentScore = childScore;
						parentKey = childKey;
					}
				}

				if (parentScore > bestScore) {
					bestScore = parentScore;
					bestKey = parentKey;
					bestText = decipheredText;
					
					if(debug == true) {
						debugStats(bestKey, bestScore, bestText, temp, trans);
					}
				}
			} // end transitions

			/*	
			 * 	This piece of code is used to prevent redundant iterations of our simulated annealing loop  
			 * 	
			 *	After a transitions cycle check if the parentScore is equal to the bestScore
			 * 	If they are still equal there is a good chance we are not making any improvements
			 * 	This will break the loop and return
			 * 
			 * 	However there a small chance this path may execute before we reach our desired output
			*/
			if (parentScore == bestScore) {
				break;
			}
		} // end temperature
		return new SAResult(bestKey, bestText, bestScore);
	}

	public void debugStats(String bestKey, double bestScore, String bestText, int temp, int trans) {
		System.out.printf("\nBest Key: %s With Score: %.5f\n", bestKey, bestScore);
		System.out.printf("Decrypted Message: %s\n", bestText);
		System.out.printf("At Temperature: %d and Transitions: %d\n", temp, trans);
	}
}
