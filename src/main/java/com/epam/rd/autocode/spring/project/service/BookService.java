/** Цей файл визначає основні типи та поведінку для цього модуля */
package com.epam.rd.autocode.spring.project.service;

import com.epam.rd.autocode.spring.project.dto.BookDTO;

import java.util.List;

/**
 * Інтерфейс сервісу для роботи з книгами
 * Описує бізнес-операції без реалізації
 */
public interface BookService {

    /**
     * Отримати список усіх книг
     *
     * @return список книг у вигляді DTO
     */
    List<BookDTO> getAllBooks();

    /**
     * Пошук книг за ключовим словом з підтримкою пагінації та сортування
     *
     * @param term слово для пошуку (назва або автор)
     * @param page номер сторінки
     * @param size кількість елементів на сторінці
     * @param sortBy поле для сортування
     * @return список знайдених книг
     */
    List<BookDTO> searchBooks(String term, int page, int size, String sortBy);

    /**
     * Отримати книгу за назвою
     *
     * @param name назва книги
     * @return знайдена книга
     */
    BookDTO getBookByName(String name);

    /**
     * Оновити книгу за назвою
     *
     * @param name назва книги, яку потрібно оновити
     * @param book нові дані книги
     * @return оновлена книга
     */
    BookDTO updateBookByName(String name, BookDTO book);

    /**
     * Видалити книгу за назвою
     *
     * @param name назва книги
     */
    void deleteBookByName(String name);

    /**
     * Додати нову книгу
     *
     * @param book дані нової книги
     * @return створена книга
     */
    BookDTO addBook(BookDTO book);
}