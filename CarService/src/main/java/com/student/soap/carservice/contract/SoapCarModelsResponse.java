//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.06.05 at 11:11:11 PM CEST 
//


package com.student.soap.carservice.contract;

import java.util.ArrayList;
import java.util.List;
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
 *     &lt;extension base="{http://www.student.com/soap/contract}StatusType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="carModels"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="carModel" maxOccurs="unbounded"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="modelId" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *                             &lt;element name="modelName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="manufacturerId" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *                             &lt;element name="manufacturerName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                           &lt;/sequence&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
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
    "carModels"
})
@XmlRootElement(name = "soapCarModelsResponse")
public class SoapCarModelsResponse
    extends StatusType
{

    @XmlElement(required = true)
    protected SoapCarModelsResponse.CarModels carModels;

    /**
     * Gets the value of the carModels property.
     * 
     * @return
     *     possible object is
     *     {@link SoapCarModelsResponse.CarModels }
     *     
     */
    public SoapCarModelsResponse.CarModels getCarModels() {
        return carModels;
    }

    /**
     * Sets the value of the carModels property.
     * 
     * @param value
     *     allowed object is
     *     {@link SoapCarModelsResponse.CarModels }
     *     
     */
    public void setCarModels(SoapCarModelsResponse.CarModels value) {
        this.carModels = value;
    }


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
     *         &lt;element name="carModel" maxOccurs="unbounded"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="modelId" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
     *                   &lt;element name="modelName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="manufacturerId" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
     *                   &lt;element name="manufacturerName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                 &lt;/sequence&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
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
        "carModel"
    })
    public static class CarModels {

        @XmlElement(required = true)
        protected List<SoapCarModelsResponse.CarModels.CarModel> carModel;

        /**
         * Gets the value of the carModel property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the carModel property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getCarModel().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link SoapCarModelsResponse.CarModels.CarModel }
         * 
         * 
         */
        public List<SoapCarModelsResponse.CarModels.CarModel> getCarModel() {
            if (carModel == null) {
                carModel = new ArrayList<SoapCarModelsResponse.CarModels.CarModel>();
            }
            return this.carModel;
        }


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
         *         &lt;element name="modelId" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
         *         &lt;element name="modelName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="manufacturerId" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
         *         &lt;element name="manufacturerName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
            "modelId",
            "modelName",
            "manufacturerId",
            "manufacturerName"
        })
        public static class CarModel {

            protected int modelId;
            @XmlElement(required = true)
            protected String modelName;
            protected int manufacturerId;
            @XmlElement(required = true)
            protected String manufacturerName;

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
             */
            public int getManufacturerId() {
                return manufacturerId;
            }

            /**
             * Sets the value of the manufacturerId property.
             * 
             */
            public void setManufacturerId(int value) {
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

        }

    }

}
