package servlet;

import com.sun.deploy.net.HttpRequest;
import dao.RecipeDAO;
import entity.Recipe;
import sun.net.www.http.HttpClient;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "DetailRecipeServlet")
public class DetailRecipeServlet extends HttpServlet {
    private final String DETAIL_JSP = "RecipeDetail.jsp";
    private final String ERROR_PAGE = "error.html";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter("recipeId");
        int recipeId = 0;
        if (idStr != null) {
            recipeId = Integer.parseInt(idStr);
            RecipeDAO dao = new RecipeDAO();
            Recipe recipeDetail = dao.findByID(recipeId);
            request.setAttribute("RecipeDTO", recipeDetail);
            request.getRequestDispatcher(DETAIL_JSP).forward(request, response);
        } else {
            response.sendRedirect(ERROR_PAGE);
        }
    }
}
