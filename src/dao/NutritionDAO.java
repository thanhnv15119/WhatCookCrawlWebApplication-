package dao;

import entity.Nutrion;
import entity.Recipe;
import jaxb.NutritionType;

public class NutritionDAO extends BaseDAO<Nutrion, Integer> {

    public NutritionDAO() {
    }

    public boolean save(Nutrion nutrion) {
        if (nutrion == null) {
            return false;
        }
//        if (nutrion.getRecipe() == null ) {
//            return false;
//        }
        insert(nutrion);
        return true;
    }

    public Nutrion toEntity(NutritionType nutritionType, Recipe recipe) {
        Nutrion rs = new Nutrion();
        rs.setCalories((double) nutritionType.getCalories());
        rs.setCarbs((double) nutritionType.getCarbs());
        rs.setFat((double) nutritionType.getFat());
        rs.setFiber((double) nutritionType.getFiber());
        rs.setProtein((double) nutritionType.getProtein());
        rs.setRecipe(null);
        return rs;
    }


}
