//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.05.31 at 05:49:18 PM BST 
//


package com.student.soap.carservice.contract;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
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
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="token" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="publisherId" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="publisherTypeId" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="modelId" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="fuelTypeId" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="transmissionTypeId" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="carClassId" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="mileage" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="childSeats" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "token",
    "publisherId",
    "publisherTypeId",
    "modelId",
    "fuelTypeId",
    "transmissionTypeId",
    "carClassId",
    "mileage",
    "childSeats"
})
@XmlRootElement(name = "soapAddCarRequest")
public class SoapAddCarRequest {

    @XmlElement(required = true)
    protected String token;
    protected int publisherId;
    protected int publisherTypeId;
    protected int modelId;
    protected int fuelTypeId;
    protected int transmissionTypeId;
    protected int carClassId;
    protected int mileage;
    protected int childSeats;

    /**
     * Gets the value of the token property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToken() {
        return token;
    }

    /**
     * Sets the value of the token property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToken(String value) {
        this.token = value;
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
     * Gets the value of the modelId property.
     * 
     */
    public int getModelId() {
        return modelId;
    }

    /**
     * Sets the value of the modelId property.
     * 
     */
    public void setModelId(int value) {
        this.modelId = value;
    }

    /**
     * Gets the value of the fuelTypeId property.
     * 
     */
    public int getFuelTypeId() {
        return fuelTypeId;
    }

    /**
     * Sets the value of the fuelTypeId property.
     * 
     */
    public void setFuelTypeId(int value) {
        this.fuelTypeId = value;
    }

    /**
     * Gets the value of the transmissionTypeId property.
     * 
     */
    public int getTransmissionTypeId() {
        return transmissionTypeId;
    }

    /**
     * Sets the value of the transmissionTypeId property.
     * 
     */
    public void setTransmissionTypeId(int value) {
        this.transmissionTypeId = value;
    }

    /**
     * Gets the value of the carClassId property.
     * 
     */
    public int getCarClassId() {
        return carClassId;
    }

    /**
     * Sets the value of the carClassId property.
     * 
     */
    public void setCarClassId(int value) {
        this.carClassId = value;
    }

    /**
     * Gets the value of the mileage property.
     * 
     */
    public int getMileage() {
        return mileage;
    }

    /**
     * Sets the value of the mileage property.
     * 
     */
    public void setMileage(int value) {
        this.mileage = value;
    }

    /**
     * Gets the value of the childSeats property.
     * 
     */
    public int getChildSeats() {
        return childSeats;
    }

    /**
     * Sets the value of the childSeats property.
     * 
     */
    public void setChildSeats(int value) {
        this.childSeats = value;
    }

}
