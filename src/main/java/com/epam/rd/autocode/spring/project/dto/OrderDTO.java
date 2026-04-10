/** This file defines core Java types and behavior for this feature/module. */
package com.epam.rd.autocode.spring.project.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO для передачі даних замовлення (Order)
 * Використовується при створенні, отриманні та обробці замовлень
 */
public class OrderDTO {

    // Email клієнта (обов'язковий + валідний формат)
    @NotBlank
    @Email
    private String clientEmail;

    // Email співробітника, який обробляє замовлення
    @NotBlank
    @Email
    private String employeeEmail;

    // Дата і час створення замовлення
    @NotNull
    private LocalDateTime orderDate;

    // Загальна ціна замовлення (обов'язково > 0)
    @NotNull
    @Positive
    private BigDecimal price;

    // Список книг у замовленні
    // @Valid → перевіряє кожен елемент списку (BookItemDTO)
    @NotNull
    @Valid
    private List<BookItemDTO> bookItems;

    /**
     * Порожній конструктор (потрібен для Spring / Jackson)
     */
    public OrderDTO() {
    }

    /**
     * Конструктор з усіма полями
     */
    public OrderDTO(String clientEmail, String employeeEmail, LocalDateTime orderDate,
                    BigDecimal price, List<BookItemDTO> bookItems) {
        this.clientEmail = clientEmail;
        this.employeeEmail = employeeEmail;
        this.orderDate = orderDate;
        this.price = price;
        this.bookItems = bookItems;
    }

    // --- Геттери та сеттери ---

    public String getClientEmail() { 
        return clientEmail; 
    }

    public void setClientEmail(String clientEmail) { 
        this.clientEmail = clientEmail; 
    }

    public String getEmployeeEmail() { 
        return employeeEmail; 
    }

    public void setEmployeeEmail(String employeeEmail) { 
        this.employeeEmail = employeeEmail; 
    }

    public LocalDateTime getOrderDate() { 
        return orderDate; 
    }

    public void setOrderDate(LocalDateTime orderDate) { 
        this.orderDate = orderDate; 
    }

    public BigDecimal getPrice() { 
        return price; 
    }

    public void setPrice(BigDecimal price) { 
        this.price = price; 
    }

    public List<BookItemDTO> getBookItems() { 
        return bookItems; 
    }

    public void setBookItems(List<BookItemDTO> bookItems) { 
        this.bookItems = bookItems; 
    }
}