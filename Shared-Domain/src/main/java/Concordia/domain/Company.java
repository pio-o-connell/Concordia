package concordia.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name = "company")
public class Company implements Serializable {
    @Id
    @Column(name = "company_id")
    private int companyId;


    @Column(name = "company_title", length = 25)
    private String companyTitle;

    @Column(name = "company_name", length = 255)
    private String companyName;

    @OneToMany(mappedBy = "company")
    private java.util.Set<Item> items = new java.util.HashSet<>();

    @OneToMany(mappedBy = "company")
    private java.util.Set<User> users = new java.util.HashSet<>();


    // No-arg constructor for JPA and tests
    public Company() {
    }


    public java.util.ArrayList<Item> getItemsAsList() {
        return new java.util.ArrayList<>(items);
    }

    public java.util.ArrayList<User> getUsersAsList() {
        return new java.util.ArrayList<>(users);
    }

    // Convenience helper used in tests to add items
    public void addItem(Item item) {
        if (item == null) {
            return;
        }
        if (items == null) {
            items = new java.util.HashSet<>();
        }
        items.add(item);
        item.setCompany(this);
    }


    public Company(int companyId, String companyTitle, String companyName, java.util.Set<Item> items, java.util.Set<User> users) {
        this.companyId = companyId;
        this.companyTitle = companyTitle;
        this.companyName = companyName;
        this.items = items;
        this.users = users;
    }

    public int getCompanyId() { return companyId; }
    public void setCompanyId(int companyId) { this.companyId = companyId; }

    public String getCompanyTitle() { return companyTitle; }
    public void setCompanyTitle(String companyTitle) { this.companyTitle = companyTitle; }

    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }

    public java.util.Set<Item> getItems() { return items; }
    public void setItems(java.util.Set<Item> items) { this.items = items; }

    public java.util.Set<User> getUsers() { return users; }
    public void setUsers(java.util.Set<User> users) { this.users = users; }

    }

    