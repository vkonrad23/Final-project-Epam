/** This file defines core Java types and behavior for this feature/module. */
package com.epam.rd.autocode.spring.project.exception;

/**
 * Кастомний виняток (Exception), який використовується,
 * коли об'єкт вже існує в системі
 * (наприклад: користувач з таким email вже зареєстрований)
 */
public class AlreadyExistException extends RuntimeException {

    /**
     * Конструктор, який приймає повідомлення про помилку
     */
    public AlreadyExistException(String message) {
        // Передаємо повідомлення в батьківський клас RuntimeException
        super(message);
    }
}