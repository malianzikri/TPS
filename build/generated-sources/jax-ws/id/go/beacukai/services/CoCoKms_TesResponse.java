
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
 *         &lt;element name="CoCoKms_TesResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "coCoKms_TesResult"
})
@XmlRootElement(name = "CoCoKms_TesResponse")
public class CoCoKms_TesResponse {

    @XmlElement(name = "CoCoKms_TesResult")
    protected String coCoKms_TesResult;

    /**
     * Gets the value of the coCoKms_TesResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCoCoKms_TesResult() {
        return coCoKms_TesResult;
    }

    /**
     * Sets the value of the coCoKms_TesResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCoCoKms_TesResult(String value) {
        this.coCoKms_TesResult = value;
    }

}
