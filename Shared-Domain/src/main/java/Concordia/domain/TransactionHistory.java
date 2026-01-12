package concordia.domain;

import java.io.Serializable;
import jakarta.persistence.*;

import concordia.domain.ServiceType;

@Entity
@Table(name = "transaction_history")
public class TransactionHistory implements Serializable {
    @Transient
    private ServiceType serviceType;

    @Id
    @Column(name = "transaction_id")
    private int transactionId;

    @Column(name = "service_type_id")
    private int serviceTypeId;

    @Column(name = "amount")
    private int amount;

    @Column(name = "location", length = 25)
    private String location;

    @Column(name = "provider")
    private String provider;

    @Column(name = "delivery_date", length = 25)
    private String deliveryDate;

    @Column(name = "notes", length = 200)
    private String notes;

    public TransactionHistory() {}

    public TransactionHistory(int transactionId, int serviceTypeId, int amount, String location, String provider, String deliveryDate, String notes) {
        this.transactionId = transactionId;
        this.serviceTypeId = serviceTypeId;
        this.amount = amount;
        this.location = location;
        this.provider = provider;
        this.deliveryDate = deliveryDate;
        this.notes = notes;
    }

    public int getTransactionId() { return transactionId; }
    public void setTransactionId(int transactionId) { this.transactionId = transactionId; }

    public int getServiceTypeId() { return serviceTypeId; }
    public void setServiceTypeId(int serviceTypeId) { this.serviceTypeId = serviceTypeId; }

    public int getAmount() { return amount; }
    public void setAmount(int amount) { this.amount = amount; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getProvider() { return provider; }
    public void setProvider(String provider) { this.provider = provider; }

    public String getDeliveryDate() { return deliveryDate; }
    public void setDeliveryDate(String deliveryDate) { this.deliveryDate = deliveryDate; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    // --- Added for swing-app compatibility ---
    public void setTransactionHistoryId(int id) { this.transactionId = id; }
    public void setServiceType(ServiceType serviceType) { this.serviceType = serviceType; }
    public ServiceType getServiceType() { return this.serviceType; }
    public void setSupplier(String supplier) { this.provider = supplier; }
}
