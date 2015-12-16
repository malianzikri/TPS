
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
 *         &lt;element name="GetDokumenManual_OnDemandResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "getDokumenManual_OnDemandResult"
})
@XmlRootElement(name = "GetDokumenManual_OnDemandResponse")
public class GetDokumenManual_OnDemandResponse {

    @XmlElement(name = "GetDokumenManual_OnDemandResult")
    protected String getDokumenManual_OnDemandResult;

    /**
     * Gets the value of the getDokumenManual_OnDemandResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGetDokumenManual_OnDemandResult() {
        return getDokumenManual_OnDemandResult;
    }

    /**
     * Sets the value of the getDokumenManual_OnDemandResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGetDokumenManual_OnDemandResult(String value) {
        this.getDokumenManual_OnDemandResult = value;
    }

}
