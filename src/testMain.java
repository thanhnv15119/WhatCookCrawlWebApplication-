
import com.sun.net.httpserver.HttpServer;
import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import dao.CategoryDAO;
import entity.Category;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;

// The Java class will be hosted at the URI path "/helloworld"
@Path("/helloworld")
public class testMain {
    // The Java method will process HTTP GET requests
    @GET
    // The Java method will produce content identified by the MIME Media type "text/plain"
    @Produces("text/plain")
    public String getClichedMessage() {
        // Return some cliched textual content
        return "Hello World";
    }
//
//    @GET
//    @Produces("application/xml")
//    public String getCategories() {
//        CategoryDAO categoryDAO = new CategoryDAO();
//        categoryDAO.
//    }

    public static void main(String[] args) throws IOException {

        System.out.println("abc server deployed");
    }
}
