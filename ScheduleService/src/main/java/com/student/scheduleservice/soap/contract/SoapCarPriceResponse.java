//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.05.24 at 09:03:46 PM BST 
//


package com.student.scheduleservice.soap.contract;

import java.math.BigInteger;
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
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.student.com/scheduleservice/soap/contract}StatusType">
 *       &lt;sequence>
 *         &lt;element name="totalPrice" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="discount" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="price" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="mileagePenalty" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="mileageThreshold" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="collisionWarranty" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
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

    @XmlElement(required = true)
    protected BigInteger totalPrice;
    @XmlElement(required = true)
    protected BigInteger discount;
    @XmlElement(required = true)
    protected BigInteger price;
    @XmlElement(required = true)
    protected BigInteger mileagePenalty;
    @XmlElement(required = true)
    protected BigInteger mileageThreshold;
    @XmlElement(required = true)
    protected BigInteger collisionWarranty;

    /**
     * Gets the value of the totalPrice property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getTotalPrice() {
        return totalPrice;
    }

    /**
     * Sets the value of the totalPrice property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setTotalPrice(BigInteger value) {
        this.totalPrice = value;
    }

    /**
     * Gets the value of the discount property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getDiscount() {
        return discount;
    }

    /**
     * Sets the value of the discount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setDiscount(BigInteger value) {
        this.discount = value;
    }

    /**
     * Gets the value of the price property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getPrice() {
        return price;
    }

    /**
     * Sets the value of the price property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setPrice(BigInteger value) {
        this.price = value;
    }

    /**
     * Gets the value of the mileagePenalty property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getMileagePenalty() {
        return mileagePenalty;
    }

    /**
     * Sets the value of the mileagePenalty property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setMileagePenalty(BigInteger value) {
        this.mileagePenalty = value;
    }

    /**
     * Gets the value of the mileageThreshold property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getMileageThreshold() {
        return mileageThreshold;
    }

    /**
     * Sets the value of the mileageThreshold property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setMileageThreshold(BigInteger value) {
        this.mileageThreshold = value;
    }

    /**
     * Gets the value of the collisionWarranty property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getCollisionWarranty() {
        return collisionWarranty;
    }

    /**
     * Sets the value of the collisionWarranty property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setCollisionWarranty(BigInteger value) {
        this.collisionWarranty = value;
    }

}
