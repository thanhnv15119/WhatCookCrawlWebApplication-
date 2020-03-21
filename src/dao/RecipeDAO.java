package dao;

import entity.Category;
import entity.Ingredient;
import entity.Method;
import entity.Nutrion;
import entity.Recipe;
import jaxb.RecipeType;
import utils.DBUtils;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import java.util.List;
import java.util.stream.Collectors;

public class RecipeDAO extends BaseDAO<Recipe, Integer> {

    public RecipeDAO() {
    }

    public List<Recipe> findByKcal(int kcal, int mealsNumber) {
        EntityManager em = DBUtils.getEntityManager();
        List<Recipe> rs = null;
        try {
            EntityTransaction et = em.getTransaction();
            et.begin();
            rs = em.createNamedQuery("Recipe.findByKcal")
                    .setParameter("kcal", kcal)
                    .setMaxResults(mealsNumber).getResultList();
            et.commit();
        }catch (NoResultException e) {
            rs = null;
        } finally {
            if(em!= null) {
                em.close();
            }
        }
        return rs;
    }

    public List<Recipe> findAll() {
        EntityManager em = DBUtils.getEntityManager();
        List<Recipe> rs = null;
        try {
            EntityTransaction et = em.getTransaction();
            et.begin();
            rs = em.createNamedQuery("Recipe.findAll").getResultList();
            et.commit();
        }catch (NoResultException e) {
            rs = null;
        } finally {
            if(em!= null) {
                em.close();
            }
        }
        return rs;
    }

    public Recipe save(Recipe recipe) {
        if (recipe == null) {
            System.out.println("RecipeDAO recipe NULL");
            return null;
        }
        if (recipe.getCategory() == null) {
            System.out.println("RecipeDAO category NULL");
            return null;
        }

        CategoryDAO categoryDAO = new CategoryDAO();
        Category checkExitCato = categoryDAO.findByName(recipe.getCategory().getName());
        if (checkExitCato == null) {
            System.out.println("RecipeDAO category not exits");
            return null;
        }

        return insert(recipe);
    }

    public Recipe toEntity(RecipeType recipeType) {
        Recipe recipe = new Recipe();
        recipe.setName(recipeType.getName().trim());
        recipe.setImg(recipeType.getImage().trim());
        recipe.setYeild(recipeType.getYield().trim());
        recipe.setCookTime(recipeType.getCookTime());
        recipe.setPrepTime(recipeType.getPrepTime());
//        recipe.setIngredients(recipeType.getIngredients().getEntity());
//        recipe.setMethods(recipeType.getMethods().toEntity());
//        recipe.setNutrion(recipeType.getNutrition().toEntity());
        return recipe;
    }

    public Recipe findByName(String name) {
        EntityManager em = DBUtils.getEntityManager();
        Recipe rs = null;
        try {
            EntityTransaction et = em.getTransaction();
            et.begin();
            rs = (Recipe) em.createNamedQuery("Recipe.findByName")
                    .setParameter("name", name)
                    .getSingleResult();
            et.commit();
        } catch (NoResultException e) {
            rs = null;
        } catch (NonUniqueResultException e) {
            rs = null;
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return rs;
    }

    public void insertRecipeType(RecipeType recipeType, Category category) {
        Recipe checkDupName = findByName(recipeType.getName());
        if(checkDupName == null) {
        Recipe recipe = toEntity(recipeType);
        recipe.setCategory(category);
        insert(recipe);
        Recipe finalRecipe = findByName(recipe.getName());
        List<Ingredient> ingredients = recipeType.getIngredients().getEntity();
        for (Ingredient item : ingredients) {
            IngredientDAO ingredientDAO = new IngredientDAO();
            item.setRecipe(finalRecipe);
            ingredientDAO.save(item);
        }

        List<Method> methods = recipeType.getMethods().toEntity();
        for (Method item : methods) {
            MethodDAO methodDAO = new MethodDAO();
            item.setRecipe(finalRecipe);
            methodDAO.save(item);
        }

        NutritionDAO nutritionDAO = new NutritionDAO();
        Nutrion nutrion = recipeType.getNutrition().toEntity();
        nutrion.setRecipe(finalRecipe);
        nutritionDAO.save(nutrion);
            System.out.println(finalRecipe.getId()+"AAAAAAA");
        }
    }

    public List<Recipe> findByCategories(int categorid) {
        List<Recipe> rs = null;
        rs = findAll().stream().filter(recipe -> {
            return recipe.getCategory().getId() == categorid;
        }).collect(Collectors.toList());
        return rs;
    }

}
