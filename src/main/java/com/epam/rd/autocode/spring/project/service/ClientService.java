/** Цей файл визначає основні типи та поведінку для цього модуля */
package com.epam.rd.autocode.spring.project.service;

import com.epam.rd.autocode.spring.project.dto.ClientDTO;

import java.util.List;

/**
 * Інтерфейс сервісу для роботи з клієнтами
 * Описує доступні бізнес-операції без реалізації
 */
public interface ClientService {

    /**
     * Отримати список усіх клієнтів
     *
     * @return список клієнтів у вигляді DTO
     */
    List<ClientDTO> getAllClients();

    /**
     * Отримати клієнта за email
     *
     * @param email email клієнта
     * @return знайдений клієнт
     */
    ClientDTO getClientByEmail(String email);

    /**
     * Оновити дані клієнта за email
     *
     * @param email email клієнта, якого потрібно оновити
     * @param client нові дані клієнта
     * @return оновлений клієнт
     */
    ClientDTO updateClientByEmail(String email, ClientDTO client);

    /**
     * Видалити клієнта за email
     *
     * @param email email клієнта
     */
    void deleteClientByEmail(String email);

    /**
     * Додати нового клієнта
     *
     * @param client дані нового клієнта
     * @return створений клієнт
     */
    ClientDTO addClient(ClientDTO client);
}