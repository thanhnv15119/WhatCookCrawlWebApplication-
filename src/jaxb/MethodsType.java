
package jaxb;

import entity.Method;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for methodsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="methodsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="method" type="{}methodType" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "methodsType", propOrder = {
    "method"
})
public class MethodsType {

    @XmlElement(required = true)
    protected List<MethodType> method;

    /**
     * Gets the value of the method property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the method property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMethod().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MethodType }
     * 
     * 
     */
    public List<MethodType> getMethod() {
        if (method == null) {
            method = new ArrayList<MethodType>();
        }
        return this.method;
    }

    public List<Method> toEntity() {
        List<Method> rs = new ArrayList<>();
        List<MethodType> methods = this.method;
        for (MethodType item: methods) {
            Method method = new Method();
            method.setContent(item.getContent().trim());
            method.setStep(item.getStep());
            method.setRecipe(null);
            rs.add(method);
        }
        return rs;
    }


}
