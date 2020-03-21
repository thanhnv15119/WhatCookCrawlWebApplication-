
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

public class CategoryType {

    protected String name;
    protected String url;
    /**
     * Gets the value of the calories property.
     * 
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
