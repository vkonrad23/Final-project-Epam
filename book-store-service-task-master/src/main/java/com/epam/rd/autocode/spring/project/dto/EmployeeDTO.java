package com.epam.rd.autocode.spring.project.dto;

import com.epam.rd.autocode.spring.project.validation.StrongPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class EmployeeDTO {
    @NotBlank
    @Email
    private String email;
    @NotBlank
    @StrongPassword
    private String password;
    @NotBlank
    private String name;
    @NotBlank
    private String phone;
    @NotNull
    private LocalDate birthDate;

    public EmployeeDTO() {
    }

    public EmployeeDTO(String email, String password, String name, String phone, LocalDate birthDate) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.birthDate = birthDate;
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public LocalDate getBirthDate() { return birthDate; }
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }
}
