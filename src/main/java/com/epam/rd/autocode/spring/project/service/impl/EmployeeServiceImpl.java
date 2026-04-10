/** Цей файл визначає основні типи та поведінку для цього модуля */
package com.epam.rd.autocode.spring.project.service.impl;

import com.epam.rd.autocode.spring.project.dto.EmployeeDTO;
import com.epam.rd.autocode.spring.project.exception.AlreadyExistException;
import com.epam.rd.autocode.spring.project.exception.NotFoundException;
import com.epam.rd.autocode.spring.project.model.Employee;
import com.epam.rd.autocode.spring.project.repo.EmployeeRepository;
import com.epam.rd.autocode.spring.project.service.EmployeeService;

import org.modelmapper.ModelMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сервіс для роботи зі співробітниками
 * Містить бізнес-логіку між Controller і Repository
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    // Логер для відслідковування подій
    private static final Logger log =
            LoggerFactory.getLogger(EmployeeServiceImpl.class);

    // Репозиторій для роботи з БД
    private final EmployeeRepository employeeRepository;

    // Mapper для перетворення Entity ↔ DTO
    private final ModelMapper modelMapper;

    // Енкодер для шифрування паролів
    private final PasswordEncoder passwordEncoder;

    /**
     * Конструктор для ініціалізації залежностей
     */
    public EmployeeServiceImpl(EmployeeRepository employeeRepository,
                               ModelMapper modelMapper,
                               PasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Отримати список усіх співробітників
     */
    @Override
    public List<EmployeeDTO> getAllEmployees() {
        return employeeRepository.findAll().stream()
                .map(employee -> modelMapper.map(employee, EmployeeDTO.class))
                .toList();
    }

    /**
     * Отримати співробітника за email
     */
    @Override
    public EmployeeDTO getEmployeeByEmail(String email) {

        // Шукаємо співробітника або кидаємо помилку
        Employee employee = employeeRepository.findByEmail(email)
                .orElseThrow(() ->
                        new NotFoundException("Employee not found: " + email));

        return modelMapper.map(employee, EmployeeDTO.class);
    }

    /**
     * Оновити співробітника за email
     */
    @Override
    public EmployeeDTO updateEmployeeByEmail(String email, EmployeeDTO employeeDTO) {

        // Знаходимо співробітника
        Employee employee = employeeRepository.findByEmail(email)
                .orElseThrow(() ->
                        new NotFoundException("Employee not found: " + email));

        // Оновлюємо дані з DTO
        modelMapper.map(employeeDTO, employee);

        // Обов'язково шифруємо пароль
        employee.setPassword(passwordEncoder.encode(employeeDTO.getPassword()));

        // Зберігаємо та повертаємо DTO
        return modelMapper.map(employeeRepository.save(employee), EmployeeDTO.class);
    }

    /**
     * Видалити співробітника за email
     */
    @Override
    public void deleteEmployeeByEmail(String email) {

        // Знаходимо співробітника
        Employee employee = employeeRepository.findByEmail(email)
                .orElseThrow(() ->
                        new NotFoundException("Employee not found: " + email));

        // Видаляємо з БД
        employeeRepository.delete(employee);
    }

    /**
     * Додати нового співробітника
     */
    @Override
    public EmployeeDTO addEmployee(EmployeeDTO employeeDTO) {

        // Логуємо реєстрацію
        log.info("Реєстрація співробітника: {}", employeeDTO.getEmail());

        // Перевіряємо, чи вже існує такий email
        if (employeeRepository.existsByEmail(employeeDTO.getEmail())) {
            throw new AlreadyExistException(
                    "Employee already exists: " + employeeDTO.getEmail()
            );
        }

        // Перетворюємо DTO → Entity
        Employee employee = modelMapper.map(employeeDTO, Employee.class);

        // Шифруємо пароль перед збереженням
        employee.setPassword(passwordEncoder.encode(employeeDTO.getPassword()));

        // Зберігаємо та повертаємо DTO
        return modelMapper.map(employeeRepository.save(employee), EmployeeDTO.class);
    }
}