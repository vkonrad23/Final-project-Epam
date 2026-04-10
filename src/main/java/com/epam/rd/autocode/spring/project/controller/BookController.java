/**
 * Цей файл визначає основні Java-типи та поведінку для цього модуля/функціоналу.
 */
package com.epam.rd.autocode.spring.project.controller;

import com.epam.rd.autocode.spring.project.dto.BookDTO;
import com.epam.rd.autocode.spring.project.service.BookService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
/**
 * Клас BookController відповідає за обробку HTTP-запитів,
 * пов’язаних із книгами (CRUD операції).
 */
public class BookController {

    // Сервіс для роботи з бізнес-логікою книг
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('EMPLOYEE','CUSTOMER','ADMIN')")
    /**
     * Отримує список усіх книг.
     *
     * @return список BookDTO
     */
    public List<BookDTO> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('EMPLOYEE','CUSTOMER','ADMIN')")
    /**
     * Пошук книг із пагінацією та сортуванням.
     *
     * @param term   пошуковий запит
     * @param page   номер сторінки
     * @param size   кількість елементів на сторінці
     * @param sortBy поле для сортування
     * @return список знайдених книг
     */
    public List<BookDTO> searchBooks(
            @RequestParam(defaultValue = "") String term,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy) {

        return bookService.searchBooks(term, page, size, sortBy);
    }

    @GetMapping("/{name}")
    @PreAuthorize("hasAnyRole('EMPLOYEE','CUSTOMER','ADMIN')")
    /**
     * Отримує книгу за її назвою.
     *
     * @param name назва книги
     * @return BookDTO
     */
    public BookDTO getBookByName(@PathVariable String name) {
        return bookService.getBookByName(name);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('EMPLOYEE','ADMIN')")
    /**
     * Додає нову книгу.
     *
     * @param bookDTO дані книги
     * @return створена книга з HTTP статусом 201
     */
    public ResponseEntity<BookDTO> addBook(@Valid @RequestBody BookDTO bookDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(bookService.addBook(bookDTO));
    }

    @PutMapping("/{name}")
    @PreAuthorize("hasAnyRole('EMPLOYEE','ADMIN')")
    /**
     * Оновлює існуючу книгу за назвою.
     *
     * @param name назва книги
     * @param bookDTO нові дані
     * @return оновлена книга
     */
    public BookDTO updateBook(@PathVariable String name,
                             @Valid @RequestBody BookDTO bookDTO) {
        return bookService.updateBookByName(name, bookDTO);
    }

    @DeleteMapping("/{name}")
    @PreAuthorize("hasAnyRole('EMPLOYEE','ADMIN')")
    /**
     * Видаляє книгу за назвою.
     *
     * @param name назва книги
     * @return HTTP 204 (No Content)
     */
    public ResponseEntity<Void> deleteBook(@PathVariable String name) {
        bookService.deleteBookByName(name);
        return ResponseEntity.noContent().build();
    }
}