//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.05.31 at 03:49:57 PM BST 
//


package com.student.soap.carservice.contract;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Car complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Car"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="locationId" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="locationName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="modelId" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="modelName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="manufacturerId" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="manufacturerName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="fuelTypeId" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="fuelTypeName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="transmissionTypeId" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="transmissionTypeName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="carClassId" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="carClassName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="mileage" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="mileageThreshold" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="mileagePenalty" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="collisionWaranty" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="price" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="estimatedPenaltyPrice" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="discount" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="totalPrice" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="childSeats" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="rating" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="publisherId" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="publisherName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="publisherTypeId" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="publisherTypeName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="image" type="{http://www.w3.org/2001/XMLSchema}int" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Car", propOrder = {
    "id",
    "locationId",
    "locationName",
    "modelId",
    "modelName",
    "manufacturerId",
    "manufacturerName",
    "fuelTypeId",
    "fuelTypeName",
    "transmissionTypeId",
    "transmissionTypeName",
    "carClassId",
    "carClassName",
    "mileage",
    "mileageThreshold",
    "mileagePenalty",
    "collisionWaranty",
    "price",
    "estimatedPenaltyPrice",
    "discount",
    "totalPrice",
    "childSeats",
    "rating",
    "publisherId",
    "publisherName",
    "publisherTypeId",
    "publisherTypeName",
    "image"
})
public class Car {

    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer id;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer locationId;
    @XmlElement(required = true)
    protected String locationName;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer modelId;
    @XmlElement(required = true)
    protected String modelName;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer manufacturerId;
    @XmlElement(required = true)
    protected String manufacturerName;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer fuelTypeId;
    @XmlElement(required = true)
    protected String fuelTypeName;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer transmissionTypeId;
    @XmlElement(required = true)
    protected String transmissionTypeName;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer carClassId;
    @XmlElement(required = true)
    protected String carClassName;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer mileage;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer mileageThreshold;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer mileagePenalty;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer collisionWaranty;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer price;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer estimatedPenaltyPrice;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer discount;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer totalPrice;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer childSeats;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer rating;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer publisherId;
    @XmlElement(required = true)
    protected String publisherName;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer publisherTypeId;
    @XmlElement(required = true)
    protected String publisherTypeName;
    @XmlElement(type = Integer.class)
    protected List<Integer> image;

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setId(Integer value) {
        this.id = value;
    }

    /**
     * Gets the value of the locationId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getLocationId() {
        return locationId;
    }

    /**
     * Sets the value of the locationId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setLocationId(Integer value) {
        this.locationId = value;
    }

    /**
     * Gets the value of the locationName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocationName() {
        return locationName;
    }

    /**
     * Sets the value of the locationName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocationName(String value) {
        this.locationName = value;
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
     * Gets the value of the modelName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModelName() {
        return modelName;
    }

    /**
     * Sets the value of the modelName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModelName(String value) {
        this.modelName = value;
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
     * Gets the value of the manufacturerName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getManufacturerName() {
        return manufacturerName;
    }

    /**
     * Sets the value of the manufacturerName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setManufacturerName(String value) {
        this.manufacturerName = value;
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
     * Gets the value of the fuelTypeName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFuelTypeName() {
        return fuelTypeName;
    }

    /**
     * Sets the value of the fuelTypeName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFuelTypeName(String value) {
        this.fuelTypeName = value;
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
     * Gets the value of the transmissionTypeName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTransmissionTypeName() {
        return transmissionTypeName;
    }

    /**
     * Sets the value of the transmissionTypeName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTransmissionTypeName(String value) {
        this.transmissionTypeName = value;
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
     * Gets the value of the carClassName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCarClassName() {
        return carClassName;
    }

    /**
     * Sets the value of the carClassName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCarClassName(String value) {
        this.carClassName = value;
    }

    /**
     * Gets the value of the mileage property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMileage() {
        return mileage;
    }

    /**
     * Sets the value of the mileage property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMileage(Integer value) {
        this.mileage = value;
    }

    /**
     * Gets the value of the mileageThreshold property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMileageThreshold() {
        return mileageThreshold;
    }

    /**
     * Sets the value of the mileageThreshold property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMileageThreshold(Integer value) {
        this.mileageThreshold = value;
    }

    /**
     * Gets the value of the mileagePenalty property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMileagePenalty() {
        return mileagePenalty;
    }

    /**
     * Sets the value of the mileagePenalty property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMileagePenalty(Integer value) {
        this.mileagePenalty = value;
    }

    /**
     * Gets the value of the collisionWaranty property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCollisionWaranty() {
        return collisionWaranty;
    }

    /**
     * Sets the value of the collisionWaranty property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCollisionWaranty(Integer value) {
        this.collisionWaranty = value;
    }

    /**
     * Gets the value of the price property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPrice() {
        return price;
    }

    /**
     * Sets the value of the price property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPrice(Integer value) {
        this.price = value;
    }

    /**
     * Gets the value of the estimatedPenaltyPrice property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getEstimatedPenaltyPrice() {
        return estimatedPenaltyPrice;
    }

    /**
     * Sets the value of the estimatedPenaltyPrice property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setEstimatedPenaltyPrice(Integer value) {
        this.estimatedPenaltyPrice = value;
    }

    /**
     * Gets the value of the discount property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getDiscount() {
        return discount;
    }

    /**
     * Sets the value of the discount property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setDiscount(Integer value) {
        this.discount = value;
    }

    /**
     * Gets the value of the totalPrice property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTotalPrice() {
        return totalPrice;
    }

    /**
     * Sets the value of the totalPrice property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTotalPrice(Integer value) {
        this.totalPrice = value;
    }

    /**
     * Gets the value of the childSeats property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getChildSeats() {
        return childSeats;
    }

    /**
     * Sets the value of the childSeats property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setChildSeats(Integer value) {
        this.childSeats = value;
    }

    /**
     * Gets the value of the rating property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getRating() {
        return rating;
    }

    /**
     * Sets the value of the rating property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setRating(Integer value) {
        this.rating = value;
    }

    /**
     * Gets the value of the publisherId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPublisherId() {
        return publisherId;
    }

    /**
     * Sets the value of the publisherId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPublisherId(Integer value) {
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
     * Gets the value of the image property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the image property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getImage().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Integer }
     * 
     * 
     */
    public List<Integer> getImage() {
        if (image == null) {
            image = new ArrayList<Integer>();
        }
        return this.image;
    }

}
