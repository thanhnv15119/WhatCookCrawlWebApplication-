package jaxb;

import entity.Nutrion;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

@XmlRootElement(name="recipe")
@XmlType(propOrder = {"id", "name","img","nutrition"})
public class RecipeTitleDTO implements Serializable {
    private int id;
    private String name;
    private String img;
    private NutritionType nutrition;

    public NutritionType getNutrition() {
        return nutrition;
    }

    public void setNutrition(NutritionType nutrition) {
        this.nutrition = nutrition;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
