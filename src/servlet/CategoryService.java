
package servlet;

import dao.CategoryDAO;
import entity.Category;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

// The Java class will be hosted at the URI path "/helloworld"
@Path("/category")
public class CategoryService {
    // The Java method will process HTTP GET requests
    @GET
    @Produces({MediaType.APPLICATION_XML})
    public List<Category> getCategories(String id) {
        CategoryDAO categoryDAO = new CategoryDAO();
        return categoryDAO.findAll();
    }

}
