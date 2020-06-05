//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.06.05 at 11:15:19 PM CEST 
//


package com.student.soap.agentservice.contract;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.student.soap.agentservice.contract package. 
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
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.student.soap.agentservice.contract
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SoapAgentsResponse }
     * 
     */
    public SoapAgentsResponse createSoapAgentsResponse() {
        return new SoapAgentsResponse();
    }

    /**
     * Create an instance of {@link SoapAgentsResponse.Agents }
     * 
     */
    public SoapAgentsResponse.Agents createSoapAgentsResponseAgents() {
        return new SoapAgentsResponse.Agents();
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
     * Create an instance of {@link SoapAgentsRequest }
     * 
     */
    public SoapAgentsRequest createSoapAgentsRequest() {
        return new SoapAgentsRequest();
    }

    /**
     * Create an instance of {@link SoapAgentByIdRequest }
     * 
     */
    public SoapAgentByIdRequest createSoapAgentByIdRequest() {
        return new SoapAgentByIdRequest();
    }

    /**
     * Create an instance of {@link SoapAgentByIdResponse }
     * 
     */
    public SoapAgentByIdResponse createSoapAgentByIdResponse() {
        return new SoapAgentByIdResponse();
    }

    /**
     * Create an instance of {@link SoapAgentsResponse.Agents.Agent }
     * 
     */
    public SoapAgentsResponse.Agents.Agent createSoapAgentsResponseAgentsAgent() {
        return new SoapAgentsResponse.Agents.Agent();
    }

}
