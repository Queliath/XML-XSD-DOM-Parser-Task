package by.epam.parsers.own_parser.service;

import by.epam.parsers.own_parser.domain.Document;
import by.epam.parsers.own_parser.exception.NoFileException;

/**
 * Created by Владислав on 24.05.2016.
 */
public interface IDOMParser {
    Document parse() throws NoFileException;
    void setXMLFile(String xmlFileURI);
}
