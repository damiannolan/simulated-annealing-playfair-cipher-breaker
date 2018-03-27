package ie.gmit.sw.ai.ui;

import java.util.Scanner;

import ie.gmit.sw.ai.cipher.PlayfairCipher;
import ie.gmit.sw.ai.documents.DocumentService;
import ie.gmit.sw.ai.documents.TextDocument;

public class UserInterface {
	private boolean running;
	private Scanner sc;
	private DocumentService docService;
	
	public UserInterface() { 
		this.sc = new Scanner(System.in);
		this.docService = new DocumentService("./resources");

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
			System.out.println("\n(1) List files");
			System.out.println("(2) Enter <filename.txt>");
			System.out.println("(3) Back");
			switch(promptUserOptionInt("\nEnter option: ")) {
				case 1:
					docService.listDocuments();
					break;
				case 2:
					TextDocument doc = docService.newTextDocument(promptUserText("Please provide <filename.txt>: "));
					PlayfairCipher cipher = new PlayfairCipher(doc.getText());
					System.out.println(doc.getText());
					break;
				case 3:
					submenu = false;
					break;
			}
		} while(submenu);
		
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
