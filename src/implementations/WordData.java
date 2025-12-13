package implementations;

import java.util.ArrayList;


public class WordData implements Comparable<WordData> {
	private String word;
	private String fileName;
	private ArrayList<Integer> fileLineNumbers;

	public WordData(String word, String fileName) {
		this.word = word;
		this.fileName = fileName;
		this.fileLineNumbers = new ArrayList<Integer>();
	}

	public String getWord() {
		return word;
	}

	public void addOccurrence(int lineNumber) {
		if (!fileLineNumbers.contains(lineNumber)) {
			fileLineNumbers.add(lineNumber);			
		}
	}
	
	public String getLineNumbers() {
		String lineNumbers = "";
		for (int i = 0; i < fileLineNumbers.size(); i++) {
			lineNumbers = lineNumbers + fileLineNumbers.get(i) + ", ";
		}
		return lineNumbers;
	}

	public String getFileName() {
		return fileName;
	}
	
	@Override
	public int compareTo(WordData other) {
		return this.word.compareTo(other.word);
	}
}
