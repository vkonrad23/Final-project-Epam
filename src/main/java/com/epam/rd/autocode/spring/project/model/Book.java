/** Цей файл визначає основні типи та поведінку для цього модуля */
package com.epam.rd.autocode.spring.project.model;

import com.epam.rd.autocode.spring.project.model.enums.AgeGroup;
import com.epam.rd.autocode.spring.project.model.enums.Language;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDate; 

/**
 * Сутність (Entity), яка представляє книгу в базі даних
 */
@Entity
@Table(name = "books")
public class Book {

    // Первинний ключ (ID книги), генерується автоматично
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Назва книги
    private String name;

    // Жанр книги
    private String genre;

    // Вікова категорія (зберігається як рядок у БД)
    @Enumerated(EnumType.STRING)
    @Column(name = "age_group")
    private AgeGroup ageGroup;

    // Ціна книги
    private BigDecimal price;

    // Дата публікації (у БД колонка називається publication_year)
    @Column(name = "publication_year")
    private LocalDate publicationDate;

    // Автор книги
    private String author;

    // Кількість сторінок (у БД колонка має іншу назву)
    @Column(name = "number_of_pages")
    private Integer pages;

    // Додаткові характеристики книги
    private String characteristics;

    // Опис книги
    private String description;

    // Мова книги (зберігається як рядок у БД)
    @Enumerated(EnumType.STRING)
    private Language language;

    /**
     * Порожній конструктор (обов'язковий для JPA)
     */
    public Book() {
    }

    /**
     * Конструктор з усіма полями
     */
    public Book(Long id, String name, String genre, AgeGroup ageGroup, BigDecimal price, LocalDate publicationDate,
                String author, Integer pages, String characteristics, String description, Language language) {
        this.id = id;
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

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

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