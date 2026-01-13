package backend.repository;

import concordia.domain.Services;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class ServiceRepository {
    private final EntityManager entityManager;

    public ServiceRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Services> getAllServices() {
        TypedQuery<Services> query = entityManager.createQuery("SELECT s FROM Services s", Services.class);
        return query.getResultList();
    }

    public void insertService(Services service) {
        EntityTransaction tx = entityManager.getTransaction();
        try {
            tx.begin();
            entityManager.persist(service);
            tx.commit();
        } catch (RuntimeException ex) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw ex;
        }
    }

    public void updateService(Services service) {
        EntityTransaction tx = entityManager.getTransaction();
        try {
            tx.begin();
            entityManager.merge(service);
            tx.commit();
        } catch (RuntimeException ex) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw ex;
        }
    }

    public void deleteService(int serviceId) {
        EntityTransaction tx = entityManager.getTransaction();
        try {
            tx.begin();
            Services s = entityManager.find(Services.class, serviceId);
            if (s != null) {
                entityManager.remove(s);
            }
            tx.commit();
        } catch (RuntimeException ex) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw ex;
        }
    }
}
