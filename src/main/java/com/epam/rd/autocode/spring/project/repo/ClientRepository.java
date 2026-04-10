/** Цей файл визначає основні типи та поведінку для цього модуля */
package com.epam.rd.autocode.spring.project.repo;

import com.epam.rd.autocode.spring.project.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Репозиторій для роботи з сутністю Client
 * Надає доступ до бази даних для клієнтів
 */
public interface ClientRepository extends JpaRepository<Client, Long> {

    /**
     * Знайти клієнта за email
     *
     * Optional використовується, щоб уникнути null
     * (або є значення, або його немає)
     */
    Optional<Client> findByEmail(String email);

    /**
     * Перевірити, чи існує клієнт з таким email
     *
     * @param email email для перевірки
     * @return true — якщо існує, false — якщо ні
     */
    boolean existsByEmail(String email);
}