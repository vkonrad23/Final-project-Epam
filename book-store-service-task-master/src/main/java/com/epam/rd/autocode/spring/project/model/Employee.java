package com.epam.rd.autocode.spring.project.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "employees")
public class Employee extends User {
    private LocalDate birthDate;
    private String phone;

    public Employee() {
    }

    public Employee(Long id, String email, String password, String name, LocalDate birthDate, String phone) {
        super(id, email, password, name);
        this.birthDate = birthDate;
        this.phone = phone;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
