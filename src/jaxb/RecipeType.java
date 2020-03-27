
package jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for recipeType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="recipeType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="image" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="yield" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="nutrition" type="{}nutritionType"/>
 *         &lt;element name="ingredients" type="{}ingredientsType"/>
 *         &lt;element name="methods" type="{}methodsType"/>
 *         &lt;element name="prepTime" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="cookTime" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "recipe")
@XmlType(name = "recipeType", propOrder = {
    "name",
    "image",
    "yield",
    "nutrition",
    "ingredients",
    "methods",
    "prepTime",
    "cookTime"
})
public class RecipeType {

    @XmlElement(required = true)
    protected String name;
    @XmlElement(required = true)
    protected String image;
    @XmlElement(required = true)
    protected String yield;
    @XmlElement(required = true)
    protected NutritionType nutrition;
    @XmlElement(required = true)
    protected IngredientsType ingredients;
    @XmlElement(required = true)
    protected MethodsType methods;
    @XmlElement(required = true)
    protected int prepTime;
    @XmlElement(required = true)
    protected int cookTime;

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the image property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImage() {
        return image;
    }

    /**
     * Sets the value of the image property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImage(String value) {
        this.image = value;
    }

    /**
     * Gets the value of the yield property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getYield() {
        return yield;
    }

    /**
     * Sets the value of the yield property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setYield(String value) {
        this.yield = value;
    }

    /**
     * Gets the value of the nutrition property.
     * 
     * @return
     *     possible object is
     *     {@link NutritionType }
     *     
     */
    public NutritionType getNutrition() {
        return nutrition;
    }

    /**
     * Sets the value of the nutrition property.
     * 
     * @param value
     *     allowed object is
     *     {@link NutritionType }
     *     
     */
    public void setNutrition(NutritionType value) {
        this.nutrition = value;
    }

    /**
     * Gets the value of the ingredients property.
     * 
     * @return
     *     possible object is
     *     {@link IngredientsType }
     *     
     */
    public IngredientsType getIngredients() {
        return ingredients;
    }

    /**
     * Sets the value of the ingredients property.
     * 
     * @param value
     *     allowed object is
     *     {@link IngredientsType }
     *     
     */
    public void setIngredients(IngredientsType value) {
        this.ingredients = value;
    }

    /**
     * Gets the value of the methods property.
     * 
     * @return
     *     possible object is
     *     {@link MethodsType }
     *     
     */
    public MethodsType getMethods() {
        return methods;
    }

    /**
     * Sets the value of the methods property.
     * 
     * @param value
     *     allowed object is
     *     {@link MethodsType }
     *     
     */
    public void setMethods(MethodsType value) {
        this.methods = value;
    }

    /**
     * Gets the value of the prepTime property.
     * 
     */
    public int getPrepTime() {
        return prepTime;
    }

    /**
     * Sets the value of the prepTime property.
     * 
     */
    public void setPrepTime(int value) {
        this.prepTime = value;
    }

    /**
     * Gets the value of the cookTime property.
     * 
     */
    public int getCookTime() {
        return cookTime;
    }

    /**
     * Sets the value of the cookTime property.
     * 
     */
    public void setCookTime(int value) {
        this.cookTime = value;
    }

}
