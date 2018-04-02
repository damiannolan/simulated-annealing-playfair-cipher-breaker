package ie.gmit.sw.ai.ui;

import ie.gmit.sw.ai.cipher.PlayfairCipher;
import ie.gmit.sw.ai.documents.Document;
import ie.gmit.sw.ai.documents.DocumentService;
import ie.gmit.sw.ai.documents.TextDocument;
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
		this.docService.listDocuments();
	}
	
	public Document getDocument() {
		return this.doc;
	}
	
	public void setDocument(String filename) {
		this.doc = this.docService.newTextDocument(filename);
	}
	
	public PlayfairCipher createCipher(TextDocument doc) {
		return new PlayfairCipher(doc.getText());
	}
	
	public void solve(PlayfairCipher cipher, int temperature) {
		this.simulatedAnnealing.solve(cipher, temperature);
	}
}
