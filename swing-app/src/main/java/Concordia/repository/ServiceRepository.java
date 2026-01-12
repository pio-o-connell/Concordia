package concordia.repository;
import concordia.annotations.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;
import concordia.domain.ServiceType;
import concordia.domain.ServicePricing;

@Repository
public class ServiceRepository {
    private final EntityManager em;
    public ServiceRepository(EntityManager em) {
        this.em = em;
    }

    // Retrieve all service types from the database using JPA
    public List<ServiceType> getAllServiceTypes() {
        TypedQuery<ServiceType> query = em.createQuery("SELECT s FROM ServiceType s", ServiceType.class);
        return query.getResultList();
    }

    // Retrieve all service pricing from the database using JPA
    public List<ServicePricing> getAllServicePricings() {
        TypedQuery<ServicePricing> query = em.createQuery("SELECT s FROM ServicePricing s", ServicePricing.class);
        return query.getResultList();
    }

    public void insertNewServiceType(String typeName) {
        em.getTransaction().begin();
        ServiceType serviceType = new ServiceType();
        serviceType.setTypeName(typeName);
        em.persist(serviceType);
        em.getTransaction().commit();
    }

    public void insertNewServicePricing(int serviceTypeId, double price, String currency, String effectiveDate) {
        em.getTransaction().begin();
        ServicePricing servicePricing = new ServicePricing();
        servicePricing.setServiceTypeId(serviceTypeId);
        servicePricing.setPrice(price);
        servicePricing.setCurrency(currency);
        servicePricing.setEffectiveDate(effectiveDate);
        em.persist(servicePricing);
        em.getTransaction().commit();
    }

    public void deleteServiceType(int serviceTypeId) {
        em.getTransaction().begin();
        ServiceType serviceType = em.find(ServiceType.class, serviceTypeId);
        if (serviceType != null) {
            em.remove(serviceType);
        }
        em.getTransaction().commit();
    }

    public void deleteServicePricing(int servicePricingId) {
        em.getTransaction().begin();
        ServicePricing servicePricing = em.find(ServicePricing.class, servicePricingId);
        if (servicePricing != null) {
            em.remove(servicePricing);
        }
        em.getTransaction().commit();
    }
}
