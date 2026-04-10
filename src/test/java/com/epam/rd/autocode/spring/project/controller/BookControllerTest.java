package com.epam.rd.autocode.spring.project.controller;

import com.epam.rd.autocode.spring.project.dto.BookDTO;
import com.epam.rd.autocode.spring.project.service.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookControllerTest {

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    @Test
    void getAllBooksReturnsServiceResult() {
        BookDTO dune = new BookDTO();
        dune.setName("Dune");

        when(bookService.getAllBooks()).thenReturn(List.of(dune));

        List<BookDTO> result = bookController.getAllBooks();

        assertEquals(1, result.size());
        assertEquals("Dune", result.get(0).getName());
    }

    @Test
    void addBookReturnsCreatedResponse() {
        BookDTO request = new BookDTO();
        request.setName("Clean Code");

        when(bookService.addBook(request)).thenReturn(request);

        ResponseEntity<BookDTO> response = bookController.addBook(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Clean Code", response.getBody().getName());
        verify(bookService).addBook(request);
    }
}
