
package concordia.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name = "company")
public class Company implements Serializable {
            // Constructor for DatabaseRestore compatibility
            public Company(int companyId, String companyTitle, String companyName, java.util.Set<concordia.domain.ServiceType> serviceTypes, java.util.Set<User> users) {
                this.companyId = companyId;
                this.companyTitle = companyTitle;
                this.companyName = companyName;
                this.serviceTypes = serviceTypes != null ? serviceTypes : new java.util.HashSet<>();
                this.users = users != null ? users : new java.util.HashSet<>();
            }
        @Transient
        private java.util.Set<concordia.domain.ServiceType> serviceTypes = new java.util.HashSet<>();
    @Id
    @Column(name = "company_id")
    private int companyId;


    @Column(name = "company_title", length = 25)
    private String companyTitle;

    @Column(name = "company_name", length = 255)
    private String companyName;


    @OneToMany(mappedBy = "company")
    private java.util.Set<User> users = new java.util.HashSet<>();


    // No-arg constructor for JPA and tests
    public Company() {
}

    public java.util.ArrayList<User> getUsersAsList() {
        return new java.util.ArrayList<>(users);
    }



    public Company(int companyId, String companyTitle, java.util.Set<Object> users) {
        this.companyId = companyId;
        this.companyTitle = companyTitle;
        this.users = new java.util.HashSet<>();
        if (users != null) {
            for (Object o : users) {
                if (o instanceof User) {
                    this.users.add((User) o);
                }
            }
        }
    }

    public Company(int companyId, String companyTitle, String companyName, java.util.Set<User> users) {
        this.companyId = companyId;
        this.companyTitle = companyTitle;
        this.companyName = companyName;
        this.users = users;
    }


    public int getCompanyId() { return companyId; }
    public void setCompanyId(int companyId) { this.companyId = companyId; }

    public String getCompanyTitle() { return companyTitle; }
    public void setCompanyTitle(String companyTitle) { this.companyTitle = companyTitle; }

    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }


    public java.util.Set<User> getUsers() { return users; }
    public void setUsers(java.util.Set<User> users) { this.users = users; }

    // --- Added for swing-app compatibility ---
    public void setServiceTypes(java.util.Set<concordia.domain.ServiceType> serviceTypes) { this.serviceTypes = serviceTypes; }
    public java.util.Set<concordia.domain.ServiceType> getServiceTypes() { return this.serviceTypes; }

    }

    