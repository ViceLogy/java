package com.company.repository.postgrerepository;

import com.company.datasource.InitConnection;
import com.company.entity.Order;
import com.company.repository.OrderRepository;
import com.company.repository.UserRepository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PostgreOrderUserRepositoryImpl implements OrderRepository {

    @Override
    public Order getOne(int id) {

        try(Connection connection = InitConnection.connect();
            Statement statement = connection.createStatement()) {
            String sql = "SELECT * FROM orders WHERE id = "+id;
            ResultSet result = statement.executeQuery(sql);
            result.next();
            return new Order(result.getInt("id"),result.getInt("user_id"),
                    result.getString("description"),result.getInt("price"));
        }catch (SQLException ex){
            System.out.println(ex.getMessage() +"\n"+ Arrays.toString(ex.getStackTrace()));
        }
        return null;
    }

    @Override
    public List<Order> getAllOrderByUser(int id) {
        try(Connection connection = InitConnection.connect();
            Statement statement = connection.createStatement()) {
            String sql = "SELECT * FROM orders WHERE user_id ="+id;
            ResultSet result = statement.executeQuery(sql);
            List<Order> orderList = new ArrayList<>();
            while (result.next()){
                orderList.add(new Order(result.getInt("id"),result.getInt("user_id"),
                        result.getString("description"),result.getInt("price")));
            }
            return orderList;
        }catch (SQLException ex){
            System.out.println(ex.getMessage() +"\n"+ Arrays.toString(ex.getStackTrace()));
        }
        return null;
    }

    @Override
    public void delete(int id) {
        if (getOne(id) != null) {
            try (Connection connection = InitConnection.connect();
                 Statement statement = connection.createStatement()) {
                String sql = "DELETE FROM orders WHERE id = " + id;
                statement.executeQuery(sql);

            } catch (SQLException ex) {
                System.out.println(ex.getMessage() + "\n" + Arrays.toString(ex.getStackTrace()));
            }
        }
    }

    @Override
    public Order update(Order order) {
        if (getOne(order.getId()).equals(order)) {
            try (Connection connection = InitConnection.connect();
                 Statement statement = connection.createStatement()) {
                String sql = "UPDATE orders SET description = '" + order.getDescription() + "', price = '" + order.getPrice() +
                        "' WHERE id = " + order.getId();
                statement.executeUpdate(sql);
                System.out.println("Успешно");
            } catch (SQLException ex) {
                System.out.println(ex.getMessage() + "\n" + Arrays.toString(ex.getStackTrace()));
            }
        }
        return null;
    }

    @Override
    public void create(int userId, Order order) {
        try(Connection connection = InitConnection.connect();
            Statement statement = connection.createStatement()) {
            String sql = "INSERT INTO orders (user_id, description, price) VALUES ('"+userId+"','"+order.getDescription()+
                    "','"+order.getPrice()+"')";
            statement.executeQuery(sql);
            System.out.println("Успешно");
        }catch (SQLException ex){
            System.out.println(ex.getMessage() +"\n"+ Arrays.toString(ex.getStackTrace()));
        }
    }
}
