package ie.gmit.sw.ai.ui;

import java.io.IOException;
import java.util.Scanner;

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
					decryptionMenu();
					break;
				case 2:
					encryptionMenu();
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
		System.out.println("============~~~CipherBreaker~~~=============");
		System.out.println("(1) Decrypt Playfair Cipher");
		System.out.println("(2) Encrypt Playfair Cipher");
		System.out.println("(3) Exit");
		System.out.println("============================================");
	}
	
	private void decryptionMenu() {
		boolean submenu = true;
		do {
			System.out.println("\n(1) List Resources");
			System.out.println("(2) Decrypt Cipher Text");
			System.out.println("(3) Back");
			switch(promptUserOptionInt("\nEnter option: ")) {
				case 1:
					cipherBreaker.listDocuments();
					break;
				case 2:
					setTextDocument();
					int temp = getTemperature();
					boolean debug = getDebug();
					
					cipherBreaker.breakCipher(temp, debug);
					break;
				case 3:
					submenu = false;
					break;
			}
		} while(submenu);
	}
	
	private void encryptionMenu() {
		boolean submenu = true;
		
		do {
			System.out.println("\n(1) List Resources");
			System.out.println("(2) Encrypt Plain Text");
			System.out.println("(3) Back");
			
			switch(promptUserOptionInt("\nEnter option: ")) {
				case 1:
					cipherBreaker.listDocuments();
					break;
				case 2:
					setTextDocument();					
					String secret = promptUserText("Please enter a key secret using chars[A-Z]: ");					
					cipherBreaker.encrypt(secret);					
				case 3:
					submenu = false;
					break;
				
			}
		} while(submenu);	
	}
	
	private void setTextDocument() {
		try {
			cipherBreaker.setDocument(promptUserText("Please enter <filename.txt>: "));
		} catch (IOException e) {
			System.out.println("\nError Parsing TextDocument: ");
			System.out.println("Please ensure the file you have specified exists");
			setTextDocument();
		}
	}
	
	private int getTemperature() {
		System.out.println("\n*NOTE: The starting temperature can have a major impact on the success of a Simulated Annealing Algorithm.");
		System.out.println("For Example: \n\t0 - 500 characters : Temperature = 10");	
		System.out.println("\t500 - 1000 characters : Temperature = 20");
		
		return promptUserOptionInt("Enter temperature: ");
	}
	
	private boolean getDebug() {
		System.out.println("\nProvide debugging stats while solving: ");
		System.out.println("\t(1) Yes");
		System.out.println("\t(2) No");
		
		switch(promptUserOptionInt("\nEnter option: ")) {
			case 1:
				return true;
			case 2:
				return false;
			default:
				return getDebug();
		}
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
