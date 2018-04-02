package ie.gmit.sw.ai.ui;

import java.io.IOException;

import ie.gmit.sw.ai.cipher.PlayfairCipher;
import ie.gmit.sw.ai.documents.Document;
import ie.gmit.sw.ai.documents.DocumentService;
import ie.gmit.sw.ai.simulated_annealing.SAResult;
import ie.gmit.sw.ai.simulated_annealing.SimulatedAnnealing;

public class CipherBreakerFacade {
	private DocumentService docService;
	private PlayfairCipher cipher;
	private SimulatedAnnealing simulatedAnnealing;
	
	private Document doc;
	
	public CipherBreakerFacade() {
		this.docService = new DocumentService("./resources");
		this.simulatedAnnealing = new SimulatedAnnealing();
	}
	
	public void listDocuments() {
		System.out.println("\n*NOTE:\tThe following documents include both encrypted ciphertexts and plaintexts.");
		System.out.println("\tDocuments listed as enc_*.txt are intended to be used for the demonstration of Simulated Annealing Decryption.");
		System.out.println("\tIn contrast, Documents listed as plain_*.txt are intended for the demonstration of encryption.");
		System.out.println("\n=========Documents=========");
		this.docService.listDocuments();
	}
	
	public Document getDocument() {
		return this.doc;
	}
	
	public void setDocument(String filename) throws IOException {
		this.doc = this.docService.createTextDocument(filename);
		this.cipher = createCipher(doc);
	}
	
	public PlayfairCipher getCipher() {
		return this.cipher;
	}
	
	public PlayfairCipher createCipher(Document doc) {
		return new PlayfairCipher(doc.getText());
	}
	
	public void breakCipher(PlayfairCipher cipher, int temperature, boolean debug) {
		System.out.println("==========...Solving...=========");
		SAResult result = this.simulatedAnnealing.solve(cipher, temperature, debug);
		System.out.println("=============Result=============");
		System.out.println(result.toString());
		
		docService.writeToFile(doc.getName().substring(4), result.getTextResult());
	}
}
