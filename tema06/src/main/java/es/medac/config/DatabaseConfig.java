package es.medac.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author ADMIN
 */
public class DatabaseConfig {

    public static Connection getConnection() throws SQLException {
        // Los datos de conexion se leen de las variables de entorno
        String url = System.getenv("DB_URL");
        if (url == null || url.isBlank()) {
            url = "jdbc:postgresql://localhost:5432/reservas";
        }

        String user = System.getenv("DB_USER");
        if (user == null || user.isBlank()) {
            user = "alumno";
        }

        // La contraseña no se pone en el codigo, tiene que venir del entorno
        String password = System.getenv("DB_PASSWORD");
        if (password == null || password.isBlank()) {
            throw new SQLException("No se ha definido la variable de entorno DB_PASSWORD.");
        }

        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new SQLException("No se pudo conectar con PostgreSQL. Comprueba que el contenedor está arrancado (docker compose ps).");
        }
    }
}
