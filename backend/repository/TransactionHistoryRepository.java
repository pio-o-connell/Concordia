package backend.repository;

import concordia.domain.TransactionHistory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class TransactionHistoryRepository {
    private final EntityManager entityManager;

    public TransactionHistoryRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<TransactionHistory> getAllTransactionHistories() {
        TypedQuery<TransactionHistory> query = entityManager.createQuery("SELECT th FROM TransactionHistory th", TransactionHistory.class);
        return query.getResultList();
    }

    public void insertTransactionHistory(TransactionHistory transactionHistory) {
        EntityTransaction tx = entityManager.getTransaction();
        try {
            tx.begin();
            entityManager.persist(transactionHistory);
            tx.commit();
        } catch (RuntimeException ex) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw ex;
        }
    }

    public void updateTransactionHistory(TransactionHistory transactionHistory) {
        EntityTransaction tx = entityManager.getTransaction();
        try {
            tx.begin();
            entityManager.merge(transactionHistory);
            tx.commit();
        } catch (RuntimeException ex) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw ex;
        }
    }

    public void deleteTransactionHistory(int transactionId) {
        EntityTransaction tx = entityManager.getTransaction();
        try {
            tx.begin();
            TransactionHistory th = entityManager.find(TransactionHistory.class, transactionId);
            if (th != null) {
                entityManager.remove(th);
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
