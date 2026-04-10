/** Цей файл визначає основні типи та поведінку для цього модуля */
package com.epam.rd.autocode.spring.project.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDate;

/**
 * Сутність (Entity), яка представляє співробітника в системі
 * Наслідує базовий клас User (id, email, пароль, ім'я)
 */
@Entity
@Table(name = "employees")
public class Employee extends User {

    // Дата народження співробітника
    private LocalDate birthDate;

    // Номер телефону співробітника
    private String phone;

    /**
     * Порожній конструктор (обов'язковий для JPA)
     */
    public Employee() {
    }

    /**
     * Конструктор з усіма полями
     * Частина полів передається в батьківський клас User
     */
    public Employee(Long id, String email, String password, String name,
                    LocalDate birthDate, String phone) {
        super(id, email, password, name); // виклик конструктора User
        this.birthDate = birthDate;
        this.phone = phone;
    }

    /**
     * Отримати дату народження співробітника
     */
    public LocalDate getBirthDate() {
        return birthDate;
    }

    /**
     * Встановити дату народження співробітника
     */
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * Отримати номер телефону співробітника
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Встановити номер телефону співробітника
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }
}