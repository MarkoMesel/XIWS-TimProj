//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.06.13 at 12:38:13 PM BST 
//


package com.student.soap.carservice.contract;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


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
 *         &lt;element name="locationId" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="startDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="endDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="publisherTypeId" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="modelId" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="manufacturerId" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="fuelTypeId" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="transmissionTypeId" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="carClassId" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="minMileage" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="maxMileage" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="minChildSeats" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="minPrice" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="maxPrice" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="plannedMileage" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="collisionWarranty" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
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
    "locationId",
    "startDate",
    "endDate",
    "publisherTypeId",
    "modelId",
    "manufacturerId",
    "fuelTypeId",
    "transmissionTypeId",
    "carClassId",
    "minMileage",
    "maxMileage",
    "minChildSeats",
    "minPrice",
    "maxPrice",
    "plannedMileage",
    "collisionWarranty"
})
@XmlRootElement(name = "soapSearchCarsRequest")
public class SoapSearchCarsRequest {

    protected int locationId;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar startDate;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar endDate;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer publisherTypeId;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer modelId;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer manufacturerId;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer fuelTypeId;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer transmissionTypeId;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer carClassId;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer minMileage;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer maxMileage;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer minChildSeats;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer minPrice;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer maxPrice;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer plannedMileage;
    @XmlElement(required = true, type = Boolean.class, nillable = true)
    protected Boolean collisionWarranty;

    /**
     * Gets the value of the locationId property.
     * 
     */
    public int getLocationId() {
        return locationId;
    }

    /**
     * Sets the value of the locationId property.
     * 
     */
    public void setLocationId(int value) {
        this.locationId = value;
    }

    /**
     * Gets the value of the startDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getStartDate() {
        return startDate;
    }

    /**
     * Sets the value of the startDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setStartDate(XMLGregorianCalendar value) {
        this.startDate = value;
    }

    /**
     * Gets the value of the endDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEndDate() {
        return endDate;
    }

    /**
     * Sets the value of the endDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEndDate(XMLGregorianCalendar value) {
        this.endDate = value;
    }

    /**
     * Gets the value of the publisherTypeId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPublisherTypeId() {
        return publisherTypeId;
    }

    /**
     * Sets the value of the publisherTypeId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPublisherTypeId(Integer value) {
        this.publisherTypeId = value;
    }

    /**
     * Gets the value of the modelId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getModelId() {
        return modelId;
    }

    /**
     * Sets the value of the modelId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setModelId(Integer value) {
        this.modelId = value;
    }

    /**
     * Gets the value of the manufacturerId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getManufacturerId() {
        return manufacturerId;
    }

    /**
     * Sets the value of the manufacturerId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setManufacturerId(Integer value) {
        this.manufacturerId = value;
    }

    /**
     * Gets the value of the fuelTypeId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getFuelTypeId() {
        return fuelTypeId;
    }

    /**
     * Sets the value of the fuelTypeId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setFuelTypeId(Integer value) {
        this.fuelTypeId = value;
    }

    /**
     * Gets the value of the transmissionTypeId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTransmissionTypeId() {
        return transmissionTypeId;
    }

    /**
     * Sets the value of the transmissionTypeId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTransmissionTypeId(Integer value) {
        this.transmissionTypeId = value;
    }

    /**
     * Gets the value of the carClassId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCarClassId() {
        return carClassId;
    }

    /**
     * Sets the value of the carClassId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCarClassId(Integer value) {
        this.carClassId = value;
    }

    /**
     * Gets the value of the minMileage property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMinMileage() {
        return minMileage;
    }

    /**
     * Sets the value of the minMileage property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMinMileage(Integer value) {
        this.minMileage = value;
    }

    /**
     * Gets the value of the maxMileage property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMaxMileage() {
        return maxMileage;
    }

    /**
     * Sets the value of the maxMileage property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMaxMileage(Integer value) {
        this.maxMileage = value;
    }

    /**
     * Gets the value of the minChildSeats property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMinChildSeats() {
        return minChildSeats;
    }

    /**
     * Sets the value of the minChildSeats property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMinChildSeats(Integer value) {
        this.minChildSeats = value;
    }

    /**
     * Gets the value of the minPrice property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMinPrice() {
        return minPrice;
    }

    /**
     * Sets the value of the minPrice property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMinPrice(Integer value) {
        this.minPrice = value;
    }

    /**
     * Gets the value of the maxPrice property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMaxPrice() {
        return maxPrice;
    }

    /**
     * Sets the value of the maxPrice property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMaxPrice(Integer value) {
        this.maxPrice = value;
    }

    /**
     * Gets the value of the plannedMileage property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPlannedMileage() {
        return plannedMileage;
    }

    /**
     * Sets the value of the plannedMileage property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPlannedMileage(Integer value) {
        this.plannedMileage = value;
    }

    /**
     * Gets the value of the collisionWarranty property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isCollisionWarranty() {
        return collisionWarranty;
    }

    /**
     * Sets the value of the collisionWarranty property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setCollisionWarranty(Boolean value) {
        this.collisionWarranty = value;
    }

}
