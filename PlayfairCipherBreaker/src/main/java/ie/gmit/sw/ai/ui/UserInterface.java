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
			displayMainMenu();
			switch(promptUserOption("\nEnter option: ")) {
				case 1:
					/*
					 * Prompt for file name
					 * Specify output directory
					 */
					displaySubMenu1();
					break;
				case 2:
					System.out.println("\nWork in progress");
					break;
				case 3:
					System.out.println("\nExiting application...");
					running = false;
					break;
				default:
					System.out.println("\nSorry - Invalid selection");
			}
		} while(running);
	}
	
	private void displayMainMenu() {
		System.out.println("============~~~Cipherator~~~=============");
		System.out.println("(1) Decrypt Playfair Cipher");
		System.out.println("(2) Encrypt Playfair Cipher");
		System.out.println("(3) Exit");
		System.out.println("=========================================");

	}
	
	private void displaySubMenu1() {
		boolean submenu = true;
		do {
			System.out.println("\n(1) Select file");
			System.out.println("(2) Enter a URL from the internet");
			System.out.println("(3) Back");
			switch(promptUserOption("\nEnter option: ")) {
				case 1:
					// List files we have
					System.out.println("\nWork in progress");
					break;
				case 2:
					// Enter a URL from the internet
					System.out.println("\nWork in progress");
					break;
				case 3:
					submenu = false;
					break;
			}
		} while(submenu);
		
	}
	
	private int promptUserOption(String message) {
		System.out.print(message);
		return sc.nextInt();
	}
}
