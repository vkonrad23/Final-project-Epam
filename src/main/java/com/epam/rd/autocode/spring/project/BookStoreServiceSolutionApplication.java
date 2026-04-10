/** Цей файл визначає основні типи та поведінку для цього модуля */
package com.epam.rd.autocode.spring.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Головний клас застосунку (точка входу в програму)
 * Запускає Spring Boot додаток
 */
@SpringBootApplication
public class BookStoreServiceSolutionApplication {

    /**
     * Головний метод (entry point)
     * Саме з нього починається виконання програми
     *
     * @param args аргументи командного рядка
     */
    public static void main(String[] args) {

        // Запускає Spring Boot:
        // - створює контекст (ApplicationContext)
        // - підключає всі біни (Controller, Service, Repository)
        // - запускає вбудований сервер (Tomcat)
        SpringApplication.run(BookStoreServiceSolutionApplication.class, args);
    }
}