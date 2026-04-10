/** Цей файл визначає основні типи та поведінку для цього модуля */
package com.epam.rd.autocode.spring.project.security;

import com.epam.rd.autocode.spring.project.model.Client;
import com.epam.rd.autocode.spring.project.model.Employee;
import com.epam.rd.autocode.spring.project.repo.ClientRepository;
import com.epam.rd.autocode.spring.project.repo.EmployeeRepository;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сервіс для завантаження користувача під час аутентифікації
 * Використовується Spring Security для перевірки логіну
 */
@Service
public class AppUserDetailsService implements UserDetailsService {

    // Репозиторій для співробітників
    private final EmployeeRepository employeeRepository;

    // Репозиторій для клієнтів
    private final ClientRepository clientRepository;

    /**
     * Конструктор для ініціалізації репозиторіїв
     */
    public AppUserDetailsService(EmployeeRepository employeeRepository,
                                 ClientRepository clientRepository) {
        this.employeeRepository = employeeRepository;
        this.clientRepository = clientRepository;
    }

    /**
     * Метод, який викликає Spring Security під час логіну
     * username = email користувача
     */
    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        // Спочатку шукаємо серед співробітників
        Employee employee = employeeRepository.findByEmail(username).orElse(null);

        if (employee != null) {
            // Якщо знайдено співробітника → повертаємо його з роллю EMPLOYEE
            return new User(
                    employee.getEmail(),
                    employee.getPassword(),
                    List.of(new SimpleGrantedAuthority("ROLE_EMPLOYEE"))
            );
        }

        // Якщо не знайдено, шукаємо серед клієнтів
        Client client = clientRepository.findByEmail(username).orElse(null);

        if (client != null) {
            // Якщо знайдено клієнта → повертаємо його з роллю CUSTOMER
            return new User(
                    client.getEmail(),
                    client.getPassword(),
                    List.of(new SimpleGrantedAuthority("ROLE_CUSTOMER"))
            );
        }

        // Якщо користувач не знайдений ніде → кидаємо помилку
        throw new UsernameNotFoundException("Користувача не знайдено: " + username);
    }
}