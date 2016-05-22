package by.epam.xml_xsd_dom_parser.my_dom_parser;

import by.epam.xml_xsd_dom_parser.my_dom_parser.implementations.AttributeImp;
import by.epam.xml_xsd_dom_parser.my_dom_parser.implementations.ElementImp;
import by.epam.xml_xsd_dom_parser.my_dom_parser.implementations.TextImp;
import by.epam.xml_xsd_dom_parser.my_dom_parser.interfaces.Attribute;
import by.epam.xml_xsd_dom_parser.my_dom_parser.interfaces.Document;
import by.epam.xml_xsd_dom_parser.my_dom_parser.interfaces.Element;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Владислав on 21.05.2016.
 */
public class MyDOMParser {
    private static final char tagOpen = '<';
    private static final char tagClose = '>';

    private static int tagReading = 0;
    private static int attributeReading = 1;
    private static int textReading = 2;

    private FileReader fileReader;
    private char currentSymbol;
    private int readingType = 0;

    private ElementImp rootElement;

    public MyDOMParser() {
    }

    public MyDOMParser(String xmlFileURI) {
        File xmlFile = new File(xmlFileURI);
        try
        {
            fileReader = new FileReader(xmlFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ElementImp parse(){
        while (readSymbol() != 0) {
            readTag(null);
        }

        return rootElement;
    }

    private void readTag(ElementImp parentElement) {
        ElementImp element = new ElementImp();
        boolean closedTag = false;
        boolean singleTag = false;
        StringBuilder tagName = new StringBuilder();
        StringBuilder textBetweenTags = new StringBuilder();

        while (currentSymbol != '<' && currentSymbol != 0)
            textBetweenTags.append(readSymbol());
        if(currentSymbol == 0)
            return;

        while (readSymbol() != '>') {
            if(currentSymbol == '/'){
                if(tagName.length() == 0)
                    closedTag = true;
                else
                    singleTag = true;
                break;
            }
            if(currentSymbol == ' ')
                readAttribute(element);
            else
                tagName.append(currentSymbol);
        }

        if(parentElement != null){
            textBetweenTags.deleteCharAt(textBetweenTags.length() - 1);
            parentElement.setTextContent(new TextImp(textBetweenTags.toString()));
        }

        element.setTagName(tagName.toString());

        System.out.println("Тэг " + tagName);

        if(!closedTag){
            element.setParentElement(parentElement);
            if(parentElement != null)
                parentElement.addChildElement(element);
            else
                rootElement = element;
        }
        if(!closedTag && !singleTag) {
            readTag(element);
        }
        else if(!closedTag)
            readTag(parentElement);
        else
            readTag((ElementImp) parentElement.getParentElement());
    }

    private void readAttribute(ElementImp element) {
        AttributeImp attribute = new AttributeImp();
        StringBuilder attributeName = new StringBuilder();
        StringBuilder attributeValue = new StringBuilder();

        while (readSymbol() != '=')
            attributeName.append(currentSymbol);
        readSymbol();
        while (readSymbol() != '"')
            attributeValue.append(currentSymbol);

        attribute.setName(attributeName.toString());
        attribute.setValue(attributeValue.toString());
        element.addAttribute(attribute);
    }

    private char readSymbol() {
        int readSymbol = 0;
        try {
            readSymbol = fileReader.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(readSymbol != -1)
            currentSymbol = (char)readSymbol;
        else
            currentSymbol = 0;

        return currentSymbol;
    }
}
