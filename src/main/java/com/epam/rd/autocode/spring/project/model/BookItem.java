/** Цей файл визначає основні типи та поведінку для цього модуля */
package com.epam.rd.autocode.spring.project.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Сутність (Entity), яка представляє елемент замовлення (BookItem)
 * Тобто конкретну книгу + її кількість у конкретному замовленні
 */
@Entity
@Table(name = "book_items")
public class BookItem {

    // Первинний ключ (ID елемента)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Кількість книг у замовленні
    private Integer quantity;

    /**
     * Зв'язок "багато до одного" з книгою
     * Багато BookItem можуть посилатися на одну Book
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id") // зовнішній ключ у таблиці
    private Book book;

    /**
     * Зв'язок "багато до одного" із замовленням
     * Багато BookItem належать одному Order
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id") // зовнішній ключ у таблиці
    private Order order;

    /**
     * Порожній конструктор (обов'язковий для JPA)
     */
    public BookItem() {
    }

    /**
     * Конструктор з усіма полями
     */
    public BookItem(Long id, Integer quantity, Book book, Order order) {
        this.id = id;
        this.quantity = quantity;
        this.book = book;
        this.order = order;
    }

    // --- Геттери та сеттери ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public Book getBook() { return book; }
    public void setBook(Book book) { this.book = book; }

    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }
}