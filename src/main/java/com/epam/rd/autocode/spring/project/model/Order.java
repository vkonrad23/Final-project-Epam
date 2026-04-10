/** Цей файл визначає основні типи та поведінку для цього модуля */
package com.epam.rd.autocode.spring.project.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Сутність (Entity), яка представляє замовлення (Order)
 */
@Entity
@Table(name = "orders")
public class Order {

    // Первинний ключ (ID замовлення)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Зв'язок "багато до одного" з клієнтом
     * Один клієнт може мати багато замовлень
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;

    /**
     * Зв'язок "багато до одного" зі співробітником
     * Один співробітник може обробляти багато замовлень
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    // Дата і час створення замовлення
    private LocalDateTime orderDate;

    // Загальна ціна замовлення
    private BigDecimal price;

    /**
     * Зв'язок "один до багатьох" з BookItem
     * Одне замовлення містить багато елементів (книг)
     *
     * mappedBy = "order" → поле order у BookItem є власником зв'язку
     * cascade = ALL → всі операції (save, delete) передаються дочірнім об'єктам
     * orphanRemoval = true → якщо елемент видалений зі списку, він видаляється з БД
     */
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookItem> bookItems;

    /**
     * Порожній конструктор (обов'язковий для JPA)
     */
    public Order() {
    }

    /**
     * Конструктор з усіма полями
     */
    public Order(Long id, Client client, Employee employee,
                 LocalDateTime orderDate, BigDecimal price,
                 List<BookItem> bookItems) {
        this.id = id;
        this.client = client;
        this.employee = employee;
        this.orderDate = orderDate;
        this.price = price;
        this.bookItems = bookItems;
    }

    // --- Геттери та сеттери ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Client getClient() { return client; }
    public void setClient(Client client) { this.client = client; }

    public Employee getEmployee() { return employee; }
    public void setEmployee(Employee employee) { this.employee = employee; }

    public LocalDateTime getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDateTime orderDate) { this.orderDate = orderDate; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public List<BookItem> getBookItems() { return bookItems; }
    public void setBookItems(List<BookItem> bookItems) { this.bookItems = bookItems; }
}