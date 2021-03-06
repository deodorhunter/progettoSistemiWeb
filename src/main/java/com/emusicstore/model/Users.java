package com.emusicstore.model;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="users")
public class Users implements Serializable{

    private static final long serialVersionUID = 4163809278805830670L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;
    @Column( updatable=false )
    private String username;
    private String password;
    @Column( updatable=false )
    private boolean enabled;
    @Column( updatable=false )
    private String authority;

    @OneToOne(mappedBy = "users")
    private Customer customer;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

}
