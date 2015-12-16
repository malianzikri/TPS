
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
 *         &lt;element name="GetPendukungPLPResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "getPendukungPLPResult"
})
@XmlRootElement(name = "GetPendukungPLPResponse")
public class GetPendukungPLPResponse {

    @XmlElement(name = "GetPendukungPLPResult")
    protected String getPendukungPLPResult;

    /**
     * Gets the value of the getPendukungPLPResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGetPendukungPLPResult() {
        return getPendukungPLPResult;
    }

    /**
     * Sets the value of the getPendukungPLPResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGetPendukungPLPResult(String value) {
        this.getPendukungPLPResult = value;
    }

}
