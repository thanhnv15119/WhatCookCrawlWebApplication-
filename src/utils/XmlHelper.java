package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class XmlHelper {

    private String filePath = "test.html";

    private boolean isInside = false;

    private int balanceTag = 0;

    public XmlHelper() {

    }

    public String parseBBCHtml(String uri) throws IOException {
        String htmlContent = "";
        URL url = new URL(uri);
        HttpURLConnection yc = (HttpURLConnection) url.openConnection();
        yc.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)");
        yc.setConnectTimeout(35000);
        yc.setReadTimeout(15000);
        InputStream rs = yc.getInputStream();
        BufferedReader in = new BufferedReader(new InputStreamReader(rs, "UTF-8"));
        String line = "";
        while ((line = in.readLine()) != null) {
            htmlContent += line;
        }
        return htmlContent;
    }

    public String parseHtml(String uri, String beginSyntax, String endSyntax) throws IOException {
        String htmlContent = "";
        URL url = new URL(uri);
        HttpURLConnection yc = (HttpURLConnection) url.openConnection();
        yc.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)");
        InputStream rs = yc.getInputStream();
        BufferedReader in = new BufferedReader(new InputStreamReader(rs, "UTF-8"));

        String line = "";
        while ((line = in.readLine()) != null) {
            if (beginSyntax != null && endSyntax != null) {
                if (line.indexOf(beginSyntax) > 0) {
                    isInside = true;
                }

                if (line.indexOf(endSyntax) > 0) {
                    isInside = false;
                }

                if (isInside) {
                    htmlContent += line + "\n";
                }
            } else {
                htmlContent += line + "\n";
            }
        }

        return htmlContent;
    }

    public String ETMCatogeriesWellformFixer(String doc) {
        doc = doc.replace(AppConstant.ETM_CATOGERY_END_SYNTAX, "")
                .replace("&", "&amp;");
        return doc;
    }

    public String ETMRecipesWellformFixer(String doc) {
        doc = doc.replace("&", "&amp;")
                .replace("selected", "")
                .replace("disabled", "")
                .concat("class=\"fa fa-chevron-right\"></i></a>\n" +
                        "                                        \n" +
                        "                                    \n" +
                        "                                </div>\n" +
                        "                            \n" +
                        "                        </div>\n" +
                        "\n" +
                        "\n" +
                        "                    </div>\n");
        return doc;
    }

    public String BBCCollectionsWellformFixer(String doc) throws IOException {
        doc = doc.substring(doc.indexOf(AppConstant.BBC_COLLECTION_BEGIN_SYNTAX),
                doc.indexOf(AppConstant.BBC_COLLECTION_END_SYNTAX))
                .replace("/>", "></img>")
                .replace("</div></div></article>", "");

        return doc;
    }

    public String BBCRecipesWellformFixer(String doc) throws IOException {
        doc = doc.substring(doc.indexOf(AppConstant.BBC_RECIPES_BEGIN_SYNTAX),
                doc.indexOf(AppConstant.BBC_RECIPES_END_SYNTAX))
                .replace("/>", "></img>");

        return doc;
    }

    public String BBCRecipeWellformFixer(String doc) throws IOException {
        doc = doc.substring(doc.indexOf(AppConstant.BBC_RECIPE_BEGIN_SYNTAX),
                doc.indexOf(AppConstant.BBC_RECIPE_END_SYNTAX))

                .replace("itemscope ", "")
                .replace("&nbsp;", "")
                .replace("&frac12;", "1/2")
                .replace("&frac14;", "1/4")
                .replace("&", "&amp;")
                .replace("<br>", "")
                .replace("<br/>", "")
                .replace("<br >", "")
                .replace("<br />", "")
                .replace("/>", "></img>")
                .replace("meta", "img")
                .replaceAll(" content=\"[\\s\\S]*?\"", "");
        return doc;
    }

    public String wellFormETMRecipeDetail(String doc) throws IOException {
        doc = doc.concat("</noscript></div></div>")
                .replace("<br/>", "")
                .replace("<hr/>","")
                .replace("/>","></meta>")
                .replace("itemscope", "")
                .replace(">\n" +
                        "                                    </div>\n" +
                        "                                \n" +
                        "\n" +
                        "                                <h3>Ingredients</h3>","></img>\n" +
                        "                                    </div>\n" +
                        "                                \n" +
                        "\n" +
                        "                                <h3>Ingredients</h3>")
                .replace("%;\">", "%\"></input>");

        File result = new File("test1.html");
        FileWriter fw = new FileWriter(result);
        fw.write(doc);
        fw.close();
        return doc;
    }

}
