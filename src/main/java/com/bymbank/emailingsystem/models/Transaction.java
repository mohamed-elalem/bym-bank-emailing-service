package com.bymbank.emailingsystem.models;

import javax.persistence.*;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Float amount;

    @ManyToOne
    private TransactionType transactionType;

    @ManyToOne
    private Account account;

    public Long getId() {
        return id;
    }

    public Float getAmount() {
        return amount;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public Account getAccount() {
        return account;
    }
}
