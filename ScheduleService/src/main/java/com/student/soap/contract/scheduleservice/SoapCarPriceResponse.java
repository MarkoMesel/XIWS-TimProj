//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.06.13 at 09:09:07 PM BST 
//


package com.student.soap.contract.scheduleservice;

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
 *     &lt;extension base="{http://www.student.com/scheduleservice/soap/contract}StatusType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="totalPrice" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="discount" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="price" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="mileagePenalty" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="mileageThreshold" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="collisionWarranty" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
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
    "totalPrice",
    "discount",
    "price",
    "mileagePenalty",
    "mileageThreshold",
    "collisionWarranty"
})
@XmlRootElement(name = "soapCarPriceResponse")
public class SoapCarPriceResponse
    extends StatusType
{

    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer totalPrice;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer discount;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer price;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer mileagePenalty;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer mileageThreshold;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer collisionWarranty;

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
     * Gets the value of the collisionWarranty property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCollisionWarranty() {
        return collisionWarranty;
    }

    /**
     * Sets the value of the collisionWarranty property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCollisionWarranty(Integer value) {
        this.collisionWarranty = value;
    }

}
