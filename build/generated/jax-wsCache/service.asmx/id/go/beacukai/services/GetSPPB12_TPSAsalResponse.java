
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
 *         &lt;element name="GetSPPB12_TPSAsalResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "getSPPB12_TPSAsalResult"
})
@XmlRootElement(name = "GetSPPB12_TPSAsalResponse")
public class GetSPPB12_TPSAsalResponse {

    @XmlElement(name = "GetSPPB12_TPSAsalResult")
    protected String getSPPB12_TPSAsalResult;

    /**
     * Gets the value of the getSPPB12_TPSAsalResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGetSPPB12_TPSAsalResult() {
        return getSPPB12_TPSAsalResult;
    }

    /**
     * Sets the value of the getSPPB12_TPSAsalResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGetSPPB12_TPSAsalResult(String value) {
        this.getSPPB12_TPSAsalResult = value;
    }

}
