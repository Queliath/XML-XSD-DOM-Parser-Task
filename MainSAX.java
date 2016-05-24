package by.epam.parsers;

import by.epam.parsers.domain.Dish;
import by.epam.parsers.domain.Kind;
import by.epam.parsers.domain.Menu;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by Владислав on 24.05.2016.
 */
public class MainSAX {

    private static final String xmlFileURI = "src\\by\\epam\\parsers\\xml\\menu.xml";

    public static void main(String[] args) throws SAXException, IOException {
        XMLReader xmlReader = XMLReaderFactory.createXMLReader();
        MenuHandler menuHandler = new MenuHandler();
        xmlReader.setContentHandler(menuHandler);
        xmlReader.parse(new InputSource(new FileInputStream(new File(xmlFileURI))));

        Menu menu = menuHandler.getMenu();

        System.out.println("Меню:\n");
        for(Kind kind : menu.getKinds()) {
            System.out.println(kind.getName());
            for(Dish dish : kind.getDishes()) {
                System.out.println("Фото: " + dish.getPhoto() + " Название: " + dish.getName() + " Описание: " + dish.getDescription() + " Порция: " + dish.getPortion() + " Цена: " + dish.getPrice());
            }
        }
    }

    private static class MenuHandler extends DefaultHandler {
        private Menu menu;
        private Kind currentKind;
        private Dish currentDish;
        private StringBuilder text;

        public Menu getMenu() {
            return menu;
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            switch (localName) {
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
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            switch (localName) {
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
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            text = new StringBuilder(length);
            text.append(ch, start, length);
        }
    }
}
