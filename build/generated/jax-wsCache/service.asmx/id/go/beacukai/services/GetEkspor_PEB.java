
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
 *         &lt;element name="No_PEB" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Tgl_PEB" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="npwp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "no_PEB",
    "tgl_PEB",
    "npwp"
})
@XmlRootElement(name = "GetEkspor_PEB")
public class GetEkspor_PEB {

    @XmlElement(name = "UserName")
    protected String userName;
    @XmlElement(name = "Password")
    protected String password;
    @XmlElement(name = "No_PEB")
    protected String no_PEB;
    @XmlElement(name = "Tgl_PEB")
    protected String tgl_PEB;
    protected String npwp;

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
     * Gets the value of the no_PEB property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNo_PEB() {
        return no_PEB;
    }

    /**
     * Sets the value of the no_PEB property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNo_PEB(String value) {
        this.no_PEB = value;
    }

    /**
     * Gets the value of the tgl_PEB property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTgl_PEB() {
        return tgl_PEB;
    }

    /**
     * Sets the value of the tgl_PEB property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTgl_PEB(String value) {
        this.tgl_PEB = value;
    }

    /**
     * Gets the value of the npwp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNpwp() {
        return npwp;
    }

    /**
     * Sets the value of the npwp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNpwp(String value) {
        this.npwp = value;
    }

}
