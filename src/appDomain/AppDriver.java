package appDomain;

import java.io.File;


/**
 * @author Nathanael Lee, James Baes, Tony Do, Eian Verastigue
 * @version 1.2 Dec. 13, 2025
 * Class Description: 
 * The main of the program
 * Separates command line arguments, finds the file to read from and starts the word tracker
 */

public class AppDriver {

    public static void main(String[] args) {
    	String fileName;
    	String printType;
    	String outputFilePath = null;

        if (args.length > 0) {
        	fileName = args[0].trim();        	
        }
        else {
            System.out.println("Usage: java -jar WordTracker.jar <input.txt> -pf/-pl/-po [-f<output.txt>]");
            System.out.println("Example: java -jar WordTracker.jar test1.txt -pf -f<output.txt>");
            return;
        }
        
        if (args.length > 1) {
        	printType = args[1].trim();        	
        }
        else {
            System.out.println("Please specify what you want printed using -pf/-pl/-po");
            System.out.println("Example: java -jar WordTracker.jar test1.txt -pl");
            return;
        }
        
        if (args.length > 2) {
        	outputFilePath = args[2].trim();        	
        }

        File file = new File(fileName);
        if (!file.exists()) {
            System.out.println("Error: File '" + args[0] + "' not found");
            return;
        }
        
       WordTracker wordTracker = new WordTracker(fileName);
       wordTracker.loadRepository();
       wordTracker.parse();
       wordTracker.printWords(printType, outputFilePath);
       wordTracker.saveRepository();
    }
}
