/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.w3c.dom.Document;
import tema3.TemaTest;
import tema3.TiendaXML;

/**
 *
 * @author ADMIN
 */
public class Tema3Test {
    
    private TiendaXML tienda;
    private Document document;
    
    public Tema3Test() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() throws Exception{
        tienda = new TiendaXML();
        document = tienda.cargarXML();
    }
    
    @AfterEach
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    @Test
    public void sumar() {
        TemaTest t = new TemaTest();
        assertEquals(6, t.sumar(2,3));
    }
    
    
    
    
    
    
    
    
    
    
    
}


