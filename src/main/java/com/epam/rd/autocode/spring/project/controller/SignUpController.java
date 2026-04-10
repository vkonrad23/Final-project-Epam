package com.epam.rd.autocode.spring.project.controller;

import com.epam.rd.autocode.spring.project.dto.ClientDTO;
import com.epam.rd.autocode.spring.project.exception.AlreadyExistException;
import com.epam.rd.autocode.spring.project.service.ClientService;

import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * REST controller responsible for public user registration (sign up).
 *
 * Exposes authentication-related endpoints under /api/auth and delegates
 * registration logic to the application service layer.
 */
@RestController
@RequestMapping("/api/auth")
public class SignUpController {

    private static final Logger log = LoggerFactory.getLogger(SignUpController.class);

    private final ClientService clientService;

    /**
     * Constructor injection for the client use case service.
     */
    public SignUpController(ClientService clientService) {
        this.clientService = clientService;
    }

    /**
     * Registers a new client account.
     *
     * Endpoint: POST /api/auth/signup
     *
     * @param clientDTO validated registration payload
     * @return created client with HTTP 201 Created
     */
    @PostMapping("/signup")
    public ResponseEntity<ClientDTO> signUp(@Valid @RequestBody ClientDTO clientDTO) {
        log.info("Sign up request received for email={}", clientDTO.getEmail());

        ClientDTO createdClient = clientService.addClient(clientDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdClient);
    }

    /**
     * Returns HTTP 409 Conflict when the email is already registered.
     */
    @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<Map<String, Object>> handleAlreadyExist(AlreadyExistException ex) {
        log.warn("Sign up conflict: {}", ex.getMessage());

        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.CONFLICT.value());
        body.put("error", ex.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }
}
