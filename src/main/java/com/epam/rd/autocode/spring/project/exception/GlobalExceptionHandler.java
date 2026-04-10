/** Цей файл визначає основні типи та поведінку для цього модуля */
package com.epam.rd.autocode.spring.project.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Глобальний обробник винятків.
 * Перехоплює всі помилки в контролерах та формує єдиний формат відповіді.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Джерело повідомлень для підтримки різних мов (локалізація)
    private final MessageSource messageSource;

    /**
     * Конструктор для ініціалізації MessageSource
     */
    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * Обробка помилки "об'єкт не знайдено"
     * Повертає статус 404
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(NotFoundException ex) {
        return buildError(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    /**
     * Обробка помилки "об'єкт вже існує"
     * Повертає статус 409
     */
    @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<Map<String, Object>> handleAlreadyExists(AlreadyExistException ex) {
        return buildError(HttpStatus.CONFLICT, ex.getMessage());
    }

    /**
     * Обробка помилок валідації (анотація @Valid)
     * Повертає статус 400 та деталі помилок по кожному полю
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex) {

        // Мапа для збереження помилок: поле → повідомлення
        Map<String, String> validation = new HashMap<>();

        // Проходимо по всіх помилках валідації
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            validation.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        // Формуємо тіло відповіді
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now()); // час виникнення помилки
        body.put("status", HttpStatus.BAD_REQUEST.value()); // код 400
        body.put(
                "error",
                messageSource.getMessage(
                        "error.validation", // ключ повідомлення
                        null,
                        LocaleContextHolder.getLocale() // мова користувача
                )
        );
        body.put("details", validation); // деталі помилок

        return ResponseEntity.badRequest().body(body);
    }

    /**
     * Обробка помилок автентифікації (невірний логін або пароль)
     * Повертає статус 401
     */
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Map<String, Object>> handleAuthenticationError(AuthenticationException ex) {
        return buildError(
                HttpStatus.UNAUTHORIZED,
                messageSource.getMessage(
                        "error.auth.invalidCredentials",
                        null,
                        LocaleContextHolder.getLocale()
                )
        );
    }

    /**
     * Обробка всіх інших (непередбачених) помилок
     * Повертає статус 500
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneralError(Exception ex) {
        return buildError(
                HttpStatus.INTERNAL_SERVER_ERROR,
                messageSource.getMessage(
                        "error.internal",
                        null,
                        LocaleContextHolder.getLocale()
                )
        );
    }

    /**
     * Допоміжний метод для створення відповіді про помилку
     */
    private ResponseEntity<Map<String, Object>> buildError(HttpStatus status, String message) {

        // Формуємо тіло відповіді
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now()); // час помилки
        body.put("status", status.value());         // HTTP статус
        body.put("error", message);                 // текст помилки

        return ResponseEntity.status(status).body(body);
    }
}