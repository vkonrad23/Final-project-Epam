package com.epam.rd.autocode.spring.project.controller;

import com.epam.rd.autocode.spring.project.dto.ClientDTO;
import com.epam.rd.autocode.spring.project.service.ClientService;
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
@RequestMapping("/api/clients")
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('EMPLOYEE','ADMIN')")
    public List<ClientDTO> getAllClients() {
        return clientService.getAllClients();
    }

    @GetMapping("/{email}")
    @PreAuthorize("hasAnyRole('EMPLOYEE','ADMIN')")
    public ClientDTO getClientByEmail(@PathVariable String email) {
        return clientService.getClientByEmail(email);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ClientDTO> addClient(@Valid @RequestBody ClientDTO clientDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clientService.addClient(clientDTO));
    }

    @PutMapping("/{email}")
    @PreAuthorize("hasAnyRole('CUSTOMER','ADMIN')")
    public ClientDTO updateClient(@PathVariable String email, @Valid @RequestBody ClientDTO clientDTO) {
        return clientService.updateClientByEmail(email, clientDTO);
    }

    @DeleteMapping("/{email}")
    @PreAuthorize("hasAnyRole('CUSTOMER','ADMIN')")
    public ResponseEntity<Void> deleteClient(@PathVariable String email) {
        clientService.deleteClientByEmail(email);
        return ResponseEntity.noContent().build();
    }
}
