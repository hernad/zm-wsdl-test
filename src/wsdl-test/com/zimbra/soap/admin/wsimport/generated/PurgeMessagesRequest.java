
package com.zimbra.soap.admin.wsimport.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for purgeMessagesRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="purgeMessagesRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="mbox" type="{urn:zimbraAdmin}mailboxByAccountIdSelector" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "purgeMessagesRequest", propOrder = {
    "mbox"
})
public class PurgeMessagesRequest {

    protected MailboxByAccountIdSelector mbox;

    /**
     * Gets the value of the mbox property.
     * 
     * @return
     *     possible object is
     *     {@link MailboxByAccountIdSelector }
     *     
     */
    public MailboxByAccountIdSelector getMbox() {
        return mbox;
    }

    /**
     * Sets the value of the mbox property.
     * 
     * @param value
     *     allowed object is
     *     {@link MailboxByAccountIdSelector }
     *     
     */
    public void setMbox(MailboxByAccountIdSelector value) {
        this.mbox = value;
    }

}
