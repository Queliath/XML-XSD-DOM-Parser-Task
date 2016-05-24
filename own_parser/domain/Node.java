package by.epam.parsers.own_parser.domain;

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
