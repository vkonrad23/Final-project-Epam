package com.epam.rd.autocode.spring.project.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "clients")
public class Client extends User {
    private BigDecimal balance;

    public Client() {
    }

    public Client(Long id, String email, String password, String name, BigDecimal balance) {
        super(id, email, password, name);
        this.balance = balance;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
