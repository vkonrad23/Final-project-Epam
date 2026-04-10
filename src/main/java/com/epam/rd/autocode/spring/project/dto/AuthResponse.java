package com.epam.rd.autocode.spring.project.dto;

/**
 * DTO (Data Transfer Object) для відповіді після аутентифікації
 * Містить JWT токен, який повертається клієнту після успішного логіну
 */
public class AuthResponse {

    // JWT токен, який буде використовуватись для подальших запитів
    private String token;

    /**
     * Конструктор для створення об'єкта з токеном
     */
    public AuthResponse(String token) {
        this.token = token;
    }

    /**
     * Отримати JWT токен
     */
    public String getToken() {
        return token;
    }
}