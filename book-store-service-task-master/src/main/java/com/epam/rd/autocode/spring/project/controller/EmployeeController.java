package com.epam.rd.autocode.spring.project.controller;

import com.epam.rd.autocode.spring.project.dto.EmployeeDTO;
import com.epam.rd.autocode.spring.project.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<EmployeeDTO> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{email}")
    @PreAuthorize("hasRole('ADMIN')")
    public EmployeeDTO getEmployeeByEmail(@PathVariable String email) {
        return employeeService.getEmployeeByEmail(email);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EmployeeDTO> addEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.addEmployee(employeeDTO));
    }

    @PutMapping("/{email}")
    @PreAuthorize("hasRole('ADMIN')")
    public EmployeeDTO updateEmployee(@PathVariable String email, @Valid @RequestBody EmployeeDTO employeeDTO) {
        return employeeService.updateEmployeeByEmail(email, employeeDTO);
    }

    @DeleteMapping("/{email}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteEmployee(@PathVariable String email) {
        employeeService.deleteEmployeeByEmail(email);
        return ResponseEntity.noContent().build();
    }
}
