package servlet;

import dao.RecipeDAO;
import entity.Recipe;
import jaxb.RecipeTitleDTO;
import jaxb.RecipeType;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Path("/recipe")
public class RecipeService {
    @GET
    @Produces({MediaType.APPLICATION_XML})
    public List<RecipeTitleDTO> findAll(
            @DefaultValue("0") @QueryParam("categoryId") int categoryId,
            @DefaultValue("-1") @QueryParam("page") int page,
            @DefaultValue("20") @QueryParam("size") int size,
            @DefaultValue("false") @QueryParam("random") boolean random,
            @DefaultValue("-1") @QueryParam("kcal") int kcal,
            @DefaultValue("-1") @QueryParam("mealNumber") int mealNumber
    ) {
        RecipeDAO recipeDAO = new RecipeDAO();
        if (categoryId != 0) {
            return recipeDAO.findByCategories(categoryId).stream().map(Recipe::toRecipeTitleDTO).collect(Collectors.toList());
        }
        if (page != -1) {
            return recipeDAO.findByPaging("Recipe.findAll", page, size).stream().map(Recipe::toRecipeTitleDTO).collect(Collectors.toList());
        }
        if (random) {
            List<RecipeTitleDTO> rs = new ArrayList<>();
            Random rd = new Random();
            List<Recipe> randomList = recipeDAO.findAll();
            while (rs.size() != 5) {
                RecipeTitleDTO randomItem = randomList.get(rd.nextInt(randomList.size() - 1)).toRecipeTitleDTO();
                if (!rs.contains(randomItem)) {
                    rs.add(randomItem);
                }
            }
            return rs;
        }
        if (kcal != -1) {
            return recipeDAO.findByKcal(kcal / mealNumber, mealNumber).stream().map(Recipe::toRecipeTitleDTO).collect(Collectors.toList());
        }

        List<Recipe> listEntity = recipeDAO.findAll();
        List<RecipeTitleDTO> rs = listEntity.stream().map(Recipe::toRecipeTitleDTO).collect(Collectors.toList());
        return rs;
    }

    @GET
    @Path("/kcal")
    @Produces({MediaType.APPLICATION_XML})
    public RecipeTitleDTO findRandomOneByKcal(
            @DefaultValue("0") @QueryParam("kcal") int kcal,
            @DefaultValue("0") @QueryParam("mealNumber") int mealNumber
    ) {
        if (kcal == 0 || mealNumber == 0) {
            return new RecipeTitleDTO();
        }
        Random random = new Random();
        RecipeDAO recipeDAO = new RecipeDAO();
        List<Recipe> list = recipeDAO.findByKcal(kcal/mealNumber, 100);
        if(list.size() ==0) {
            return new RecipeTitleDTO();
        }
        return list.get(random.nextInt(list.size())).toRecipeTitleDTO();
    }
}
