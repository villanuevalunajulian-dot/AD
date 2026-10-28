/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.tema6;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Tema6 {
      private static final String url= "jdbc:mysql://localhost:3307/instituto";
         private static final String usuario = "root";
         private static final String password = "root";

    public static void main(String[] args) {
       mostrarCantidadDeFilas();
       mostrarSoloNombre();
       mostrarEdadesSinRepetir();
       mostrarMayoresDeEdad();         
        
        
        
        
    
    }
  public static Connection conectar() throws SQLException{
        return DriverManager.getConnection(url,usuario,password);
    }
   
    public static void mostrarCantidadDeFilas(){
        String sql = "select count(*) from customer";
        try {
            Connection con = conectar();
           
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int cantidad = rs.getInt(1);
                System.out.println("Filas: " + cantidad);
            }
        } catch (SQLException e) {
            System.err.println("Error de conexión");
            System.err.println(e.getMessage());
        }
    }
   
    public static void mostrarSoloNombre(){
        String sql = "select first_name from customer";
        try {
            Connection con = conectar();
           
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            System.out.println("Nombres:\n");
            while (rs.next()) {
                String name = rs.getString("first_name");
                System.out.println(name);
            }
        } catch (SQLException e) {
            System.err.println("Error de conexión");
            System.err.println(e.getMessage());
        }
    }
   
    public static void mostrarEdadesSinRepetir(){
        String sql = "select distinct age from customer";
        try {
            Connection con = conectar();
           
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            System.out.println("Edades:\n");
            while (rs.next()) {
                int age = rs.getInt("age");
                System.out.println(age);
            }
        } catch (SQLException e) {
            System.err.println("Error de conexión");
            System.err.println(e.getMessage());
        }
    }
   
    public static void mostrarMayoresDeEdad(){
        String sql ="select * from customer where age > 18";
        try {
            Connection con = conectar();
           
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            System.out.println("Mayores de Edad:\n");
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("first_name");
                String email = rs.getString("id");
                int age = rs.getInt("age");
                System.out.println(id + " - " + name + " - " + email + " - " + age);
            }
        } catch (SQLException e) {
            System.err.println("Error de conexión");
            System.err.println(e.getMessage());
        }
    }
    }
    