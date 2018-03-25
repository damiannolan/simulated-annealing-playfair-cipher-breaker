package ie.gmit.sw.ai.ui;

import java.util.Scanner;

public class UserInterface {
	
	private boolean running;
	private Scanner sc;
	
	public UserInterface() { 
		sc = new Scanner(System.in);
	}
	
	public void start() {
		running = true;
		do {
			displayMenu();
			switch(promptUserOption("\nEnter option: ")) {
				case 1:
					/*
					 * Prompt for file name
					 * Specify output directory
					 */
					System.out.println("\nWork in progress");
					break;
				case 2:
					System.out.println("\nWork in progress");
					break;
				case 3:
					System.out.println("\nExiting application...");
					running = false;
					break;
			}
		} while(running);
	}
	
	private void displayMenu() {
		System.out.println("============~~~Cipherator~~~=============");
		System.out.println("(1) Decrypt Playfair Cipher");
		System.out.println("(2) Encrypt Playfair Cipher");
		System.out.println("(3) Exit");
		System.out.println("=========================================");

	}
	
	private int promptUserOption(String message) {
		System.out.print(message);
		return sc.nextInt();
	}
}
