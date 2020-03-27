package test;

import crawler.BBCCrawler;
import crawler.ETMCrawler;

public class TestClass {
    public static int count = 0;

    public static void main(String[] as) {
        try {
            ETMCrawler etmCrawler = new ETMCrawler();
            etmCrawler.getETMRecipes();
            BBCCrawler bbcCrawler = new BBCCrawler();
            bbcCrawler.startCrawling();
        } catch (Exception e ) {
            System.out.println(e.getMessage());
        }

    }
}
