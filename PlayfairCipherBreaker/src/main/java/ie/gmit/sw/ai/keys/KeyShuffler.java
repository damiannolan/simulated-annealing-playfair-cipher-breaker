package ie.gmit.sw.ai.keys;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;

public class KeyShuffler {
	private static Random rng = new SecureRandom();
	
	public static String shuffleKey(String key) {
		switch(rng.nextInt(100)) {
			case 0:
			case 1:
				return keyReverse(key);
			case 2:
			case 3:
				return keySwapCols(key, rng.nextInt(5), rng.nextInt(5));
			case 4:
			case 5:
				return keySwapRows(key, rng.nextInt(5), rng.nextInt(5));
			case 6:
			case 7:
				return keyFlipCols(key);
			case 8:
			case 9:
				return keyFlipRows(key);
			default:
				String ch1 = Character.toString(key.charAt(rng.nextInt(25)));
				String ch2 = Character.toString(key.charAt(rng.nextInt(25)));
				return keySwapChars(key, ch1, ch2);
		}
	}

	private static String keySwapChars(String key, String one, String two) {	
		return Arrays.stream(key.split(one, -1))
				.map(s -> s.replaceAll(two, one))
				.collect(Collectors.joining(two));
	}
	
	private static String keyReverse(String key) {
		return new StringBuilder(key).reverse().toString();
	}
	
	private static String keySwapCols(String key, int c1, int c2) {
		char[] cKey = key.toCharArray();
		
		for(int i = 0; i < 5; i++) {
			char temp = cKey[i * 5 + c1];
			cKey[i * 5 + c1] = cKey[i * 5 + c2];
			cKey[i * 5 + c2] = temp;
		}
		
		return new String(cKey);
	}
	
	private static String keySwapRows(String key, int r1, int r2) {
		char[] cKey = key.toCharArray();
		
		for(int i = 0; i < 5; i++) {
			char temp = cKey[r1 * 5 + i];
			cKey[r1 * 5 + i] = cKey[r2 * 5 + i];
			cKey[r2 * 5 + i] = temp;
		}
		
		return new String(cKey);
	}
	
	private static String keyFlipCols(String key) {
		char[][] keyMatrix = toMatrix(key);
		
		for(int col = 0; col < keyMatrix[0].length; col++) {
			for(int row = 0; row < keyMatrix.length / 2; row++) {
				char temp = keyMatrix[row][col];
                keyMatrix[row][col] = keyMatrix[keyMatrix.length - row - 1][col];
                keyMatrix[keyMatrix.length - row - 1][col] = temp;
			}
		}

		return matrixToString(keyMatrix);
	}
	
	private static String keyFlipRows(String key) {
		char[][] keyMatrix = toMatrix(key);
		
		for(int row = 0; row < keyMatrix.length; row++){
	        for(int col = 0; col < keyMatrix[row].length / 2; col++) {
	            char temp = keyMatrix[row][col];
	            keyMatrix[row][col] = keyMatrix[row][keyMatrix[row].length - col - 1];
	            keyMatrix[row][keyMatrix[row].length - col - 1] = temp;
	        }
	    }
		
		return matrixToString(keyMatrix);
	}
	
	private static char[][] toMatrix(String key) {
		char[][] keyMatrix = new char[5][5];
		int index = 0;
		
		for(int i = 0; i < 5; i++) {
			for(int j = 0; j < 5; j++) {
				keyMatrix[i][j] = key.charAt(index);
				index++;
			}
		}
		return keyMatrix;
	}
	
	private static String matrixToString(char[][] keyMatrix) {
		StringBuilder sb = new StringBuilder(keyMatrix.length);
		
		for(int i = 0; i < 5; i++) {
			for(int j = 0; j < 5; j++) {
				sb.append(keyMatrix[i][j]);
			}
		}
		
		return sb.toString();
	}

}
