/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tema2ad;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
/**
 *
 * @author ADMIN
 */
public class Ejercicio2_CopiaEstadisticas {
    private static int totalCaracteres = 0;
    private static int totalLineas = 0;

    public static void main(String[] args) {
    File ficheroOrigen = new File("origen.txt");
    File ficheroDestino = new File("destino.txt");

    // Creamos un fichero origen de prueba si no existe
    if (!ficheroOrigen.exists()) {
    try (FileWriter fw = new FileWriter(ficheroOrigen)) {
        fw.write("Linea 1: Ejemplo de texto de origen.\n");
        fw.write("Linea 2: Segunda linea del archivo.\n");
        fw.write("Linea 3: Copia de seguridad finalizada.");
    } catch (IOException e) {
        e.printStackTrace();
    }
    }

    try {
    // Conectamos las tuberías
    PipedOutputStream pos = new PipedOutputStream();
    PipedInputStream pis = new PipedInputStream(pos);

    // Hilo Emisor
    Thread hiloEmisor = new Thread(new Runnable() {
        @Override
        public void run() {
            try (FileInputStream fis = new FileInputStream(ficheroOrigen)) {
                int dato;
                while ((dato = fis.read()) != -1) {
                    pos.write(dato);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    pos.close();
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
    try (FileOutputStream fos = new FileOutputStream(ficheroDestino)) {
        int dato;
        while ((dato = pis.read()) != -1) {
            fos.write(dato);
            totalCaracteres++;
            if (dato == '\n') {
                totalLineas++;
            }
        }
        if (totalCaracteres > 0 && totalLineas == 0) {
            totalLineas = 1;
        } else if (totalCaracteres > 0) {
            totalLineas++; 
        }
    } catch (IOException e) {
        e.printStackTrace();
    } finally {
        try {
            pis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
});

    // Iniciar hilos
    hiloEmisor.start();
    hiloReceptor.start();

    // Esperar a que terminen ambos hilos
    hiloEmisor.join();
    hiloReceptor.join();

    // Mostrar resultados finales
    System.out.println("--- PROCESO TERMINADO ---");
    System.out.println("Fichero origen: " + ficheroOrigen.getAbsolutePath());
    System.out.println("Fichero destino: " + ficheroDestino.getAbsolutePath());
    System.out.println("Tamaño origen: " + ficheroOrigen.length() + " bytes");
    System.out.println("Tamaño destino: " + ficheroDestino.length() + " bytes");
    System.out.println("Líneas procesadas: " + totalLineas);
    System.out.println("Caracteres procesados: " + totalCaracteres);

    } catch (IOException | InterruptedException e) {
    e.printStackTrace();
    }
    }
    }
