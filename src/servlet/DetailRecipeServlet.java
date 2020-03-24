package servlet;

import com.sun.deploy.net.HttpRequest;
import dao.RecipeDAO;
import entity.Ingredient;
import entity.Method;
import entity.Recipe;
import jaxb.IngredientsType;
import jaxb.MethodType;
import jaxb.MethodsType;
import jaxb.RecipeType;
import sun.net.www.http.HttpClient;
import utils.TrAxUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
            RecipeType recipeType = toRecipeType(recipeDetail);

            ByteArrayOutputStream os = TrAxUtils.marshalRecipeDetail(recipeType);

            request.setAttribute("RecipeXml", os.toString());
            request.getRequestDispatcher(DETAIL_JSP).forward(request, response);
        } else {
            response.sendRedirect(ERROR_PAGE);
        }
    }

    private RecipeType toRecipeType(Recipe recipe) {
        RecipeType recipeType = new RecipeType();
        recipeType.setName(recipe.getName());
        recipeType.setImage(recipe.getImg());
        recipeType.setNutrition(recipe.getNutrion().toNuitritionType());
        recipeType.setCookTime(recipe.getCookTime());
        recipeType.setPrepTime(recipe.getPrepTime());
        recipeType.setYield(recipe.getYeild());
        IngredientsType ingredientsType = new IngredientsType();
        List<String> ingredientsList = new ArrayList<>();
        for (Ingredient item: recipe.getIngredients()) {
            ingredientsList.add(item.getContent());
        }
        ingredientsType.setIngredient(ingredientsList);
        recipeType.setIngredients(ingredientsType);
        List<MethodType> listMethods = new ArrayList<>();
        for (Method item: recipe.getMethods()) {
            MethodType methodType = new MethodType();
            methodType.setStep(item.getStep());
            methodType.setContent(item.getContent());
            listMethods.add(methodType);
        }
        MethodsType methodsType = new MethodsType();
        methodsType.setMethod(listMethods);
        recipeType.setMethods(methodsType);
        return recipeType;
    }
}
