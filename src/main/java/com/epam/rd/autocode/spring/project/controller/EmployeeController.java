/**
 * Цей файл визначає основні Java-типи та поведінку для цього модуля/функціоналу.
 */
package com.epam.rd.autocode.spring.project.controller;

import com.epam.rd.autocode.spring.project.dto.EmployeeDTO;
import com.epam.rd.autocode.spring.project.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
/**
 * Клас EmployeeController відповідає за обробку HTTP-запитів,
 * пов’язаних із працівниками (CRUD операції).
 */
public class EmployeeController {

    // Сервіс для бізнес-логіки працівників
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    /**
     * Отримує список усіх працівників.
     * Доступ дозволено тільки для ADMIN.
     *
     * @return список EmployeeDTO
     */
    public List<EmployeeDTO> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('ADMIN')")
    /**
     * Отримує працівника за email.
     * Доступ тільки для ADMIN.
     *
     * @param email email працівника
     * @return EmployeeDTO
     */
    public EmployeeDTO getEmployeeByEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return employeeService.getEmployeeByEmail(email);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    /**
     * Додає нового працівника.
     * Доступ тільки для ADMIN.
     *
     * @param employeeDTO дані працівника
     * @return створений працівник зі статусом 201 (Created)
     */
    public ResponseEntity<EmployeeDTO> addEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(employeeService.addEmployee(employeeDTO));
    }

    @PutMapping("/me")
    @PreAuthorize("hasRole('ADMIN')")
    /**
     * Оновлює дані працівника за email.
     * Доступ тільки для ADMIN.
     *
     * @param email email працівника
     * @param employeeDTO нові дані
     * @return оновлений працівник
     */
    public EmployeeDTO updateEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return employeeService.updateEmployeeByEmail(email, employeeDTO);
    }

    @DeleteMapping("/me")
    @PreAuthorize("hasRole('ADMIN')")
    /**
     * Видаляє працівника за email.
     * Доступ тільки для ADMIN.
     *
     * @param email email працівника
     * @return HTTP 204 (No Content)
     */
    public ResponseEntity<Void> deleteEmployee() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        employeeService.deleteEmployeeByEmail(email);
        return ResponseEntity.noContent().build();
    }
}