package main;

import java.io.InputStream;
import java.util.*;

/**
 * Main class.
 *
 * @author <a href=mailto:francohormazabalgodoy@gmail.com>Franco Hormazabal Godoy</a>
 */
public class Main {

    /**
     * Read a file line by line and save the valid ones in a dictionary.
     *
     * @author <a href=mailto:francohormazabalgodoy@gmail.com>Franco Hormazabal Godoy</a>
     */
    public static void main(String[] args) throws FileError {
        Map<String, String> dictionary = new HashMap<>();
        InputStream inputStream;
        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter the file root: ");
            String fileName = scanner.nextLine();
            try{
                inputStream = ReviewFile.readFile(fileName);
                break;
            }
            catch (FileError e) {
                System.out.println("Enter a valid file root! \n");
            }
        }
        ArrayList<String> lines = ReviewFile.readInputStream(inputStream);
        for (String line : lines) {
            try {
                ReviewFile.readLines(line, dictionary);
            } catch (FileError e) {
                System.out.println("Line <" + line + "> is invalid, continue...");
            }
        }
        ReviewFile.closeFile(inputStream);
        System.out.println("Dictionary: " + dictionary);
        System.out.println("Process finished.");
    }
}