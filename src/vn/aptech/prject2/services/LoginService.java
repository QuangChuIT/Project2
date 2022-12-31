/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.aptech.prject2.services;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import vn.aptech.entities.User;
import vn.aptech.project2.utils.JPAUtils;
import vn.aptech.project2.utils.MD5Util;

/**
 *
 * @author chuva
 */
public class LoginService {
    
    private static LoginService instance;
    
    private User authUser;
    
    private LoginService() {
        
    }
    
    public static LoginService getInstance() {
        if (instance == null) {
            instance = new LoginService();
        }
        return instance;
    }
    
    public User login(String username, String password) {
        EntityManager em = null;
        try {
            em = JPAUtils.getInstance().getEntityManager();
            Query query = em.createNamedQuery("User.login", User.class);
            query.setParameter("username", username);
            String pwdHash = MD5Util.encrypt(password);
            query.setParameter("password", pwdHash);
            User user = (User) query.getSingleResult();
            if (user != null) {
                this.setAuthUser(user);
            }
            return user;
        } catch(Exception e) {
            return null;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public boolean checkAuthen() {
        return authUser != null;
    }
    
    public User getAuthUser() {
        return authUser;
    }

    public void setAuthUser(User authUser) {
        this.authUser = authUser;
    }
    
}
