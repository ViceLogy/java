package com.company.repository;

import com.company.entity.Order;

import java.util.List;

public interface OrderRepository {

    Order getOne(int id);

    List<Order> getAllOrderByUser(int id);

    void delete(int id);

    Order update(Order order);

    void create(int userId,Order order);

}
