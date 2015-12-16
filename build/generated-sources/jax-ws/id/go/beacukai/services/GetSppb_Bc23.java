
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
 *         &lt;element name="No_Sppb" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Tgl_Sppb" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NPWP_Imp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "no_Sppb",
    "tgl_Sppb",
    "npwp_Imp"
})
@XmlRootElement(name = "GetSppb_Bc23")
public class GetSppb_Bc23 {

    @XmlElement(name = "UserName")
    protected String userName;
    @XmlElement(name = "Password")
    protected String password;
    @XmlElement(name = "No_Sppb")
    protected String no_Sppb;
    @XmlElement(name = "Tgl_Sppb")
    protected String tgl_Sppb;
    @XmlElement(name = "NPWP_Imp")
    protected String npwp_Imp;

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
     * Gets the value of the no_Sppb property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNo_Sppb() {
        return no_Sppb;
    }

    /**
     * Sets the value of the no_Sppb property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNo_Sppb(String value) {
        this.no_Sppb = value;
    }

    /**
     * Gets the value of the tgl_Sppb property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTgl_Sppb() {
        return tgl_Sppb;
    }

    /**
     * Sets the value of the tgl_Sppb property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTgl_Sppb(String value) {
        this.tgl_Sppb = value;
    }

    /**
     * Gets the value of the npwp_Imp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNPWP_Imp() {
        return npwp_Imp;
    }

    /**
     * Sets the value of the npwp_Imp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNPWP_Imp(String value) {
        this.npwp_Imp = value;
    }

}
