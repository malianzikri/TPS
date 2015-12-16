
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
 *         &lt;element name="GetSppb_Bc23Result" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "getSppb_Bc23Result"
})
@XmlRootElement(name = "GetSppb_Bc23Response")
public class GetSppb_Bc23Response {

    @XmlElement(name = "GetSppb_Bc23Result")
    protected String getSppb_Bc23Result;

    /**
     * Gets the value of the getSppb_Bc23Result property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGetSppb_Bc23Result() {
        return getSppb_Bc23Result;
    }

    /**
     * Sets the value of the getSppb_Bc23Result property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGetSppb_Bc23Result(String value) {
        this.getSppb_Bc23Result = value;
    }

}
