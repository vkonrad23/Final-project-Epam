/** Цей файл визначає основні типи та поведінку для цього модуля */
package com.epam.rd.autocode.spring.project.repo;

import com.epam.rd.autocode.spring.project.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * Репозиторій для роботи з сутністю Book
 * Наслідує JpaRepository і надає готові CRUD-операції:
 * save, findById, findAll, delete тощо
 */
public interface BookRepository extends JpaRepository<Book, Long> {

    /**
     * Знайти книгу за назвою (без врахування регістру)
     * Наприклад: "clean code" = "Clean Code"
     */
    Optional<Book> findByNameIgnoreCase(String name);

    /**
     * Пошук книг за ключовим словом (у назві або авторі)
     * Використовує JPQL-запит
     *
     * lower(...) → ігнорує регістр
     * like '%term%' → частковий збіг (містить слово)
     *
     * @param term слово для пошуку
     * @param pageable параметри пагінації (сторінка, розмір, сортування)
     * @return сторінка (Page) знайдених книг
     */
    @Query("select b from Book b where lower(b.name) like lower(concat('%', :term, '%')) " +
            "or lower(b.author) like lower(concat('%', :term, '%'))")
    Page<Book> search(@Param("term") String term, Pageable pageable);
}