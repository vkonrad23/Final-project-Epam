package com.epam.rd.autocode.spring.project.repo;

import com.epam.rd.autocode.spring.project.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByNameIgnoreCase(String name);

    @Query("select b from Book b where lower(b.name) like lower(concat('%', :term, '%')) " +
            "or lower(b.author) like lower(concat('%', :term, '%'))")
    Page<Book> search(@Param("term") String term, Pageable pageable);
}
