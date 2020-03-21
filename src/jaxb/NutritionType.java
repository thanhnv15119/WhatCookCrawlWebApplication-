
package jaxb;

import entity.Nutrion;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for nutritionType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="nutritionType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="calories" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="carbs" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="fat" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="protein" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="fiber" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "nutritionType", propOrder = {
    "calories",
    "carbs",
    "fat",
    "protein",
    "fiber"
})
public class NutritionType {

    protected float calories;
    protected float carbs;
    protected float fat;
    protected float protein;
    protected float fiber;

    /**
     * Gets the value of the calories property.
     * 
     */
    public float getCalories() {
        return calories;
    }

    /**
     * Sets the value of the calories property.
     * 
     */
    public void setCalories(float value) {
        this.calories = value;
    }

    /**
     * Gets the value of the carbs property.
     * 
     */
    public float getCarbs() {
        return carbs;
    }

    /**
     * Sets the value of the carbs property.
     * 
     */
    public void setCarbs(float value) {
        this.carbs = value;
    }

    /**
     * Gets the value of the fat property.
     * 
     */
    public float getFat() {
        return fat;
    }

    /**
     * Sets the value of the fat property.
     * 
     */
    public void setFat(float value) {
        this.fat = value;
    }

    /**
     * Gets the value of the protein property.
     * 
     */
    public float getProtein() {
        return protein;
    }

    /**
     * Sets the value of the protein property.
     * 
     */
    public void setProtein(float value) {
        this.protein = value;
    }

    /**
     * Gets the value of the fiber property.
     * 
     */
    public float getFiber() {
        return fiber;
    }

    /**
     * Sets the value of the fiber property.
     * 
     */
    public void setFiber(float value) {
        this.fiber = value;
    }

    public Nutrion toEntity() {
        Nutrion rs = new Nutrion();
        rs.setProtein((double) this.protein);
        rs.setFiber((double) this.fiber);
        rs.setFat((double) this.fat);
        rs.setCarbs((double) this.carbs);
        rs.setCalories((double) this.calories);
        return rs;
    }

}
