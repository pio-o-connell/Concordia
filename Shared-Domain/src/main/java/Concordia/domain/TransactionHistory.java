package concordia.domain;

import java.io.Serializable;
import jakarta.persistence.*;

import concordia.domain.ServiceType;

@Entity
@Table(name = "transaction_history")
public class TransactionHistory implements Serializable {
        @Column(name = "service_id")
        private Integer serviceId;

        @Column(name = "service_snapshot_size")
        private String serviceSnapshotSize;

        @Column(name = "quantity")
        private Integer quantity;

        @Column(name = "unit_cost_snapshot")
        private Double unitCostSnapshot;

        @Column(name = "cost")
        private Double cost;

        @Column(name = "service_name")
        private String serviceName;
    @Transient
    private ServiceType serviceType;

    @Id
    @Column(name = "transaction_id")
    private Integer transactionId;

    @Column(name = "service_type_id")
    private Integer serviceTypeId;

    @Column(name = "amount")
    private Integer amount;

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
            // Add new fields to constructor as needed
        this.transactionId = transactionId;
        this.serviceTypeId = serviceTypeId;
        this.amount = amount;
        this.location = location;
        this.provider = provider;
        this.deliveryDate = deliveryDate;
        this.notes = notes;
    }

    public int getTransactionId() { return transactionId != null ? transactionId : 0; }
    public void setTransactionId(int transactionId) { this.transactionId = transactionId; }

    public int getServiceTypeId() { return serviceTypeId != null ? serviceTypeId : 0; }
    public void setServiceTypeId(int serviceTypeId) { this.serviceTypeId = serviceTypeId; }

    public int getAmount() { return amount != null ? amount : 0; }
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
        public int getServiceId() { return serviceId != null ? serviceId : 0; }
        public void setServiceId(int serviceId) { this.serviceId = serviceId; }

        public String getServiceSnapshotSize() { return serviceSnapshotSize; }
        public void setServiceSnapshotSize(String serviceSnapshotSize) { this.serviceSnapshotSize = serviceSnapshotSize; }

        public int getQuantity() { return quantity != null ? quantity : 0; }
        public void setQuantity(int quantity) { this.quantity = quantity; }

        public double getUnitCostSnapshot() { return unitCostSnapshot != null ? unitCostSnapshot : 0.0; }
        public void setUnitCostSnapshot(double unitCostSnapshot) { this.unitCostSnapshot = unitCostSnapshot; }

        public double getCost() { return cost; }
        public void setCost(double cost) { this.cost = cost; }

        public String getServiceName() { return serviceName; }
        public void setServiceName(String serviceName) { this.serviceName = serviceName; }
    public void setTransactionHistoryId(int id) { this.transactionId = id; }
    public void setServiceType(ServiceType serviceType) { this.serviceType = serviceType; }
    public ServiceType getServiceType() { return this.serviceType; }
    public void setSupplier(String supplier) { this.provider = supplier; }
}
