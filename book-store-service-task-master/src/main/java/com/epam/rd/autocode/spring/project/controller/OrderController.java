package com.epam.rd.autocode.spring.project.controller;

import com.epam.rd.autocode.spring.project.dto.OrderDTO;
import com.epam.rd.autocode.spring.project.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/client/{email}")
    @PreAuthorize("hasAnyRole('CUSTOMER','EMPLOYEE','ADMIN')")
    public List<OrderDTO> getOrdersByClient(@PathVariable("email") String email) {
        return orderService.getOrdersByClient(email);
    }

    @GetMapping("/employee/{email}")
    @PreAuthorize("hasAnyRole('EMPLOYEE','ADMIN')")
    public List<OrderDTO> getOrdersByEmployee(@PathVariable("email") String email) {
        return orderService.getOrdersByEmployee(email);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('CUSTOMER','EMPLOYEE','ADMIN')")
    public ResponseEntity<OrderDTO> addOrder(@Valid @RequestBody OrderDTO orderDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.addOrder(orderDTO));
    }
}
