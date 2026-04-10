package com.epam.rd.autocode.spring.project.dto;

// Анотація для перевірки, що поле не є порожнім (не null і не "")
import jakarta.validation.constraints.NotBlank;

/**
 * DTO (Data Transfer Object) для запиту аутентифікації
 * Використовується при логіні користувача
 */
public class AuthRequest {

    // Email користувача (обов'язкове поле)
    @NotBlank
    private String email;

    // Пароль користувача (обов'язкове поле)
    @NotBlank
    private String password;

    /**
     * Отримати email користувача
     */
    public String getEmail() {
        return email;
    }

    /**
     * Встановити email користувача
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Отримати пароль користувача
     */
    public String getPassword() {
        return password;
    }

    /**
     * Встановити пароль користувача
     */
    public void setPassword(String password) {
        this.password = password;
    }
}