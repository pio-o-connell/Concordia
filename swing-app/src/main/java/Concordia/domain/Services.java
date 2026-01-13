package concordia.domain;

public class Services {
    private int serviceId;
    private int companyId;
    private String serviceName;
    private String notes;
    // Add constructors, getters, setters as needed
    public int getServiceId() { return serviceId; }
    public void setServiceId(int serviceId) { this.serviceId = serviceId; }
    public int getCompanyId() { return companyId; }
    public void setCompanyId(int companyId) { this.companyId = companyId; }
    public String getServiceName() { return serviceName; }
    public void setServiceName(String serviceName) { this.serviceName = serviceName; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}
