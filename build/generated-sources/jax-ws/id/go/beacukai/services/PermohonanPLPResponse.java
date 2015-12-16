
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
 *         &lt;element name="PermohonanPLPResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "permohonanPLPResult"
})
@XmlRootElement(name = "PermohonanPLPResponse")
public class PermohonanPLPResponse {

    @XmlElement(name = "PermohonanPLPResult")
    protected String permohonanPLPResult;

    /**
     * Gets the value of the permohonanPLPResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPermohonanPLPResult() {
        return permohonanPLPResult;
    }

    /**
     * Sets the value of the permohonanPLPResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPermohonanPLPResult(String value) {
        this.permohonanPLPResult = value;
    }

}
