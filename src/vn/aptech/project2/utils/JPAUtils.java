/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.aptech.project2.utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author chuva
 */
public class JPAUtils {
    private static EntityManagerFactory emf;
    private static JPAUtils instance;
    private JPAUtils() {
        
    }
    
    public static JPAUtils getInstance() {
      if (instance == null) {
          instance = new JPAUtils();
      }  
      return instance;
    }
    
    public EntityManager getEntityManager() {
        if (emf == null) {
            emf = Persistence.createEntityManagerFactory("Project2PU");
        }
        return emf.createEntityManager();
    }
}
