/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Jon
 */

@ManagedBean
@Entity
@Table(name="Message")
public class Message implements Serializable{
    
    
    
    @Id
    @GeneratedValue
    @Column(name="id")
    private Long id;
    
    @ManyToOne
    @JoinColumn(name="siteUser")
    private SiteUser siteUser;
    //message is owner object
    
    @Column(name="message")
    private String message;
    
    @Column(name="datePosted")
    private Timestamp datePosted;
    
    @Column(name="numOfLikes")
    private int numOfLikes;
   
    public Message()
    {
        //set the time this post was created, used for ordering posts 
        Date date = new java.util.Date();
        datePosted = new Timestamp(date.getTime());
        numOfLikes = 0;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the siteUser
     */
    public SiteUser getSiteUser() {
        return siteUser;
    }

    /**
     * @param siteUser
     */
    public void setSiteUser(SiteUser siteUser) {
        this.siteUser = siteUser;
    }

    /**
     * @return the datePosted
     */
    public Timestamp getDatePosted() {
        return datePosted;
    }

    /**
     * @param datePosted the datePosted to set
     */
    public void setDatePosted(Timestamp datePosted) {
        this.datePosted = datePosted;
    }

    /**
     * @return the numOfLikes
     */
    public int getNumOfLikes() {
        return numOfLikes;
    }

    /**
     * @param numOfLikes the numOfLikes to set
     */
    public void setNumOfLikes(int numOfLikes) {
        this.numOfLikes = numOfLikes;
    }
}
