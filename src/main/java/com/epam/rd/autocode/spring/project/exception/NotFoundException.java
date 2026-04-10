/** Цей файл визначає основні типи та поведінку для цього модуля */
package com.epam.rd.autocode.spring.project.exception;

/**
 * Кастомний виняток, який використовується,
 * коли об'єкт не знайдено в системі
 * (наприклад: книга, клієнт або замовлення)
 */
public class NotFoundException extends RuntimeException {

    /**
     * Конструктор, який приймає повідомлення про помилку
     */
    public NotFoundException(String message) {
        // Передаємо повідомлення в батьківський клас RuntimeException
        super(message);
    }
}