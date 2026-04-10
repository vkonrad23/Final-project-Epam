/** This file defines core Java types and behavior for this feature/module. */
package com.epam.rd.autocode.spring.project.controller;

// DTO для передачі даних замовлення між клієнтом і сервером
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epam.rd.autocode.spring.project.dto.OrderDTO;
import com.epam.rd.autocode.spring.project.service.OrderService;

import jakarta.validation.Valid;

/**
 * Контролер для роботи із замовленнями (Orders)
 * Обробляє HTTP-запити від клієнта
 */
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    // Сервіс для роботи з замовленнями (бізнес-логіка)
    private final OrderService orderService;

    /**
     * Конструктор для ініціалізації сервісу
     */
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Отримати всі замовлення поточного клієнта
     * GET /api/orders/client/me
     */
    @GetMapping("/client/me")
    @PreAuthorize("hasAnyRole('CUSTOMER','EMPLOYEE','ADMIN')")
    public List<OrderDTO> getOrdersByClient() {

        // Отримуємо інформацію про поточного користувача
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Email користувача (використовується як ідентифікатор)
        String email = authentication.getName();

        // Повертаємо список замовлень цього клієнта
        return orderService.getOrdersByClient(email);
    }

    /**
     * Отримати всі замовлення, які обробляє співробітник
     * GET /api/orders/employee/me
     */
    @GetMapping("/employee/me")
    @PreAuthorize("hasAnyRole('EMPLOYEE','ADMIN')")
    public List<OrderDTO> getOrdersByEmployee() {

        // Отримуємо поточного користувача
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Email співробітника
        String email = authentication.getName();

        // Повертаємо замовлення, які він обробляє
        return orderService.getOrdersByEmployee(email);
    }

    /**
     * Отримати всі замовлення для адміністратора
     * GET /api/orders/admin/all
     */
    @GetMapping("/admin/all")
    @PreAuthorize("hasRole('ADMIN')")
    public List<OrderDTO> getAllOrdersForAdmin() {
        return orderService.getAllOrders();
    }

    /**
     * Створити нове замовлення
     * POST /api/orders
     */
    @PostMapping
    @PreAuthorize("hasAnyRole('CUSTOMER','EMPLOYEE','ADMIN')")
    public ResponseEntity<OrderDTO> addOrder(@Valid @RequestBody OrderDTO orderDTO) {

        // Викликаємо сервіс для створення замовлення
        OrderDTO createdOrder = orderService.addOrder(orderDTO);

        // Повертаємо статус 201 (Created) + створене замовлення
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdOrder);
    }
}