package es.medac;

import es.medac.config.DatabaseConfig;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 *
 * @author ADMIN
 */
public class Main {

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int opcion = -1;

    while (opcion != 0) {
        System.out.println("=================================");
        System.out.println("     GESTOR DE RESERVAS");
        System.out.println("=================================");
        System.out.println("1. Registrar una reserva");
        System.out.println("2. Mostrar todas las reservas");
        System.out.println("3. Buscar reservas por aula");
        System.out.println("4. Eliminar una reserva");
        System.out.println("0. Salir");

          opcion = leerNumero("Selecciona una opción: ");

     switch (opcion) {
    case 1 -> {
         String alumno = leerTexto("Nombre del alumno: ");
         String aula = leerTexto("Aula: ");
         Date fecha = leerFecha("Fecha (YYYY-MM-DD): ");

         String sql = "insert into reserva (alumno, aula, fecha) VALUES (?, ?, ?)";
            try (Connection con = DatabaseConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
                    ps.setString(1, alumno);
                    ps.setString(2, aula);
                    ps.setDate(3, fecha);
                    ps.executeUpdate();

       System.out.println("Reserva registrada correctamente.");
          } catch (SQLException e) {
       System.out.println("Error en la base de datos: " + e.getMessage());
                    }
          }
    case 2 -> {
         String sql = "select * from reserva order by id";
             try (Connection con = DatabaseConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
             boolean hayReservas = false;
                while (rs.next()) {
                    hayReservas = true;
             long id = rs.getLong("id");
                String alumno = rs.getString("alumno");
                String aula = rs.getString("aula");
                Date fecha = rs.getDate("fecha");
                System.out.println("ID: " + id + " | Alumno: " + alumno + " | Aula: " + aula + " | Fecha: " + fecha);
                        }
                if (!hayReservas) {
            System.out.println("No se han encontrado reservas registradas.");
                        }
                    } catch (SQLException e) {
            System.out.println("Error en la base de datos: " + e.getMessage());
                    }
                }
        case 3 -> {
        String aulaBuscada = leerTexto("Introduce el aula a buscar: ");
        String sql = "select * from reserva where aula = ? order by id";
        try (Connection con = DatabaseConfig.getConnection();
              PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, aulaBuscada);

        try (ResultSet rs = ps.executeQuery()) {
            boolean hayReservas = false;
            while (rs.next()) {
                hayReservas = true;
                long id = rs.getLong("id");
                String alumno = rs.getString("alumno");
                String aula = rs.getString("aula");
                Date fecha = rs.getDate("fecha");
                System.out.println("ID: " + id + " | Alumno: " + alumno + " | Aula: " + aula + " | Fecha: " + fecha);
             }
            if (!hayReservas) {
                System.out.println("No existen reservas para el aula " + aulaBuscada);
            }
        }
            } catch (SQLException e) {
                System.out.println("Error en la base de datos: " + e.getMessage());
                    }
                }
     case 4 -> {
        int id = leerNumero("Introduce el ID de la reserva a eliminar: ");

          String sql = "DELETE FROM reserva WHERE id = ?";
            try (Connection con = DatabaseConfig.getConnection();
                  PreparedStatement ps = con.prepareStatement(sql)) {

             ps.setLong(1, id);
             int filasAfectadas = ps.executeUpdate();
             if (filasAfectadas > 0) {
                 System.out.println("Reserva eliminada correctamente.");
             } else {
                 System.out.println("No existe ninguna reserva con ese ID.");
            }
            } catch (SQLException e) {
                System.out.println("Error en la base de datos: " + e.getMessage());
                    }
                }
     case 0 -> System.out.println("Hasta pronto");
                default -> System.out.println("Opción inválida.");
            }
        }
        scanner.close();
    }

    // Evita entradas vacías
    public static String leerTexto(String mensaje) {
        String texto = "";
        while (texto.isEmpty()) {
            System.out.print(mensaje);
            texto = scanner.nextLine().trim();
            if (texto.isEmpty()) {
            System.out.println("Este campo no puede estar vacío.");
            }
        }
        return texto;
    }

    // Evita que el programa se estropee con letras 
    public static int leerNumero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Introduce un número válido.");
            }
        }
    }

    // Evita fechas con formato incorrecto
    public static Date leerFecha(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                LocalDate fecha = LocalDate.parse(scanner.nextLine().trim());
                return Date.valueOf(fecha);
            } catch (DateTimeParseException e) {
                System.out.println("Formato de fecha no válido, usa YYYY-MM-DD.");
            }
        }
    }
}
