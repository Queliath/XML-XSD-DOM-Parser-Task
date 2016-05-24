package by.epam.parsers;

import by.epam.parsers.domain.Dish;
import by.epam.parsers.domain.Kind;
import by.epam.parsers.domain.Menu;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by Владислав on 24.05.2016.
 */
public class MainStAX {

    private static final String xmlFileURI = "src\\by\\epam\\parsers\\xml\\menu.xml";

    public static void main(String[] args) throws FileNotFoundException, XMLStreamException {

        XMLInputFactory xmlInputFactory = XMLInputFactory.newFactory();
        XMLStreamReader xmlStreamReader = xmlInputFactory.createXMLStreamReader(new FileInputStream(new File(xmlFileURI)));

        Menu menu = readMenu(xmlStreamReader);

        System.out.println("Меню:\n");
        for(Kind kind : menu.getKinds()) {
            System.out.println(kind.getName());
            for(Dish dish : kind.getDishes()) {
                System.out.println("Фото: " + dish.getPhoto() + " Название: " + dish.getName() + " Описание: " + dish.getDescription() + " Порция: " + dish.getPortion() + " Цена: " + dish.getPrice());
            }
        }
    }

    public static Menu readMenu(XMLStreamReader xmlStreamReader) throws XMLStreamException {
        Menu menu = null;
        Kind currentKind = null;
        Dish currentDish = null;
        StringBuilder text = null;

        while (xmlStreamReader.hasNext()) {

            switch (xmlStreamReader.next()) {

                case XMLStreamConstants.START_ELEMENT:
                    switch (xmlStreamReader.getLocalName()) {
                        case "menu":
                            menu = new Menu();
                            break;
                        case "kind":
                            currentKind = new Kind();
                            break;
                        case "dish":
                            currentDish = new Dish();
                            break;
                    }
                    break;

                case XMLStreamConstants.END_ELEMENT:
                    switch (xmlStreamReader.getLocalName()) {
                        case "kind":
                            menu.addKind(currentKind);
                            break;
                        case "dish":
                            currentKind.addDish(currentDish);
                            break;
                        case "kind-name":
                            currentKind.setName(text.toString());
                            break;
                        case "dish-photo":
                            currentDish.setPhoto(text.toString());
                            break;
                        case "dish-name":
                            currentDish.setName(text.toString());
                            break;
                        case "dish-description":
                            currentDish.setDescription(text.toString());
                            break;
                        case "dish-portion":
                            currentDish.setPortion(text.toString());
                            break;
                        case "dish-price":
                            currentDish.setPrice(Integer.parseInt(text.toString()));
                            break;
                    }
                    break;

                case XMLStreamConstants.CHARACTERS:
                    text = new StringBuilder();
                    text.append(xmlStreamReader.getText());
                    break;

            }

        }

        return menu;
    }
}
