package com.epam.rd.autocode.spring.project.dto;

import com.epam.rd.autocode.spring.project.model.enums.AgeGroup;
import com.epam.rd.autocode.spring.project.model.enums.Language;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public class BookDTO {
    @NotBlank
    private String name;
    @NotBlank
    private String genre;
    @NotNull
    private AgeGroup ageGroup;
    @NotNull
    @Positive
    private BigDecimal price;
    @NotNull
    private LocalDate publicationDate;
    @NotBlank
    private String author;
    @NotNull
    @Positive
    private Integer pages;
    private String characteristics;
    private String description;
    @NotNull
    private Language language;

    public BookDTO() {
    }

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
