package com.epam.rd.autocode.spring.project.service.impl;

import com.epam.rd.autocode.spring.project.dto.BookDTO;
import com.epam.rd.autocode.spring.project.exception.AlreadyExistException;
import com.epam.rd.autocode.spring.project.exception.NotFoundException;
import com.epam.rd.autocode.spring.project.model.Book;
import com.epam.rd.autocode.spring.project.repo.BookRepository;
import com.epam.rd.autocode.spring.project.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    private static final Logger log = LoggerFactory.getLogger(BookServiceImpl.class);

    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;

    public BookServiceImpl(BookRepository bookRepository, ModelMapper modelMapper) {
        this.bookRepository = bookRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<BookDTO> getAllBooks() {
        log.info("Fetching all books");
        return bookRepository.findAll().stream()
                .map(book -> modelMapper.map(book, BookDTO.class))
                .toList();
    }

    @Override
    public List<BookDTO> searchBooks(String term, int page, int size, String sortBy) {
        log.debug("Searching books, term={}, page={}, size={}, sortBy={}", term, page, size, sortBy);
        return bookRepository.search(term, PageRequest.of(page, size, Sort.by(sortBy))).stream()
                .map(book -> modelMapper.map(book, BookDTO.class))
                .toList();
    }

    @Override
    public BookDTO getBookByName(String name) {
        Book book = bookRepository.findByNameIgnoreCase(name)
                .orElseThrow(() -> new NotFoundException("Book not found: " + name));
        return modelMapper.map(book, BookDTO.class);
    }

    @Override
    public BookDTO updateBookByName(String name, BookDTO bookDTO) {
        Book book = bookRepository.findByNameIgnoreCase(name)
                .orElseThrow(() -> new NotFoundException("Book not found: " + name));
        modelMapper.map(bookDTO, book);
        return modelMapper.map(bookRepository.save(book), BookDTO.class);
    }

    @Override
    public void deleteBookByName(String name) {
        Book book = bookRepository.findByNameIgnoreCase(name)
                .orElseThrow(() -> new NotFoundException("Book not found: " + name));
        bookRepository.delete(book);
    }

    @Override
    public BookDTO addBook(BookDTO bookDTO) {
        bookRepository.findByNameIgnoreCase(bookDTO.getName())
                .ifPresent(book -> {
                    throw new AlreadyExistException("Book already exists: " + bookDTO.getName());
                });
        Book book = modelMapper.map(bookDTO, Book.class);
        return modelMapper.map(bookRepository.save(book), BookDTO.class);
    }
}
