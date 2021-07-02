package main;

/**
 * FileError Exception class.
 *
 * @author <a href=mailto:francohormazabalgodoy@gmail.com>Franco Hormazabal Godoy</a>
 */
public class FileError extends Exception{

    /**
     * FileError Constructor.
     *
     * @param s
     *      The message to throw.
     *
     * @author <a href=mailto:francohormazabalgodoy@gmail.com>Franco Hormazabal Godoy</a>
     */
    public FileError(String s){
        super(s);
    }
}