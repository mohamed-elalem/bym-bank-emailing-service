package com.bymbank.emailingsystem.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String email;
    private String username;

    @OneToMany(mappedBy = "user")
    private List<Account> accounts;

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public List<Account> getAccounts() {
        return accounts;
    }
}
