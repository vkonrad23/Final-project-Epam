/** Цей файл визначає основні типи та поведінку для цього модуля */
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

/**
 * Сервіс для роботи з клієнтами
 * Містить бізнес-логіку (між Controller і Repository)
 */
@Service
public class ClientServiceImpl implements ClientService {

    // Логер для відслідковування подій
    private static final Logger log =
            LoggerFactory.getLogger(ClientServiceImpl.class);

    // Репозиторій для роботи з клієнтами в БД
    private final ClientRepository clientRepository;

    // Mapper для перетворення Entity ↔ DTO
    private final ModelMapper modelMapper;

    // Енкодер для шифрування паролів
    private final PasswordEncoder passwordEncoder;

    /**
     * Конструктор для ініціалізації залежностей
     */
    public ClientServiceImpl(ClientRepository clientRepository,
                             ModelMapper modelMapper,
                             PasswordEncoder passwordEncoder) {
        this.clientRepository = clientRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Отримати список усіх клієнтів
     */
    @Override
    public List<ClientDTO> getAllClients() {
        return clientRepository.findAll().stream()
                .map(client -> modelMapper.map(client, ClientDTO.class))
                .toList();
    }

    /**
     * Отримати клієнта за email
     */
    @Override
    public ClientDTO getClientByEmail(String email) {

        // Шукаємо клієнта або кидаємо помилку
        Client client = clientRepository.findByEmail(email)
                .orElseThrow(() ->
                        new NotFoundException("Client not found: " + email));

        return modelMapper.map(client, ClientDTO.class);
    }

    /**
     * Оновити клієнта за email
     */
    @Override
    public ClientDTO updateClientByEmail(String email, ClientDTO clientDTO) {

        // Знаходимо клієнта
        Client client = clientRepository.findByEmail(email)
                .orElseThrow(() ->
                        new NotFoundException("Client not found: " + email));

        // Оновлюємо дані з DTO
        modelMapper.map(clientDTO, client);

        // Обов'язково шифруємо пароль
        client.setPassword(passwordEncoder.encode(clientDTO.getPassword()));

        // Зберігаємо та повертаємо DTO
        return modelMapper.map(clientRepository.save(client), ClientDTO.class);
    }

    /**
     * Видалити клієнта за email
     */
    @Override
    public void deleteClientByEmail(String email) {

        // Знаходимо клієнта
        Client client = clientRepository.findByEmail(email)
                .orElseThrow(() ->
                        new NotFoundException("Client not found: " + email));

        // Видаляємо з БД
        clientRepository.delete(client);
    }

    /**
     * Зареєструвати нового клієнта
     */
    @Override
    public ClientDTO addClient(ClientDTO clientDTO) {

        // Логуємо реєстрацію
        log.info("Реєстрація клієнта: {}", clientDTO.getEmail());

        // Перевіряємо, чи вже існує клієнт з таким email
        if (clientRepository.existsByEmail(clientDTO.getEmail())) {
            throw new AlreadyExistException(
                    "Client already exists: " + clientDTO.getEmail()
            );
        }

        // Перетворюємо DTO → Entity
        Client client = modelMapper.map(clientDTO, Client.class);

        // Шифруємо пароль перед збереженням
        client.setPassword(passwordEncoder.encode(clientDTO.getPassword()));

        // Зберігаємо та повертаємо DTO
        return modelMapper.map(clientRepository.save(client), ClientDTO.class);
    }
}