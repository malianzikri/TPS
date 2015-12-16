
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
 *         &lt;element name="No_Bc11" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Tgl_Bc11" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "no_Bc11",
    "tgl_Bc11"
})
@XmlRootElement(name = "GetImpor_Bc11")
public class GetImpor_Bc11 {

    @XmlElement(name = "UserName")
    protected String userName;
    @XmlElement(name = "Password")
    protected String password;
    @XmlElement(name = "No_Bc11")
    protected String no_Bc11;
    @XmlElement(name = "Tgl_Bc11")
    protected String tgl_Bc11;

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
     * Gets the value of the no_Bc11 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNo_Bc11() {
        return no_Bc11;
    }

    /**
     * Sets the value of the no_Bc11 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNo_Bc11(String value) {
        this.no_Bc11 = value;
    }

    /**
     * Gets the value of the tgl_Bc11 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTgl_Bc11() {
        return tgl_Bc11;
    }

    /**
     * Sets the value of the tgl_Bc11 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTgl_Bc11(String value) {
        this.tgl_Bc11 = value;
    }

}
