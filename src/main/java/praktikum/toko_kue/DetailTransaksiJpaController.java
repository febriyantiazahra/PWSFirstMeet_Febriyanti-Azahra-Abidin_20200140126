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
public class DetailTransaksiJpaController implements Serializable {

    public DetailTransaksiJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("praktikum_toko_kue_jar_0.0.1-SNAPSHOTPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public DetailTransaksiJpaController() {
    }
    
   

    public void create(DetailTransaksi detailTransaksi) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(detailTransaksi);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findDetailTransaksi(detailTransaksi.getIdDetail()) != null) {
                throw new PreexistingEntityException("DetailTransaksi " + detailTransaksi + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(DetailTransaksi detailTransaksi) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            detailTransaksi = em.merge(detailTransaksi);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = detailTransaksi.getIdDetail();
                if (findDetailTransaksi(id) == null) {
                    throw new NonexistentEntityException("The detailTransaksi with id " + id + " no longer exists.");
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
            DetailTransaksi detailTransaksi;
            try {
                detailTransaksi = em.getReference(DetailTransaksi.class, id);
                detailTransaksi.getIdDetail();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The detailTransaksi with id " + id + " no longer exists.", enfe);
            }
            em.remove(detailTransaksi);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<DetailTransaksi> findDetailTransaksiEntities() {
        return findDetailTransaksiEntities(true, -1, -1);
    }

    public List<DetailTransaksi> findDetailTransaksiEntities(int maxResults, int firstResult) {
        return findDetailTransaksiEntities(false, maxResults, firstResult);
    }

    private List<DetailTransaksi> findDetailTransaksiEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(DetailTransaksi.class));
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

    public DetailTransaksi findDetailTransaksi(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(DetailTransaksi.class, id);
        } finally {
            em.close();
        }
    }

    public int getDetailTransaksiCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<DetailTransaksi> rt = cq.from(DetailTransaksi.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
