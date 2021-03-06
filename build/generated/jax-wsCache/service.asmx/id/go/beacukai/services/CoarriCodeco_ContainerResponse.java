
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
 *         &lt;element name="CoarriCodeco_ContainerResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "coarriCodeco_ContainerResult"
})
@XmlRootElement(name = "CoarriCodeco_ContainerResponse")
public class CoarriCodeco_ContainerResponse {

    @XmlElement(name = "CoarriCodeco_ContainerResult")
    protected String coarriCodeco_ContainerResult;

    /**
     * Gets the value of the coarriCodeco_ContainerResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCoarriCodeco_ContainerResult() {
        return coarriCodeco_ContainerResult;
    }

    /**
     * Sets the value of the coarriCodeco_ContainerResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCoarriCodeco_ContainerResult(String value) {
        this.coarriCodeco_ContainerResult = value;
    }

}
