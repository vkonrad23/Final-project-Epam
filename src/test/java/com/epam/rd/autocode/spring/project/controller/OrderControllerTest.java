package com.epam.rd.autocode.spring.project.controller;

import com.epam.rd.autocode.spring.project.dto.OrderDTO;
import com.epam.rd.autocode.spring.project.service.OrderService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    @AfterEach
    void clearContext() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void getOrdersByClientUsesAuthenticatedEmail() {
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("client1@example.com", "n/a")
        );

        when(orderService.getOrdersByClient("client1@example.com")).thenReturn(List.of(new OrderDTO()));

        List<OrderDTO> result = orderController.getOrdersByClient();

        assertEquals(1, result.size());
        verify(orderService).getOrdersByClient("client1@example.com");
    }

    @Test
    void addOrderReturnsCreatedStatus() {
        OrderDTO order = new OrderDTO();
        when(orderService.addOrder(order)).thenReturn(order);

        ResponseEntity<OrderDTO> response = orderController.addOrder(order);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(order, response.getBody());
        verify(orderService).addOrder(order);
    }
}
