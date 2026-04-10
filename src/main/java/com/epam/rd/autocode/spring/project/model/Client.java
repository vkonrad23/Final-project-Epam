/** Цей файл визначає основні типи та поведінку для цього модуля */
package com.epam.rd.autocode.spring.project.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.math.BigDecimal;

/**
 * Сутність (Entity), яка представляє клієнта в системі
 * Наслідує базовий клас User (email, пароль, ім'я)
 */
@Entity
@Table(name = "clients")
public class Client extends User {

    // Баланс клієнта (скільки грошей є на рахунку)
    private BigDecimal balance;

    /**
     * Порожній конструктор (обов'язковий для JPA)
     */
    public Client() {
    }

    /**
     * Конструктор з усіма полями
     * Частина полів передається в батьківський клас User
     */
    public Client(Long id, String email, String password, String name, BigDecimal balance) {
        super(id, email, password, name); // виклик конструктора User
        this.balance = balance;
    }

    /**
     * Отримати баланс клієнта
     */
    public BigDecimal getBalance() {
        return balance;
    }

    /**
     * Встановити баланс клієнта
     */
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}