
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
 *         &lt;element name="UserName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Password" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NPWP_Eks" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="No_Npe" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Kd_Ktr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "userName",
    "password",
    "npwp_Eks",
    "no_Npe",
    "kd_Ktr"
})
@XmlRootElement(name = "GetNPE")
public class GetNPE {

    @XmlElement(name = "UserName")
    protected String userName;
    @XmlElement(name = "Password")
    protected String password;
    @XmlElement(name = "NPWP_Eks")
    protected String npwp_Eks;
    @XmlElement(name = "No_Npe")
    protected String no_Npe;
    @XmlElement(name = "Kd_Ktr")
    protected String kd_Ktr;

    /**
     * Gets the value of the userName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the value of the userName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserName(String value) {
        this.userName = value;
    }

    /**
     * Gets the value of the password property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the value of the password property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPassword(String value) {
        this.password = value;
    }

    /**
     * Gets the value of the npwp_Eks property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNPWP_Eks() {
        return npwp_Eks;
    }

    /**
     * Sets the value of the npwp_Eks property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNPWP_Eks(String value) {
        this.npwp_Eks = value;
    }

    /**
     * Gets the value of the no_Npe property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNo_Npe() {
        return no_Npe;
    }

    /**
     * Sets the value of the no_Npe property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNo_Npe(String value) {
        this.no_Npe = value;
    }

    /**
     * Gets the value of the kd_Ktr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKd_Ktr() {
        return kd_Ktr;
    }

    /**
     * Sets the value of the kd_Ktr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKd_Ktr(String value) {
        this.kd_Ktr = value;
    }

}
