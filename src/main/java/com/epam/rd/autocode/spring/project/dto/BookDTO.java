/** This file defines core Java types and behavior for this feature/module. */
package com.epam.rd.autocode.spring.project.dto;

// Enum для вікової категорії книги (наприклад: CHILDREN, TEEN, ADULT)
import com.epam.rd.autocode.spring.project.model.enums.AgeGroup;

// Enum для мови книги (наприклад: ENGLISH, UKRAINIAN тощо)
import com.epam.rd.autocode.spring.project.model.enums.Language;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO для передачі даних про книгу між клієнтом і сервером
 * Використовується при створенні, оновленні або отриманні книги
 */
public class BookDTO {

    // Назва книги (обов'язкове поле, не може бути порожнім)
    @NotBlank
    private String name;

    // Жанр книги (обов'язкове поле)
    @NotBlank
    private String genre;

    // Вікова категорія (enum, обов'язкове поле)
    @NotNull
    private AgeGroup ageGroup;

    // Ціна книги (обов'язково > 0)
    @NotNull
    @Positive
    private BigDecimal price;

    // Дата публікації (обов'язкове поле)
    @NotNull
    private LocalDate publicationDate;

    // Автор книги (обов'язкове поле)
    @NotBlank
    private String author;

    // Кількість сторінок (обов'язково > 0)
    @NotNull
    @Positive
    private Integer pages;

    // Додаткові характеристики (необов'язкове поле)
    private String characteristics;

    // Опис книги (необов'язкове поле)
    private String description;

    // Мова книги (enum, обов'язкове поле)
    @NotNull
    private Language language;

    /**
     * Порожній конструктор (потрібен для Spring / Jackson)
     */
    public BookDTO() {
    }

    /**
     * Конструктор з усіма полями
     */
    public BookDTO(String name, String genre, AgeGroup ageGroup, BigDecimal price, LocalDate publicationDate,
                   String author, Integer pages, String characteristics, String description, Language language) {
        this.name = name;
        this.genre = genre;
        this.ageGroup = ageGroup;
        this.price = price;
        this.publicationDate = publicationDate;
        this.author = author;
        this.pages = pages;
        this.characteristics = characteristics;
        this.description = description;
        this.language = language;
    }

    // --- Геттери та сеттери ---

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public AgeGroup getAgeGroup() { return ageGroup; }
    public void setAgeGroup(AgeGroup ageGroup) { this.ageGroup = ageGroup; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public LocalDate getPublicationDate() { return publicationDate; }
    public void setPublicationDate(LocalDate publicationDate) { this.publicationDate = publicationDate; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public Integer getPages() { return pages; }
    public void setPages(Integer pages) { this.pages = pages; }

    public String getCharacteristics() { return characteristics; }
    public void setCharacteristics(String characteristics) { this.characteristics = characteristics; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Language getLanguage() { return language; }
    public void setLanguage(Language language) { this.language = language; }
}