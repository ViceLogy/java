package com.company.repository.postgrerepository;

import com.company.datasource.InitConnection;
import com.company.entity.User;
import com.company.repository.UserRepository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PostgreUserUserRepositoryImpl implements UserRepository {


    @Override
    public User getOne(int id) {
        User user = null;
        try (Connection connection = InitConnection.connect();
             Statement statement = connection.createStatement()){
            String sql = "SELECT * FROM users WHERE id = "+id;
            ResultSet result = statement.executeQuery(sql);
            result.next();
            user = new User(result.getInt("id"), result.getString("name"),result.getString("lastname"));
        }catch (SQLException ex){
            System.out.println(ex.getMessage() +"\n"+ Arrays.toString(ex.getStackTrace()));
        }
        return user;
    }

    @Override
    public List<User> getAll() {
        List<User> list= new ArrayList<>();
        try (Connection connection = InitConnection.connect();
             Statement statement = connection.createStatement()){
            String sql = "SELECT * FROM users";
            ResultSet result = statement.executeQuery(sql);
            while (result.next()){
                User user = new User(result.getInt("id"), result.getString("name"),result.getString("lastname"));
                list.add(user);
            }
        }catch (SQLException ex){
            System.out.println(ex.getMessage() +"\n"+ Arrays.toString(ex.getStackTrace()));
        }
        return list;
    }

    @Override
    public void delete(int id) {
        if(getOne(id)!= null) {
            try (Connection connection = InitConnection.connect();
                 Statement statement = connection.createStatement()) {
                String sql = "DELETE FROM users WHERE id = " + id;
                statement.executeQuery(sql);
            } catch (SQLException ex) {
                System.out.println(ex.getMessage() + "\n" + Arrays.toString(ex.getStackTrace()));
            }
        }
    }

    @Override
    public User update(User user) {
            if(!user.equals(getOne(user.getId()))) {
                try (Connection connection = InitConnection.connect();
                     Statement statement = connection.createStatement()) {
                        String sql = "UPDATE users SET name = '" + user.getName() + "', lastname = '" + user.getLastName() +
                                "' WHERE id = " + user.getId();
                        statement.executeQuery(sql);
                        return user;
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage() + "\n" + Arrays.toString(ex.getStackTrace()));

                }
            }
        return null;
    }

    @Override
    public void create(User user) {
        if (!getOne(user.getId()).equals(user)) {
            try (Connection connection = InitConnection.connect();
                 Statement statement = connection.createStatement()) {
                String sql = "INSERT INTO users (name, lastname) VALUES ('" + user.getName() + "','" + user.getLastName() + "')";
                statement.executeQuery(sql);
            } catch (SQLException ex) {
                System.out.println(ex.getMessage() + "\n" + Arrays.toString(ex.getStackTrace()));
            }
        }
    }
}
