
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
 *         &lt;element name="noBc11" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tglBc11" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="noCont" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "noBc11",
    "tglBc11",
    "noCont"
})
@XmlRootElement(name = "GetPendukungPLP_BL")
public class GetPendukungPLP_BL {

    @XmlElement(name = "UserName")
    protected String userName;
    @XmlElement(name = "Password")
    protected String password;
    protected String noBc11;
    protected String tglBc11;
    protected String noCont;

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
     * Gets the value of the noBc11 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNoBc11() {
        return noBc11;
    }

    /**
     * Sets the value of the noBc11 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNoBc11(String value) {
        this.noBc11 = value;
    }

    /**
     * Gets the value of the tglBc11 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTglBc11() {
        return tglBc11;
    }

    /**
     * Sets the value of the tglBc11 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTglBc11(String value) {
        this.tglBc11 = value;
    }

    /**
     * Gets the value of the noCont property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNoCont() {
        return noCont;
    }

    /**
     * Sets the value of the noCont property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNoCont(String value) {
        this.noCont = value;
    }

}
