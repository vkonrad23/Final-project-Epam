/** Цей файл визначає основні типи та поведінку для цього модуля */
package com.epam.rd.autocode.spring.project.service;

import com.epam.rd.autocode.spring.project.dto.EmployeeDTO;

import java.util.List;

/**
 * Інтерфейс сервісу для роботи зі співробітниками
 * Описує доступні бізнес-операції без реалізації
 */
public interface EmployeeService {

    /**
     * Отримати список усіх співробітників
     *
     * @return список співробітників у вигляді DTO
     */
    List<EmployeeDTO> getAllEmployees();

    /**
     * Отримати співробітника за email
     *
     * @param email email співробітника
     * @return знайдений співробітник
     */
    EmployeeDTO getEmployeeByEmail(String email);

    /**
     * Оновити дані співробітника за email
     *
     * @param email email співробітника, якого потрібно оновити
     * @param employee нові дані співробітника
     * @return оновлений співробітник
     */
    EmployeeDTO updateEmployeeByEmail(String email, EmployeeDTO employee);

    /**
     * Видалити співробітника за email
     *
     * @param email email співробітника
     */
    void deleteEmployeeByEmail(String email);

    /**
     * Додати нового співробітника
     *
     * @param employee дані нового співробітника
     * @return створений співробітник
     */
    EmployeeDTO addEmployee(EmployeeDTO employee);
}