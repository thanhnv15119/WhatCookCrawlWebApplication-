package test;

import crawler.BBCCrawler;
import crawler.ETMCrawler;
import dao.RecipeDAO;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

public class TestClass {
    public static int count = 0;

    public static void main(String[] as) throws IOException, XMLStreamException, TransformerException, JAXBException {
//        BBCCrawler bbcCrawler = new BBCCrawler();
//        bbcCrawler.startCrawling();
        ETMCrawler etmCrawler = new ETMCrawler();
//
        etmCrawler.getETMRecipes();
//        RecipeDAO dao = new RecipeDAO();
//        Recipe rs = dao.findByID(1);
//        System.out.println(rs.getName());
//        CategoryDAO dao = new CategoryDAO();
//        Category a = new Category("test3");
//        dao.insert(a);

    }
}
