package ie.gmit.sw.ai.ui;

import java.io.IOException;

import ie.gmit.sw.ai.cipher.PlayfairCipher;
import ie.gmit.sw.ai.documents.Document;
import ie.gmit.sw.ai.documents.DocumentService;
import ie.gmit.sw.ai.simulated_annealing.SAResult;
import ie.gmit.sw.ai.simulated_annealing.SimulatedAnnealing;

public class CipherBreakerFacade {
	private DocumentService docService;
	private SimulatedAnnealing simulatedAnnealing;
	
	private Document doc;
	
	public CipherBreakerFacade() {
		this.docService = new DocumentService("./resources");
		this.simulatedAnnealing = new SimulatedAnnealing();
	}
	
	public void listDocuments(String path) {
		this.docService.listDocuments(path);
	}
	
	public Document getDocument() {
		return this.doc;
	}
	
	public void setDocument(String path, String filename) throws IOException {
		this.doc = this.docService.createTextDocument(path, filename);
	}
	
	public PlayfairCipher createCipher(Document doc) {
		return new PlayfairCipher(doc.getText());
	}
	
	public PlayfairCipher createCipher(Document doc, String secret) {
		return new PlayfairCipher(doc.getText(), secret);
	}
	
	public void breakCipher(int temperature, boolean debug) {
		PlayfairCipher cipher = new PlayfairCipher(this.doc.getText());		
		SAResult result = this.simulatedAnnealing.solve(cipher, temperature, debug);
		System.out.println(result.toString());
		
		docService.writeToFile("/decrypted", doc.getName(), result.getTextResult());
	}
	
	public void encrypt(String secret) {
		PlayfairCipher cipher = new PlayfairCipher(this.doc.getText(), secret);
		
		docService.writeToFile("/encrypted", this.doc.getName(), cipher.encrypt(cipher.getKey()));
	}
	
}
