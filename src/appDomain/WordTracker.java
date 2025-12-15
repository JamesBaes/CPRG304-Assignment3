package appDomain;

import java.io.*;
import java.util.*;
import implementations.BSTree;
import implementations.BSTreeNode;
import implementations.WordData;
import utilities.Iterator;

/**
 * WordTracker class for tracking words across text files.
 * 
 * The class uses a Binary Search Tree of words and provides functions for
 * parsing text files and extracting words with their line numbers, storing and
 * retrieve word data with Java serialization, and generating output in various
 * formats.
 * 
 * @author Nathanael Lee, James Baes, Tony Do, Eian Verastigue
 * @version 1.3 Dec. 13, 2025 Class Description:
 * 
 */
public class WordTracker {

	private static final String REPO_FILE = "res/repository.ser";
	private String inputFileName;
	private BSTree<WordData> BST;

	/**
	 * Constructor for WordTracker class.
	 * 
	 * @param inputFileName The name of the text file to be processed.
	 */
	public WordTracker(String inputFileName) {
		this.inputFileName = inputFileName;
		BST = new BSTree<WordData>();
	}

	/**
	 * Parses the input text file and extracts all words with their line numbers It
	 * splits the line into words, removes any punctuation, and then adds the words
	 * to the BST. (or updates if there is an existing entry.)
	 */
	public void parse() {
		try (BufferedReader reader = new BufferedReader(new FileReader(inputFileName))) {
			String line;
			int lineNumber = 1;

			while ((line = reader.readLine()) != null) {

				// regex for splitting on any character that is not a number, letter, or
				// apostrophe.
				String[] words = line.split("[^a-zA-Z0-9']+");

				for (String word : words) {

					// skip when word is empty
					if (word.isEmpty()) {
						continue;
					}

					// regex for replacing
					word = word.replaceAll("^'+|'+$", "").toLowerCase();

					if (word.isEmpty()) {
						continue;
					}

					WordData searchKey = new WordData(word);

					BSTreeNode<WordData> node = BST.search(searchKey);

					if (node != null) {
						// word exists
						node.getElement().addOccurrence(inputFileName, lineNumber);
					} else {
						// new word so create a WordData and add to BST
						WordData newData = new WordData(word);
						newData.addOccurrence(inputFileName, lineNumber);
						BST.add(newData);
					}

				}
				lineNumber++;
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Loads existing word data from the repository.ser file If the file exists, it
	 * would load and restore previously stored word data If the file doesn't exist,
	 * the method would return.
	 */
	@SuppressWarnings("unchecked")
	public void loadRepository() {
		File repo = new File(REPO_FILE);

		if (!repo.exists()) {
			return;
		}

		try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(repo))) {

			BST = (BSTree<WordData>) input.readObject();
			input.close();
		} 
		catch (FileNotFoundException e){
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		} 
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Saves the current word tree to the repository.ser file
	 * This method serializes the entire BSTree, preserving all word data for future use.
	 */
	public void saveRepository() {
		try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(REPO_FILE))) {
			output.writeObject(BST);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	/**
	 * Prints word data based on the specified format option
	 * Writes to the provided output file path.
	 * 
     * @param outputFilePath the path of where the output file will be generated
     * @param Option determines what information will be displayed
	 */
	public void printWords(String Option, String outputFilePath) {
		if (!Option.equals("-pf") && !Option.equals("-po") && !Option.equals("-pl")) {
			System.out.println("Invalid option: " + Option);
			return;
		}
		
		Iterator<WordData> iterator = BST.inorderIterator();
		StringBuilder lineData = new StringBuilder();
		
		while (iterator.hasNext()) {
			// pf section
			WordData data = iterator.next();
			lineData.append("Key : ===" + data.getWord() + "=== ");

			Map<String, List<Integer>> fileLines = data.getFileLines();
			Set<String> fileNames = new TreeSet<>(fileLines.keySet());
			int totalCount = 0;
			
			for (String fileName : fileNames) {
				lineData.append(" found in file: " + fileName);
				
				// pl section
				if (Option.equals("-pl") || Option.equals("-po")) {
					List<Integer> lineNumbers = fileLines.get(fileName);
					Collections.sort(lineNumbers);
					
					lineData.append(" on lines: ");
					
					for (int i = 0; i < lineNumbers.size(); i++) {
						lineData.append(lineNumbers.get(i));
						
						totalCount = totalCount + lineNumbers.size();
						if (i < lineNumbers.size() - 1) {
							lineData.append(", ");
						}
					}
				}
				
				// po section
				if (Option.equals("-po")) {
					lineData.append(" number of entries: " + totalCount);
				}
			}
			lineData.append("\n");
		}
		System.out.println(lineData);
		if (outputFilePath != null) {
			try {
				writeData(outputFilePath, lineData);
			} catch (IOException e) {
				e.printStackTrace();
			}			
		}
	}
	
	/**
	 * Writes the output of the program into a file given by the user
	 * 
     * @param outputFile the path of where the output file will be generated
     * @param Data the data that will be stored into the output file
	 */
	private void writeData(String outputFile, StringBuilder Data) {
		File File = new File(outputFile);
		try {
			if (File.createNewFile()) {
				System.out.println("Created: " + File.getName() + " at path: " + File.getAbsolutePath());
			} else {
				System.out.println("Overwriting file: " + File.getName() + " at path: " + File.getAbsolutePath());
			}
			FileWriter myWriter = new FileWriter(outputFile);

			myWriter.write(data.toString());
			myWriter.close();
			System.out.println("Successfully wrote to the file.");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
