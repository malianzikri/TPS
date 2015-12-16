
package id.go.beacukai.services;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="GetImpor_Bc11Result" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "getImpor_Bc11Result"
})
@XmlRootElement(name = "GetImpor_Bc11Response")
public class GetImpor_Bc11Response {

    @XmlElement(name = "GetImpor_Bc11Result")
    protected String getImpor_Bc11Result;

    /**
     * Gets the value of the getImpor_Bc11Result property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGetImpor_Bc11Result() {
        return getImpor_Bc11Result;
    }

    /**
     * Sets the value of the getImpor_Bc11Result property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGetImpor_Bc11Result(String value) {
        this.getImpor_Bc11Result = value;
    }

}
