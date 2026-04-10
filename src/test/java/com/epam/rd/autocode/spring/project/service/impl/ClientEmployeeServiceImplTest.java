package com.epam.rd.autocode.spring.project.service.impl;

import com.epam.rd.autocode.spring.project.dto.ClientDTO;
import com.epam.rd.autocode.spring.project.dto.EmployeeDTO;
import com.epam.rd.autocode.spring.project.model.Client;
import com.epam.rd.autocode.spring.project.model.Employee;
import com.epam.rd.autocode.spring.project.repo.ClientRepository;
import com.epam.rd.autocode.spring.project.repo.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClientEmployeeServiceImplTest {
    @Mock
    private ClientRepository clientRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private final ModelMapper modelMapper = new ModelMapper();

    private ClientServiceImpl clientService;

    private EmployeeServiceImpl employeeService;

    @BeforeEach
    void setUp() {
        clientService = new ClientServiceImpl(clientRepository, modelMapper, passwordEncoder);
        employeeService = new EmployeeServiceImpl(employeeRepository, modelMapper, passwordEncoder);
    }

    @Test
    void addClientEncodesPassword() {
        ClientDTO dto = new ClientDTO();
        dto.setEmail("client1@example.com");
        dto.setPassword("Aa!12345");
        dto.setName("Client");

        when(clientRepository.existsByEmail(dto.getEmail())).thenReturn(false);
        when(passwordEncoder.encode("Aa!12345")).thenReturn("{bcrypt}hash");
        when(clientRepository.save(any(Client.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ClientDTO result = clientService.addClient(dto);

        assertEquals("client1@example.com", result.getEmail());
    }

    @Test
    void updateEmployeeEncodesPassword() {
        Employee entity = new Employee();
        entity.setEmail("john.doe@email.com");
        entity.setPassword("old");
        EmployeeDTO dto = new EmployeeDTO();
        dto.setEmail("john.doe@email.com");
        dto.setPassword("Bb!12345");

        when(employeeRepository.findByEmail("john.doe@email.com")).thenReturn(Optional.of(entity));
        when(passwordEncoder.encode("Bb!12345")).thenReturn("{bcrypt}new");
        when(employeeRepository.save(any(Employee.class))).thenAnswer(invocation -> invocation.getArgument(0));

        EmployeeDTO result = employeeService.updateEmployeeByEmail("john.doe@email.com", dto);

        assertEquals("john.doe@email.com", result.getEmail());
    }
}