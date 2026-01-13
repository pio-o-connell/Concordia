package concordia.domain;

import java.io.Serializable;
import jakarta.persistence.*;

import concordia.domain.ServiceType;

@Entity
@Table(name = "service_pricing")
public class ServicePricing implements Serializable {
        @Transient
        private concordia.domain.ServiceType serviceType;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_pricing_id")
    private Integer servicePricingId;

    @Column(name = "service_type_id")
    private Integer serviceTypeId;


    @Column(name = "price")
    private Double price;

    @Column(name = "currency", length = 10)
    private String currency;

    @Column(name = "effective_date", length = 25)
    private String effectiveDate;


    public ServicePricing() {}

    public ServicePricing(int servicePricingId, int serviceTypeId, Double price, String currency, String effectiveDate) {
        this.servicePricingId = servicePricingId;
        this.serviceTypeId = serviceTypeId;
        this.price = price;
        this.currency = currency;
        this.effectiveDate = effectiveDate;
    }

    public int getServicePricingId() { return servicePricingId != null ? servicePricingId : 0; }
    public void setServicePricingId(int servicePricingId) { this.servicePricingId = servicePricingId; }

    public int getServiceTypeId() { return serviceTypeId != null ? serviceTypeId : 0; }
    public void setServiceTypeId(int serviceTypeId) { this.serviceTypeId = serviceTypeId; }

    public double getPrice() { return price != null ? price : 0.0; }
    public void setPrice(Double price) { this.price = price; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public String getEffectiveDate() { return effectiveDate; }
    public void setEffectiveDate(String effectiveDate) { this.effectiveDate = effectiveDate; }

    // --- Added for swing-app compatibility ---
    public void setServiceType(concordia.domain.ServiceType serviceType) { this.serviceType = serviceType; }
    public concordia.domain.ServiceType getServiceType() { return this.serviceType; }
}
