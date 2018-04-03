package ie.gmit.sw.ai.documents;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DocumentService {
	private String path;
	private List<String> docList;

	public DocumentService(String path) {
		this.path = path;
		this.docList = Arrays.stream(new File(path).listFiles())
			.map(file -> file.getName())
			.collect(Collectors.toList());
	}
	
	public void listDocuments() {
		docList.stream().forEach(i -> System.out.println(i));
	}
	
	public TextDocument createTextDocument(String name) throws IOException {
		String text = parseTextFile(name);
		return new TextDocument(name, text);
	}
	
	public String parseTextFile(String textFile) throws IOException {		
		return Files.lines(Paths.get(path + "/" + textFile)).collect(Collectors.joining());
	}
	
	public void writeToFile(String name, String text) {
		try {
			System.out.println("Writing to file...");
			Files.write(Paths.get(path + "/" + name), text.getBytes());
		} catch (IOException e) {
			System.out.println("Error writing to file");
		}
	}
}
