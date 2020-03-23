package crawler;

import dao.CategoryDAO;
import dao.RecipeDAO;
import entity.Category;
import jaxb.RecipeType;
import jdk.internal.org.xml.sax.SAXException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import parser.StaxParser;
import utils.AppConstant;
import utils.JAXBHelper;
import utils.TrAxUtils;
import utils.XmlHelper;
import utils.XmlValidate;

import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ETMCrawler {

    private int count = 0;
    private int flag = 0;

    public ETMCrawler() {
    }

    private List<String> listCategories = new ArrayList<>();

    public Map<String, String> getETMCategories() throws IOException, XMLStreamException {
        String doc = "";
        Map<String, String> rs = new HashMap<>();

        XmlHelper xmlHelper = new XmlHelper();
        doc = xmlHelper.parseHtml(AppConstant.ETM_URL, AppConstant.ETM_CATOGERY_BEGIN_SYNTAX, AppConstant.ETM_CATOGERY_END_SYNTAX);
        doc = xmlHelper.ETMCatogeriesWellformFixer(doc);
        StaxParser parser = new StaxParser();
        XMLEventReader reader = parser.parserXMLtoEventReader(doc);
        while (reader.hasNext()) {
            XMLEvent event = (XMLEvent) reader.next();
            if (event.isStartElement()) {
                if (event.asStartElement().getName().getLocalPart().equals("a")) {
                    String catogeryUrl = event.asStartElement().getAttributeByName(new QName("href")).getValue().trim();
                    event = (XMLEvent) reader.next();
                    if (event.isCharacters()) {
                        if (!event.asCharacters().getData().trim().equals("All")) {
                            String catogeryName = event.asCharacters().getData().trim();
                            rs.put(catogeryName, catogeryUrl);
                        }
                    }
                }
            }
        }
        return rs;
    }

    public Document parseStringToDOM(String doc) throws ParserConfigurationException, SAXException, IOException, org.xml.sax.SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document domRs = builder.parse(new InputSource(new StringReader(doc)));
        return domRs;
    }

    public void getReceipeUrlsByCategory(String categoryUrl) throws IOException, SAXException, ParserConfigurationException, org.xml.sax.SAXException, XPathExpressionException, TransformerException {
        XmlHelper xmlHelper = new XmlHelper();
        String doc = xmlHelper.parseHtml(AppConstant.ETM_ROOT_URL + categoryUrl, AppConstant.ETM_PRODUCTS_BEGIN_SYNTAX, AppConstant.ETM_PRODUCTS_END_SYNTAX);
        doc = xmlHelper.ETMRecipesWellformFixer(doc);
        Document dom = parseStringToDOM(doc);
        XPathFactory factory = XPathFactory.newInstance();
        XPath xPath = factory.newXPath();
        String expUrls = "/div/div[contains(@class, \"row food_result\")]/div[contains(@class, \"col-11\")]/a";
        NodeList urlRecipes = (NodeList) xPath.evaluate(expUrls, dom, XPathConstants.NODESET);
        for (int i = 0; i < urlRecipes.getLength(); i++) {
            listCategories.add(urlRecipes.item(i).getAttributes().getNamedItem("href").getNodeValue());
            count++;
        }
        //generate next page link
        String exp = "//div/div[contains(@class, 'bottom_pagination search_padding')]/div[2]/a";
        NodeList nodeList = (NodeList) xPath.evaluate(exp, dom, XPathConstants.NODESET);

        String nextPageUrl = ((Node) nodeList.item(nodeList.getLength() - 1)).getAttributes().getNamedItem("href").getNodeValue();
        if (flag == AppConstant.LIMIT_PAGE) {
            flag = 0;
        } else {
            System.out.println(nextPageUrl);
            flag++;
            getReceipeUrlsByCategory(nextPageUrl);
        }
    }


    public void getETMRecipes() throws IOException, XMLStreamException {
        if (listCategories != null) {
            getETMCategories().forEach((s1, s2) -> {
                try {
                    CategoryDAO categoryDAO = new CategoryDAO();
                    boolean isExit = categoryDAO.findByName(s1) !=null;
                    if(!isExit) {
                        categoryDAO.save(new Category(s1));
                    }
                    Category category = categoryDAO.findByName(s1);
                    listCategories = new ArrayList<>();
                    getReceipeUrlsByCategory(s2);
                    for (int i = 0; i < listCategories.size(); i++) {
                        String url = listCategories.get(i);

                        Thread t = new Thread() {
                            @Override
                            public void run() {
                                try {
                                    XmlHelper helper = new XmlHelper();
                                    String doc = helper.parseHtml(AppConstant.ETM_ROOT_URL + url,
                                            AppConstant.ETM_RECIPE_BEGIN_SYNTAX, AppConstant.ETM_RECIPE_END_SYNTAX);
                                    doc = helper.wellFormETMRecipeDetail(doc);
                                    InputStream is = new ByteArrayInputStream(doc.getBytes("UTF-8"));
                                    ByteArrayOutputStream os = TrAxUtils.transform(is, "src/resource/ETMRecipe.xsl");


                                    String extensionUrl = url.substring(url.indexOf(',') + 1, url.length() - 1);
                                    String extensionDoc = helper.parseBBCHtml(AppConstant.ETM_ROOT_URL2 + extensionUrl);
                                    String prepTime = getPrepareTime(extensionDoc);
                                    String cookTime = getCookTime(extensionDoc);
                                    List<String> listMethods = getMethods(extensionDoc);
                                    Document newDoc = parseStringToDOM(os.toString());

                                    XPathFactory factory = XPathFactory.newInstance();
                                    XPath xPath = factory.newXPath();
                                    String exp = "/recipe";
                                    Node root = (Node) xPath.evaluate(exp, newDoc, XPathConstants.NODE);
                                    Element prepTimeEle = newDoc.createElement("prepTime");
                                    prepTimeEle.setTextContent(prepTime);

                                    Element cookTimeEle = newDoc.createElement("cookTime");
                                    cookTimeEle.setTextContent(cookTime);

                                    Element methodsEle = newDoc.createElement("methods");
                                    root.appendChild(methodsEle);
                                    for (String item : listMethods) {
                                        Element methodEle = newDoc.createElement("method");

                                        Element contentEle = newDoc.createElement("content");
                                        contentEle.setTextContent(item);

                                        Element stepEle = newDoc.createElement("step");
                                        stepEle.setTextContent(listMethods.indexOf(item)+1 + "");

                                        methodEle.appendChild(stepEle);
                                        methodEle.appendChild(contentEle);

                                        methodsEle.appendChild(methodEle);
                                    }
                                    root.appendChild(prepTimeEle);
                                    root.appendChild(cookTimeEle);

                                    exp = "/recipe/nutrition/*";
                                    NodeList nutritionList = (NodeList) xPath.evaluate(exp, newDoc, XPathConstants.NODESET);
                                    for (int i = 0; i < nutritionList.getLength(); i++) {
                                        String content = nutritionList.item(i).getTextContent();
                                        String newContent = content.replaceAll("[A-z]+", "");
                                        nutritionList.item(i).setTextContent(newContent);
                                    }
                                    String xmlContent = TrAxUtils.transform(root);
                                    JAXBHelper jaxbHelper = new JAXBHelper();
                                    XmlValidate xmlValidate = new XmlValidate();
                                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream(xmlContent.getBytes("UTF-8").length);
                                    outputStream.write(xmlContent.getBytes("UTF-8"),0,xmlContent.getBytes("UTF-8").length);
                                    if(xmlValidate.validateXMLSchema(AppConstant.RECIPE_XSD_PATH, outputStream)) {
                                        RecipeType recipeType = jaxbHelper.xmlRecipeToObject(xmlContent.getBytes("UTF-8"));
                                        RecipeDAO recipeDAO = new RecipeDAO();
                                        recipeDAO.insertRecipeType(recipeType, category);
                                        System.out.println("DONE");
                                    } else {
                                        System.out.println("NOT VALID");
                                    }
                                } catch (IOException | JAXBException e) {
                                    System.out.println(e.getMessage());
                                } catch (SAXException | ParserConfigurationException | org.xml.sax.SAXException | XPathExpressionException | TransformerException e) {
                                    System.out.println(e.getMessage());
                                }
                            }

                        };
                        t.start();
                    }
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                } catch (SAXException e) {
                    e.printStackTrace();
                } catch (ParserConfigurationException e) {
                    e.printStackTrace();
                } catch (org.xml.sax.SAXException e) {
                    e.printStackTrace();
                } catch (XPathExpressionException e) {
                    e.printStackTrace();
                } catch (TransformerException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("DONE");
                }
            });

        }
    }

    public String getCookTime(String doc) {
        String result = null;
        int begin = doc.indexOf(AppConstant.ETM_COOKTIME) + AppConstant.ETM_COOKTIME.length() + 3;
        int end = doc.indexOf(AppConstant.ETM_CURATED) - 2;
        if ((begin - AppConstant.ETM_COOKTIME.length() - 3) != -1 && end != -3) {
            result = doc.substring(begin, end).replaceAll("\\D", "");
        }
        return result;
    }

    public String getPrepareTime(String doc) throws IOException {
        String result = null;
        doc = doc.substring(doc.lastIndexOf("nutrition"));
        int begin = doc.indexOf(AppConstant.ETM_PREPTIME) + AppConstant.ETM_PREPTIME.length() + 3;
        int end = doc.indexOf(AppConstant.ETM_RESOURCE_URI) - 2;
        if ((begin - AppConstant.ETM_PREPTIME.length() - 3) != -1 && end != -3) {
            result = doc.substring(begin, end).replaceAll("\\D", "");
        }
        return result;
    }

    public List<String> getMethods(String doc) {
        List<String> result = new ArrayList<>();
        doc = doc.substring(doc.indexOf("directions"), doc.length() - 1);
        boolean isNotDone = true;
        int begin = -1;
        int end = -1;
        while (isNotDone) {
            begin = doc.indexOf("text");
            if (begin == -1) {
                isNotDone = false;
            } else {
                end = doc.indexOf("\"}, {");
                if (end > doc.indexOf("\"}]")) {
                    end = doc.indexOf("\"}]");
                }
                if (doc.indexOf("}], \"discrete_u") != 1) {
                    if (end != -1) {
                        String text = doc.substring(begin + 8, end);
                        result.add(text);
                        doc = doc.substring(end + 3, doc.length() - 1);
                    } else {
                        isNotDone = false;
                    }
                } else {
                    isNotDone = false;
                }
            }
        }

        return result;
    }
}
