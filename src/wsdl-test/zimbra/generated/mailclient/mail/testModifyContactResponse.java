
package zimbra.generated.mailclient.mail;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for modifyContactResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="modifyContactResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="cn" type="{urn:zimbraMail}contactInfo" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "modifyContactResponse", propOrder = {
    "cn"
})
public class testModifyContactResponse {

    protected testContactInfo cn;

    /**
     * Gets the value of the cn property.
     * 
     * @return
     *     possible object is
     *     {@link testContactInfo }
     *     
     */
    public testContactInfo getCn() {
        return cn;
    }

    /**
     * Sets the value of the cn property.
     * 
     * @param value
     *     allowed object is
     *     {@link testContactInfo }
     *     
     */
    public void setCn(testContactInfo value) {
        this.cn = value;
    }

}