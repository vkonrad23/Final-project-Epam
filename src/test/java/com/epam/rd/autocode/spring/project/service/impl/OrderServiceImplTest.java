package com.epam.rd.autocode.spring.project.service.impl;

import com.epam.rd.autocode.spring.project.dto.BookItemDTO;
import com.epam.rd.autocode.spring.project.dto.OrderDTO;
import com.epam.rd.autocode.spring.project.model.Book;
import com.epam.rd.autocode.spring.project.model.Client;
import com.epam.rd.autocode.spring.project.model.Employee;
import com.epam.rd.autocode.spring.project.model.Order;
import com.epam.rd.autocode.spring.project.repo.BookRepository;
import com.epam.rd.autocode.spring.project.repo.ClientRepository;
import com.epam.rd.autocode.spring.project.repo.EmployeeRepository;
import com.epam.rd.autocode.spring.project.repo.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private ClientRepository clientRepository;
    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    void addOrderCalculatesTotalPrice() {
        Client client = new Client();
        client.setEmail("client1@example.com");
        Employee employee = new Employee();
        employee.setEmail("john.doe@email.com");
        Book book = new Book();
        book.setName("Dune");
        book.setPrice(new BigDecimal("19.40"));

        OrderDTO dto = new OrderDTO(
                "client1@example.com",
                "john.doe@email.com",
                LocalDateTime.now(),
                BigDecimal.ZERO,
                List.of(new BookItemDTO("Dune", 2))
        );

        when(clientRepository.findByEmail("client1@example.com")).thenReturn(Optional.of(client));
        when(employeeRepository.findByEmail("john.doe@email.com")).thenReturn(Optional.of(employee));
        when(bookRepository.findByNameIgnoreCase("Dune")).thenReturn(Optional.of(book));
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

        OrderDTO result = orderService.addOrder(dto);

        assertEquals(new BigDecimal("38.80"), result.getPrice());
        assertEquals(1, result.getBookItems().size());
    }
}