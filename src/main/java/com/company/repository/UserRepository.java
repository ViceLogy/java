package com.company.repository;

import com.company.entity.Order;
import com.company.entity.User;

import java.lang.reflect.Type;
import java.util.List;

public interface UserRepository {

    User getOne(int id);

    List<User> getAll();

    void delete(int id);

    User update(User user);

    void create(User user);
}
