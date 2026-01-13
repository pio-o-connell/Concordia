package concordia.repository;

import concordia.annotations.Repository;
import concordia.domain.ServicePricing;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

@Repository
public class ServicePricingRepository {
    private final EntityManager em;
    public ServicePricingRepository(EntityManager em) {
        this.em = em;
    }

    public List<ServicePricing> getAllServicePricings() {
        TypedQuery<ServicePricing> query = em.createQuery("SELECT s FROM ServicePricing s", ServicePricing.class);
        return query.getResultList();
    }

    public void insertServicePricing(ServicePricing servicePricing) {
        em.getTransaction().begin();
        em.persist(servicePricing);
        em.getTransaction().commit();
    }

    public void updateServicePricing(ServicePricing servicePricing) {
        em.getTransaction().begin();
        em.merge(servicePricing);
        em.getTransaction().commit();
    }

    public void deleteServicePricing(int servicePricingId) {
        em.getTransaction().begin();
        ServicePricing sp = em.find(ServicePricing.class, servicePricingId);
        if (sp != null) {
            em.remove(sp);
        }
        em.getTransaction().commit();
    }
}
