package com.epam.rd.autocode.spring.project.service.impl;

import com.epam.rd.autocode.spring.project.dto.BookDTO;
import com.epam.rd.autocode.spring.project.model.Book;
import com.epam.rd.autocode.spring.project.repo.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {
    @Mock
    private BookRepository bookRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    private BookServiceImpl bookService;

    @BeforeEach
    void setUp() {
        bookService = new BookServiceImpl(bookRepository, modelMapper);
    }

    @Test
    void getBookByNameReturnsBook() {
        Book book = new Book();
        book.setName("Dune");
        book.setPrice(BigDecimal.TEN);
        when(bookRepository.findByNameIgnoreCase("Dune")).thenReturn(Optional.of(book));

        BookDTO result = bookService.getBookByName("Dune");

        assertEquals("Dune", result.getName());
    }

    @Test
    void searchBooksUsesPagingAndSorting() {
        Book book = new Book();
        book.setName("Dune");
        when(bookRepository.search(eq("du"), eq(PageRequest.of(0, 5, Sort.by("name")))))
                .thenReturn(new PageImpl<>(List.of(book)));

        List<BookDTO> result = bookService.searchBooks("du", 0, 5, "name");

        assertEquals(1, result.size());
        verify(bookRepository).search(eq("du"), eq(PageRequest.of(0, 5, Sort.by("name"))));
    }

    @Test
    void addBookSavesMappedEntity() {
        BookDTO dto = new BookDTO();
        dto.setName("Clean Code");

        when(bookRepository.findByNameIgnoreCase("Clean Code")).thenReturn(Optional.empty());
        when(bookRepository.save(any(Book.class))).thenAnswer(invocation -> invocation.getArgument(0));

        BookDTO result = bookService.addBook(dto);

        assertEquals("Clean Code", result.getName());
    }
}