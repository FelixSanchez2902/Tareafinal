package Final;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/registros";
        String usuario = "root";
        String password = "admin";

        try {
            Connection connection = DriverManager.getConnection(url, usuario, password);
            System.out.println("Conexion exitosa");
        } catch (SQLException e) {
            System.out.println("Conexion fallida");
        }

    }

}