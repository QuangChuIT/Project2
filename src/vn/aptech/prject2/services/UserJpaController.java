/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.aptech.prject2.services;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import vn.aptech.entities.Role;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import vn.aptech.entities.User;
import vn.aptech.prject2.services.exceptions.NonexistentEntityException;

/**
 *
 * @author chuva
 */
public class UserJpaController implements Serializable {

    public UserJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(User user) {
        if (user.getRoleCollection() == null) {
            user.setRoleCollection(new ArrayList<>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Role> attachedRoleCollection = new ArrayList<>();
            for (Role roleCollectionRoleToAttach : user.getRoleCollection()) {
                roleCollectionRoleToAttach = em.getReference(roleCollectionRoleToAttach.getClass(), roleCollectionRoleToAttach.getId());
                attachedRoleCollection.add(roleCollectionRoleToAttach);
            }
            user.setRoleCollection(attachedRoleCollection);
            em.persist(user);
            for (Role roleCollectionRole : user.getRoleCollection()) {
                roleCollectionRole.getUserCollection().add(user);
                roleCollectionRole = em.merge(roleCollectionRole);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(User user) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            User persistentUser = em.find(User.class, user.getId());
            Collection<Role> roleCollectionOld = persistentUser.getRoleCollection();
            Collection<Role> roleCollectionNew = user.getRoleCollection();
            Collection<Role> attachedRoleCollectionNew = new ArrayList<Role>();
            for (Role roleCollectionNewRoleToAttach : roleCollectionNew) {
                roleCollectionNewRoleToAttach = em.getReference(roleCollectionNewRoleToAttach.getClass(), roleCollectionNewRoleToAttach.getId());
                attachedRoleCollectionNew.add(roleCollectionNewRoleToAttach);
            }
            roleCollectionNew = attachedRoleCollectionNew;
            user.setRoleCollection(roleCollectionNew);
            user = em.merge(user);
            for (Role roleCollectionOldRole : roleCollectionOld) {
                if (!roleCollectionNew.contains(roleCollectionOldRole)) {
                    roleCollectionOldRole.getUserCollection().remove(user);
                    roleCollectionOldRole = em.merge(roleCollectionOldRole);
                }
            }
            for (Role roleCollectionNewRole : roleCollectionNew) {
                if (!roleCollectionOld.contains(roleCollectionNewRole)) {
                    roleCollectionNewRole.getUserCollection().add(user);
                    roleCollectionNewRole = em.merge(roleCollectionNewRole);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = user.getId();
                if (findUser(id) == null) {
                    throw new NonexistentEntityException("The user with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            User user;
            try {
                user = em.getReference(User.class, id);
                user.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The user with id " + id + " no longer exists.", enfe);
            }
            Collection<Role> roleCollection = user.getRoleCollection();
            for (Role roleCollectionRole : roleCollection) {
                roleCollectionRole.getUserCollection().remove(user);
                roleCollectionRole = em.merge(roleCollectionRole);
            }
            em.remove(user);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<User> findUserEntities() {
        return findUserEntities(true, -1, -1);
    }

    public List<User> findUserEntities(int maxResults, int firstResult) {
        return findUserEntities(false, maxResults, firstResult);
    }

    private List<User> findUserEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(User.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public User findUser(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(User.class, id);
        } finally {
            em.close();
        }
    }

    public int getUserCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<User> rt = cq.from(User.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
