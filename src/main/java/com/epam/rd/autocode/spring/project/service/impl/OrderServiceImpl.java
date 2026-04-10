/** Цей файл визначає основні типи та поведінку для цього модуля */
package com.epam.rd.autocode.spring.project.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.epam.rd.autocode.spring.project.dto.BookItemDTO;
import com.epam.rd.autocode.spring.project.dto.OrderDTO;
import com.epam.rd.autocode.spring.project.exception.NotFoundException;
import com.epam.rd.autocode.spring.project.model.Book;
import com.epam.rd.autocode.spring.project.model.BookItem;
import com.epam.rd.autocode.spring.project.model.Client;
import com.epam.rd.autocode.spring.project.model.Employee;
import com.epam.rd.autocode.spring.project.model.Order;
import com.epam.rd.autocode.spring.project.repo.BookRepository;
import com.epam.rd.autocode.spring.project.repo.ClientRepository;
import com.epam.rd.autocode.spring.project.repo.EmployeeRepository;
import com.epam.rd.autocode.spring.project.repo.OrderRepository;
import com.epam.rd.autocode.spring.project.service.OrderService;

/**
 * Сервіс для роботи із замовленнями
 * Містить бізнес-логіку створення та отримання замовлень
 */
@Service
public class OrderServiceImpl implements OrderService {

    // Логер для відслідковування дій
    private static final Logger log =
            LoggerFactory.getLogger(OrderServiceImpl.class);

    // Репозиторії
    private final OrderRepository orderRepository;
    private final ClientRepository clientRepository;
    private final EmployeeRepository employeeRepository;
    private final BookRepository bookRepository;

    /**
     * Конструктор для ініціалізації залежностей
     */
    public OrderServiceImpl(OrderRepository orderRepository,
                            ClientRepository clientRepository,
                            EmployeeRepository employeeRepository,
                            BookRepository bookRepository) {
        this.orderRepository = orderRepository;
        this.clientRepository = clientRepository;
        this.employeeRepository = employeeRepository;
        this.bookRepository = bookRepository;
    }

    /**
     * Отримати всі замовлення клієнта
     */
    @Override
    public List<OrderDTO> getOrdersByClient(String clientEmail) {
        return orderRepository.findByClientEmail(clientEmail).stream()
                .map(this::toDto)
                .toList();
    }

    /**
     * Отримати всі замовлення співробітника
     */
    @Override
    public List<OrderDTO> getOrdersByEmployee(String employeeEmail) {
        return orderRepository.findByEmployeeEmail(employeeEmail).stream()
                .map(this::toDto)
                .toList();
    }

        /**
         * Отримати всі замовлення (для ADMIN)
         */
        @Override
        public List<OrderDTO> getAllOrders() {
                return orderRepository.findAll().stream()
                                .map(this::toDto)
                                .toList();
        }

    /**
     * Створити нове замовлення
     */
    @Override
    public OrderDTO addOrder(OrderDTO orderDTO) {

        // Логуємо створення замовлення
        log.info("Створення замовлення, client={}, employee={}",
                orderDTO.getClientEmail(),
                orderDTO.getEmployeeEmail());

        // Знаходимо клієнта
        Client client = clientRepository.findByEmail(orderDTO.getClientEmail())
                .orElseThrow(() ->
                        new NotFoundException("Client not found: " + orderDTO.getClientEmail()));

        // Знаходимо співробітника
        Employee employee = employeeRepository.findByEmail(orderDTO.getEmployeeEmail())
                .orElseThrow(() ->
                        new NotFoundException("Employee not found: " + orderDTO.getEmployeeEmail()));

        // Створюємо нове замовлення
        Order order = new Order();
        order.setClient(client);
        order.setEmployee(employee);
        order.setOrderDate(orderDTO.getOrderDate());

        // Список елементів замовлення
        List<BookItem> items = new ArrayList<>();

        // Загальна ціна
        BigDecimal total = BigDecimal.ZERO;

        /**
         * Проходимо по кожному елементу замовлення
         */
        for (BookItemDTO itemDTO : orderDTO.getBookItems()) {

            // Знаходимо книгу
            Book book = bookRepository.findByNameIgnoreCase(itemDTO.getBookName())
                    .orElseThrow(() ->
                            new NotFoundException("Book not found: " + itemDTO.getBookName()));

            // Створюємо BookItem
            BookItem item = new BookItem();
            item.setBook(book);
            item.setOrder(order);
            item.setQuantity(itemDTO.getQuantity());

            items.add(item);

            // Рахуємо загальну ціну
            total = total.add(
                    book.getPrice().multiply(BigDecimal.valueOf(itemDTO.getQuantity()))
            );
        }

        // Встановлюємо список товарів і ціну
        order.setBookItems(items);
        order.setPrice(total);

        // Зберігаємо і повертаємо DTO
        return toDto(orderRepository.save(order));
    }

    /**
     * Перетворення Order → OrderDTO
     */
    private OrderDTO toDto(Order order) {

        // Конвертуємо BookItem → BookItemDTO
        List<BookItemDTO> items = order.getBookItems().stream()
                .map(item ->
                        new BookItemDTO(
                                item.getBook().getName(),
                                item.getQuantity()
                        )
                )
                .toList();

        // Створюємо DTO
        return new OrderDTO(
                order.getClient().getEmail(),
                order.getEmployee().getEmail(),
                order.getOrderDate(),
                order.getPrice(),
                items
        );
    }
}