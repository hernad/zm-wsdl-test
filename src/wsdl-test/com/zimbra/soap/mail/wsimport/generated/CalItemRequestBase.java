
package com.zimbra.soap.mail.wsimport.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for calItemRequestBase complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="calItemRequestBase">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="m" type="{urn:zimbraMail}calendarItemMsg" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="echo" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="max" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="html" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="neuter" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "calItemRequestBase", propOrder = {
    "m"
})
@XmlSeeAlso({
    CreateAppointmentExceptionRequest.class,
    ModifyAppointmentRequest.class,
    CreateAppointmentRequest.class
})
public class CalItemRequestBase {

    protected CalendarItemMsg m;
    @XmlAttribute
    protected Boolean echo;
    @XmlAttribute
    protected Integer max;
    @XmlAttribute
    protected Boolean html;
    @XmlAttribute
    protected Boolean neuter;

    /**
     * Gets the value of the m property.
     * 
     * @return
     *     possible object is
     *     {@link CalendarItemMsg }
     *     
     */
    public CalendarItemMsg getM() {
        return m;
    }

    /**
     * Sets the value of the m property.
     * 
     * @param value
     *     allowed object is
     *     {@link CalendarItemMsg }
     *     
     */
    public void setM(CalendarItemMsg value) {
        this.m = value;
    }

    /**
     * Gets the value of the echo property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isEcho() {
        return echo;
    }

    /**
     * Sets the value of the echo property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setEcho(Boolean value) {
        this.echo = value;
    }

    /**
     * Gets the value of the max property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMax() {
        return max;
    }

    /**
     * Sets the value of the max property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMax(Integer value) {
        this.max = value;
    }

    /**
     * Gets the value of the html property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isHtml() {
        return html;
    }

    /**
     * Sets the value of the html property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setHtml(Boolean value) {
        this.html = value;
    }

    /**
     * Gets the value of the neuter property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isNeuter() {
        return neuter;
    }

    /**
     * Sets the value of the neuter property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setNeuter(Boolean value) {
        this.neuter = value;
    }

}
