package ie.gmit.sw.ai.documents;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Collectors;

public class DocumentService {
	private String basePath;

	public DocumentService(String basePath) {
		this.basePath = basePath;
	}
	
	public void listDocuments(String path) {
		Arrays.stream(new File(basePath + path).listFiles())
			.map(file -> file.getName())
			.forEach(i -> System.out.println(i));		
	}
	
	public TextDocument createTextDocument(String path, String name) throws IOException {
		String text = parseTextFile(path, name);
		return new TextDocument(name, text);
	}
	
	public String parseTextFile(String path, String textFile) throws IOException {		
		return Files.lines(Paths.get(basePath + path + "/" + textFile)).collect(Collectors.joining());
	}
	
	public void writeToFile(String path, String name, String text) {
		try {
			System.out.println("Writing to file...");
			Files.write(Paths.get(basePath + path + "/" + name), text.getBytes());
		} catch (IOException e) {
			System.out.println("Error writing to file");
		}
	}
}
