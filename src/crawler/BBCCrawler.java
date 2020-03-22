package crawler;

import dao.CategoryDAO;
import dao.IngredientDAO;
import dao.MethodDAO;
import dao.NutritionDAO;
import dao.RecipeDAO;
import entity.Category;
import entity.Ingredient;
import entity.Method;
import entity.Nutrion;
import entity.Recipe;
import jaxb.RecipeType;
import parser.StaxParser;
import utils.AppConstant;
import utils.JAXBHelper;
import utils.TrAxUtils;
import utils.XmlHelper;
import utils.XmlValidate;

import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.TransformerException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BBCCrawler {

    public BBCCrawler() {
    }

    public void startCrawling() throws IOException, XMLStreamException {
        CategoryDAO categoryDAO = new CategoryDAO();
        Map<String, String> mapCollections = new HashMap<>();
        for (String link: getBBCCollectionLinks()) {
            mapCollections.putAll(getBBCCollections(link));
        }
        for (Map.Entry entry : mapCollections.entrySet()) {
            categoryDAO.save(new Category(entry.getKey().toString()));
            final Category newCatogery = categoryDAO.findByName(entry.getKey().toString());
            Map<String, String> recipesLink = getRecipesLinkByCollections(entry.getValue().toString());
            for (Map.Entry item : recipesLink.entrySet()) {
                Thread t = new Thread() {
                    @Override
                    public void run() {
                        try {
                            getRecipeByLink(item.getValue().toString(), newCatogery);
                        } catch (IOException e) {
                            System.out.println("getRecipeByLink ERROR:" + e.getMessage());
                        } catch (TransformerException e) {
                            System.out.println("getRecipeByLink ERROR:" + e.getMessage());
                        } catch (JAXBException e) {
                            System.out.println("getRecipeByLink ERROR:" + e.getMessage());
                        }
                    }
                };
                t.start();
            }
        }
    }

    public List<String> getBBCCollectionLinks() throws IOException, XMLStreamException {
        List<String> rs = new ArrayList<>();
        XmlHelper helper = new XmlHelper();
        String doc = helper.parseBBCHtml(AppConstant.BBCGOODFOOD_ROOT);
        doc = helper.getCollectionsPath(doc);
        XMLInputFactory factory = XMLInputFactory.newInstance();
        factory.setProperty(XMLInputFactory.IS_REPLACING_ENTITY_REFERENCES, false);
        factory.setProperty(XMLInputFactory.IS_VALIDATING, false);

        XMLEventReader reader = factory.createXMLEventReader(new ByteArrayInputStream(doc.getBytes("UTF-8")));
        while (reader.hasNext()) {
            XMLEvent event = (XMLEvent) reader.next();
            if(event.isStartElement() ) {
                if(event.asStartElement().getName().toString().equals("a")) {
                    String url =event.asStartElement().getAttributeByName(new QName("href")).getValue();
                    if (url.contains("/recipes/category") && !url.contains("https://www.bbcgoodfood.com/") && !rs.contains(url) ) {
                        rs.add(url);
                    }
                }
            }
        }
        return rs;
    }

    public Map<String, String> getBBCCollections(String collectionLink) throws IOException, XMLStreamException {
        Map<String, String> result = new HashMap<>();
        XmlHelper xmlHelper = new XmlHelper();
        String doc = xmlHelper.parseBBCHtml(AppConstant.BBCGOODFOOD_ROOT+collectionLink);
        doc = xmlHelper.BBCCollectionsWellformFixer(doc);
        StaxParser parser = new StaxParser();
        XMLEventReader reader = parser.parserXMLtoEventReader(doc);

        while (reader.hasNext()) {
            XMLEvent event = (XMLEvent) reader.next();
            if (event.isStartElement()) {
                String tagName = event.asStartElement().getName().toString();
                String url, name = null;
                if (tagName.equals("a")) {
                    url = event.asStartElement()
                            .getAttributeByName(new QName("href")).getValue();
                    event = (XMLEvent) reader.next();
                    if (event.isCharacters()) {
                        name = event.asCharacters().getData();
                    }
                    if (url != null && name != null) {
                        result.put(name, url);
                    }
                }
            }
        }
        return result;
    }

    public Map<String, String> getRecipesLinkByCollections(String categoryUrl) throws IOException, XMLStreamException {
        Map<String, String> listResult = new HashMap<>();
        XmlHelper xmlHelper = new XmlHelper();
        String doc = xmlHelper.parseBBCHtml(AppConstant.BBCGOODFOOD_ROOT + categoryUrl);
        doc = xmlHelper.BBCRecipesWellformFixer(doc);
        StaxParser parser = new StaxParser();
        XMLEventReader reader = parser.parserXMLtoEventReader(doc);
        while (reader.hasNext()) {
            XMLEvent event = (XMLEvent) reader.next();
            if (event.isStartElement()) {
                if (event.asStartElement().getName().toString().equals("h3")) {
                    event = (XMLEvent) reader.next();
                    event = (XMLEvent) reader.next();
                    String name = null;
                    String url = null;
                    if (event.asStartElement().getName().toString().equals("a")) {
                        url = event.asStartElement().getAttributeByName(new QName("href")).getValue();
                        event = (XMLEvent) reader.next();
                        name = event.asCharacters().getData();
                        listResult.put(name, url);
                    }
                }
            }
        }
        return listResult;
    }

    public void getRecipeByLink(String url, Category category) throws IOException, TransformerException, JAXBException {
        XmlHelper helper = new XmlHelper();
        String doc = helper.parseBBCHtml(AppConstant.BBCGOODFOOD_ROOT + url);
        doc = helper.BBCRecipeWellformFixer(doc);
        InputStream is = new ByteArrayInputStream(doc.getBytes("UTF-8"));
        ByteArrayOutputStream os = TrAxUtils.transform(is, "src/resource/BBCRecipe.xsl");
        XmlValidate validate = new XmlValidate();
        boolean isValidated = validate.validateXMLSchema(AppConstant.RECIPE_XSD_PATH, os);
        if (isValidated) {
            RecipeDAO recipeDAO = new RecipeDAO();
            JAXBHelper jaxbHelper = new JAXBHelper();
            RecipeType recipeType = jaxbHelper.xmlRecipeToObject(os.toByteArray());
            boolean isDup = recipeDAO.findByName(recipeType.getName()) == null;
            if (isDup) {
                recipeDAO.insertRecipeType(recipeType, category);
            }
        }
    }
}
