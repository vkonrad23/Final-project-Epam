/** Цей файл визначає основні типи та поведінку для цього модуля */
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

/**
 * Сервіс для роботи з книгами
 * Реалізує бізнес-логіку (між Controller і Repository)
 */
@Service
public class BookServiceImpl implements BookService {

    // Логер для відслідковування дій у системі
    private static final Logger log =
            LoggerFactory.getLogger(BookServiceImpl.class);

    // Репозиторій для роботи з базою даних
    private final BookRepository bookRepository;

    // Mapper для перетворення Entity ↔ DTO
    private final ModelMapper modelMapper;

    /**
     * Конструктор для ініціалізації залежностей
     */
    public BookServiceImpl(BookRepository bookRepository,
                           ModelMapper modelMapper) {
        this.bookRepository = bookRepository;
        this.modelMapper = modelMapper;
    }

    /**
     * Отримати всі книги
     */
    @Override
    public List<BookDTO> getAllBooks() {

        // Логуємо дію
        log.info("Отримання всіх книг");

        // Отримуємо всі книги з БД → конвертуємо в DTO
        return bookRepository.findAll().stream()
                .map(book -> modelMapper.map(book, BookDTO.class))
                .toList();
    }

    /**
     * Пошук книг за ключовим словом + пагінація + сортування
     */
    @Override
    public List<BookDTO> searchBooks(String term, int page, int size, String sortBy) {

        // Детальний лог (debug)
        log.debug("Пошук книг, term={}, page={}, size={}, sortBy={}",
                term, page, size, sortBy);

        return bookRepository.search(
                        term,
                        PageRequest.of(page, size, Sort.by(sortBy))
                )
                .stream()
                .map(book -> modelMapper.map(book, BookDTO.class))
                .toList();
    }

    /**
     * Отримати книгу за назвою
     */
    @Override
    public BookDTO getBookByName(String name) {

        // Шукаємо книгу або кидаємо помилку
        Book book = bookRepository.findByNameIgnoreCase(name)
                .orElseThrow(() ->
                        new NotFoundException("Book not found: " + name));

        return modelMapper.map(book, BookDTO.class);
    }

    /**
     * Оновити книгу за назвою
     */
    @Override
    public BookDTO updateBookByName(String name, BookDTO bookDTO) {

        // Знаходимо існуючу книгу
        Book book = bookRepository.findByNameIgnoreCase(name)
                .orElseThrow(() ->
                        new NotFoundException("Book not found: " + name));

        // Оновлюємо поля книги з DTO
        modelMapper.map(bookDTO, book);

        // Зберігаємо в БД та повертаємо DTO
        return modelMapper.map(bookRepository.save(book), BookDTO.class);
    }

    /**
     * Видалити книгу за назвою
     */
    @Override
    public void deleteBookByName(String name) {

        // Знаходимо книгу
        Book book = bookRepository.findByNameIgnoreCase(name)
                .orElseThrow(() ->
                        new NotFoundException("Book not found: " + name));

        // Видаляємо з БД
        bookRepository.delete(book);
    }

    /**
     * Додати нову книгу
     */
    @Override
    public BookDTO addBook(BookDTO bookDTO) {

        // Перевіряємо, чи вже існує книга з такою назвою
        bookRepository.findByNameIgnoreCase(bookDTO.getName())
                .ifPresent(book -> {
                    throw new AlreadyExistException(
                            "Book already exists: " + bookDTO.getName()
                    );
                });

        // Перетворюємо DTO → Entity
        Book book = modelMapper.map(bookDTO, Book.class);

        // Зберігаємо та повертаємо DTO
        return modelMapper.map(bookRepository.save(book), BookDTO.class);
    }
}