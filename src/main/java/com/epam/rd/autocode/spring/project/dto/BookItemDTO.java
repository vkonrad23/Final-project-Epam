/** This file defines core Java types and behavior for this feature/module. */
package com.epam.rd.autocode.spring.project.dto;

// Анотації для валідації полів
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * DTO для одного елемента замовлення (Book Item)
 * Представляє конкретну книгу + її кількість у замовленні
 */
public class BookItemDTO {

    // Назва книги (обов'язкове поле, не може бути порожнім)
    @NotBlank
    private String bookName;

    // Кількість книг (обов'язкове поле, має бути > 0)
    @NotNull
    @Positive
    private Integer quantity;

    /**
     * Порожній конструктор (потрібен для Spring / Jackson)
     */
    public BookItemDTO() {
    }

    /**
     * Конструктор з параметрами
     */
    public BookItemDTO(String bookName, Integer quantity) {
        this.bookName = bookName;
        this.quantity = quantity;
    }

    /**
     * Отримати назву книги
     */
    public String getBookName() { 
        return bookName; 
    }

    /**
     * Встановити назву книги
     */
    public void setBookName(String bookName) { 
        this.bookName = bookName; 
    }

    /**
     * Отримати кількість книг
     */
    public Integer getQuantity() { 
        return quantity; 
    }

    /**
     * Встановити кількість книг
     */
    public void setQuantity(Integer quantity) { 
        this.quantity = quantity; 
    }
}