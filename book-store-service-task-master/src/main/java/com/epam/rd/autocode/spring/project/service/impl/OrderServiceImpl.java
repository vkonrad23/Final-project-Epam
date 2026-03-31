package com.epam.rd.autocode.spring.project.service.impl;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final OrderRepository orderRepository;
    private final ClientRepository clientRepository;
    private final EmployeeRepository employeeRepository;
    private final BookRepository bookRepository;

        public OrderServiceImpl(OrderRepository orderRepository, ClientRepository clientRepository,
                                                        EmployeeRepository employeeRepository, BookRepository bookRepository) {
                this.orderRepository = orderRepository;
                this.clientRepository = clientRepository;
                this.employeeRepository = employeeRepository;
                this.bookRepository = bookRepository;
        }

    @Override
    public List<OrderDTO> getOrdersByClient(String clientEmail) {
        return orderRepository.findByClientEmail(clientEmail).stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public List<OrderDTO> getOrdersByEmployee(String employeeEmail) {
        return orderRepository.findByEmployeeEmail(employeeEmail).stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public OrderDTO addOrder(OrderDTO orderDTO) {
        log.info("Creating order, client={}, employee={}", orderDTO.getClientEmail(), orderDTO.getEmployeeEmail());
        Client client = clientRepository.findByEmail(orderDTO.getClientEmail())
                .orElseThrow(() -> new NotFoundException("Client not found: " + orderDTO.getClientEmail()));
        Employee employee = employeeRepository.findByEmail(orderDTO.getEmployeeEmail())
                .orElseThrow(() -> new NotFoundException("Employee not found: " + orderDTO.getEmployeeEmail()));

        Order order = new Order();
        order.setClient(client);
        order.setEmployee(employee);
        order.setOrderDate(orderDTO.getOrderDate());

        List<BookItem> items = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;
        for (BookItemDTO itemDTO : orderDTO.getBookItems()) {
            Book book = bookRepository.findByNameIgnoreCase(itemDTO.getBookName())
                    .orElseThrow(() -> new NotFoundException("Book not found: " + itemDTO.getBookName()));

            BookItem item = new BookItem();
            item.setBook(book);
            item.setOrder(order);
            item.setQuantity(itemDTO.getQuantity());
            items.add(item);

            total = total.add(book.getPrice().multiply(BigDecimal.valueOf(itemDTO.getQuantity())));
        }

        order.setBookItems(items);
        order.setPrice(total);

        return toDto(orderRepository.save(order));
    }

    private OrderDTO toDto(Order order) {
        List<BookItemDTO> items = order.getBookItems().stream()
                .map(item -> new BookItemDTO(item.getBook().getName(), item.getQuantity()))
                .toList();

        return new OrderDTO(
                order.getClient().getEmail(),
                order.getEmployee().getEmail(),
                order.getOrderDate(),
                order.getPrice(),
                items
        );
    }
}
