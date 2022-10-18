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
public class KasirJpaController implements Serializable {

    public KasirJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("praktikum_toko_kue_jar_0.0.1-SNAPSHOTPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public KasirJpaController() {
    }
    
    

    public void create(Kasir kasir) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(kasir);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findKasir(kasir.getIdKasir()) != null) {
                throw new PreexistingEntityException("Kasir " + kasir + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Kasir kasir) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            kasir = em.merge(kasir);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = kasir.getIdKasir();
                if (findKasir(id) == null) {
                    throw new NonexistentEntityException("The kasir with id " + id + " no longer exists.");
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
            Kasir kasir;
            try {
                kasir = em.getReference(Kasir.class, id);
                kasir.getIdKasir();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The kasir with id " + id + " no longer exists.", enfe);
            }
            em.remove(kasir);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Kasir> findKasirEntities() {
        return findKasirEntities(true, -1, -1);
    }

    public List<Kasir> findKasirEntities(int maxResults, int firstResult) {
        return findKasirEntities(false, maxResults, firstResult);
    }

    private List<Kasir> findKasirEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Kasir.class));
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

    public Kasir findKasir(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Kasir.class, id);
        } finally {
            em.close();
        }
    }

    public int getKasirCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Kasir> rt = cq.from(Kasir.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
