package entity;

import jaxb.NutritionType;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.util.Objects;

@Entity
public class Nutrion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private int id;
    private Double calories;
    private Double fat;
    private Double fiber;
    private Double protein;
    private Double carbs;

    public Nutrion() {
    }


    @OneToOne
    @JoinColumn(name = "RECIPE_ID")
    private Recipe recipe;

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "CALORIES", nullable = true, precision = 0)
    public Double getCalories() {
        return calories;
    }

    public void setCalories(Double calories) {
        this.calories = calories;
    }

    @Basic
    @Column(name = "FAT", nullable = true, precision = 0)
    public Double getFat() {
        return fat;
    }

    public void setFat(Double fat) {
        this.fat = fat;
    }

    @Basic
    @Column(name = "FIBER", nullable = true, precision = 0)
    public Double getFiber() {
        return fiber;
    }

    public void setFiber(Double fiber) {
        this.fiber = fiber;
    }

    @Basic
    @Column(name = "PROTEIN", nullable = true, precision = 0)
    public Double getProtein() {
        return protein;
    }

    public void setProtein(Double protein) {
        this.protein = protein;
    }

    @Basic
    @Column(name = "CARBS", nullable = true, precision = 0)
    public Double getCarbs() {
        return carbs;
    }

    public void setCarbs(Double carbs) {
        this.carbs = carbs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Nutrion nutrion = (Nutrion) o;
        return id == nutrion.id &&
                Objects.equals(calories, nutrion.calories) &&
                Objects.equals(fat, nutrion.fat) &&
                Objects.equals(fiber, nutrion.fiber) &&
                Objects.equals(protein, nutrion.protein) &&
                Objects.equals(carbs, nutrion.carbs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, calories, fat, fiber, protein, carbs);
    }

    public NutritionType toNuitritionType() {
        NutritionType dto = new NutritionType();
        dto.setCalories(this.calories.floatValue());
        dto.setCarbs(this.carbs.floatValue());
        dto.setFat(this.fat.floatValue());
        dto.setFiber(this.fiber.floatValue());
        dto.setProtein(this.protein.floatValue());
        return dto;
    }
}
