package dao;

import entity.Ingredient;
import entity.Recipe;
import jaxb.IngredientsType;

import java.util.ArrayList;
import java.util.List;

public class IngredientDAO extends BaseDAO<Ingredient, Integer> {

    public IngredientDAO() {
    }

    public boolean save(Ingredient ingredient) {
        if (ingredient == null) {
            return false;
        }
        if (ingredient.getContent() == null) {
            return false;
        }
        insert(ingredient);
        return true;
    }

    public List<Ingredient> toEntity(IngredientsType ingredientsType, Recipe recipe) {
        List<Ingredient> rs = new ArrayList<>();
        List<String> ingredients = ingredientsType.getIngredient();
        for (String item: ingredients) {
            Ingredient ingredient = new Ingredient();
            ingredient.setContent(item.trim());
            ingredient.setRecipe(null);
            rs.add(ingredient);
        }
        return rs;
    }


}
