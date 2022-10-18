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
public class PembeliJpaController implements Serializable {

    public PembeliJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf =Persistence.createEntityManagerFactory("praktikum_toko_kue_jar_0.0.1-SNAPSHOTPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public PembeliJpaController() {
    }
    

    public void create(Pembeli pembeli) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(pembeli);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPembeli(pembeli.getIdPembeli()) != null) {
                throw new PreexistingEntityException("Pembeli " + pembeli + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pembeli pembeli) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            pembeli = em.merge(pembeli);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = pembeli.getIdPembeli();
                if (findPembeli(id) == null) {
                    throw new NonexistentEntityException("The pembeli with id " + id + " no longer exists.");
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
            Pembeli pembeli;
            try {
                pembeli = em.getReference(Pembeli.class, id);
                pembeli.getIdPembeli();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pembeli with id " + id + " no longer exists.", enfe);
            }
            em.remove(pembeli);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pembeli> findPembeliEntities() {
        return findPembeliEntities(true, -1, -1);
    }

    public List<Pembeli> findPembeliEntities(int maxResults, int firstResult) {
        return findPembeliEntities(false, maxResults, firstResult);
    }

    private List<Pembeli> findPembeliEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pembeli.class));
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

    public Pembeli findPembeli(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pembeli.class, id);
        } finally {
            em.close();
        }
    }

    public int getPembeliCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pembeli> rt = cq.from(Pembeli.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
