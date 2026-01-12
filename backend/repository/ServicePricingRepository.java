package backend.repository;

import concordia.domain.ServicePricing;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class ServicePricingRepository {
    private final EntityManager entityManager;

    public ServicePricingRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<ServicePricing> getAllServicePricings() {
        TypedQuery<ServicePricing> query = entityManager.createQuery("SELECT sp FROM ServicePricing sp", ServicePricing.class);
        return query.getResultList();
    }

    public void insertServicePricing(ServicePricing servicePricing) {
        EntityTransaction tx = entityManager.getTransaction();
        try {
            tx.begin();
            entityManager.persist(servicePricing);
            tx.commit();
        } catch (RuntimeException ex) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw ex;
        }
    }

    public void updateServicePricing(ServicePricing servicePricing) {
        EntityTransaction tx = entityManager.getTransaction();
        try {
            tx.begin();
            entityManager.merge(servicePricing);
            tx.commit();
        } catch (RuntimeException ex) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw ex;
        }
    }

    public void deleteServicePricing(int pricingId) {
        EntityTransaction tx = entityManager.getTransaction();
        try {
            tx.begin();
            ServicePricing sp = entityManager.find(ServicePricing.class, pricingId);
            if (sp != null) {
                entityManager.remove(sp);
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
