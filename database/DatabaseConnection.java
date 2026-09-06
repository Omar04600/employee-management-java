package database;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {

    private static final String URL =
            "jdbc:mysql://mysql:3306/employee_db";

    private static final String USER =
            "root";

    private static final String PASSWORD =
            "password";

    public static Connection getConnection() throws Exception {

        return DriverManager.getConnection(
                URL,
                USER,
                PASSWORD
        );

    }

    public static void main(String[] args) {

        try {

            Connection connection = getConnection();

            System.out.println("Database Connected Successfully!");

            connection.close();

        } catch (Exception e) {

            System.out.println("Database Connection Failed!");

            e.printStackTrace();

        }

    }

}