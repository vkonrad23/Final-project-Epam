package com.epam.rd.autocode.spring.project.controller;

import com.epam.rd.autocode.spring.project.dto.BookDTO;
import com.epam.rd.autocode.spring.project.service.BookService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('EMPLOYEE','CUSTOMER','ADMIN')")
    public List<BookDTO> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('EMPLOYEE','CUSTOMER','ADMIN')")
    public List<BookDTO> searchBooks(
            @RequestParam(defaultValue = "") String term,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy) {
        return bookService.searchBooks(term, page, size, sortBy);
    }

    @GetMapping("/{name}")
    @PreAuthorize("hasAnyRole('EMPLOYEE','CUSTOMER','ADMIN')")
    public BookDTO getBookByName(@PathVariable String name) {
        return bookService.getBookByName(name);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('EMPLOYEE','ADMIN')")
    public ResponseEntity<BookDTO> addBook(@Valid @RequestBody BookDTO bookDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.addBook(bookDTO));
    }

    @PutMapping("/{name}")
    @PreAuthorize("hasAnyRole('EMPLOYEE','ADMIN')")
    public BookDTO updateBook(@PathVariable String name, @Valid @RequestBody BookDTO bookDTO) {
        return bookService.updateBookByName(name, bookDTO);
    }

    @DeleteMapping("/{name}")
    @PreAuthorize("hasAnyRole('EMPLOYEE','ADMIN')")
    public ResponseEntity<Void> deleteBook(@PathVariable String name) {
        bookService.deleteBookByName(name);
        return ResponseEntity.noContent().build();
    }
}
