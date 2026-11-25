/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.medac;

import es.medac.config.DatabaseConfig;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
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
        System.out.println("5. Modificar una reserva");
        System.out.println("6. Buscar reservas de un alumno");
        System.out.println("7. Buscar alumno por texto (LIKE)");
        System.out.println("8. Buscar reservas de una fecha");
        System.out.println("9. Buscar por aula y fecha (AND)");
        System.out.println("10. Buscar entre dos fechas (BETWEEN)");
        System.out.println("11. Buscar reservas que no sean de un aula (NOT)");
        System.out.println("12. Probar transaccion (SAVEPOINT)");
        System.out.println("0. Salir");

    opcion = leerNumero("Selecciona una opción: ");

    switch (opcion) {
        case 1 -> {
    String alumno = leerTexto("Nombre del alumno: ");
    String aula = leerTexto("Aula: ");
    Date fecha = leerFecha("Fecha (YYYY-MM-DD): ");

            String sql = "insert into reserva (alumno, aula, fecha) values (?, ?, ?)";
            try (Connection con = DatabaseConfig.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
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
            String sql = "select * from reserva order by fecha asc";
            try (Connection con = DatabaseConfig.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

                mostrarReservas(rs, "No se han encontrado reservas registradas.");
            } catch (SQLException e) {
                System.out.println("Error en la base de datos: " + e.getMessage());
            }
        }
        case 3 -> {
            String aulaBuscada = leerTexto("Introduce el aula a buscar: ");

            String sql = "select * from reserva where aula = ? order by fecha asc";
            try (Connection con = DatabaseConfig.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, aulaBuscada);

                try (ResultSet rs = ps.executeQuery()) {
                    mostrarReservas(rs, "No existen reservas para el aula " + aulaBuscada);
                }
            } catch (SQLException e) {
                System.out.println("Error en la base de datos: " + e.getMessage());
            }
        }
        case 4 -> {
            int id = leerNumero("Introduce el ID de la reserva a eliminar: ");

            String sql = "delete from reserva where id = ?";
            try (Connection con = DatabaseConfig.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
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
        case 5 -> {
           int id = leerNumero("Introduce el ID de la reserva a modificar: ");

            if (!existeReserva(id)) {
                System.out.println("No existe ninguna reserva con ese ID.");
            } else {
                String alumno = leerTexto("Nuevo nombre del alumno: ");
                String aula = leerTexto("Nueva aula: ");
                Date fecha = leerFecha("Nueva fecha (YYYY-MM-DD): ");

                String sql = "update reserva set alumno = ?, aula = ?, fecha = ? where id = ?";
                try (Connection con = DatabaseConfig.getConnection();
                     PreparedStatement ps = con.prepareStatement(sql)) {
                    ps.setString(1, alumno);
                    ps.setString(2, aula);
                    ps.setDate(3, fecha);
                    ps.setLong(4, id);

                    int filasAfectadas = ps.executeUpdate();
                    if (filasAfectadas > 0) {
                        System.out.println("Reserva modificada correctamente.");
                    } else {
                        System.out.println("No se ha podido modificar la reserva.");
                    }
                } catch (SQLException e) {
                    System.out.println("Error en la base de datos: " + e.getMessage());
                }
            }
        }
        case 6 -> {
            String alumno = leerTexto("Nombre del alumno: ");

            String sql = "select * from reserva where alumno = ? order by fecha asc";
            try (Connection con = DatabaseConfig.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, alumno);

                try (ResultSet rs = ps.executeQuery()) {
                    mostrarReservas(rs, "No hay reservas de " + alumno);
                }
            } catch (SQLException e) {
                System.out.println("Error en la base de datos: " + e.getMessage());
            }
        }
        case 7 -> {
            String texto = leerTexto("Texto a buscar en el nombre: ");
            String patron = "%" + texto + "%";

            String sql = "select * from reserva where alumno like ? order by fecha asc";
            try (Connection con = DatabaseConfig.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, patron);

                try (ResultSet rs = ps.executeQuery()) {
                    mostrarReservas(rs, "No hay alumnos que contengan \"" + texto + "\"");
                }
            } catch (SQLException e) {
                System.out.println("Error en la base de datos: " + e.getMessage());
            }
        }
        case 8 -> {
            Date fecha = leerFecha("Fecha (YYYY-MM-DD): ");

            String sql = "select * from reserva where fecha = ? order by id";
            try (Connection con = DatabaseConfig.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setDate(1, fecha);

                try (ResultSet rs = ps.executeQuery()) {
                    mostrarReservas(rs, "No hay reservas el día " + fecha);
                }
            } catch (SQLException e) {
                System.out.println("Error en la base de datos: " + e.getMessage());
            }
        }
        case 9 -> {
            String aula = leerTexto("Aula: ");
            Date fecha = leerFecha("Fecha (YYYY-MM-DD): ");

            String sql = "select * from reserva where aula = ? and fecha = ?";
            try (Connection con = DatabaseConfig.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, aula);
                ps.setDate(2, fecha);

                try (ResultSet rs = ps.executeQuery()) {
                    mostrarReservas(rs, "No hay reservas del aula " + aula + " ese día.");
                }
            } catch (SQLException e) {
                System.out.println("Error en la base de datos: " + e.getMessage());
            }
        }
        case 10 -> {
            Date inicio = leerFecha("Fecha de inicio (YYYY-MM-DD): ");
            Date fin = leerFecha("Fecha de fin (YYYY-MM-DD): ");

            String sql = "select * from reserva where fecha between ? and ? order by fecha asc";
            try (Connection con = DatabaseConfig.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setDate(1, inicio);
                ps.setDate(2, fin);

                try (ResultSet rs = ps.executeQuery()) {
                    mostrarReservas(rs, "No hay reservas entre esas fechas.");
                }
            } catch (SQLException e) {
                System.out.println("Error en la base de datos: " + e.getMessage());
            }
        }
        case 11 -> {
            String aula = leerTexto("Aula que quieres excluir: ");

            String sql = "select * from reserva where not aula = ? order by fecha asc";
            try (Connection con = DatabaseConfig.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, aula);

                try (ResultSet rs = ps.executeQuery()) {
                    mostrarReservas(rs, "No hay reservas de otras aulas.");
                }
            } catch (SQLException e) {
                System.out.println("Error en la base de datos: " + e.getMessage());
            }
        }
        case 12 -> {
            int id = leerNumero("ID de la reserva para probar la transacción: ");

            if (!existeReserva(id)) {
                System.out.println("No existe ninguna reserva con ese ID.");
            } else {
                Connection con = null;
                try {
                con = DatabaseConfig.getConnection();
                con.setAutoCommit(false);

                // Queremos cambiar la fecha
                PreparedStatement ps1 = con.prepareStatement("update reserva set fecha = '2026-12-31' where id = ?");
                ps1.setLong(1, id);
                ps1.executeUpdate();
                ps1.close();
                System.out.println("Fecha cambiada a 2026-12-31.");

                // Punto donde guardamos 
                Savepoint punto = con.setSavepoint("punto_1");

                // Cambiamos el aula
                PreparedStatement ps2 = con.prepareStatement("update reserva set aula = 'AULA-PRUEBA' where id = ?");
                ps2.setLong(1, id);
                ps2.executeUpdate();
                ps2.close();
                System.out.println("Aula cambiada a AULA-PRUEBA.");

                // Cancelar el cambio de aula
                con.rollback(punto);
                System.out.println("Rollback hasta el punto de guardado.");

                // Guardar los cambios
                con.commit();
                System.out.println("Commit realizado.");

                // Mostramos como ha quedado la reserva
                PreparedStatement ps3 = con.prepareStatement("select * from reserva where id = ?");
                ps3.setLong(1, id);
                ResultSet rs = ps3.executeQuery();
                System.out.println("Reserva final:");
                mostrarReservas(rs, "No se ha encontrado la reserva.");
                rs.close();
                ps3.close();

                } catch (SQLException e) {
                System.out.println("Error en la transacción: " + e.getMessage());
                if (con != null) {
                     try {
                        con.rollback();
                        } catch (SQLException ex) {
                          System.out.println(ex.getMessage());
                        }
                    }
                } finally {
                if (con != null) {
                    try {
                        con.setAutoCommit(true);
                        con.close();
                    } catch (SQLException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }
            }
        }
        case 0 ->
            System.out.println("Hasta pronto");
        default ->
            System.out.println("Opción inválida.");
    }
}
        scanner.close();
    }

    // Recorre el ResultSet y muestra las reservas, o el mensaje si no hay ninguna
    public static void mostrarReservas(ResultSet rs, String mensajeSiNoHay) throws SQLException {
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
            System.out.println(mensajeSiNoHay);
        }
    }

    // Comprueba si existe una reserva con ese ID
    public static boolean existeReserva(int id) {
        String sql = "select id from reserva where id = ?";
        try (Connection con = DatabaseConfig.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.out.println("Error en la base de datos: " + e.getMessage());
            return false;
        }
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
