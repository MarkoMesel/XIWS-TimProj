//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.07.12 at 11:16:35 AM CEST 
//


package com.student.soap.contract.userservice;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.student.soap.contract.userservice package. 
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
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.student.soap.contract.userservice
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SoapResponse }
     * 
     */
    public SoapResponse createSoapResponse() {
        return new SoapResponse();
    }

    /**
     * Create an instance of {@link StatusType }
     * 
     */
    public StatusType createStatusType() {
        return new StatusType();
    }

    /**
     * Create an instance of {@link SoapRegisterRequest }
     * 
     */
    public SoapRegisterRequest createSoapRegisterRequest() {
        return new SoapRegisterRequest();
    }

    /**
     * Create an instance of {@link SoapVerifyRequest }
     * 
     */
    public SoapVerifyRequest createSoapVerifyRequest() {
        return new SoapVerifyRequest();
    }

    /**
     * Create an instance of {@link SoapLoginRequest }
     * 
     */
    public SoapLoginRequest createSoapLoginRequest() {
        return new SoapLoginRequest();
    }

    /**
     * Create an instance of {@link SoapLoginResponse }
     * 
     */
    public SoapLoginResponse createSoapLoginResponse() {
        return new SoapLoginResponse();
    }

    /**
     * Create an instance of {@link SoapEditRequest }
     * 
     */
    public SoapEditRequest createSoapEditRequest() {
        return new SoapEditRequest();
    }

    /**
     * Create an instance of {@link SoapGetRequest }
     * 
     */
    public SoapGetRequest createSoapGetRequest() {
        return new SoapGetRequest();
    }

    /**
     * Create an instance of {@link SoapGetResponse }
     * 
     */
    public SoapGetResponse createSoapGetResponse() {
        return new SoapGetResponse();
    }

    /**
     * Create an instance of {@link SoapUsersRequest }
     * 
     */
    public SoapUsersRequest createSoapUsersRequest() {
        return new SoapUsersRequest();
    }

    /**
     * Create an instance of {@link SoapUsersResponse }
     * 
     */
    public SoapUsersResponse createSoapUsersResponse() {
        return new SoapUsersResponse();
    }

    /**
     * Create an instance of {@link SoapUser }
     * 
     */
    public SoapUser createSoapUser() {
        return new SoapUser();
    }

    /**
     * Create an instance of {@link SoapInternalGetUserRequest }
     * 
     */
    public SoapInternalGetUserRequest createSoapInternalGetUserRequest() {
        return new SoapInternalGetUserRequest();
    }

    /**
     * Create an instance of {@link SoapBlockUserRequest }
     * 
     */
    public SoapBlockUserRequest createSoapBlockUserRequest() {
        return new SoapBlockUserRequest();
    }

    /**
     * Create an instance of {@link SoapActivateUserRequest }
     * 
     */
    public SoapActivateUserRequest createSoapActivateUserRequest() {
        return new SoapActivateUserRequest();
    }

    /**
     * Create an instance of {@link SoapDeleteUserRequest }
     * 
     */
    public SoapDeleteUserRequest createSoapDeleteUserRequest() {
        return new SoapDeleteUserRequest();
    }

}