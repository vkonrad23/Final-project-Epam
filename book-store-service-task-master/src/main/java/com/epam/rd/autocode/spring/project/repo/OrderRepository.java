package com.epam.rd.autocode.spring.project.repo;

import com.epam.rd.autocode.spring.project.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("select o from Order o where o.client.email = :email order by o.orderDate desc")
    List<Order> findByClientEmail(@Param("email") String email);

    @Query("select o from Order o where o.employee.email = :email order by o.orderDate desc")
    List<Order> findByEmployeeEmail(@Param("email") String email);
}
