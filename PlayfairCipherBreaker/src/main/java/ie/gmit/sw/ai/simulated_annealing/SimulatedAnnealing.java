package ie.gmit.sw.ai.simulated_annealing;

import java.security.SecureRandom;
import java.util.Random;

import ie.gmit.sw.ai.cipher.PlayfairCipher;
import ie.gmit.sw.ai.keys.IKeyGenerator;
import ie.gmit.sw.ai.keys.KeyGenerator;
import ie.gmit.sw.ai.keys.KeyShuffler;

public class SimulatedAnnealing {
	// probably don't need this
	// if PlayfairCipher contains the parentKey access via getKey()
	private IKeyGenerator keygen;
	private PlayfairCipher cipher;
	private FitnessCalculator fitness;
	private int temp;

	public SimulatedAnnealing(PlayfairCipher cipher, int temp) {
		super();
		this.fitness = new FitnessCalculator();
		this.keygen = KeyGenerator.getInstance();
		this.cipher = cipher;
		this.temp = temp;
	}

	public void start() {
		Random rand = new SecureRandom();
		String parentKey = keygen.generateKey();
		String decipheredText = cipher.decrypt(parentKey);

		double parentScore = fitness.logProbability(decipheredText);
		double bestScore = parentScore;

		for (int temp = this.temp; temp > 0; temp--) {
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
					String bestKey = parentKey;
					displayStats(bestKey, bestScore, decipheredText, temp, trans);
				}
			} // end transitions

			if (parentScore == bestScore) {
				break;
			}
		} // end temperature
	}

	public void displayStats(String bestKey, double bestScore, String decipheredText, int temp, int trans) {
		System.out.printf("\nBest Key: %s With Score: %.5f\n", bestKey, bestScore);
		System.out.println("Decrypted Message: " + decipheredText);
		System.out.printf("With Temperature: %d and Transitions: %d\n", temp, trans);
	}
}
