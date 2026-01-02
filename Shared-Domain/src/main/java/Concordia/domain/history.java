package concordia.domain;

import java.io.Serializable;
import jakarta.persistence.*;

@Entity
@Table(name = "history")
public class history implements Serializable {

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

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

    @Id
    @Column(name = "history_id")
    private int historyId;

    // No-arg constructor for JPA
    public history() {}

    // Constructor matching tests (without Item reference)
    public history(int historyId, int amount, String location, String provider, String deliveryDate, String notes) {
        this.historyId = historyId;
        this.amount = amount;
        this.location = location;
        this.provider = provider;
        this.deliveryDate = deliveryDate;
        this.notes = notes;
    }

    // Full constructor including associated Item
    public history(int historyId, Item item, int amount, String location, String provider, String deliveryDate, String notes) {
        this.historyId = historyId;
        this.item = item;
        this.amount = amount;
        this.location = location;
        this.provider = provider;
        this.deliveryDate = deliveryDate;
        this.notes = notes;
    }

    // Legacy-style itemId accessors if needed
    public int getItemId() { return item != null ? item.getItemId() : 0; }
    public void setItemId(int itemId) { /* no-op for compatibility */ }

    public int getHistoryId() { return historyId; }
    public void setHistoryId(int historyId) { this.historyId = historyId; }

    public Item getItem() { return item; }
    public void setItem(Item item) { this.item = item; }

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

    // Compatibility methods for legacy code using "supplier"
    public String getSupplier() { return getProvider(); }
    public void setSupplier(String supplier) { setProvider(supplier); }
}


