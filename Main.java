package by.epam.xml_xsd_dom_parser;

import by.epam.xml_xsd_dom_parser.my_dom_parser.MyDOMParser;
import by.epam.xml_xsd_dom_parser.my_dom_parser.interfaces.Document;
import by.epam.xml_xsd_dom_parser.my_dom_parser.interfaces.Element;

import java.util.List;

/**
 * Created by Владислав on 21.05.2016.
 */
public class Main {

    private static final String xmlFileURI = "src\\by\\epam\\xml_xsd_dom_parser\\xml\\menu.xml";

    public static void main(String args[]) {

        MyDOMParser myDOMParser = new MyDOMParser(xmlFileURI);
        Document document = myDOMParser.parse();
        Element menuElement = document.getDocumentElement();

        List<Element> kinds = menuElement.getElementsByTagName("dish-name");

        for(Element element : kinds){
            System.out.println(element.getInnerText());
        }
    }

}
