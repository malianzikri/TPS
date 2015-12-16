
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
 *         &lt;element name="GetEksporPermit_FNPEResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "getEksporPermit_FNPEResult"
})
@XmlRootElement(name = "GetEksporPermit_FNPEResponse")
public class GetEksporPermit_FNPEResponse {

    @XmlElement(name = "GetEksporPermit_FNPEResult")
    protected String getEksporPermit_FNPEResult;

    /**
     * Gets the value of the getEksporPermit_FNPEResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGetEksporPermit_FNPEResult() {
        return getEksporPermit_FNPEResult;
    }

    /**
     * Sets the value of the getEksporPermit_FNPEResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGetEksporPermit_FNPEResult(String value) {
        this.getEksporPermit_FNPEResult = value;
    }

}
