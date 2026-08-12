package server;
import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.InputStream;
import java.util.ArrayList;


public class Server {

    public static void main(String[] args) throws Exception {

        HttpServer server = HttpServer.create(
                new InetSocketAddress(8081),
                0
        );

        // ===========================
        // Employee Service
        // ===========================

        EmployeeService employeeService = new EmployeeService();

        // ===========================
        // HOME PAGE
        // ===========================

        server.createContext("/", exchange -> {

            String path = exchange.getRequestURI().getPath();

            if (!path.equals("/")) {

                String response = """
                        <h1>404 Page Not Found</h1>
                        """;

                exchange.getResponseHeaders().add("Content-Type", "text/html");

                exchange.sendResponseHeaders(404, response.getBytes().length);

                exchange.getResponseBody().write(response.getBytes());

                exchange.close();

                return;
            }

            System.out.println("-------------------------");
            System.out.println("Method : " + exchange.getRequestMethod());
            System.out.println("Path   : " + exchange.getRequestURI());

            byte[] response = Files.readAllBytes(Paths.get("server", "index.html"));

            exchange.getResponseHeaders().add("Content-Type", "text/html");

            exchange.sendResponseHeaders(200, response.length);

            exchange.getResponseBody().write(response);

            exchange.close();

        });

        // ===========================
        // CSS
        // ===========================

        server.createContext("/style.css", exchange -> {

            System.out.println("-------------------------");
            System.out.println("Method : " + exchange.getRequestMethod());
            System.out.println("Path   : " + exchange.getRequestURI());

            byte[] response = Files.readAllBytes(Paths.get("server", "style.css"));

            exchange.getResponseHeaders().add("Content-Type", "text/css");

            exchange.sendResponseHeaders(200, response.length);

            exchange.getResponseBody().write(response);

            exchange.close();

        });

        // ===========================
        // HELLO
        // ===========================

        server.createContext("/hello", exchange -> {

            System.out.println("-------------------------");
            System.out.println("Method : " + exchange.getRequestMethod());
            System.out.println("Path   : " + exchange.getRequestURI());

            String response = """
                    <html>
                    <body>

                    <h1>Hello Abdullah!</h1>

                    <a href="/">Go Home</a>

                    </body>
                    </html>
                    """;

            exchange.getResponseHeaders().add("Content-Type", "text/html");

            exchange.sendResponseHeaders(200, response.getBytes().length);

            exchange.getResponseBody().write(response.getBytes());

            exchange.close();

        });

        // ===========================
        // LOGIN
        // ===========================

        server.createContext("/login", exchange -> {

            System.out.println("-------------------------");
            System.out.println("Method : " + exchange.getRequestMethod());
            System.out.println("Path   : " + exchange.getRequestURI());

            InputStream input = exchange.getRequestBody();

            String body = new String(input.readAllBytes());

            System.out.println("Request Body:");
            System.out.println(body);

            String response = """
                    <html>
                    <body>

                    <h1>Login Request Received!</h1>

                    <a href="/">Go Home</a>

                    </body>
                    </html>
                    """;

            exchange.getResponseHeaders().add("Content-Type", "text/html");

            exchange.sendResponseHeaders(200, response.getBytes().length);

            exchange.getResponseBody().write(response.getBytes());

            exchange.close();

        });

        // ===========================
        // EMPLOYEES API
        // ===========================

        server.createContext("/employees", exchange -> {

            System.out.println("-------------------------");
            System.out.println("Method : " + exchange.getRequestMethod());
            System.out.println("Path   : " + exchange.getRequestURI());

            // ===========================
            // GET
            // ===========================

            if (exchange.getRequestMethod().equals("GET")) {

                ArrayList<Employee> employees = employeeService.getEmployees();

                StringBuilder response = new StringBuilder();

                response.append("[\n");

                for (int i = 0; i < employees.size(); i++) {

                    Employee emp = employees.get(i);

                    response.append("""
                            {
                                "id": %d,
                                "name": "%s",
                                "department": "%s"
                            }
                            """.formatted(
                            emp.getId(),
                            emp.getName(),
                            emp.getDepartment()
                    ));

                    if (i != employees.size() - 1) {
                        response.append(",");
                    }

                    response.append("\n");
                }

                response.append("]");

                byte[] data = response.toString().getBytes();

                exchange.getResponseHeaders().add("Content-Type", "application/json");

                exchange.sendResponseHeaders(200, data.length);

                exchange.getResponseBody().write(data);

                exchange.close();
            }

            // ===========================
            // POST
            // ===========================

           else if (exchange.getRequestMethod().equals("POST")) {

    String body = new String(exchange.getRequestBody().readAllBytes());

    System.out.println();
    System.out.println("Received JSON:");
    System.out.println(body);

    Employee employee = JsonUtil.parseEmployee(body);

    employeeService.addEmployee(employee);
    System.out.println("Total Employees: " + employeeService.getEmployees().size());

    String response = "Employee Added Successfully";

    exchange.getResponseHeaders().add("Content-Type", "text/plain");

    exchange.sendResponseHeaders(200, response.getBytes().length);

    exchange.getResponseBody().write(response.getBytes());

    exchange.close();
}
            // ===========================
// DELETE
// ===========================

else if (exchange.getRequestMethod().equals("DELETE")) {

    String path = exchange.getRequestURI().getPath();

    String[] parts = path.split("/");

    int id = Integer.parseInt(parts[2]);

    System.out.println("Deleting employee ID: " + id);

    employeeService.deleteEmployee(id);

    String response = "Employee Deleted Successfully";

    exchange.getResponseHeaders().add(
        "Content-Type",
        "text/plain"
    );

    exchange.sendResponseHeaders(
        200,
        response.getBytes().length
    );

    exchange.getResponseBody().write(
        response.getBytes()
    );

    exchange.close();
}
            // ===========================
            // OTHER METHODS
            // ===========================

            else {

                exchange.sendResponseHeaders(405, -1);

                exchange.close();

            }
            

        });

        // ===========================
        // START SERVER
        // ===========================

        server.start();

        System.out.println("================================");
        System.out.println("Server Started...");
        System.out.println("http://localhost:8081");
        System.out.println("================================");

    }

}