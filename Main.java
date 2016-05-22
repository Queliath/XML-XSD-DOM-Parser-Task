package by.epam.xml_xsd_dom_parser;

import by.epam.xml_xsd_dom_parser.entity.Dish;
import by.epam.xml_xsd_dom_parser.entity.Kind;
import by.epam.xml_xsd_dom_parser.entity.Menu;
import by.epam.xml_xsd_dom_parser.my_dom_parser.MyDOMParser;
import by.epam.xml_xsd_dom_parser.my_dom_parser.interfaces.Document;
import by.epam.xml_xsd_dom_parser.my_dom_parser.interfaces.Element;

import java.util.List;

/**
 * Created by Владислав on 21.05.2016.
 */
public class Main {

    private static final String xmlFileURI = "src\\by\\epam\\xml_xsd_dom_parser\\xml\\menu.xml";

    public static void main(String[] args) {

        Menu menu = new Menu();

        MyDOMParser myDOMParser = new MyDOMParser(xmlFileURI);
        Document document = myDOMParser.parse();
        Element menuElement = document.getDocumentElement();

        List<Element> kindElements = menuElement.getElementsByTagName("kind");
        for(Element kindElement : kindElements) {
            Kind kind = new Kind();
            kind.setName(kindElement.getElementsByTagName("kind-name").get(0).getInnerText());
            menu.addKind(kind);

            List<Element> dishElements = kindElement.getElementsByTagName("dish");
            for(Element dishElement : dishElements) {
                Dish dish = new Dish();
                dish.setPhoto(dishElement.getElementsByTagName("dish-photo").get(0).getInnerText());
                dish.setName(dishElement.getElementsByTagName("dish-name").get(0).getInnerText());
                dish.setDescription(dishElement.getElementsByTagName("dish-description").get(0).getInnerText());
                dish.setPortion(dishElement.getElementsByTagName("dish-portion").get(0).getInnerText());
                dish.setPrice(Integer.parseInt(dishElement.getElementsByTagName("dish-price").get(0).getInnerText()));
                kind.addDish(dish);
            }
        }

        System.out.println(menu);
    }

}
