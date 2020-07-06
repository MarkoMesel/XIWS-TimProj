//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.07.06 at 07:41:48 PM BST 
//


package com.student.soap.contract.scheduleservice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.student.com/scheduleservice/soap/contract}StatusType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="unavailability" type="{http://www.student.com/scheduleservice/soap/contract}SoapUnavailability" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "unavailability"
})
@XmlRootElement(name = "soapUnavailabilityResponse")
public class SoapUnavailabilityResponse
    extends StatusType
{

    protected List<SoapUnavailability> unavailability;

    /**
     * Gets the value of the unavailability property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the unavailability property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUnavailability().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SoapUnavailability }
     * 
     * 
     */
    public List<SoapUnavailability> getUnavailability() {
        if (unavailability == null) {
            unavailability = new ArrayList<SoapUnavailability>();
        }
        return this.unavailability;
    }

}
