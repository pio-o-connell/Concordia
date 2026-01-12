
package concordia.repository;
import concordia.annotations.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;
import concordia.domain.TransactionHistory;
import concordia.domain.Company;
import concordia.domain.TransactionHistory;
import concordia.domain.Company;

@Repository
public class TransactionHistoryRepository {
    private final EntityManager em;
    public TransactionHistoryRepository(EntityManager em) {
        this.em = em;
    }

    // Retrieve all history records from the database using JPA
    public List<TransactionHistory> getAllTransactionHistory() {
        TypedQuery<TransactionHistory> query = em.createQuery("SELECT h FROM TransactionHistory h", TransactionHistory.class);
        return query.getResultList();
    }

    public void updateTransactionHistory(TransactionHistory hist) {
        em.getTransaction().begin();
        em.merge(hist);
        em.getTransaction().commit();
    }

    public void deleteTransactionHistory(int transactionId) {
        em.getTransaction().begin();
        TransactionHistory hist = em.find(TransactionHistory.class, transactionId);
        if (hist != null) {
            em.remove(hist);
        }
        em.getTransaction().commit();
    }

    public void insertTransactionHistory(int serviceTypeId, int amount, String location, String provider, String deliveryDate, String notes) {
        em.getTransaction().begin();
        TransactionHistory hist = new TransactionHistory();
        hist.setServiceTypeId(serviceTypeId);
        hist.setAmount(amount);
        hist.setLocation(location);
        hist.setSupplier(provider);
        hist.setDeliveryDate(deliveryDate);
        hist.setNotes(notes);
        em.persist(hist);
        em.getTransaction().commit();
    }
}
