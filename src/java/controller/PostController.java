/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.transaction.UserTransaction;
import model.Message;
import model.SiteUser;

/**
 *
 * @author Jon
 */
@ManagedBean
public class PostController {
    
    @PersistenceUnit(unitName = "FinalProjectPU")
    private EntityManagerFactory entityManagerFactory;
    @Resource
    private UserTransaction userTransaction;
    
    @ManagedProperty(value = "#{message}")
    private Message message;
    
    @ManagedProperty(value = "#{siteUser}")
    private SiteUser siteUser;

    public String savePost() {
        // save post to DB
        // post = user + message
        String returnValue = "error";
        SiteUser potentialUser = userExists();
        try {
            userTransaction.begin();
            EntityManager em = entityManagerFactory.createEntityManager();
            //save message and user to the DB in respective tables
            if(potentialUser == null)
            {
                em.persist(siteUser);
                message.setSiteUser(siteUser);
                em.persist(message);
                userTransaction.commit();
                em.close();
                returnValue = "postSaved";
            }
            else
            {
                message.setSiteUser(potentialUser);
                em.persist(message);
                userTransaction.commit();
                em.close();
                returnValue = "postSaved";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnValue;
    }

    public List getAllPosts() {
        // query DB for all messages
        List<Message> messages = new ArrayList();
        String selectSQL = "select c from Message c ";
        try {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            Query selectQuery = entityManager.createQuery(selectSQL);
            messages = selectQuery.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return messages;
    }
    
    public List userAutoComplete(String userName)
    {
        //List<SiteUser> users = new ArrayList();
        List<String> users = new ArrayList();
        //String selectSQL = "select c from SiteUser c where UPPER(c.userName) like UPPER(:userName)";
        String selectSQL = "select c.userName from SiteUser c where UPPER(c.userName) like UPPER(:userName)";
        try {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            Query selectQuery = entityManager.createQuery(selectSQL);
            selectQuery.setParameter("userName", userName + "%");
            users = selectQuery.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }
    
    public SiteUser userExists()
    {
        //check if the username entered is already in the database
        List<SiteUser> results = new ArrayList();
        SiteUser user = new SiteUser();
        String selectSQL = "select c from SiteUser c where c.userName = :userName";
        try {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            Query selectQuery = entityManager.createQuery(selectSQL);
            selectQuery.setParameter("userName", siteUser.getUserName());
            results = selectQuery.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(results.size()<1)
        {
            return null;
        }
        else
        {    
            user = results.get(0);
            return user;
        }
    }
    
    public void addLike(Message m)
    {
        //update message's numOfLikes +1
        try
        {
            userTransaction.begin();
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            Message msg = entityManager.find(Message.class, m.getId());
            System.out.println(msg.getMessage());
            msg.setNumOfLikes(m.getNumOfLikes()+1);
            entityManager.persist(msg);
            userTransaction.commit();
            entityManager.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void addDislike(Message m)
    {
        //update message's numOfLikes -1
        String page = "error";
        try
        {
            userTransaction.begin();
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            Message msg = entityManager.find(Message.class, m.getId());
            System.out.println(msg.getMessage());
            msg.setNumOfLikes(m.getNumOfLikes()-1);
            
            if(msg.getNumOfLikes() < -10)
            {
                //delete post for recieving too many dislikes
                entityManager.remove(msg);
                userTransaction.commit();
                page = "postDeleted";
            }
            else
            {
                entityManager.persist(msg);
                userTransaction.commit();
                entityManager.close();
                page = "userPosts";
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * @return the message
     */
    public Message getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(Message message) {
        this.message = message;
    }

    /**
     * @return the siteUser
     */
    public SiteUser getSiteUser() {
        return siteUser;
    }

    /**
     * @param siteUser the siteUser to set
     */
    public void setSiteUser(SiteUser siteUser) {
        this.siteUser = siteUser;
    }
}
