package ie.gmit.sw.ai.ui;

import java.util.Scanner;

import ie.gmit.sw.ai.documents.DocumentManager;

public class UserInterface {
	private boolean running;
	private Scanner sc;
	private DocumentManager docManager;
	
	public UserInterface() { 
		this.sc = new Scanner(System.in);
		this.docManager = new DocumentManager("./resources");

	}
	
	public void start() {
		running = true;
		do {
			displayMainMenu();
			switch(promptUserOption("\nEnter option: ")) {
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
			System.out.println("\n(1) List files");
			System.out.println("(2) Enter <filename.txt> or provide a URL from the internet");
			System.out.println("(3) Back");
			switch(promptUserOption("\nEnter option: ")) {
				case 1:
					// List files we have
					docManager.listDocuments();
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
