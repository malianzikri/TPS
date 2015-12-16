
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
 *         &lt;element name="GetPendukungPLP_BLResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "getPendukungPLP_BLResult"
})
@XmlRootElement(name = "GetPendukungPLP_BLResponse")
public class GetPendukungPLP_BLResponse {

    @XmlElement(name = "GetPendukungPLP_BLResult")
    protected String getPendukungPLP_BLResult;

    /**
     * Gets the value of the getPendukungPLP_BLResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGetPendukungPLP_BLResult() {
        return getPendukungPLP_BLResult;
    }

    /**
     * Sets the value of the getPendukungPLP_BLResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGetPendukungPLP_BLResult(String value) {
        this.getPendukungPLP_BLResult = value;
    }

}
