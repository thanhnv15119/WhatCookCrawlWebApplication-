package utils;

import jaxb.RecipeType;
import org.w3c.dom.Node;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;

public class TrAxUtils {
    public static TransformerFactory tf = null;

    private static TransformerFactory getTransformerFactory() {
        if (tf == null) {
            tf = TransformerFactory.newInstance();
        }
        return tf;
    }

    public static ByteArrayOutputStream transform(InputStream xmlIs, String xslPath)
            throws FileNotFoundException, TransformerConfigurationException, TransformerException {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        TransformerFactory factory = TransformerFactory.newInstance();

        StreamSource source = new StreamSource(xmlIs);
        StreamSource xslSource = new StreamSource(new FileInputStream(xslPath));
        StreamResult result = new StreamResult(outputStream);
        Transformer trans = factory.newTransformer(xslSource);
        trans.transform(source, result);

        return outputStream;
    }

    public static String transform(Node node) throws TransformerException {
        StringWriter sw = new StringWriter();
        Transformer t = TransformerFactory.newInstance().newTransformer();
        t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        t.setOutputProperty(OutputKeys.INDENT, "yes");
        t.transform(new DOMSource(node), new StreamResult(sw));
        return sw.toString();
    }

    public static ByteArrayOutputStream marshalRecipeDetail(RecipeType recipeType) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            JAXBContext ctx = JAXBContext.newInstance(RecipeType.class);
            Marshaller mar = ctx.createMarshaller();
            mar.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            mar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
//            mar.marshal(recipeType, new File("recipeDetail.xml"));
            mar.marshal(recipeType, os);
        } catch (JAXBException e) {
            System.out.println("marshalRecipeDetail ERROR: " + e.getMessage());
        }
        return os;
    }

}
