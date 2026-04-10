/** Цей файл визначає основні типи та поведінку для цього модуля */
package com.epam.rd.autocode.spring.project.repo;

import com.epam.rd.autocode.spring.project.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Репозиторій для роботи з сутністю Employee
 * Забезпечує доступ до бази даних для співробітників
 */
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    /**
     * Знайти співробітника за email
     *
     * Optional використовується для безпечної роботи з відсутніми значеннями
     */
    Optional<Employee> findByEmail(String email);

    /**
     * Перевірити, чи існує співробітник з таким email
     *
     * @param email email для перевірки
     * @return true — якщо існує, false — якщо ні
     */
    boolean existsByEmail(String email);
}