package by.epam.parsers;

import by.epam.parsers.domain.Dish;
import by.epam.parsers.domain.Kind;
import by.epam.parsers.domain.Menu;
import by.epam.parsers.own_parser.factory.DOMParserFactory;
import by.epam.parsers.own_parser.service.IDOMParser;
import by.epam.parsers.own_parser.domain.Document;
import by.epam.parsers.own_parser.domain.Element;

import java.util.List;

/**
 * Created by Владислав on 21.05.2016.
 */
public class MainOwnParser implements MenuTagNames{

    private static final String xmlFileURI = "src\\by\\epam\\parsers\\xml\\menu.xml";

    public static void main(String[] args) throws Exception{

        Menu menu = new Menu();

        DOMParserFactory domParserFactory = DOMParserFactory.getInstance();
        IDOMParser domParser = domParserFactory.getDOMParser();
        domParser.setXMLFile(xmlFileURI);
        Document document = domParser.parse();

        Element menuElement = document.getDocumentElement();

        List<Element> kindElements = menuElement.getElementsByTagName(kindTag);
        for(Element kindElement : kindElements) {
            Kind kind = new Kind();
            kind.setName(kindElement.getElementsByTagName(kindNameTag).get(0).getInnerText());
            menu.addKind(kind);

            List<Element> dishElements = kindElement.getElementsByTagName(dishTag);
            for(Element dishElement : dishElements) {
                Dish dish = new Dish();
                dish.setPhoto(dishElement.getElementsByTagName(dishPhotoTag).get(0).getInnerText());
                dish.setName(dishElement.getElementsByTagName(dishNameTag).get(0).getInnerText());
                dish.setDescription(dishElement.getElementsByTagName(dishDescriptionTag).get(0).getInnerText());
                dish.setPortion(dishElement.getElementsByTagName(dishPortionTag).get(0).getInnerText());
                dish.setPrice(Integer.parseInt(dishElement.getElementsByTagName(dishPriceTag).get(0).getInnerText()));
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
