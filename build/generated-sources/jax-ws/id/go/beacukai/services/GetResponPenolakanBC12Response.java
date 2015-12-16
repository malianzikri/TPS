
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
 *         &lt;element name="GetResponPenolakanBC12Result" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "getResponPenolakanBC12Result"
})
@XmlRootElement(name = "GetResponPenolakanBC12Response")
public class GetResponPenolakanBC12Response {

    @XmlElement(name = "GetResponPenolakanBC12Result")
    protected String getResponPenolakanBC12Result;

    /**
     * Gets the value of the getResponPenolakanBC12Result property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGetResponPenolakanBC12Result() {
        return getResponPenolakanBC12Result;
    }

    /**
     * Sets the value of the getResponPenolakanBC12Result property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGetResponPenolakanBC12Result(String value) {
        this.getResponPenolakanBC12Result = value;
    }

}
