
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
 *         &lt;element name="GetResponPLP_TujuanResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "getResponPLP_TujuanResult"
})
@XmlRootElement(name = "GetResponPLP_TujuanResponse")
public class GetResponPLP_TujuanResponse {

    @XmlElement(name = "GetResponPLP_TujuanResult")
    protected String getResponPLP_TujuanResult;

    /**
     * Gets the value of the getResponPLP_TujuanResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGetResponPLP_TujuanResult() {
        return getResponPLP_TujuanResult;
    }

    /**
     * Sets the value of the getResponPLP_TujuanResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGetResponPLP_TujuanResult(String value) {
        this.getResponPLP_TujuanResult = value;
    }

}
