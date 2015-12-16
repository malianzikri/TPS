
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
 *         &lt;element name="PermohonanBC12Result" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "permohonanBC12Result"
})
@XmlRootElement(name = "PermohonanBC12Response")
public class PermohonanBC12Response {

    @XmlElement(name = "PermohonanBC12Result")
    protected String permohonanBC12Result;

    /**
     * Gets the value of the permohonanBC12Result property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPermohonanBC12Result() {
        return permohonanBC12Result;
    }

    /**
     * Sets the value of the permohonanBC12Result property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPermohonanBC12Result(String value) {
        this.permohonanBC12Result = value;
    }

}
