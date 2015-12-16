
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
 *         &lt;element name="GetSPPB12_TPSTujuanResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "getSPPB12_TPSTujuanResult"
})
@XmlRootElement(name = "GetSPPB12_TPSTujuanResponse")
public class GetSPPB12_TPSTujuanResponse {

    @XmlElement(name = "GetSPPB12_TPSTujuanResult")
    protected String getSPPB12_TPSTujuanResult;

    /**
     * Gets the value of the getSPPB12_TPSTujuanResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGetSPPB12_TPSTujuanResult() {
        return getSPPB12_TPSTujuanResult;
    }

    /**
     * Sets the value of the getSPPB12_TPSTujuanResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGetSPPB12_TPSTujuanResult(String value) {
        this.getSPPB12_TPSTujuanResult = value;
    }

}
