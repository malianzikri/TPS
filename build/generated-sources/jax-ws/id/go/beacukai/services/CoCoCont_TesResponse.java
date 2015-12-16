
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
 *         &lt;element name="CoCoCont_TesResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "coCoCont_TesResult"
})
@XmlRootElement(name = "CoCoCont_TesResponse")
public class CoCoCont_TesResponse {

    @XmlElement(name = "CoCoCont_TesResult")
    protected String coCoCont_TesResult;

    /**
     * Gets the value of the coCoCont_TesResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCoCoCont_TesResult() {
        return coCoCont_TesResult;
    }

    /**
     * Sets the value of the coCoCont_TesResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCoCoCont_TesResult(String value) {
        this.coCoCont_TesResult = value;
    }

}
