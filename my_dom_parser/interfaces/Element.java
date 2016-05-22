package by.epam.xml_xsd_dom_parser.my_dom_parser.interfaces;

import java.util.List;

/**
 * Created by Владислав on 21.05.2016.
 */
public interface Element extends Node {
    String getTagName();
    boolean hasAttribute();
    String getAttribute(String name);
    Attribute getAttributeNode(String name);
    void setAttribute(String name, String value);
    void removeAttribute(String name);
    List<Element> getElementsByTagName(String name);
    String getInnerText();
}
