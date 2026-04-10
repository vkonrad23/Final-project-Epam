/** This file defines core Java types and behavior for this feature/module. */
package com.epam.rd.autocode.spring.project.dto;

// Кастомна валідація для складного пароля
import java.time.LocalDate;

import com.epam.rd.autocode.spring.project.validation.StrongPassword;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
        
/**
 * DTO для передачі даних співробітника (Employee)
 * Використовується при реєстрації, оновленні або отриманні співробітника
 */
public class EmployeeDTO {

    // Email співробітника (обов'язковий + валідний формат)
    @NotBlank
    @Email
    private String email;

    // Пароль (обов'язковий + кастомна перевірка складності)                    
    @NotBlank
    @StrongPassword
    private String password;

    // Ім'я співробітника (обов'язкове поле)
    @NotBlank
    private String name;

    // Номер телефону (обов'язкове поле)
    @NotBlank
    private String phone;

    // Дата народження (обов'язкове поле)
    @NotNull
    private LocalDate birthDate;

    /**
     * Порожній конструктор (потрібен для Spring / Jackson)
     */
    public EmployeeDTO() {
    }

    /**
     * Конструктор з усіма полями
     */
    public EmployeeDTO(String email, String password, String name, String phone, LocalDate birthDate) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.birthDate = birthDate;
    }

    // --- Геттери та сеттери ---

    public String getEmail() { 
        return email; 
    }

    public void setEmail(String email) { 
        this.email = email; 
    }

    public String getPassword() { 
        return password; 
    }

    public void setPassword(String password) { 
        this.password = password; 
    }

    public String getName() { 
        return name; 
    }

    public void setName(String name) { 
        this.name = name; 
    }

    public String getPhone() { 
        return phone; 
    }

    public void setPhone(String phone) { 
        this.phone = phone; 
    }

    public LocalDate getBirthDate() { 
        return birthDate; 
    }

    public void setBirthDate(LocalDate birthDate) { 
        this.birthDate = birthDate; 
    }
}