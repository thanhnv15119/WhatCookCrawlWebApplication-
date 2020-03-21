package servlet;

import dao.RecipeDAO;
import entity.Recipe;
import jaxb.RecipeTitleDTO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "FindMealsServlet")
public class FindMealsServlet extends HttpServlet {
    private final String ERROR_PAGE = "error.html";
    private final String RECOMMEND_PAGE = "recommend.jsp";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String kcalStr = request.getParameter("txtKcal");
        String mealNumberStr = request.getParameter("mealNumber");
        if (kcalStr != null && mealNumberStr!= null) {
            int kcal = Integer.parseInt(kcalStr);
            int mealNumber = Integer.parseInt(mealNumberStr);
            RecipeDAO recipeDAO = new RecipeDAO();
            List<Recipe> recipeList = recipeDAO.findByKcal(kcal/mealNumber,mealNumber);
            if(mealNumber > recipeList.size()) {
                recipeList = null;
            } else {
                request.setAttribute("recipeList", recipeList);
                request.setAttribute("txtKcal", kcal);
                request.setAttribute("mealNumber", mealNumber);
            }
            request.getRequestDispatcher(RECOMMEND_PAGE).forward(request,response);
        } else {
            response.sendRedirect(ERROR_PAGE);
        }
    }

}
