
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
 *         &lt;element name="GetEkspor_PEBResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "getEkspor_PEBResult"
})
@XmlRootElement(name = "GetEkspor_PEBResponse")
public class GetEkspor_PEBResponse {

    @XmlElement(name = "GetEkspor_PEBResult")
    protected String getEkspor_PEBResult;

    /**
     * Gets the value of the getEkspor_PEBResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGetEkspor_PEBResult() {
        return getEkspor_PEBResult;
    }

    /**
     * Sets the value of the getEkspor_PEBResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGetEkspor_PEBResult(String value) {
        this.getEkspor_PEBResult = value;
    }

}
