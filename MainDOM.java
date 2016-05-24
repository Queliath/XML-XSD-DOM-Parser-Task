package by.epam.parsers;

import by.epam.parsers.domain.Dish;
import by.epam.parsers.domain.Kind;
import by.epam.parsers.domain.Menu;
import org.w3c.dom.Element;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * Created by Владислав on 22.05.2016.
 */
public class MainDOM {

    private static final String xmlFileURI = "src\\by\\epam\\parsers\\xml\\menu.xml";

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {

        Menu menu = new Menu();

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(xmlFileURI);

        Element menuElement = document.getDocumentElement();

        NodeList kindElements = menuElement.getElementsByTagName("kind");
        for(int i = 0; i < kindElements.getLength(); i++) {
            Element kindElement = (Element) kindElements.item(i);
            Kind kind = new Kind();
            kind.setName(kindElement.getElementsByTagName("kind-name").item(0).getTextContent());
            menu.addKind(kind);

            NodeList dishElements = kindElement.getElementsByTagName("dish");
            for(int j = 0; j < dishElements.getLength(); j++) {
                Element dishElement = (Element) dishElements.item(j);
                Dish dish = new Dish();
                dish.setPhoto(dishElement.getElementsByTagName("dish-photo").item(0).getTextContent());
                dish.setName(dishElement.getElementsByTagName("dish-name").item(0).getTextContent());
                dish.setDescription(dishElement.getElementsByTagName("dish-description").item(0).getTextContent());
                dish.setPortion(dishElement.getElementsByTagName("dish-portion").item(0).getTextContent());
                dish.setPrice(Integer.parseInt(dishElement.getElementsByTagName("dish-price").item(0).getTextContent()));
                kind.addDish(dish);
            }
        }

        System.out.println("Меню:\n");
        for(Kind kind : menu.getKinds()) {
            System.out.println(kind.getName());
            for(Dish dish : kind.getDishes()) {
                System.out.println("Фото: " + dish.getPhoto() + " Название: " + dish.getName() + " Описание: " + dish.getDescription() + " Порция: " + dish.getPortion() + " Цена: " + dish.getPrice());
            }
        }
    }
}
