package backend.repository;

import concordia.domain.ServiceType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class ServiceTypeRepository {
        public java.util.List<ServiceType> getAllServiceTypesForCompany(int companyId) {
            TypedQuery<ServiceType> query = entityManager.createQuery(
                "SELECT st FROM ServiceType st WHERE st.company.companyId = :companyId",
                ServiceType.class
            );
            query.setParameter("companyId", companyId);
            return query.getResultList();
        }
    private final EntityManager entityManager;

    public ServiceTypeRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<ServiceType> getAllServiceTypes() {
        TypedQuery<ServiceType> query = entityManager.createQuery("SELECT st FROM ServiceType st", ServiceType.class);
        return query.getResultList();
    }

    public void insertServiceType(ServiceType serviceType) {
        EntityTransaction tx = entityManager.getTransaction();
        try {
            tx.begin();
            entityManager.persist(serviceType);
            tx.commit();
        } catch (RuntimeException ex) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw ex;
        }
    }

    public void updateServiceType(ServiceType serviceType) {
        EntityTransaction tx = entityManager.getTransaction();
        try {
            tx.begin();
            entityManager.merge(serviceType);
            tx.commit();
        } catch (RuntimeException ex) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw ex;
        }
    }

    public void deleteServiceType(int serviceTypeId) {
        EntityTransaction tx = entityManager.getTransaction();
        try {
            tx.begin();
            ServiceType st = entityManager.find(ServiceType.class, serviceTypeId);
            if (st != null) {
                entityManager.remove(st);
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
