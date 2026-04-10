/** Цей файл визначає основні типи та поведінку для цього модуля */
package com.epam.rd.autocode.spring.project.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

/**
 * Базовий клас для всіх користувачів системи
 * Не є окремою таблицею, але його поля наслідуються іншими сутностями
 */
@MappedSuperclass
public class User {

    // Первинний ключ (ID користувача)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Email користувача (обов'язковий та унікальний)
    @Column(nullable = false, unique = true)
    private String email;

    // Пароль користувача (обов'язковий)
    @Column(nullable = false)
    private String password;

    // Ім'я користувача (обов'язкове)
    @Column(nullable = false)
    private String name;

    /**
     * Порожній конструктор (обов'язковий для JPA)
     */
    public User() {
    }

    /**
     * Конструктор з усіма полями
     */
    public User(Long id, String email, String password, String name) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
    }

    /**
     * Отримати ID користувача
     */
    public Long getId() {
        return id;
    }

    /**
     * Встановити ID користувача
     */
    public void setId(Long id) {
        this.id = id;
    }

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

    /**
     * Отримати ім'я користувача
     */
    public String getName() {
        return name;
    }

    /**
     * Встановити ім'я користувача
     */
    public void setName(String name) {
        this.name = name;
    }
}