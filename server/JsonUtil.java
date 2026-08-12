package server;

public class JsonUtil {

    public static Employee parseEmployee(String body) {

        body = body.replace("{", "");
        body = body.replace("}", "");
        body = body.replace("\"", "");
        body = body.replace("\n", "");
        body = body.replace("\r", "");

        String[] fields = body.split(",");

        int id = 0;
        String name = "";
        String department = "";

        for (String field : fields) {

            String[] keyValue = field.split(":");

            String key = keyValue[0].trim();
            String value = keyValue[1].trim();

            if (key.equals("id")) {
                id = Integer.parseInt(value);
            }
            else if (key.equals("name")) {
                name = value;
            }
            else if (key.equals("department")) {
                department = value;
            }
        }

        return new Employee(id, name, department);
    }
}