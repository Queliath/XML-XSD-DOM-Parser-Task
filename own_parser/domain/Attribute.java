package by.epam.parsers.own_parser.domain;

/**
 * Created by Владислав on 21.05.2016.
 */
public interface Attribute extends Node {
    String getName();
    Element getOwnerElement();
    String getValue();
    void setValue(String value);
    boolean isId();
}
