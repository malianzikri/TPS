
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
 *         &lt;element name="GetBC23Permit_FASPResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "getBC23Permit_FASPResult"
})
@XmlRootElement(name = "GetBC23Permit_FASPResponse")
public class GetBC23Permit_FASPResponse {

    @XmlElement(name = "GetBC23Permit_FASPResult")
    protected String getBC23Permit_FASPResult;

    /**
     * Gets the value of the getBC23Permit_FASPResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGetBC23Permit_FASPResult() {
        return getBC23Permit_FASPResult;
    }

    /**
     * Sets the value of the getBC23Permit_FASPResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGetBC23Permit_FASPResult(String value) {
        this.getBC23Permit_FASPResult = value;
    }

}
