package server;

import java.util.ArrayList;

import database.EmployeeRepository;

public class EmployeeService {

    // ===========================
    // GET EMPLOYEES
    // ===========================

    public ArrayList<Employee> getEmployees() {

        try {

            return EmployeeRepository.getEmployees();

        } catch (Exception e) {

            e.printStackTrace();

            return new ArrayList<>();

        }
    }


    // ===========================
    // ADD EMPLOYEE
    // ===========================

    public void addEmployee(Employee employee) {

        try {

            EmployeeRepository.addEmployee(employee);

            System.out.println(
                "Employee added: " + employee.getName()
            );

        } catch (Exception e) {

            e.printStackTrace();

        }
    }


    // ===========================
    // DELETE EMPLOYEE
    // ===========================

    public void deleteEmployee(int id) {

        try {

            EmployeeRepository.deleteEmployee(id);

            System.out.println(
                "Employee deleted: " + id
            );

        } catch (Exception e) {

            e.printStackTrace();

        }
    }

}