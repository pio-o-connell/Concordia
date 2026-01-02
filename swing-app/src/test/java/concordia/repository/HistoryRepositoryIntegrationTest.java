package concordia.repository;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import concordia.domain.Company;
import concordia.domain.Item;
import concordia.domain.history;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceException;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HistoryRepositoryIntegrationTest {

    private EntityManagerFactory emf;

    @BeforeAll
    void setUpEntityManagerFactory() {
        try {
            emf = Persistence.createEntityManagerFactory("test-pu");
        } catch (PersistenceException ex) {
            Assumptions.assumeTrue(false, "Postgres database is not available: " + ex.getMessage());
        }
    }

    @AfterAll
    void tearDownEntityManagerFactory() {
        if (emf != null) {
            emf.close();
        }
    }

    @Test
    void getAllHistoryReturnsInsertedRows() {
        EntityManager em = emf.createEntityManager();
        HistoryRepository repository = new HistoryRepository(em);
        PersistedIds ids = null;
        try {
            ids = insertHistoryGraph(em, "QA-Lab-Records", 42);
            final PersistedIds capturedIds = ids;
            List<history> histories = repository.getAllHistory();
            assertFalse(histories.isEmpty(), "Repository should return persisted history rows");
            assertTrue(histories.stream().anyMatch(h -> h.getHistoryId() == capturedIds.historyId
                    && "QA-Lab-Records".equals(h.getLocation())
                    && 42 == h.getAmount()),
                "Inserted history row should be present in query results");
        } finally {
            cleanupInsertedRows(em, ids);
            em.close();
        }
    }

    @Test
    void deleteHistoryRemovesRowFromDatabase() {
        EntityManager em = emf.createEntityManager();
        HistoryRepository repository = new HistoryRepository(em);
        PersistedIds ids = null;
        try {
            ids = insertHistoryGraph(em, "QA-Deletion", 13);
            repository.deleteHistory(ids.historyId);
            history removed = em.find(history.class, ids.historyId);
            assertNull(removed, "Repository delete should remove the history row");
        } finally {
            cleanupInsertedRows(em, ids);
            em.close();
        }
    }

    private PersistedIds insertHistoryGraph(EntityManager em, String location, int amount) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        int companyId = randomId();
        Company company = new Company();
        company.setCompanyId(companyId);
        company.setCompanyTitle("QA");
        company.setCompanyName("JUnit Company " + companyId);
        em.persist(company);

        int itemId = randomId();
        Item item = new Item();
        item.setItemId(itemId);
        item.setCompanyId(companyId);
        item.setCompany(company);
        item.setQuantity(15);
        item.setItemName("JUnit Item " + itemId);
        item.setLocation("Aisle-5");
        item.setNotes("Created for integration testing");
        item.setDate(Timestamp.from(Instant.now()));
        em.persist(item);

        int historyId = randomId();
        history entry = new history();
        entry.setHistoryId(historyId);
        entry.setItem(item);
        entry.setAmount(amount);
        entry.setLocation(location);
        entry.setSupplier("JUnit Supplier");
        entry.setDeliveryDate("2026-01-01");
        entry.setNotes("Integration test entry");
        em.persist(entry);

        tx.commit();
        em.clear();
        return new PersistedIds(companyId, itemId, historyId);
    }

    private void cleanupInsertedRows(EntityManager em, PersistedIds ids) {
        if (ids == null) {
            return;
        }
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        history hist = em.find(history.class, ids.historyId);
        if (hist != null) {
            em.remove(hist);
        }
        Item item = em.find(Item.class, ids.itemId);
        if (item != null) {
            em.remove(item);
        }
        Company company = em.find(Company.class, ids.companyId);
        if (company != null) {
            em.remove(company);
        }
        tx.commit();
    }

    private static int randomId() {
        return ThreadLocalRandom.current().nextInt(1_000_000, 2_000_000);
    }

    private static final class PersistedIds {
        private final int companyId;
        private final int itemId;
        private final int historyId;

        private PersistedIds(int companyId, int itemId, int historyId) {
            this.companyId = companyId;
            this.itemId = itemId;
            this.historyId = historyId;
        }
    }
}
