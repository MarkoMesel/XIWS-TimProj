//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.05.27 at 07:19:26 PM BST 
//


package com.student.soap.resourse.contract;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.student.soap.resourse.contract package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.student.soap.resourse.contract
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SoapNamedObjectsResponse }
     * 
     */
    public SoapNamedObjectsResponse createSoapNamedObjectsResponse() {
        return new SoapNamedObjectsResponse();
    }

    /**
     * Create an instance of {@link SoapNamedObjectsResponse.NamedObjects }
     * 
     */
    public SoapNamedObjectsResponse.NamedObjects createSoapNamedObjectsResponseNamedObjects() {
        return new SoapNamedObjectsResponse.NamedObjects();
    }

    /**
     * Create an instance of {@link SoapCarRatingRequest }
     * 
     */
    public SoapCarRatingRequest createSoapCarRatingRequest() {
        return new SoapCarRatingRequest();
    }

    /**
     * Create an instance of {@link SoapCarRatingResponse }
     * 
     */
    public SoapCarRatingResponse createSoapCarRatingResponse() {
        return new SoapCarRatingResponse();
    }

    /**
     * Create an instance of {@link StatusType }
     * 
     */
    public StatusType createStatusType() {
        return new StatusType();
    }

    /**
     * Create an instance of {@link SoapNamedObjectsResponse.NamedObjects.NamedObject }
     * 
     */
    public SoapNamedObjectsResponse.NamedObjects.NamedObject createSoapNamedObjectsResponseNamedObjectsNamedObject() {
        return new SoapNamedObjectsResponse.NamedObjects.NamedObject();
    }

}
