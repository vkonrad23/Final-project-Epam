package com.epam.rd.autocode.spring.project.dto;

import com.epam.rd.autocode.spring.project.validation.StrongPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public class ClientDTO {
    @NotBlank
    @Email
    private String email;
    @NotBlank
    @StrongPassword
    private String password;
    @NotBlank
    private String name;
    @NotNull
    @PositiveOrZero
    private BigDecimal balance;

    public ClientDTO() {
    }

    public ClientDTO(String email, String password, String name, BigDecimal balance) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.balance = balance;
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }
}
