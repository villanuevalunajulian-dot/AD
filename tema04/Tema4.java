/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.tema4;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author ADMIN
 */
public class Tema4 {

    public static void main(String[] args) {
      
        String url = "jdbc:mysql://localhost:3307/AD";
        String usuario = "julian";
        String password = "julian123";
        
        try {
        
        Connection conexion = DriverManager.getConnection(
                url,
                usuario,
                password
        );
        
        System.out.println("CONEXIÓN CORRECTA");
          String sql = "SELECT id, nombre, email FROM usuarios";
     PreparedStatement ps= conexion.prepareStatement(sql);
     ResultSet rs = ps.executeQuery();
    while (rs.next() ){
  int id = rs.getInt ("id");
  String nombre= rs.getString("nombre");
  String email= rs.getString("email");
  System.out.println(id +"-"+ nombre +"-"+ email);
}
        conexion.close();
        
    }catch (SQLException e) {
    
    System.out.println("ERROR DE CONEXIÓN");
    e.getMessage();
    }
        


    }
}
        
    

