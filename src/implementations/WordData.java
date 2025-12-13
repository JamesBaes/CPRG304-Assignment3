package implementations;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * Represents a single unique word and all the files and line numbers
 * where it appears.
 * 
 * @author Nathanael Lee, James Baes, Tony Do, Eian Verastigue
 * @version 1.1 Dec. 12, 2025
 * 
 */
public class WordData implements Comparable<WordData>, Serializable {

    private static final long serialVersionUID = 1L;

    private String word;
    private Map<String, List<Integer>> fileLines;
    
    
    /**
     * WordData constructor
     * creates new WordData object for the given word
     * @param word The unique word this object represents.
     */
    public WordData(String word) {
        this.word = word;
        this.fileLines = new HashMap<>();
    }
     
    /**
     * Getter for the word string represented by this object.
     * @return The word string.
     */
    public String getWord() {
        return word;
    }
    
    /**
     * Records a new occurrence of the word at a specific line number within a file
     * @param fileName The name of the file where the word was found.
     * @param lineNumber The line number within the file where the word was found.
     */
    public void addOccurrence(String fileName, int lineNumber) {
    	
    	List<Integer> lines = fileLines.get(fileName);

        if (lines == null) {
            lines = new ArrayList<>();
            fileLines.put(fileName, lines);
        }
        
        if (!lines.contains(lineNumber)) { 
            lines.add(lineNumber);
        }
    }

    /**
     * Retrieves the map containing all file and line number occurrences.
     * @return A map where keys = file names, value = list of unique line numbers.
     */
    public Map<String, List<Integer>> getFileLines() {
        return fileLines;
    }
    
    /**
     * Compares this WordData object to another.
     *
     * @param other The other WordData object to compare against.
     * @return An negative integer if less than 
     * (lexicographically), 0 if equal to, and positive integer if its greater.
     */
    @Override
    public int compareTo(WordData other) {
        return this.word.compareTo(other.word);
    }
}
