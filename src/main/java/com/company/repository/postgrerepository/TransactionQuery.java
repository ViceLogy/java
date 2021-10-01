package com.company.repository.postgrerepository;

import com.company.datasource.InitConnection;
import com.company.entity.Order;
import com.company.entity.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

public class TransactionQuery {
    
    public void addUserWithOrder(User user, Order order) {
        if (user != null & order != null) {
            try (Connection connection = InitConnection.connect()) {
                connection.setAutoCommit(false);
                try (Statement statement = connection.createStatement()) {
                    String sqlQuery = "INSERT INTO users (name,lastname) VALUES ('" + user.getName() +
                            "','" + user.getLastName() + "')";
                    statement.executeUpdate(sqlQuery);
                    sqlQuery = "INSERT INTO orders (user_id, description, price) VALUES ('" + user.getId() + "','" +
                            order.getDescription() + "','" + order.getPrice() + "')";
                    statement.executeUpdate(sqlQuery);
                    connection.commit();
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage() + "\n" + Arrays.toString(ex.getStackTrace()));
                    connection.rollback();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage() + "\n" + Arrays.toString(ex.getStackTrace()));
            }
        }
    }




}
