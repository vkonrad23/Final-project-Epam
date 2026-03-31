package com.epam.rd.autocode.spring.project.service.impl;

import com.epam.rd.autocode.spring.project.dto.ClientDTO;
import com.epam.rd.autocode.spring.project.exception.AlreadyExistException;
import com.epam.rd.autocode.spring.project.exception.NotFoundException;
import com.epam.rd.autocode.spring.project.model.Client;
import com.epam.rd.autocode.spring.project.repo.ClientRepository;
import com.epam.rd.autocode.spring.project.service.ClientService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {
    private static final Logger log = LoggerFactory.getLogger(ClientServiceImpl.class);

    private final ClientRepository clientRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public ClientServiceImpl(ClientRepository clientRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.clientRepository = clientRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<ClientDTO> getAllClients() {
        return clientRepository.findAll().stream()
                .map(client -> modelMapper.map(client, ClientDTO.class))
                .toList();
    }

    @Override
    public ClientDTO getClientByEmail(String email) {
        Client client = clientRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Client not found: " + email));
        return modelMapper.map(client, ClientDTO.class);
    }

    @Override
    public ClientDTO updateClientByEmail(String email, ClientDTO clientDTO) {
        Client client = clientRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Client not found: " + email));
        modelMapper.map(clientDTO, client);
        client.setPassword(passwordEncoder.encode(clientDTO.getPassword()));
        return modelMapper.map(clientRepository.save(client), ClientDTO.class);
    }

    @Override
    public void deleteClientByEmail(String email) {
        Client client = clientRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Client not found: " + email));
        clientRepository.delete(client);
    }

    @Override
    public ClientDTO addClient(ClientDTO clientDTO) {
        log.info("Registering client: {}", clientDTO.getEmail());
        if (clientRepository.existsByEmail(clientDTO.getEmail())) {
            throw new AlreadyExistException("Client already exists: " + clientDTO.getEmail());
        }
        Client client = modelMapper.map(clientDTO, Client.class);
        client.setPassword(passwordEncoder.encode(clientDTO.getPassword()));
        return modelMapper.map(clientRepository.save(client), ClientDTO.class);
    }
}
