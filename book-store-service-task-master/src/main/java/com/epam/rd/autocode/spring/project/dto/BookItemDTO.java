package com.epam.rd.autocode.spring.project.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class BookItemDTO {
    @NotBlank
    private String bookName;
    @NotNull
    @Positive
    private Integer quantity;

    public BookItemDTO() {
    }

    public BookItemDTO(String bookName, Integer quantity) {
        this.bookName = bookName;
        this.quantity = quantity;
    }

    public String getBookName() { return bookName; }
    public void setBookName(String bookName) { this.bookName = bookName; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
}
