package ie.gmit.sw.ai.ui;

import java.io.IOException;

import ie.gmit.sw.ai.cipher.PlayfairCipher;
import ie.gmit.sw.ai.documents.Document;
import ie.gmit.sw.ai.documents.DocumentService;
import ie.gmit.sw.ai.documents.TextDocument;
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
	
	public void listDocuments() {
		System.out.println("=========Documents=========");
		this.docService.listDocuments();
	}
	
	public Document getDocument() {
		return this.doc;
	}
	
	public void setDocument(String filename) throws IOException {
		this.doc = this.docService.createTextDocument(filename);
	}
	
	public PlayfairCipher createCipher(TextDocument doc) {
		return new PlayfairCipher(doc.getText());
	}
	
	public void solve(PlayfairCipher cipher, int temperature, boolean debug) {
		SAResult result = this.simulatedAnnealing.solve(cipher, temperature, debug);
		System.out.println("Result: " + result.toString());
	}
}
