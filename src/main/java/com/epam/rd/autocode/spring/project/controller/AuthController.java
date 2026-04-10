package com.epam.rd.autocode.spring.project.controller;

// Імпорт DTO для запиту (email + password)
import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.epam.rd.autocode.spring.project.dto.AuthRequest;
import com.epam.rd.autocode.spring.project.dto.AuthResponse;
import com.epam.rd.autocode.spring.project.dto.ChangePasswordRequest;
import com.epam.rd.autocode.spring.project.dto.RegisterRequest;
import com.epam.rd.autocode.spring.project.exception.AlreadyExistException;
import com.epam.rd.autocode.spring.project.model.Client;
import com.epam.rd.autocode.spring.project.repo.ClientRepository;
import com.epam.rd.autocode.spring.project.repo.EmployeeRepository;
import com.epam.rd.autocode.spring.project.security.JwtService;

import jakarta.validation.Valid;

/**
 * Контролер для обробки аутентифікації користувачів
 * (логін та отримання JWT токена)
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    // Менеджер, який перевіряє логін/пароль
    private final AuthenticationManager authenticationManager;

    // Сервіс для створення JWT токена
    private final JwtService jwtService;
    private final ClientRepository clientRepository;
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Конструктор для ініціалізації залежностей
     */
    public AuthController(AuthenticationManager authenticationManager,
                          JwtService jwtService,
                          ClientRepository clientRepository,
                          EmployeeRepository employeeRepository,
                          PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.clientRepository = clientRepository;
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Ендпоінт для логіну користувача
     * POST /api/auth/login
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest authRequest) {

        // Виконуємо аутентифікацію:
        // передаємо email і password у Spring Security
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getEmail(),   // email користувача
                        authRequest.getPassword() // пароль користувача
                )
        );

        // Отримуємо інформацію про користувача після успішного логіну
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // Генеруємо JWT токен для цього користувача
        String token = jwtService.generateToken(userDetails);

        // Повертаємо токен у відповіді
        return ResponseEntity.ok(new AuthResponse(token));
    }

    /**
     * Ендпоінт для реєстрації нового клієнта
     * POST /api/auth/register
     */
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {

        String email = registerRequest.getEmail();

        if (clientRepository.existsByEmail(email) || employeeRepository.existsByEmail(email)) {
            throw new AlreadyExistException("User already exists: " + email);
        }

        Client client = new Client();
        client.setEmail(email);
        client.setName(registerRequest.getName());
        client.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        client.setBalance(BigDecimal.ZERO);

        Client savedClient = clientRepository.save(client);

        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                savedClient.getEmail(),
                savedClient.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_CUSTOMER"))
        );

        String token = jwtService.generateToken(userDetails);
        return ResponseEntity.ok(new AuthResponse(token));
    }

    /**
     * Ендпоінт для зміни пароля поточного користувача
     * POST /api/auth/change-password
     */
    @PostMapping("/change-password")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> changePassword(@Valid @RequestBody ChangePasswordRequest request) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        Client client = clientRepository.findByEmail(email).orElse(null);
        if (client != null) {
            if (!passwordEncoder.matches(request.getCurrentPassword(), client.getPassword())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Current password is incorrect");
            }
            client.setPassword(passwordEncoder.encode(request.getNewPassword()));
            clientRepository.save(client);
            return ResponseEntity.noContent().build();
        }

        var employee = employeeRepository.findByEmail(email).orElse(null);
        if (employee != null) {
            if (!passwordEncoder.matches(request.getCurrentPassword(), employee.getPassword())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Current password is incorrect");
            }
            employee.setPassword(passwordEncoder.encode(request.getNewPassword()));
            employeeRepository.save(employee);
            return ResponseEntity.noContent().build();
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found in database");
    }
}