/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;


import com.sun.codemodel.JCodeModel;
import com.sun.tools.xjc.api.ErrorListener;
import com.sun.tools.xjc.api.S2JJAXBModel;
import com.sun.tools.xjc.api.SchemaCompiler;
import com.sun.tools.xjc.api.XJC;
import jaxb.RecipeType;
import org.xml.sax.InputSource;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

public class JAXBHelper {

    public void generateJaxbPackage() throws JAXBException {
        try {
            String output = "src/jaxb/";
            SchemaCompiler sc = XJC.createSchemaCompiler();
            sc.setErrorListener(new ErrorListener() {
                @Override
                public void error(org.xml.sax.SAXParseException e) {
                    System.out.println(e.getMessage());
                }

                @Override
                public void fatalError(org.xml.sax.SAXParseException e) {
                    System.out.println(e.getMessage());

                }

                @Override
                public void warning(org.xml.sax.SAXParseException e) {
                    System.out.println(e.getMessage());

                }

                @Override
                public void info(org.xml.sax.SAXParseException e) {

                }
            });
            sc.forcePackageName("src.jaxb");
            File schema = new File(output + "recipe.xsd");
            InputSource is = new InputSource(schema.toURI().toString());
            sc.parseSchema(is);
            S2JJAXBModel model = sc.bind();
            JCodeModel code = model.generateCode(null, null);
            code.build(new File(output));
            System.out.println("Finish");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public RecipeType xmlRecipeToObject(byte[] array) throws JAXBException {
        RecipeType recipe = null;
        JAXBContext jaxbContext = JAXBContext.newInstance(RecipeType.class);
        Unmarshaller unmarshaller = (Unmarshaller) jaxbContext.createUnmarshaller();
        recipe = (RecipeType) unmarshaller.unmarshal(new ByteArrayInputStream(array));
        return recipe;
    }



}
