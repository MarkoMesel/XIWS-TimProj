//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.06.20 at 11:11:24 AM CEST 
//


package com.student.soap.contract.scheduleservice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Bundle complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Bundle"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="bundleId" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="publisherId" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="publisherName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="publisherTypeId" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="publisherTypeName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="userId" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="userName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="stateName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="stateId" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="car" type="{http://www.student.com/scheduleservice/soap/contract}Car" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Bundle", propOrder = {
    "bundleId",
    "publisherId",
    "publisherName",
    "publisherTypeId",
    "publisherTypeName",
    "userId",
    "userName",
    "stateName",
    "stateId",
    "car"
})
public class Bundle {

    protected int bundleId;
    protected int publisherId;
    @XmlElement(required = true)
    protected String publisherName;
    protected int publisherTypeId;
    @XmlElement(required = true)
    protected String publisherTypeName;
    protected int userId;
    @XmlElement(required = true)
    protected String userName;
    @XmlElement(required = true)
    protected String stateName;
    protected int stateId;
    @XmlElement(required = true)
    protected List<Car> car;

    /**
     * Gets the value of the bundleId property.
     * 
     */
    public int getBundleId() {
        return bundleId;
    }

    /**
     * Sets the value of the bundleId property.
     * 
     */
    public void setBundleId(int value) {
        this.bundleId = value;
    }

    /**
     * Gets the value of the publisherId property.
     * 
     */
    public int getPublisherId() {
        return publisherId;
    }

    /**
     * Sets the value of the publisherId property.
     * 
     */
    public void setPublisherId(int value) {
        this.publisherId = value;
    }

    /**
     * Gets the value of the publisherName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPublisherName() {
        return publisherName;
    }

    /**
     * Sets the value of the publisherName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPublisherName(String value) {
        this.publisherName = value;
    }

    /**
     * Gets the value of the publisherTypeId property.
     * 
     */
    public int getPublisherTypeId() {
        return publisherTypeId;
    }

    /**
     * Sets the value of the publisherTypeId property.
     * 
     */
    public void setPublisherTypeId(int value) {
        this.publisherTypeId = value;
    }

    /**
     * Gets the value of the publisherTypeName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPublisherTypeName() {
        return publisherTypeName;
    }

    /**
     * Sets the value of the publisherTypeName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPublisherTypeName(String value) {
        this.publisherTypeName = value;
    }

    /**
     * Gets the value of the userId property.
     * 
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets the value of the userId property.
     * 
     */
    public void setUserId(int value) {
        this.userId = value;
    }

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
     * Gets the value of the stateName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStateName() {
        return stateName;
    }

    /**
     * Sets the value of the stateName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStateName(String value) {
        this.stateName = value;
    }

    /**
     * Gets the value of the stateId property.
     * 
     */
    public int getStateId() {
        return stateId;
    }

    /**
     * Sets the value of the stateId property.
     * 
     */
    public void setStateId(int value) {
        this.stateId = value;
    }

    /**
     * Gets the value of the car property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the car property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCar().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Car }
     * 
     * 
     */
    public List<Car> getCar() {
        if (car == null) {
            car = new ArrayList<Car>();
        }
        return this.car;
    }

}
