package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import server.Employee;

public class EmployeeRepository {

    // ===========================
    // GET EMPLOYEES
    // ===========================

    public static ArrayList<Employee> getEmployees() throws Exception {

        ArrayList<Employee> employees = new ArrayList<>();

        Connection connection = DatabaseConnection.getConnection();

        Statement statement = connection.createStatement();

        ResultSet resultSet =
                statement.executeQuery("SELECT * FROM employees");

        while (resultSet.next()) {

            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String department = resultSet.getString("department");

            employees.add(
                new Employee(id, name, department)
            );
        }

        connection.close();

        return employees;
    }


    // ===========================
    // ADD EMPLOYEE
    // ===========================

    public static void addEmployee(Employee employee) throws Exception {

        Connection connection = DatabaseConnection.getConnection();

        Statement statement = connection.createStatement();

        String sql = "INSERT INTO employees (name, department) VALUES ('"
                + employee.getName() + "', '"
                + employee.getDepartment() + "')";

        statement.executeUpdate(sql);

        connection.close();
    }


    // ===========================
    // DELETE EMPLOYEE
    // ===========================

    public static void deleteEmployee(int id) throws Exception {

        Connection connection = DatabaseConnection.getConnection();

        Statement statement = connection.createStatement();

        String sql = "DELETE FROM employees WHERE id = " + id;

        statement.executeUpdate(sql);

        connection.close();
    }

}