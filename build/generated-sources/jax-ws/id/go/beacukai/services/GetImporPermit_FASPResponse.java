
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
 *         &lt;element name="GetImporPermit_FASPResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "getImporPermit_FASPResult"
})
@XmlRootElement(name = "GetImporPermit_FASPResponse")
public class GetImporPermit_FASPResponse {

    @XmlElement(name = "GetImporPermit_FASPResult")
    protected String getImporPermit_FASPResult;

    /**
     * Gets the value of the getImporPermit_FASPResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGetImporPermit_FASPResult() {
        return getImporPermit_FASPResult;
    }

    /**
     * Sets the value of the getImporPermit_FASPResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGetImporPermit_FASPResult(String value) {
        this.getImporPermit_FASPResult = value;
    }

}
