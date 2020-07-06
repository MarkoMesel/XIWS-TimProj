//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.07.06 at 07:41:59 PM BST 
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
 *         &lt;element name="pendingComment" type="{http://www.student.com/scheduleservice/soap/contract}Correspondence" maxOccurs="unbounded" minOccurs="0"/&gt;
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
    "pendingComment"
})
@XmlRootElement(name = "soapPendingCommentsResponse")
public class SoapPendingCommentsResponse
    extends StatusType
{

    protected List<Correspondence> pendingComment;

    /**
     * Gets the value of the pendingComment property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pendingComment property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPendingComment().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Correspondence }
     * 
     * 
     */
    public List<Correspondence> getPendingComment() {
        if (pendingComment == null) {
            pendingComment = new ArrayList<Correspondence>();
        }
        return this.pendingComment;
    }

}
