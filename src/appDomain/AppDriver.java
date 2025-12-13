package appDomain;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * @author Nathanael Lee, James Baes, Tony Do, Eian Verastigue
 * @version 1.0 Dec. 12, 2025
 * Class Description: 
 * The main of the program
 * Separates command line arguments, finds the file to read from and writes to an output file if specified
 */

public class AppDriver {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: java -jar WordTracker.jar <input.txt> -pf/-pl/-po [-f<output.txt>]");
            System.out.println("Example: java -jar WordTracker.jar test1.txt -pf -f<output.txt>");
            return;
        }

        String fileName = args[0].trim();
        String printType = args[1].trim();
        String outputFilePath = args[2].trim();

        File file = new File(fileName);
        if (!file.exists()) {
            System.out.println("Error: File '" + args[0] + "' not found");
            return;
        }
        
        if (printType.toLowerCase() == "-pf") {
        	
        }
        else if (printType.toLowerCase() == "-pl") {
        	
        }
        else if (printType.toLowerCase() == "-po") {
        
        }
        else {
        	System.out.println("Please provide a print format that you want");
        	System.out.println("-pf prints in alphabetic order all words, along with the corresponding list of files\r\n in which the words occur. ");
        	System.out.println("-pl prints in alphabetic order all words, along with the corresponding list of files\r\n and line numbers in which the word occur. ");
        	System.out.println("-po prints in alphabetic order all words, along with the corresponding list of files,\r\n line numbers in which the word occur and the frequency of occurrence of the\r\n words.");
        }
        
        WordTracker wordTracker = new WordTracker(fileName);
        
        if (outputFilePath != null) {
        	File outputFile = new File(outputFilePath);
        	try {
				if (outputFile.createNewFile()) {
					System.out.println("Created: " + outputFile.getName() + " at path: " + outputFile.getAbsolutePath());
				}
				else {
					System.out.println("Overwriting file: " + outputFile.getName() + " at path: " + outputFile.getAbsolutePath());
				}
				FileWriter myWriter = new FileWriter(outputFile);
				
				myWriter.write(output);
				myWriter.close();
				System.out.println("Successfully wrote to the file.");
				
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
    }
    
    //stolen kitty code
	public static void serializePlayersToFile()
	{
		try
		{
			ObjectOutputStream oos = new ObjectOutputStream(
					new FileOutputStream("res/players.ser"));
			
			for(int i = 0; i < players.size(); i++)
			{
				oos.writeObject(players);
			}
			oos.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings( "unchecked" )
	public static void deserializePlayersFromFile()
	{
		try
		{
			ObjectInputStream ois = new ObjectInputStream(
						new FileInputStream("res/players.ser"));
			
			players = (ArrayList<Player>) ois.readObject();
			ois.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}
}
