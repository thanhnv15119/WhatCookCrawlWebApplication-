package entity;

import jaxb.RecipeTitleDTO;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@NamedQueries({
        @NamedQuery(name = "Recipe.findByName", query = "select r from Recipe r where r.name=:name"),
        @NamedQuery(name = "Recipe.findAll", query = "select r from Recipe  r"),
        @NamedQuery(name = "Recipe.findByKcal", query = "select  r from Recipe  r inner join Nutrion n on r.id = n.recipe.id where :kcal -20 < n.calories AND  :kcal + 20 > n.calories ")
})
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private int id;
    private String name;
    private String img;
    private Integer prepTime;
    private Integer cookTime;
    private String yeild;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
    private List<Method> methods = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
    private List<Ingredient> ingredients = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "recipe")
    private Nutrion nutrion;

    public List<Method> getMethods() {
        return methods;
    }

    public void setMethods(List<Method> methods) {
        this.methods = methods;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public Nutrion getNutrion() {
        return nutrion;
    }

    public void setNutrion(Nutrion nutrion) {
        this.nutrion = nutrion;
    }


    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @OneToOne()
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "NAME", nullable = true, length = 100)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "IMG", nullable = true, length = 225)
    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Basic
    @Column(name = "PREP_TIME", nullable = true)
    public Integer getPrepTime() {
        return prepTime;
    }

    public void setPrepTime(Integer prepTime) {
        this.prepTime = prepTime;
    }

    @Basic
    @Column(name = "COOK_TIME", nullable = true)
    public Integer getCookTime() {
        return cookTime;
    }

    public void setCookTime(Integer cookTime) {
        this.cookTime = cookTime;
    }

    @Basic
    @Column(name = "YEILD", nullable = true, length = 45)
    public String getYeild() {
        return yeild;
    }

    public void setYeild(String yeild) {
        this.yeild = yeild;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recipe recipe = (Recipe) o;
        return id == recipe.id &&
                Objects.equals(name, recipe.name) &&
                Objects.equals(img, recipe.img) &&
                Objects.equals(prepTime, recipe.prepTime) &&
                Objects.equals(cookTime, recipe.cookTime) &&
                Objects.equals(yeild, recipe.yeild);
    }

    public Recipe() {
    }
    public RecipeTitleDTO toRecipeTitleDTO() {
        RecipeTitleDTO dto = new RecipeTitleDTO();
        dto.setId(this.id);
        dto.setName(this.name);
        dto.setImg(this.img);
        dto.setNutrition(this.nutrion.toNuitritionType());
        return dto;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, img, prepTime, cookTime, yeild);
    }
}
