package lk.projects.library.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseCon {

    private static Connection connection;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookmaster", "root", "1234");
            } catch (SQLException e) {
                System.out.println("Database Connection Error: " + e.getMessage());
            }
        }
        return connection;
    }
}
