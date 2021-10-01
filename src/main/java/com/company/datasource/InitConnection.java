package com.company.datasource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;

public class InitConnection {
    private static final String URL = "jdbc:postgresql://localhost/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "153759";


    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Соединение установленно");
        } catch (SQLException e) {
            System.out.println(e.getMessage() +"\n" + Arrays.toString(e.getStackTrace()));
        }

        return conn;
    }


}
