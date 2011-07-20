
package zimbra.generated.mailclient.zm;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import zimbra.generated.mailclient.mail.testCalDataSourceId;
import zimbra.generated.mailclient.mail.testCaldavDataSourceId;
import zimbra.generated.mailclient.mail.testGalDataSourceId;
import zimbra.generated.mailclient.mail.testImapDataSourceId;
import zimbra.generated.mailclient.mail.testPop3DataSourceId;
import zimbra.generated.mailclient.mail.testRssDataSourceId;
import zimbra.generated.mailclient.mail.testUnknownDataSourceId;
import zimbra.generated.mailclient.mail.testYabDataSourceId;


/**
 * <p>Java class for id complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="id">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *       &lt;/sequence>
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "id")
@XmlSeeAlso({
    testPop3DataSourceId.class,
    testRssDataSourceId.class,
    testCaldavDataSourceId.class,
    testImapDataSourceId.class,
    testUnknownDataSourceId.class,
    testYabDataSourceId.class,
    testGalDataSourceId.class,
    testCalDataSourceId.class
})
public class testId {

    @XmlAttribute(name = "id")
    protected String id;

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

}