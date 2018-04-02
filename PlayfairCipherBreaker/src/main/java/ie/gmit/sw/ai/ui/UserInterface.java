package ie.gmit.sw.ai.ui;

import java.util.Scanner;

import ie.gmit.sw.ai.cipher.PlayfairCipher;

public class UserInterface {
	private boolean running;
	private Scanner sc;
	private CipherBreakerFacade cipherBreaker;
		
	public UserInterface() { 
		this.sc = new Scanner(System.in);
		this.cipherBreaker = new CipherBreakerFacade();
	}
	
	public void start() {
		running = true;
		do {
			displayMainMenu();
			switch(promptUserOptionInt("\nEnter option: ")) {
				case 1:
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
			System.out.println("\n(1) List Cipher Text Resources");
			System.out.println("(2) Decrypt Cipher Text");
			System.out.println("(3) Back");
			switch(promptUserOptionInt("\nEnter option: ")) {
				case 1:
					cipherBreaker.listDocuments();
					break;
				case 2:
					cipherBreaker.setDocument(promptUserText("Please enter <filename.txt>: "));
					PlayfairCipher cipher = new PlayfairCipher(cipherBreaker.getDocument().getText());
					temperatureNotice();
					cipherBreaker.solve(cipher, promptUserOptionInt("Enter temperature: "));
					break;
				case 3:
					submenu = false;
					break;
			}
		} while(submenu);
	}
	
	private void temperatureNotice() {
		System.out.println("\n*NOTE: The starting temperature can have a major impact on the success of a Simulated Annealing Algorithm.");
		System.out.println("For Example: \n\t0 - 500 characters : Temperature = 10");	
		System.out.println("\t500 - 1000 characters : Temperature = 20");
	}
	
	private int promptUserOptionInt(String message) {
		System.out.print(message);
		return sc.nextInt();
	}
	
	private String promptUserText(String message) {
		System.out.print(message);
		return sc.next();
	}
}
