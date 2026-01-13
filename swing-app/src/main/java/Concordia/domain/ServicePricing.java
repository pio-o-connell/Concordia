package concordia.domain;

public class ServicePricing {
    private int servicePricingId;
    private int serviceTypeId;
    private double price;
    private String currency;
    private String effectiveDate;

    // For ORM/JPA compatibility
    private ServiceType serviceType;

    public int getServicePricingId() { return servicePricingId; }
    public void setServicePricingId(int servicePricingId) { this.servicePricingId = servicePricingId; }
    public int getServiceTypeId() { return serviceTypeId; }
    public void setServiceTypeId(int serviceTypeId) { this.serviceTypeId = serviceTypeId; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
    public String getEffectiveDate() { return effectiveDate; }
    public void setEffectiveDate(String effectiveDate) { this.effectiveDate = effectiveDate; }

    public ServiceType getServiceType() { return serviceType; }
    public void setServiceType(ServiceType serviceType) { this.serviceType = serviceType; }
}
