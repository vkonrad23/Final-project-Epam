/** Цей файл визначає основні типи та поведінку для цього модуля */
package com.epam.rd.autocode.spring.project.service;

import java.util.List;

import com.epam.rd.autocode.spring.project.dto.OrderDTO;

/**
 * Інтерфейс сервісу для роботи із замовленнями
 * Описує доступні бізнес-операції без реалізації
 */
public interface OrderService {

    /**
     * Отримати всі замовлення конкретного клієнта
     *
     * @param clientEmail email клієнта
     * @return список замовлень клієнта
     */
    List<OrderDTO> getOrdersByClient(String clientEmail);

    /**
     * Отримати всі замовлення, які обробляє співробітник
     *
     * @param employeeEmail email співробітника
     * @return список замовлень співробітника
     */
    List<OrderDTO> getOrdersByEmployee(String employeeEmail);

    /**
     * Отримати всі замовлення (для адміністратора)
     *
     * @return список усіх замовлень
     */
    List<OrderDTO> getAllOrders();

    /**
     * Створити нове замовлення
     *
     * @param order дані замовлення
     * @return створене замовлення
     */
    OrderDTO addOrder(OrderDTO order);
}