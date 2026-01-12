// ORM/JPA version of Databases.java
// This file uses JPA (Jakarta Persistence) and EntityManager for ORM-based database operations.
package concordia;

import jakarta.persistence.*;
import java.util.List;
import java.util.ArrayList;
import concordia.domain.Company;
import concordia.domain.ServiceType;
import concordia.domain.ServicePricing;
import concordia.domain.TransactionHistory;
import concordia.domain.User;

public class Databases {
    @PersistenceContext
    private EntityManager entityManager;

    public Databases(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    // Insert a new company using JPA
    public void insertCompany(int companyId, String companyName) {
        Company company = new Company(companyId, "", companyName, new java.util.HashSet<>(), new java.util.HashSet<>());
        entityManager.getTransaction().begin();
        entityManager.persist(company);
        entityManager.getTransaction().commit();
    }

    // Update a transaction history record using JPA
    public void updateTransactionHistory(int historyId, String location, String provider, String deliveryDate, int amount, String notes) {
        entityManager.getTransaction().begin();
        TransactionHistory hist = entityManager.find(TransactionHistory.class, historyId);
        if (hist != null) {
            hist.setLocation(location);
            hist.setProvider(provider);
            hist.setDeliveryDate(deliveryDate);
            hist.setAmount(amount);
            hist.setNotes(notes);
            entityManager.merge(hist);
        }
        entityManager.getTransaction().commit();
    }

    // Get all companies
    public List<Company> getAllCompanies() {
        return entityManager.createQuery("SELECT c FROM Company c", Company.class).getResultList();
    }

    // Delete a company
    public void deleteCompany(int companyId) {
        entityManager.getTransaction().begin();
        Company company = entityManager.find(Company.class, companyId);
        if (company != null) {
            entityManager.remove(company);
        }
        entityManager.getTransaction().commit();
    }

    // Insert a new service type
    public void insertServiceType(String typeName) {
        ServiceType serviceType = new ServiceType();
        serviceType.setTypeName(typeName);
        entityManager.getTransaction().begin();
        entityManager.persist(serviceType);
        entityManager.getTransaction().commit();
    }

    // Insert a new service pricing
    public void insertServicePricing(int serviceTypeId, double price, String currency) {
        ServiceType serviceType = entityManager.find(ServiceType.class, serviceTypeId);
        if (serviceType != null) {
            ServicePricing pricing = new ServicePricing();
            pricing.setServiceType(serviceType);
            pricing.setPrice(price);
            pricing.setCurrency(currency);
            entityManager.getTransaction().begin();
            entityManager.persist(pricing);
            entityManager.getTransaction().commit();
        }
    }

    // Insert a new transaction history
    public void insertTransactionHistory(int serviceTypeId, int amount, String location, String provider, String deliveryDate, String notes) {
        ServiceType serviceType = entityManager.find(ServiceType.class, serviceTypeId);
        if (serviceType != null) {
            TransactionHistory history = new TransactionHistory();
            history.setServiceType(serviceType);
            history.setAmount(amount);
            history.setLocation(location);
            history.setProvider(provider);
            history.setDeliveryDate(deliveryDate);
            history.setNotes(notes);
            entityManager.getTransaction().begin();
            entityManager.persist(history);
            entityManager.getTransaction().commit();
        }
    }

    // Get all service types
    public List<ServiceType> getAllServiceTypes() {
        return entityManager.createQuery("SELECT s FROM ServiceType s", ServiceType.class).getResultList();
    }

    // Get all transaction histories
    public List<TransactionHistory> getAllTransactionHistories() {
        return entityManager.createQuery("SELECT t FROM TransactionHistory t", TransactionHistory.class).getResultList();
    }

    // Delete a service type
    public void deleteServiceType(int serviceTypeId) {
        entityManager.getTransaction().begin();
        ServiceType serviceType = entityManager.find(ServiceType.class, serviceTypeId);
        if (serviceType != null) {
            entityManager.remove(serviceType);
        }
        entityManager.getTransaction().commit();
    }

    // Delete a transaction history
    public void deleteTransactionHistory(int historyId) {
        entityManager.getTransaction().begin();
        TransactionHistory history = entityManager.find(TransactionHistory.class, historyId);
        if (history != null) {
            entityManager.remove(history);
        }
        entityManager.getTransaction().commit();
    }
}
