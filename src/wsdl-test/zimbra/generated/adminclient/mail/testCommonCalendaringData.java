
package zimbra.generated.adminclient.mail;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for commonCalendaringData complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="commonCalendaringData">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:zimbraMail}instanceDataAttrs">
 *       &lt;sequence>
 *       &lt;/sequence>
 *       &lt;attribute name="x_uid" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="uid" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="f" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="t" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="l" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="s" use="required" type="{http://www.w3.org/2001/XMLSchema}long" />
 *       &lt;attribute name="md" use="required" type="{http://www.w3.org/2001/XMLSchema}long" />
 *       &lt;attribute name="ms" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="rev" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "commonCalendaringData")
@XmlSeeAlso({
    testCalendaringData.class,
    testCalendarItemHitInfo.class,
    testLegacyCalendaringData.class
})
public class testCommonCalendaringData
    extends testInstanceDataAttrs
{

    @XmlAttribute(name = "x_uid", required = true)
    protected String xUid;
    @XmlAttribute(name = "uid", required = true)
    protected String uid;
    @XmlAttribute(name = "f")
    protected String f;
    @XmlAttribute(name = "t")
    protected String t;
    @XmlAttribute(name = "l")
    protected String l;
    @XmlAttribute(name = "s", required = true)
    protected long s;
    @XmlAttribute(name = "md", required = true)
    protected long md;
    @XmlAttribute(name = "ms", required = true)
    protected int ms;
    @XmlAttribute(name = "rev", required = true)
    protected int rev;
    @XmlAttribute(name = "id")
    protected String id;

    /**
     * Gets the value of the x_Uid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getX_Uid() {
        return xUid;
    }

    /**
     * Sets the value of the x_Uid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setX_Uid(String value) {
        this.xUid = value;
    }

    /**
     * Gets the value of the uid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUid() {
        return uid;
    }

    /**
     * Sets the value of the uid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUid(String value) {
        this.uid = value;
    }

    /**
     * Gets the value of the f property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getF() {
        return f;
    }

    /**
     * Sets the value of the f property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setF(String value) {
        this.f = value;
    }

    /**
     * Gets the value of the t property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getT() {
        return t;
    }

    /**
     * Sets the value of the t property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setT(String value) {
        this.t = value;
    }

    /**
     * Gets the value of the l property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getL() {
        return l;
    }

    /**
     * Sets the value of the l property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setL(String value) {
        this.l = value;
    }

    /**
     * Gets the value of the s property.
     * 
     */
    public long getS() {
        return s;
    }

    /**
     * Sets the value of the s property.
     * 
     */
    public void setS(long value) {
        this.s = value;
    }

    /**
     * Gets the value of the md property.
     * 
     */
    public long getMd() {
        return md;
    }

    /**
     * Sets the value of the md property.
     * 
     */
    public void setMd(long value) {
        this.md = value;
    }

    /**
     * Gets the value of the ms property.
     * 
     */
    public int getMs() {
        return ms;
    }

    /**
     * Sets the value of the ms property.
     * 
     */
    public void setMs(int value) {
        this.ms = value;
    }

    /**
     * Gets the value of the rev property.
     * 
     */
    public int getRev() {
        return rev;
    }

    /**
     * Sets the value of the rev property.
     * 
     */
    public void setRev(int value) {
        this.rev = value;
    }

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
