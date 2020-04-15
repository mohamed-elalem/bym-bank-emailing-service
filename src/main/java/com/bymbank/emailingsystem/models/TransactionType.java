package com.bymbank.emailingsystem.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class TransactionType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "transactionType")
    private List<Transaction> transactions;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }
}
