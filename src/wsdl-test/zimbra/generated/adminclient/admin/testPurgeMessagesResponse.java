
package zimbra.generated.adminclient.admin;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for purgeMessagesResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="purgeMessagesResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="mbox" type="{urn:zimbraAdmin}mailboxWithMailboxId" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "purgeMessagesResponse", propOrder = {
    "mbox"
})
public class testPurgeMessagesResponse {

    protected List<testMailboxWithMailboxId> mbox;

    /**
     * Gets the value of the mbox property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the mbox property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMbox().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link testMailboxWithMailboxId }
     * 
     * 
     */
    public List<testMailboxWithMailboxId> getMbox() {
        if (mbox == null) {
            mbox = new ArrayList<testMailboxWithMailboxId>();
        }
        return this.mbox;
    }

}