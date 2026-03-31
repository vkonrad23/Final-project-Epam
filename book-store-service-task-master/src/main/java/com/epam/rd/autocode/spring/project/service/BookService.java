package com.epam.rd.autocode.spring.project.service;

import com.epam.rd.autocode.spring.project.dto.BookDTO;

import java.util.List;

public interface BookService {

    List<BookDTO> getAllBooks();

    List<BookDTO> searchBooks(String term, int page, int size, String sortBy);

    BookDTO getBookByName(String name);

    BookDTO updateBookByName(String name, BookDTO book);

    void deleteBookByName(String name);

    BookDTO addBook(BookDTO book);
}
