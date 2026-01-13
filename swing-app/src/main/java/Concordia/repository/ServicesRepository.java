package concordia.repository;

import concordia.annotations.Repository;
import concordia.domain.Services;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

@Repository
public class ServicesRepository {
    private final EntityManager em;
    public ServicesRepository(EntityManager em) {
        this.em = em;
    }

    public List<Services> getAllServices() {
        TypedQuery<Services> query = em.createQuery("SELECT s FROM Services s", Services.class);
        return query.getResultList();
    }

    public void insertService(Services service) {
        em.getTransaction().begin();
        em.persist(service);
        em.getTransaction().commit();
    }

    public void updateService(Services service) {
        em.getTransaction().begin();
        em.merge(service);
        em.getTransaction().commit();
    }

    public void deleteService(int serviceId) {
        em.getTransaction().begin();
        Services s = em.find(Services.class, serviceId);
        if (s != null) {
            em.remove(s);
        }
        em.getTransaction().commit();
    }
}
