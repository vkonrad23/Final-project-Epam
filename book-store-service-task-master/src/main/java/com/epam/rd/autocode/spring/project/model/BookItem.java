package com.epam.rd.autocode.spring.project.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "book_items")
public class BookItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    public BookItem() {
    }

    public BookItem(Long id, Integer quantity, Book book, Order order) {
        this.id = id;
        this.quantity = quantity;
        this.book = book;
        this.order = order;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public Book getBook() { return book; }
    public void setBook(Book book) { this.book = book; }
    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }
}
