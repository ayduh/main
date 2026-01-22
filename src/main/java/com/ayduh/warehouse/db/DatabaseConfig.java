package com.ayduh.warehouse.db;

import java.sql.*;

public class DatabaseConfig {
    private static final String URL = "jdbc:postgresql://localhost:5432/Warehouse_DB";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgre";

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver not found");
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
