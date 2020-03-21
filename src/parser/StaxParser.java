package parser;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;

public class StaxParser {

    public StaxParser() {

    }

    public XMLEventReader parserXMLtoEventReader(String doc) throws UnsupportedEncodingException, XMLStreamException {
        byte[] byteArray = doc.getBytes("UTF-8");
        ByteArrayInputStream inputStream = new ByteArrayInputStream(byteArray);
        XMLInputFactory factory = XMLInputFactory.newFactory();
        XMLEventReader reader = factory.createXMLEventReader(inputStream);
        return reader;
    }
}
