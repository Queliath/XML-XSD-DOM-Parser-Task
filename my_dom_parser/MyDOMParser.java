package by.epam.xml_xsd_dom_parser.my_dom_parser;

import by.epam.xml_xsd_dom_parser.my_dom_parser.implementations.AttributeImp;
import by.epam.xml_xsd_dom_parser.my_dom_parser.implementations.DocumentImp;
import by.epam.xml_xsd_dom_parser.my_dom_parser.implementations.ElementImp;
import by.epam.xml_xsd_dom_parser.my_dom_parser.implementations.TextImp;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Владислав on 21.05.2016.
 */
public class MyDOMParser {
    private FileReader fileReader;
    private char currentSymbol;

    private DocumentImp documentImp;

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

    public DocumentImp parse(){
        while (readSymbol() != 0) {
            readTag(null);
        }

        return documentImp;
    }

    private void readTag(ElementImp parentElement) {
        ElementImp element = new ElementImp();
        boolean closedTag = false;
        boolean singleTag = false;
        boolean declaration = false;
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

        if(tagName.length() != 0)
            if(tagName.charAt(0) == '?' && tagName.charAt(tagName.length() - 1) == '?')
                declaration = true;

        if(!closedTag && !declaration){
            element.setParentElement(parentElement);
            if(parentElement != null)
                parentElement.addChildElement(element);
            else
                documentImp = new DocumentImp(element);
        }
        if(!closedTag && !singleTag && !declaration) {
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
