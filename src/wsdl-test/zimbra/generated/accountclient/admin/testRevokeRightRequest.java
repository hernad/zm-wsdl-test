
package zimbra.generated.accountclient.admin;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for revokeRightRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="revokeRightRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="target" type="{urn:zimbraAdmin}effectiveRightsTargetSelector"/>
 *         &lt;element name="grantee" type="{urn:zimbraAdmin}granteeSelector"/>
 *         &lt;element name="right" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "revokeRightRequest", propOrder = {
    "target",
    "grantee",
    "right"
})
public class testRevokeRightRequest {

    @XmlElement(required = true)
    protected testEffectiveRightsTargetSelector target;
    @XmlElement(required = true)
    protected testGranteeSelector grantee;
    @XmlElement(required = true)
    protected String right;

    /**
     * Gets the value of the target property.
     * 
     * @return
     *     possible object is
     *     {@link testEffectiveRightsTargetSelector }
     *     
     */
    public testEffectiveRightsTargetSelector getTarget() {
        return target;
    }

    /**
     * Sets the value of the target property.
     * 
     * @param value
     *     allowed object is
     *     {@link testEffectiveRightsTargetSelector }
     *     
     */
    public void setTarget(testEffectiveRightsTargetSelector value) {
        this.target = value;
    }

    /**
     * Gets the value of the grantee property.
     * 
     * @return
     *     possible object is
     *     {@link testGranteeSelector }
     *     
     */
    public testGranteeSelector getGrantee() {
        return grantee;
    }

    /**
     * Sets the value of the grantee property.
     * 
     * @param value
     *     allowed object is
     *     {@link testGranteeSelector }
     *     
     */
    public void setGrantee(testGranteeSelector value) {
        this.grantee = value;
    }

    /**
     * Gets the value of the right property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRight() {
        return right;
    }

    /**
     * Sets the value of the right property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRight(String value) {
        this.right = value;
    }

}
