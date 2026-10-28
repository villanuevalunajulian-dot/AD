/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tema2ad;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
/**
 *
 * @author ADMIN
 */
public class Ejercicio1_Pipe {
     public static void main(String[] args) {
    // Creamos un fichero de prueba por si no existe
    File ficheroOrigen = new File("entrada.txt");
    if (!ficheroOrigen.exists()) {
        try (FileWriter fw = new FileWriter(ficheroOrigen)) {
            fw.write("Hola, esta es la primera linea.\n");
            fw.write("Esta es la segunda linea del fichero.\n");
            fw.write("Y esta es la ultima linea.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

try {
    PipedOutputStream pos = new PipedOutputStream();
    PipedInputStream pis = new PipedInputStream(pos);

    // Hilo Emisor
    Thread hiloEmisor = new Thread(new Runnable() {
        @Override
    public void run() {
        try (FileInputStream fis = new FileInputStream(ficheroOrigen)) {
            int dato;
            while ((dato = fis.read()) != -1) {
                pos.write(dato); // Enviamos el byte al pipe
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                pos.close(); // Cerramos la salida para avisar al receptor de que terminó
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
        });

// Hilo Receptor
    Thread hiloReceptor = new Thread(new Runnable() {
    @Override
    public void run() {
        try {
            int dato;
            while ((dato = pis.read()) != -1) {
                System.out.print((char) dato); // Convertimos el byte a carácter
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                pis.close(); // Cerramos la entrada
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    });

    // Arrancamos los hilos
    hiloEmisor.start();
    hiloReceptor.start();

    // Esperamos a que ambos hilos terminen
    hiloEmisor.join();
    hiloReceptor.join();

    System.out.println("\n\nLectura completada correctamente.");

    } catch (IOException | InterruptedException e) {
    e.printStackTrace();
    }
    }
    }

