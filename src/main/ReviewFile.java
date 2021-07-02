package main;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

/**
 * ReviewFile class.
 *
 * @author <a href=mailto:francohormazabalgodoy@gmail.com>Franco Hormazabal Godoy</a>
 */
public class ReviewFile {

    /**
     * Read a file with extension tsv.
     *
     * @param rootName
     *     The root of the file.
     *
     * @return {@param inputStream}: The file read as InputStream.
     *
     * @author <a href=mailto:francohormazabalgodoy@gmail.com>Franco Hormazabal Godoy</a>
     */
    public static InputStream readFile(String rootName) throws FileError{
        InputStream inputStream;
        String extension = "";
        int i = rootName.lastIndexOf('.');
        if (i > 0) {
            extension = rootName.substring(i+1);
        }
        if (extension.equals("tsv")) {
            try {
                File file = new File(rootName);
                inputStream = new FileInputStream(file);
            } catch (FileNotFoundException e) {
                throw new FileError("File not found");
            }
        } else {
            throw new FileError("File extension is not .tsv");
        }
        return inputStream;
    }

    /**
     * Close a InputStream file.
     *
     * @param inputStream
     *     The file to close.
     *
     * @author <a href=mailto:francohormazabalgodoy@gmail.com>Franco Hormazabal Godoy</a>
     */
    public static void closeFile(InputStream inputStream) throws FileError{
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e) {
                throw new FileError("File was not closed");
            }
        } else {
            throw new FileError("File was not opened");
        }
    }

    /**
     * Convert a InputStream file to ArrayList<String> with the lines of the file.
     *
     * @param inputStream
     *     The InputStream file.
     *
     * @return {@param lines}: The file converted to ArrayList<String>.
     *
     * @author <a href=mailto:francohormazabalgodoy@gmail.com>Franco Hormazabal Godoy</a>
     */
    public static ArrayList<String> readInputStream(InputStream inputStream) throws FileError{
        ArrayList<String> lines;
        lines = new ArrayList<>();
        if (inputStream != null) {
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line = bufferedReader.readLine();
                while (line != null) {
                    lines.add(line);
                    line = bufferedReader.readLine();
                }
            } catch (IOException e) {
                throw new FileError("BufferedReader was not read");
            }
        } else {
            throw new FileError("File was not opened");
        }
        return lines;
    }

    /**
     * Add a String line to a Map<String, String> dictionary if the line is valid.
     *
     * @param line
     *     The String line.
     *
     * @param dictionary
     *     The Map<String, String> dictionary.
     *
     * @author <a href=mailto:francohormazabalgodoy@gmail.com>Franco Hormazabal Godoy</a>
     */
    public static void readLines(String line, Map<String, String> dictionary) throws FileError{
        if (reviewLineFormat(line)){
            String[] arrLine = line.split(" ");
            String rut = arrLine[0];
            String name = arrLine[1] + " " + arrLine[2];
            if (reviewRutFormat(rut)){
                String rutWithoutPoints = rut.replace(".", "");
                String[] arrRut = rutWithoutPoints.split("-");
                int correlativeNumber = Integer.parseInt(arrRut[0]);
                char checkDigit = arrRut[1].charAt(0);
                if (reviewRutValidate(correlativeNumber, checkDigit)) {
                    dictionary.put(rut, name);
                } else {
                    throw new FileError("Rut is invalid");
                }
            } else {
                throw new FileError("Rut format is incorrect");
            }
        } else {
            throw new FileError("Line format is incorrect");
        }
    }

    /**
     * Review if a String line has rut and name format to add to a dictionary.
     *
     * @param line
     *     The String line.
     *
     * @return true if {@param line} has rut and name format, else return false.
     *
     * @author <a href=mailto:francohormazabalgodoy@gmail.com>Franco Hormazabal Godoy</a>
     */
    public static boolean reviewLineFormat(String line){
        String[] arrLine = line.split(" ");
        return arrLine.length == 3;
    }

    /**
     * Review if a String rut has correct format.
     *
     * @param rut
     *     The String rut.
     *
     * @return true if {@param rut} has correct format, else return false.
     *
     * @author <a href=mailto:francohormazabalgodoy@gmail.com>Franco Hormazabal Godoy</a>
     */
    public static boolean reviewRutFormat(String rut){
        return Pattern.matches("^\\d{1,2}(.\\d{3}.\\d{3}|\\d{6})-\\d$",rut);
    }

    /**
     * Review if a rut formed by a int correlative number and a char check digit has correct format.
     *
     * @param correlativeNumber
     *     The int correlative number.
     *
     * @param checkDigit
     *     The char check digit.
     *
     * @return true if the rut has correct format, else return false.
     *
     * @author <a href=mailto:francohormazabalgodoy@gmail.com>Franco Hormazabal Godoy</a>
     */
    public static boolean reviewRutValidate(int correlativeNumber, char checkDigit) {
        int m = 0;
        int s = 1;
        while (correlativeNumber != 0) {
            s = (s + correlativeNumber % 10 * (9 - m++ % 6)) % 11;
            correlativeNumber /= 10;
        }
        return checkDigit == (char) (s != 0 ? s + 47 : 75);
    }
}