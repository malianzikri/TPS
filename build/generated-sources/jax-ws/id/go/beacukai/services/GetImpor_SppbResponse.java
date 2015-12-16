
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
 *         &lt;element name="GetImpor_SppbResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "getImpor_SppbResult"
})
@XmlRootElement(name = "GetImpor_SppbResponse")
public class GetImpor_SppbResponse {

    @XmlElement(name = "GetImpor_SppbResult")
    protected String getImpor_SppbResult;

    /**
     * Gets the value of the getImpor_SppbResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGetImpor_SppbResult() {
        return getImpor_SppbResult;
    }

    /**
     * Sets the value of the getImpor_SppbResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGetImpor_SppbResult(String value) {
        this.getImpor_SppbResult = value;
    }

}
