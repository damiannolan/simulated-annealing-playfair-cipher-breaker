package ie.gmit.sw.ai.documents;

public class TextDocument implements Document {
	private String name;
	private String text;
	
	public TextDocument(String name, String text) {
		this.name = name;
		this.text = text;
	}
	
	@Override
	public String getText() {
		return this.text;
	}

	@Override
	public String getName() {
		return this.name;
	}
	
}
