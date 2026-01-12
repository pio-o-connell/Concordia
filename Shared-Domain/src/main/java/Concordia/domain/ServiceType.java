package concordia.domain;

import java.io.Serializable;
import jakarta.persistence.*;

@Entity
@Table(name = "service_type")
public class ServiceType implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_type_id")
    private int serviceTypeId;

    @Column(name = "type_name", length = 50)
    private String typeName;

    public ServiceType() {}

    public ServiceType(int serviceTypeId, String typeName) {
        this.serviceTypeId = serviceTypeId;
        this.typeName = typeName;
    }

    public int getServiceTypeId() { return serviceTypeId; }
    public void setServiceTypeId(int serviceTypeId) { this.serviceTypeId = serviceTypeId; }

    public String getTypeName() { return typeName; }
    public void setTypeName(String typeName) { this.typeName = typeName; }
}
