
package jaxb;

import entity.Ingredient;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ingredientsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ingredientsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ingredient" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ingredientsType", propOrder = {
    "ingredient"
})
public class IngredientsType {

    protected List<String> ingredient;

    /**
     * Gets the value of the ingredient property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ingredient property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIngredient().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getIngredient() {
        if (ingredient == null) {
            ingredient = new ArrayList<String>();
        }
        return this.ingredient;
    }

    public List<Ingredient> getEntity() {
        List<Ingredient> rs = new ArrayList<>();
        if (ingredient == null) {
            rs = null;
        }
        Ingredient ingredient = null;
        for (String item: this.ingredient) {
            ingredient = new Ingredient();
            ingredient.setContent(item.trim());
            rs.add(ingredient);
        }
        return rs;
    }

    public void setIngredient(List<String> ingredients) {
        this.ingredient = ingredients;
    }

}
