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
 * parsing text files and extracting words with their line numbers,
 * storing and retrieve word data with Java serialization, and
 * generating output in various formats.
 * 
 * @author Nathanael Lee, James Baes, Tony Do, Eian Verastigue
 * @version 1.2 Dec. 12, 2025 Class Description:
 * 
 */
public class WordTracker {
	
	private static final String REPO_FILE = "repository.ser";
	private String inputFileName;
	private BSTree<WordData> BST;

	
	/**
	 * Constructor for WordTracker class.
	 * @param inputFileName The name of the text file to be processed.
	 */
	public WordTracker(String inputFileName) {
		this.inputFileName = inputFileName;
		BST = new BSTree<WordData>();
	}
	
	
	/**
	 * Parses the input text file and extracts all words with their line numbers
	 * It splits the line into words, removes any punctuation, and then adds the words to the BST.
	 * (or updates if there is an existing entry.)
	 */
	public void parse() {
		try (BufferedReader reader = new BufferedReader(new FileReader(inputFileName))) {
			String line;
			int lineNumber = 1;
			
			while ((line = reader.readLine()) != null) {
				
				// regex for splitting on any character that is not a number, letter, or apostrophe.
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
	 * Loads existing word data from the repository.ser file
	 * If the file exists, it would load and restore previously stored word data
	 * If the file doesn't exist, the method would return.
	 */
	@SuppressWarnings("unchecked")
	public void loadRepository() {
		File repo = new File(REPO_FILE);
		
		if (!repo.exists()) {
			return;
		}
		
		try (ObjectInputStream input = new ObjectInputStream( new FileInputStream(repo))) {
			
			BST = (BSTree<WordData>) input.readObject();
		}
		catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Saves the current word tree to the repository.ser file
	 * 
	 * This method serializes the entire BSTree, preserving all word data for future use.
	 */
	public void saveRepository() {
		try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(REPO_FILE))) {
			
			output.writeObject(BST);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Main print method that based on a given option, would display an output accordingly.
	 * @param output The PrintStream to write output to.
	 * @param option The print format option.
	 */
	public void printWords(PrintStream output, String option) {
		if(option.equals("-pf") ) {
			printWordsWithFiles(output);
		} else if (option.equals("-pl")) {
			printWordsWithLines(output);
		} else if (option.equals("-po")) {
			printWordsWithOccurrences(output);
		} else {
			output.println("Invalid option: " + option);
		}
		
	}
	
	
	
	
	/**
	 * Helper method used for -pf option
	 * Prints words with their associated files.
	 * @param output the PrintStream to write output to
	 */
	private void printWordsWithFiles(PrintStream output) {
		Iterator<WordData> iterator = BST.inorderIterator();
		while (iterator.hasNext()) {
			WordData data = iterator.next();
			output.print(data.getWord() + " ");
			
			Map<String, List<Integer>> fileLines = data.getFileLines();
			
			Set<String> fileNames = new TreeSet<>(fileLines.keySet());
			
			for (String fileName : fileNames) {
				output.print(fileName + " ");
			}
			
			output.println();
		}
	}
	
	
	/**
	 * Helper method used for -pl option
	 * Prints words with files and line numbers
	 * @param output the PrintStream to write output to
	 */
	private void printWordsWithLines(PrintStream output) {
		Iterator<WordData> iterator = BST.inorderIterator();
		
		while(iterator.hasNext()) {
			WordData data = iterator.next();
			
			output.println(data.getWord());
			
			Map<String, List<Integer>> fileLines = data.getFileLines();
			Set<String> fileNames = new TreeSet<>(fileLines.keySet());
			
			for (String fileName : fileNames) {
				List<Integer> lineNumbers = fileLines.get(fileName);
				
				Collections.sort(lineNumbers);
				
				output.print(" " + fileName + ": ");
				
				
				for (int i = 0; i < lineNumbers.size(); i++) {
					output.print(lineNumbers.get(i));
					
	
					if (i < lineNumbers.size() - 1) {
						output.print(", ");
					}
				}
				output.println();
			}
		}
	}
	
	/**
	 * Helper method used for -po option
	 * Prints word with files, line numbers, and frequency counts
	 * @param output the PrintStream to write output to 
	 */
	private void printWordsWithOccurrences(PrintStream output) {
		Iterator<WordData> iterator = BST.inorderIterator();
		
		while (iterator.hasNext()) {
			WordData data = iterator.next();
			
			Map<String, List<Integer>> fileLines = data.getFileLines();
			
			int totalCount = 0;
			for(String fileName : fileLines.keySet()) {
				List<Integer> lineNumbers = fileLines.get(fileName);
				totalCount = totalCount + lineNumbers.size();
			}
			
			output.println(data.getWord() + " Total: " + totalCount);
			
			Set<String> fileNames = new TreeSet<>(fileLines.keySet());
			
			for (String fileName : fileNames) {
				List<Integer> lineNumbers = fileLines.get(fileName);
				Collections.sort(lineNumbers);
				
				int fileCount = lineNumbers.size();
				
				output.print(" " + fileName + " " + fileCount);
				
				for (int i = 0; i < lineNumbers.size(); i++) {
					output.print(lineNumbers.get(i));
					if (i < lineNumbers.size() - 1) {
						output.print(", ");
					}
				}
				output.println();
			}
		}
	}
	

	
}
