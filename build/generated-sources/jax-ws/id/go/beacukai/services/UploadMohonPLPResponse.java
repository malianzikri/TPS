
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
 *         &lt;element name="UploadMohonPLPResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "uploadMohonPLPResult"
})
@XmlRootElement(name = "UploadMohonPLPResponse")
public class UploadMohonPLPResponse {

    @XmlElement(name = "UploadMohonPLPResult")
    protected String uploadMohonPLPResult;

    /**
     * Gets the value of the uploadMohonPLPResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUploadMohonPLPResult() {
        return uploadMohonPLPResult;
    }

    /**
     * Sets the value of the uploadMohonPLPResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUploadMohonPLPResult(String value) {
        this.uploadMohonPLPResult = value;
    }

}
