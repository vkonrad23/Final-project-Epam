/** Цей файл визначає основні типи та поведінку для цього модуля */
package com.epam.rd.autocode.spring.project.repo;

import com.epam.rd.autocode.spring.project.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Репозиторій для роботи з сутністю Order
 * Забезпечує доступ до замовлень у базі даних
 */
public interface OrderRepository extends JpaRepository<Order, Long> {

    /**
     * Знайти всі замовлення клієнта за email
     * Результати сортуються за датою (нові спочатку)
     *
     * JPQL-запит:
     * o.client.email → доступ до email через зв'язок з Client
     * order by o.orderDate desc → сортування за спаданням
     */
    @Query("select o from Order o where o.client.email = :email order by o.orderDate desc")
    List<Order> findByClientEmail(@Param("email") String email);

    /**
     * Знайти всі замовлення, які обробляє співробітник
     * Результати сортуються за датою (нові спочатку)
     *
     * o.employee.email → доступ до email через зв'язок з Employee
     */
    @Query("select o from Order o where o.employee.email = :email order by o.orderDate desc")
    List<Order> findByEmployeeEmail(@Param("email") String email);
}