package by.epam.parsers.own_parser.exception;

/**
 * Created by Владислав on 24.05.2016.
 */
public class NoFileException extends Exception{
    public NoFileException() {
        super("There is no file for parsing");
    }
}
