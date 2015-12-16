
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
 *         &lt;element name="No_PKBE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TGL_PKBE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="kdKantor" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "no_PKBE",
    "tgl_PKBE",
    "kdKantor"
})
@XmlRootElement(name = "GetEkspor_PKBE")
public class GetEkspor_PKBE {

    @XmlElement(name = "UserName")
    protected String userName;
    @XmlElement(name = "Password")
    protected String password;
    @XmlElement(name = "No_PKBE")
    protected String no_PKBE;
    @XmlElement(name = "TGL_PKBE")
    protected String tgl_PKBE;
    protected String kdKantor;

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
     * Gets the value of the no_PKBE property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNo_PKBE() {
        return no_PKBE;
    }

    /**
     * Sets the value of the no_PKBE property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNo_PKBE(String value) {
        this.no_PKBE = value;
    }

    /**
     * Gets the value of the tgl_PKBE property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTGL_PKBE() {
        return tgl_PKBE;
    }

    /**
     * Sets the value of the tgl_PKBE property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTGL_PKBE(String value) {
        this.tgl_PKBE = value;
    }

    /**
     * Gets the value of the kdKantor property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKdKantor() {
        return kdKantor;
    }

    /**
     * Sets the value of the kdKantor property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKdKantor(String value) {
        this.kdKantor = value;
    }

}
