package appDomain;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import implementations.BSTree;
import implementations.WordData;

/**
 * @author Nathanael Lee, James Baes, Tony Do, Eian Verastigue
 * @version 1.2 Dec. 12, 2025 Class Description:
 * 
 */

public class WordTracker {
	private String fileName;
	private BSTree<WordData> BST;

	public WordTracker(String fileName) {
		this.fileName = fileName;
		BST = new BSTree<WordData>();
	}

	// count the amount the word has shown up
	// show which line in the text file it appeared
	// the file name where the word showed
	public void parse() {
		try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
			
			
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
