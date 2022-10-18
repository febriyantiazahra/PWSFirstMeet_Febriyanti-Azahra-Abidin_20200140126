/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package praktikum.toko_kue;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import praktikum.toko_kue.exceptions.NonexistentEntityException;
import praktikum.toko_kue.exceptions.PreexistingEntityException;

/**
 *
 * @author Febriyanti Azahra
 */
public class KueJpaController implements Serializable {

    public KueJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("praktikum_toko_kue_jar_0.0.1-SNAPSHOTPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public KueJpaController() {
    }
    
    

    public void create(Kue kue) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(kue);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findKue(kue.getIdKue()) != null) {
                throw new PreexistingEntityException("Kue " + kue + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Kue kue) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            kue = em.merge(kue);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = kue.getIdKue();
                if (findKue(id) == null) {
                    throw new NonexistentEntityException("The kue with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Kue kue;
            try {
                kue = em.getReference(Kue.class, id);
                kue.getIdKue();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The kue with id " + id + " no longer exists.", enfe);
            }
            em.remove(kue);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Kue> findKueEntities() {
        return findKueEntities(true, -1, -1);
    }

    public List<Kue> findKueEntities(int maxResults, int firstResult) {
        return findKueEntities(false, maxResults, firstResult);
    }

    private List<Kue> findKueEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Kue.class));
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

    public Kue findKue(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Kue.class, id);
        } finally {
            em.close();
        }
    }

    public int getKueCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Kue> rt = cq.from(Kue.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
