package by.epam.xml_xsd_dom_parser.my_dom_parser.interfaces;

/**
 * Created by Владислав on 21.05.2016.
 */
public interface Text extends Node {
    String getWholeText();
    void replaceWholeText(String content);
}
