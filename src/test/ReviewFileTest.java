package test;

import main.FileError;
import main.ReviewFile;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

/**
 * ReviewFile Test class.
 *
 * @author <a href=mailto:francohormazabalgodoy@gmail.com>Franco Hormazabal Godoy</a>
 */
public class ReviewFileTest {

    /**
     * Test of method reviewRutValidate.
     *
     * @author <a href=mailto:francohormazabalgodoy@gmail.com>Franco Hormazabal Godoy</a>
     */
    @Test
    void reviewRutValidateTest(){
        int validCorrelativeNumber = 20351616;
        char validcheckDigit = '9';
        assertTrue(ReviewFile.reviewRutValidate(validCorrelativeNumber,validcheckDigit));
        int invalidCorrelativeNumber = 20979925;
        char invalidcheckDigit = '0';
        assertFalse(ReviewFile.reviewRutValidate(invalidCorrelativeNumber,invalidcheckDigit));
    }

    /**
     * Test of method reviewRutFormat.
     *
     * @author <a href=mailto:francohormazabalgodoy@gmail.com>Franco Hormazabal Godoy</a>
     */
    @Test
    void reviewRutFormatTest(){
        String validRut1 = "20351616-9";
        assertTrue(ReviewFile.reviewRutFormat(validRut1));
        String validRut2 = "8.090.533-5";
        assertTrue(ReviewFile.reviewRutFormat(validRut2));
        String invalidRut = "80.90.533-5";
        assertFalse(ReviewFile.reviewRutFormat(invalidRut));
    }

    /**
     * Test of method reviewLineFormat.
     *
     * @author <a href=mailto:francohormazabalgodoy@gmail.com>Franco Hormazabal Godoy</a>
     */
    @Test
    void reviewLineFormatTest(){
        String validLine = "20351616-9 Giorno Giovana";
        assertTrue(ReviewFile.reviewLineFormat(validLine));
        String invalidLine1 = "20351616-9";
        assertFalse(ReviewFile.reviewLineFormat(invalidLine1));
        String invalidLine2 = "Giorno Giovana";
        assertFalse(ReviewFile.reviewLineFormat(invalidLine2));
        String invalidLine3 = "20351616-9 Giorno";
        assertFalse(ReviewFile.reviewLineFormat(invalidLine3));
        String invalidLine4 = "20351616-9 Giorno Joseph Giovana";
        assertFalse(ReviewFile.reviewLineFormat(invalidLine4));
        String invalidLine5 = "20351616-9Giorno Giovana";
        assertFalse(ReviewFile.reviewLineFormat(invalidLine5));
    }

    /**
     * Test of method reviewLine.
     *
     * @author <a href=mailto:francohormazabalgodoy@gmail.com>Franco Hormazabal Godoy</a>
     */
    @Test
    void reviewLineTest() throws FileError {
        Map<String, String> dictionary = new HashMap<>();
        String validLine = "20351616-9 Giorno Giovana";
        ReviewFile.readLines(validLine, dictionary);
        assertEquals(dictionary.get("20351616-9"), "Giorno Giovana");
        String invalidLine1 = "8.010.979-2Erina Pendleton";
        assertThrows(FileError.class, ()-> ReviewFile.readLines(invalidLine1, dictionary));
        assertNull(dictionary.get("8.010.979-2"));
        String invalidLine2 = "80.90.533-5 Joseph Joestar";
        assertThrows(FileError.class, ()-> ReviewFile.readLines(invalidLine2, dictionary));
        assertNull(dictionary.get("8.010.979-2"));
        String invalidLine3 = "20.979.925-0 Jolyne Kujo";
        assertThrows(FileError.class, ()-> ReviewFile.readLines(invalidLine3, dictionary));
        assertNull(dictionary.get("8.010.979-2"));
    }

    /**
     * Test of method readInputStream.
     *
     * @author <a href=mailto:francohormazabalgodoy@gmail.com>Franco Hormazabal Godoy</a>
     */
    @Test
    void readInputStreamTest() throws FileNotFoundException {
        assertThrows(FileError.class, () -> ReviewFile.readInputStream(null));
        File file = new File("./src/test/testFile.tsv");
        InputStream inputStream = new FileInputStream(file);
        try {
            ReviewFile.readInputStream(inputStream);
        } catch (FileError e) {
            System.out.println("This readInputStreamTest test is incorrect");
        }
    }

    /**
     * Test of method closeFile.
     *
     * @author <a href=mailto:francohormazabalgodoy@gmail.com>Franco Hormazabal Godoy</a>
     */
    @Test
    void closeFileTest() throws FileNotFoundException {
        assertThrows(FileError.class, ()-> ReviewFile.closeFile(null));
        File file = new File("./src/test/testFile.tsv");
        InputStream inputStream = new FileInputStream(file);
        try {
            ReviewFile.closeFile(inputStream);
        } catch (FileError e) {
            System.out.println("This readInputStreamTest test is incorrect");
        }
    }

    /**
     * Test of method readFile.
     *
     * @author <a href=mailto:francohormazabalgodoy@gmail.com>Franco Hormazabal Godoy</a>
     */
    @Test
    void readFileTest() {
        assertThrows(FileError.class, () -> ReviewFile.readFile("./src/test/testFile.pdf"));
        assertThrows(FileError.class, () -> ReviewFile.readFile("./src/testFile.tsv"));
        try {
            ReviewFile.readFile("./src/test/testFile.tsv");
        } catch (FileError e) {
            System.out.println("This readInputStreamTest test is incorrect");
        }
    }
}
