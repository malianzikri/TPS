
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
 *         &lt;element name="GetEkspor_PKBEResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "getEkspor_PKBEResult"
})
@XmlRootElement(name = "GetEkspor_PKBEResponse")
public class GetEkspor_PKBEResponse {

    @XmlElement(name = "GetEkspor_PKBEResult")
    protected String getEkspor_PKBEResult;

    /**
     * Gets the value of the getEkspor_PKBEResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGetEkspor_PKBEResult() {
        return getEkspor_PKBEResult;
    }

    /**
     * Sets the value of the getEkspor_PKBEResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGetEkspor_PKBEResult(String value) {
        this.getEkspor_PKBEResult = value;
    }

}
