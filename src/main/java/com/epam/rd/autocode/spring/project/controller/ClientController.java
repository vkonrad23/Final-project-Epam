/**
 * Цей файл визначає основні Java-типи та поведінку для цього модуля/функціоналу.
 */
package com.epam.rd.autocode.spring.project.controller;

import com.epam.rd.autocode.spring.project.dto.ClientDTO;
import com.epam.rd.autocode.spring.project.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
/**
 * Клас ClientController відповідає за обробку HTTP-запитів,
 * пов’язаних із клієнтами (CRUD операції).
 */
public class ClientController {

    // Сервіс для бізнес-логіки клієнтів
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('EMPLOYEE','ADMIN')")
    /**
     * Отримує список усіх клієнтів.
     *
     * @return список ClientDTO
     */
    public List<ClientDTO> getAllClients() {
        return clientService.getAllClients();
    }

    @GetMapping("/me")
    @PreAuthorize("hasAnyRole('CUSTOMER','ADMIN')")
    /**
     * Отримує клієнта за email.
     *
     * @param email email клієнта
     * @return ClientDTO
     */
    public ClientDTO getClientByEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return clientService.getClientByEmail(email);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    /**
     * Додає нового клієнта.
     *
     * @param clientDTO дані клієнта
     * @return створений клієнт зі статусом 201 (Created)
     */
    public ResponseEntity<ClientDTO> addClient(@Valid @RequestBody ClientDTO clientDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(clientService.addClient(clientDTO));
    }

    @PutMapping("/me")
    @PreAuthorize("hasAnyRole('CUSTOMER','ADMIN')")
    /**
     * Оновлює дані клієнта за email.
     *
     * @param email email клієнта
     * @param clientDTO нові дані
     * @return оновлений клієнт
     */
    public ClientDTO updateClient(@Valid @RequestBody ClientDTO clientDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return clientService.updateClientByEmail(email, clientDTO);
    }

    @DeleteMapping("/me")
    @PreAuthorize("hasAnyRole('CUSTOMER','ADMIN')")
    /**
     * Видаляє клієнта за email.
     *
     * @param email email клієнта
     * @return HTTP 204 (No Content)
     */
    public ResponseEntity<Void> deleteClient() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        clientService.deleteClientByEmail(email);
        return ResponseEntity.noContent().build();
    }
}