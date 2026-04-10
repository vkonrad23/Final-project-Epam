package com.epam.rd.autocode.spring.project.dto;

import com.epam.rd.autocode.spring.project.validation.StrongPassword;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO для запиту реєстрації нового користувача (клієнта)
 */
public class RegisterRequest {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @StrongPassword
    private String password;

    @NotBlank
    private String name;

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
}
