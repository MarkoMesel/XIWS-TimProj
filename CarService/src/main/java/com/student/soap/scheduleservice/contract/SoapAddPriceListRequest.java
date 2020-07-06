//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.07.06 at 11:35:47 PM BST 
//


package com.student.soap.scheduleservice.contract;

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
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="discountPercentage" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="mileageThreshold" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="mileagePenalty" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="warrantyPrice" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
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
    "name",
    "discountPercentage",
    "mileageThreshold",
    "mileagePenalty",
    "warrantyPrice"
})
@XmlRootElement(name = "soapAddPriceListRequest")
public class SoapAddPriceListRequest {

    @XmlElement(required = true)
    protected String token;
    @XmlElement(required = true)
    protected String name;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer discountPercentage;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer mileageThreshold;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer mileagePenalty;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer warrantyPrice;

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
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the discountPercentage property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getDiscountPercentage() {
        return discountPercentage;
    }

    /**
     * Sets the value of the discountPercentage property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setDiscountPercentage(Integer value) {
        this.discountPercentage = value;
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
     * Gets the value of the warrantyPrice property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getWarrantyPrice() {
        return warrantyPrice;
    }

    /**
     * Sets the value of the warrantyPrice property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setWarrantyPrice(Integer value) {
        this.warrantyPrice = value;
    }

}
