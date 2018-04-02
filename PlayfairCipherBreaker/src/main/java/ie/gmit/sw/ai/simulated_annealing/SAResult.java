package ie.gmit.sw.ai.simulated_annealing;

public class SAResult {
	
	private String bestKey;
	private String textResult;
	private double bestScore;
	
	public SAResult(String bestKey, String textResult, double bestScore) {
		this.bestKey = bestKey;
		this.textResult = textResult;
		this.bestScore = bestScore;
	}
	
	public String toString() {
		return String.format("Best Key: %s With Score: %.5f\n"
				+ "Decrypted Message: %s\n",
				bestKey, bestScore, textResult);
	}

	public String getBestKey() {
		return bestKey;
	}

	public void setBestKey(String bestKey) {
		this.bestKey = bestKey;
	}

	public String getTextResult() {
		return textResult;
	}

	public void setTextResult(String textResult) {
		this.textResult = textResult;
	}

	public double getBestScore() {
		return bestScore;
	}

	public void setBestScore(double bestScore) {
		this.bestScore = bestScore;
	}	
}
