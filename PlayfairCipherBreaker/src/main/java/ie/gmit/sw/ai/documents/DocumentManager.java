package ie.gmit.sw.ai.documents;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DocumentManager {
	private File[] fileArray;
	private List<String> docList;

	public DocumentManager(String path) {
		this.fileArray = new File("./resources").listFiles();
		
		this.docList = Arrays.stream(new File(path).listFiles())
			.map(file -> file.getName())
			.collect(Collectors.toList());
	}
	
	public void listDocuments() {
		//Stream<File> files = Arrays.stream(this.fileArray);
		//files.map(file -> file.getName()).forEach(file -> System.out.println(file));
		docList.stream().forEach(i -> System.out.println(i));
	}
}
