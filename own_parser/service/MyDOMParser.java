package by.epam.parsers.own_parser.service;

import by.epam.parsers.own_parser.domain.AttributeImp;
import by.epam.parsers.own_parser.domain.DocumentImp;
import by.epam.parsers.own_parser.domain.ElementImp;
import by.epam.parsers.own_parser.domain.TextImp;
import by.epam.parsers.own_parser.exception.NoFileException;
import org.w3c.dom.Element;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Владислав on 21.05.2016.
 */
public class MyDOMParser implements IDOMParser{

    private final static char tagStart = '<';
    private final static char tagEnd = '>';
    private final static char tagClose = '/';
    private final static char quotes = '"';
    private final static char space = ' ';
    private final static char equally = '=';
    private final static char question = '?';

    private FileReader fileReader;
    private char currentSymbol;

    private DocumentImp documentImp;

    public MyDOMParser() {
    }

    public void setXMLFile(String xmlFileURI) {
        File xmlFile = new File(xmlFileURI);
        try
        {
            fileReader = new FileReader(xmlFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public DocumentImp parse() throws NoFileException{
        if(fileReader == null)
            throw new NoFileException();

        //Пока не достигнут конец файла происходит рекурсивный вызов функции readTag
        //(предпочтение отдано рекурсивному подходу вместо итерационного т.к. в процессе считывания xml происходит построение DOM дерева
        while (readSymbol() != 0) {
            //null означает что у первого считываемого элемента нет родителя
            readTag(null);
        }

        return documentImp;
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

    private void readTag(ElementImp parentElement) {
        ElementImp element = new ElementImp();

        boolean closedTag = false;
        boolean singleTag = false;
        boolean declaration = false;

        StringBuilder tagName = new StringBuilder();

        //До тех пор пока не встретится начало тега считываемый текст заносится в переменную textBetweenTags
        String textBetweenTags = readText();
        //Если достигнут конец файла выполнение функции прекращается
        if(currentSymbol == 0)
            return;

        //Считываение содержимого тега до тех пор пока не встретится закрывающий символ
        while (readSymbol() != tagEnd) {

            if(currentSymbol == tagClose){
                //Если символ / встречем в самом начале тега, то этог тег закрывающий
                if(tagName.length() == 0)
                    closedTag = true;
                //Если нет, то это одиночный пустой тег
                else
                    singleTag = true;
                break;
            }
            //Если встречем пробел внутри тега начинается считывание атрибута тега
            if(currentSymbol == space)
                readAttribute(element);
            //Во всех остальных случаях символ заносится в имя тега
            else
                tagName.append(currentSymbol);
        }

        //Проверка является ли считанный тег объявлением
        if(tagName.length() != 0)
            if(tagName.charAt(0) == question && tagName.charAt(tagName.length() - 1) == question)
                declaration = true;

        //Если это закрывающий тег то считанный текст заносится в содержимое элемента
        if(closedTag){
            parentElement.setTextContent(new TextImp(textBetweenTags.toString()));
        }

        //Если тег не закрывающий и не объявление то он заносится в дерево DOM
        if(!closedTag && !declaration){
            element.setTagName(tagName.toString());
            element.setParentElement(parentElement);
            if(parentElement != null)
                parentElement.addChildElement(element);
            else
                documentImp = new DocumentImp(element);
        }

        //Рекурсивный вызов функции в зависимости от типа тега
        if(!closedTag && !singleTag && !declaration) {
            //Далее считываемые теги будут восприниматься как дочерние элементы
            readTag(element);
        }
        //Если тег одиночный или это объявление то родительский элемент остается тем же
        else if(!closedTag)
            readTag(parentElement);
        //Если это закрывающий тег то далее считываемые теги будет дочерними для родителя родителя
        //(это происхоидт потому что закрывающий тег воспринимается как дочерний для открывающего)
        else
            readTag((ElementImp) parentElement.getParentElement());

    }

    private String readText() {
        StringBuilder textBetweenTags = new StringBuilder();

        while (currentSymbol != tagStart && currentSymbol != 0)
            textBetweenTags.append(readSymbol());

        if(textBetweenTags.length() != 0)
            textBetweenTags.deleteCharAt(textBetweenTags.length() - 1);

        return textBetweenTags.toString();
    }

    private void readAttribute(ElementImp element) {
        AttributeImp attribute = new AttributeImp();
        StringBuilder attributeName = new StringBuilder();
        StringBuilder attributeValue = new StringBuilder();

        //До тех пор пока не встретится символ равно, считываемые символы воспринимаются как имя тега
        while (readSymbol() != equally)
            attributeName.append(currentSymbol);
        //Считывается лишний символ (")
        readSymbol();
        //До тех пор пока не встретится символ ", считываемые символы воспринимаются как значение тега
        while (readSymbol() != quotes)
            attributeValue.append(currentSymbol);

        attribute.setName(attributeName.toString());
        attribute.setValue(attributeValue.toString());
        //Атрибут добавляется в дерево DOM
        element.addAttribute(attribute);
    }

}
