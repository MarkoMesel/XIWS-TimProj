//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.05.31 at 05:43:42 PM CEST 
//


package com.student.scheduleservice.soap.contract;

import java.util.ArrayList;
import java.util.List;
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
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.student.com/scheduleservice/soap/contract}StatusType">
 *       &lt;sequence>
 *         &lt;element name="comments">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="comment" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="rating" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                             &lt;element name="userId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                             &lt;element name="comment" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="userName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="date" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *                             &lt;element name="replies">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="reply" maxOccurs="unbounded" minOccurs="0">
 *                                         &lt;complexType>
 *                                           &lt;complexContent>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                               &lt;sequence>
 *                                                 &lt;element name="publisherId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                                                 &lt;element name="publisherTypeId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                                                 &lt;element name="comment" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                                 &lt;element name="publisherName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                                 &lt;element name="publisherTypeName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                                 &lt;element name="date" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *                                               &lt;/sequence>
 *                                             &lt;/restriction>
 *                                           &lt;/complexContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
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
    "comments"
})
@XmlRootElement(name = "soapCarRatingsAndCommentsResponse")
public class SoapCarRatingsAndCommentsResponse
    extends StatusType
{

    @XmlElement(required = true)
    protected SoapCarRatingsAndCommentsResponse.Comments comments;

    /**
     * Gets the value of the comments property.
     * 
     * @return
     *     possible object is
     *     {@link SoapCarRatingsAndCommentsResponse.Comments }
     *     
     */
    public SoapCarRatingsAndCommentsResponse.Comments getComments() {
        return comments;
    }

    /**
     * Sets the value of the comments property.
     * 
     * @param value
     *     allowed object is
     *     {@link SoapCarRatingsAndCommentsResponse.Comments }
     *     
     */
    public void setComments(SoapCarRatingsAndCommentsResponse.Comments value) {
        this.comments = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="comment" maxOccurs="unbounded">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="rating" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *                   &lt;element name="userId" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *                   &lt;element name="comment" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="userName" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="date" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
     *                   &lt;element name="replies">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="reply" maxOccurs="unbounded" minOccurs="0">
     *                               &lt;complexType>
     *                                 &lt;complexContent>
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                     &lt;sequence>
     *                                       &lt;element name="publisherId" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *                                       &lt;element name="publisherTypeId" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *                                       &lt;element name="comment" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                                       &lt;element name="publisherName" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                                       &lt;element name="publisherTypeName" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                                       &lt;element name="date" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
     *                                     &lt;/sequence>
     *                                   &lt;/restriction>
     *                                 &lt;/complexContent>
     *                               &lt;/complexType>
     *                             &lt;/element>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "comment"
    })
    public static class Comments {

        @XmlElement(required = true)
        protected List<SoapCarRatingsAndCommentsResponse.Comments.Comment> comment;

        /**
         * Gets the value of the comment property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the comment property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getComment().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link SoapCarRatingsAndCommentsResponse.Comments.Comment }
         * 
         * 
         */
        public List<SoapCarRatingsAndCommentsResponse.Comments.Comment> getComment() {
            if (comment == null) {
                comment = new ArrayList<SoapCarRatingsAndCommentsResponse.Comments.Comment>();
            }
            return this.comment;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="rating" type="{http://www.w3.org/2001/XMLSchema}int"/>
         *         &lt;element name="userId" type="{http://www.w3.org/2001/XMLSchema}int"/>
         *         &lt;element name="comment" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="userName" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="date" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
         *         &lt;element name="replies">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="reply" maxOccurs="unbounded" minOccurs="0">
         *                     &lt;complexType>
         *                       &lt;complexContent>
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                           &lt;sequence>
         *                             &lt;element name="publisherId" type="{http://www.w3.org/2001/XMLSchema}int"/>
         *                             &lt;element name="publisherTypeId" type="{http://www.w3.org/2001/XMLSchema}int"/>
         *                             &lt;element name="comment" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                             &lt;element name="publisherName" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                             &lt;element name="publisherTypeName" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                             &lt;element name="date" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
         *                           &lt;/sequence>
         *                         &lt;/restriction>
         *                       &lt;/complexContent>
         *                     &lt;/complexType>
         *                   &lt;/element>
         *                 &lt;/sequence>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "rating",
            "userId",
            "comment",
            "userName",
            "date",
            "replies"
        })
        public static class Comment {

            protected int rating;
            protected int userId;
            @XmlElement(required = true)
            protected String comment;
            @XmlElement(required = true)
            protected String userName;
            @XmlElement(required = true)
            @XmlSchemaType(name = "dateTime")
            protected XMLGregorianCalendar date;
            @XmlElement(required = true)
            protected SoapCarRatingsAndCommentsResponse.Comments.Comment.Replies replies;

            /**
             * Gets the value of the rating property.
             * 
             */
            public int getRating() {
                return rating;
            }

            /**
             * Sets the value of the rating property.
             * 
             */
            public void setRating(int value) {
                this.rating = value;
            }

            /**
             * Gets the value of the userId property.
             * 
             */
            public int getUserId() {
                return userId;
            }

            /**
             * Sets the value of the userId property.
             * 
             */
            public void setUserId(int value) {
                this.userId = value;
            }

            /**
             * Gets the value of the comment property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getComment() {
                return comment;
            }

            /**
             * Sets the value of the comment property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setComment(String value) {
                this.comment = value;
            }

            /**
             * Gets the value of the userName property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getUserName() {
                return userName;
            }

            /**
             * Sets the value of the userName property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setUserName(String value) {
                this.userName = value;
            }

            /**
             * Gets the value of the date property.
             * 
             * @return
             *     possible object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public XMLGregorianCalendar getDate() {
                return date;
            }

            /**
             * Sets the value of the date property.
             * 
             * @param value
             *     allowed object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public void setDate(XMLGregorianCalendar value) {
                this.date = value;
            }

            /**
             * Gets the value of the replies property.
             * 
             * @return
             *     possible object is
             *     {@link SoapCarRatingsAndCommentsResponse.Comments.Comment.Replies }
             *     
             */
            public SoapCarRatingsAndCommentsResponse.Comments.Comment.Replies getReplies() {
                return replies;
            }

            /**
             * Sets the value of the replies property.
             * 
             * @param value
             *     allowed object is
             *     {@link SoapCarRatingsAndCommentsResponse.Comments.Comment.Replies }
             *     
             */
            public void setReplies(SoapCarRatingsAndCommentsResponse.Comments.Comment.Replies value) {
                this.replies = value;
            }


            /**
             * <p>Java class for anonymous complex type.
             * 
             * <p>The following schema fragment specifies the expected content contained within this class.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;complexContent>
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *       &lt;sequence>
             *         &lt;element name="reply" maxOccurs="unbounded" minOccurs="0">
             *           &lt;complexType>
             *             &lt;complexContent>
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                 &lt;sequence>
             *                   &lt;element name="publisherId" type="{http://www.w3.org/2001/XMLSchema}int"/>
             *                   &lt;element name="publisherTypeId" type="{http://www.w3.org/2001/XMLSchema}int"/>
             *                   &lt;element name="comment" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *                   &lt;element name="publisherName" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *                   &lt;element name="publisherTypeName" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *                   &lt;element name="date" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
             *                 &lt;/sequence>
             *               &lt;/restriction>
             *             &lt;/complexContent>
             *           &lt;/complexType>
             *         &lt;/element>
             *       &lt;/sequence>
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "reply"
            })
            public static class Replies {

                protected List<SoapCarRatingsAndCommentsResponse.Comments.Comment.Replies.Reply> reply;

                /**
                 * Gets the value of the reply property.
                 * 
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the reply property.
                 * 
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getReply().add(newItem);
                 * </pre>
                 * 
                 * 
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link SoapCarRatingsAndCommentsResponse.Comments.Comment.Replies.Reply }
                 * 
                 * 
                 */
                public List<SoapCarRatingsAndCommentsResponse.Comments.Comment.Replies.Reply> getReply() {
                    if (reply == null) {
                        reply = new ArrayList<SoapCarRatingsAndCommentsResponse.Comments.Comment.Replies.Reply>();
                    }
                    return this.reply;
                }


                /**
                 * <p>Java class for anonymous complex type.
                 * 
                 * <p>The following schema fragment specifies the expected content contained within this class.
                 * 
                 * <pre>
                 * &lt;complexType>
                 *   &lt;complexContent>
                 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *       &lt;sequence>
                 *         &lt;element name="publisherId" type="{http://www.w3.org/2001/XMLSchema}int"/>
                 *         &lt;element name="publisherTypeId" type="{http://www.w3.org/2001/XMLSchema}int"/>
                 *         &lt;element name="comment" type="{http://www.w3.org/2001/XMLSchema}string"/>
                 *         &lt;element name="publisherName" type="{http://www.w3.org/2001/XMLSchema}string"/>
                 *         &lt;element name="publisherTypeName" type="{http://www.w3.org/2001/XMLSchema}string"/>
                 *         &lt;element name="date" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
                 *       &lt;/sequence>
                 *     &lt;/restriction>
                 *   &lt;/complexContent>
                 * &lt;/complexType>
                 * </pre>
                 * 
                 * 
                 */
                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "", propOrder = {
                    "publisherId",
                    "publisherTypeId",
                    "comment",
                    "publisherName",
                    "publisherTypeName",
                    "date"
                })
                public static class Reply {

                    protected int publisherId;
                    protected int publisherTypeId;
                    @XmlElement(required = true)
                    protected String comment;
                    @XmlElement(required = true)
                    protected String publisherName;
                    @XmlElement(required = true)
                    protected String publisherTypeName;
                    @XmlElement(required = true)
                    @XmlSchemaType(name = "dateTime")
                    protected XMLGregorianCalendar date;

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
                     * Gets the value of the comment property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getComment() {
                        return comment;
                    }

                    /**
                     * Sets the value of the comment property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setComment(String value) {
                        this.comment = value;
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
                     * Gets the value of the date property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link XMLGregorianCalendar }
                     *     
                     */
                    public XMLGregorianCalendar getDate() {
                        return date;
                    }

                    /**
                     * Sets the value of the date property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link XMLGregorianCalendar }
                     *     
                     */
                    public void setDate(XMLGregorianCalendar value) {
                        this.date = value;
                    }

                }

            }

        }

    }

}
