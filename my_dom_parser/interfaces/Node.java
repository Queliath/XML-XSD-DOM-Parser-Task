package by.epam.xml_xsd_dom_parser.my_dom_parser.interfaces;

import java.util.List;

/**
 * Created by Владислав on 21.05.2016.
 */
public interface Node {
    short getNodeType();
    String getNodeValue();
    Node getParentNode();

    Node getFirstChild();
    Node getLastChild();
}
