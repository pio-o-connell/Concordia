package concordia.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "services")
public class Services {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int serviceId;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @Column(name = "service_name")
    private String serviceName;

    @Column(name = "notes")
    private String notes;

    // Getters and setters
    public int getServiceId() { return serviceId; }
    public void setServiceId(int serviceId) { this.serviceId = serviceId; }

    public Company getCompany() { return company; }
    public void setCompany(Company company) { this.company = company; }

    public String getServiceName() { return serviceName; }
    public void setServiceName(String serviceName) { this.serviceName = serviceName; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}
