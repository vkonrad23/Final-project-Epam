package com.epam.rd.autocode.spring.project.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderDTO {
    @NotBlank
    @Email
    private String clientEmail;
    @NotBlank
    @Email
    private String employeeEmail;
    @NotNull
    private LocalDateTime orderDate;
    @NotNull
    @Positive
    private BigDecimal price;
    @NotNull
    @Valid
    private List<BookItemDTO> bookItems;

    public OrderDTO() {
    }

    public OrderDTO(String clientEmail, String employeeEmail, LocalDateTime orderDate, BigDecimal price, List<BookItemDTO> bookItems) {
        this.clientEmail = clientEmail;
        this.employeeEmail = employeeEmail;
        this.orderDate = orderDate;
        this.price = price;
        this.bookItems = bookItems;
    }

    public String getClientEmail() { return clientEmail; }
    public void setClientEmail(String clientEmail) { this.clientEmail = clientEmail; }
    public String getEmployeeEmail() { return employeeEmail; }
    public void setEmployeeEmail(String employeeEmail) { this.employeeEmail = employeeEmail; }
    public LocalDateTime getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDateTime orderDate) { this.orderDate = orderDate; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public List<BookItemDTO> getBookItems() { return bookItems; }
    public void setBookItems(List<BookItemDTO> bookItems) { this.bookItems = bookItems; }
}
