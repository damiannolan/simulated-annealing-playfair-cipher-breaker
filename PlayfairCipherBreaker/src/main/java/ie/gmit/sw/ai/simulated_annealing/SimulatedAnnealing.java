package ie.gmit.sw.ai.simulated_annealing;

import ie.gmit.sw.ai.cipher.PlayfairCipher;
import ie.gmit.sw.ai.keys.IKeyGenerator;
import ie.gmit.sw.ai.keys.KeyGenerator;

public class SimulatedAnnealing {
	private FitnessCalculator fitness;
	private IKeyGenerator keygen;
	private PlayfairCipher cipher;
	private int temp;
	
	public SimulatedAnnealing(PlayfairCipher cipher, int temp) {
		super();
		this.fitness = new FitnessCalculator();
		this.keygen = KeyGenerator.getInstance();
		this.cipher = cipher;
		this.temp = temp;
	}
	
	public void start() {
		/*
		 * Bulk of algorithm goes here
		 */
	}
}
