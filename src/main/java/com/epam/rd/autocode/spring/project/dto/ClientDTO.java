/** This file defines core Java types and behavior for this feature/module. */
package com.epam.rd.autocode.spring.project.dto;

// Кастомна валідація для складного пароля
import com.epam.rd.autocode.spring.project.validation.StrongPassword;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

/**
 * DTO для передачі даних клієнта (Client)
 * Використовується при реєстрації, оновленні або отриманні клієнта
 */
public class ClientDTO {

    // Email клієнта (обов'язковий + повинен мати правильний формат)
    @NotBlank
    @Email
    private String email;

    // Пароль клієнта (обов'язковий + проходить кастомну перевірку StrongPassword)
    @NotBlank
    @StrongPassword
    private String password;

    // Ім'я клієнта (обов'язкове поле)
    @NotBlank
    private String name;

    // Баланс клієнта (не може бути null і має бути >= 0)
    @NotNull
    @PositiveOrZero
    private BigDecimal balance;

    /**
     * Порожній конструктор (потрібен для Spring / Jackson)
     */
    public ClientDTO() {
    }

    /**
     * Конструктор з усіма полями
     */
    public ClientDTO(String email, String password, String name, BigDecimal balance) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.balance = balance;
    }

    // --- Геттери та сеттери ---

    public String getEmail() { 
        return email; 
    }

    public void setEmail(String email) { 
        this.email = email; 
    }

    public String getPassword() { 
        return password; 
    }

    public void setPassword(String password) { 
        this.password = password; 
    }

    public String getName() { 
        return name; 
    }

    public void setName(String name) { 
        this.name = name; 
    }

    public BigDecimal getBalance() { 
        return balance; 
    }

    public void setBalance(BigDecimal balance) { 
        this.balance = balance; 
    }
}