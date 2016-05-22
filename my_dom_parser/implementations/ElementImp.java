package by.epam.xml_xsd_dom_parser.my_dom_parser.implementations;

import by.epam.xml_xsd_dom_parser.my_dom_parser.interfaces.Attribute;
import by.epam.xml_xsd_dom_parser.my_dom_parser.interfaces.Element;
import by.epam.xml_xsd_dom_parser.my_dom_parser.interfaces.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Владислав on 21.05.2016.
 */
public class ElementImp implements Element {
    private String tagName;
    private Element parentElement;
    private List<Element> childElements;
    private List<Attribute> attributes;
    private Text textContent;

    public ElementImp() {
        childElements = new ArrayList<>();
        attributes = new ArrayList<>();
    }

    public ElementImp(String tagName, Text textContent, List<Attribute> attributes, List<Element> childElements, Element parentElement) {
        this.tagName = tagName;
        this.textContent = textContent;
        this.attributes = attributes;
        this.childElements = childElements;
        this.parentElement = parentElement;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public Element getParentElement() {
        return parentElement;
    }

    public void setParentElement(Element parentElement) {
        this.parentElement = parentElement;
    }

    public List<Element> getChildElements() {
        return childElements;
    }

    public void setChildElements(List<Element> childElements) {
        this.childElements = childElements;
    }

    public void addChildElement(Element childElement) {
        this.childElements.add(childElement);
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }

    public void addAttribute(Attribute attribute){
        this.attributes.add(attribute);
    }

    public Text getTextContent() {
        return textContent;
    }

    public void setTextContent(Text textContent) {
        this.textContent = textContent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ElementImp that = (ElementImp) o;

        if (parentElement != null ? !parentElement.equals(that.parentElement) : that.parentElement != null)
            return false;
        if (childElements != null ? !childElements.equals(that.childElements) : that.childElements != null)
            return false;
        return !(textContent != null ? !textContent.equals(that.textContent) : that.textContent != null);

    }

    @Override
    public int hashCode() {
        int result = parentElement != null ? parentElement.hashCode() : 0;
        result = 31 * result + (childElements != null ? childElements.hashCode() : 0);
        result = 31 * result + (textContent != null ? textContent.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ElementImp{" +
                "parentElement=" + parentElement +
                ", childElements=" + childElements +
                ", textContent=" + textContent +
                '}';
    }
}
