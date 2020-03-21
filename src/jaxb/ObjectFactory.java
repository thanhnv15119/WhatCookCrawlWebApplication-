
package jaxb;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the src.jaxb package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Recipe_QNAME = new QName("", "recipe");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: src.jaxb
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link RecipeType }
     * 
     */
    public RecipeType createRecipeType() {
        return new RecipeType();
    }

    /**
     * Create an instance of {@link IngredientsType }
     * 
     */
    public IngredientsType createIngredientsType() {
        return new IngredientsType();
    }

    /**
     * Create an instance of {@link MethodType }
     * 
     */
    public MethodType createMethodType() {
        return new MethodType();
    }

    /**
     * Create an instance of {@link MethodsType }
     * 
     */
    public MethodsType createMethodsType() {
        return new MethodsType();
    }

    /**
     * Create an instance of {@link NutritionType }
     * 
     */
    public NutritionType createNutritionType() {
        return new NutritionType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RecipeType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "recipe")
    public JAXBElement<RecipeType> createRecipe(RecipeType value) {
        return new JAXBElement<RecipeType>(_Recipe_QNAME, RecipeType.class, null, value);
    }

}
