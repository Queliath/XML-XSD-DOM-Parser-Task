package by.epam.xml_xsd_dom_parser.my_dom_parser.implementations;

import by.epam.xml_xsd_dom_parser.my_dom_parser.interfaces.Text;

/**
 * Created by Владислав on 21.05.2016.
 */
public class TextImp implements Text {
    private String value;

    public TextImp() {
    }

    public TextImp(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TextImp textImp = (TextImp) o;

        return !(value != null ? !value.equals(textImp.value) : textImp.value != null);

    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "TextImp{" +
                "value='" + value + '\'' +
                '}';
    }
}
