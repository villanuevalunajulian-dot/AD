/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tema1ad;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.io.IOException;
import java.util.Date;

/**
 *
 * @author ADMIN
 */
public class Tema1_Actividad1 {
public static void main(String[] args) throws IOException {

        /*
        - ============================================================
        - TEMA 1 - INTRODUCCIÓN AL MANEJO DE FICHEROS
        - ACCESO A DATOS - EJERCICIOS
        - ============================================================
        */

        // ============================================================
        // EJERCICIO 1 - CREAR UN FICHERO
        // ============================================================
        // TODO: EJERCICIO 1
        File f1 = new File("datos.txt");
        if (!f1.exists()) {
            f1.createNewFile();
            System.out.println("Fichero creado correctamente.");
        } else {
            System.out.println("El fichero ya existe.");
        }
        System.out.println("Nombre del fichero: " + f1.getName());


        // ============================================================
        // EJERCICIO 2 - INFORMACIÓN DEL FICHERO
        // ============================================================
        // TODO: EJERCICIO 2
        File f2 = new File("datos.txt");
        System.out.println("\n--- INFORMACIÓN DEL FICHERO ---");
        System.out.println("Nombre: " + f2.getName());
        System.out.println("Ruta: " + f2.getPath());
        System.out.println("Ruta absoluta: " + f2.getAbsolutePath());
        System.out.println("Directorio padre: " + f2.getParent());
        System.out.println("¿Existe?: " + f2.exists());
        System.out.println("¿Se puede leer?: " + f2.canRead());
        System.out.println("¿Se puede escribir?: " + f2.canWrite());


        // ============================================================
        // EJERCICIO 3 - CREAR DIRECTORIO Y MOVER UN FICHERO
        // ============================================================
        // TODO: EJERCICIO 3
        File dirBackup = new File("backup");
        if (!dirBackup.exists()) {
            dirBackup.mkdir();
        }

        if (dirBackup.exists()) {
            File destino = new File(dirBackup, f1.getName());
            boolean movido = f1.renameTo(destino);
            if (movido) {
                System.out.println("\nFichero movido con éxito a backup/datos.txt");
            }
        }


        // ============================================================
        // EJERCICIO 4 - ESCRIBIR TEXTO CON FileWriter
        // ============================================================
        // TODO: EJERCICIO 4
        FileWriter fw4 = new FileWriter("alumnos.txt");
        fw4.write("Juan\n");
        fw4.write("María\n");
        fw4.write("Pedro\n");
        fw4.write("Ana\n");
        fw4.close();
        System.out.println("\nEscribiendo en alumnos.txt...");


        // ============================================================
        // EJERCICIO 5 - LEER UN CARÁCTER CON FileReader
        // ============================================================
        // TODO: EJERCICIO 5
        FileWriter fwAux = new FileWriter("mensaje.txt");
        fwAux.write("Hola");
        fwAux.close();

        FileReader fr5 = new FileReader("mensaje.txt");
        int primerCaracter = fr5.read();
        if (primerCaracter != -1) {
            System.out.println("\nPrimer carácter de mensaje.txt: " + (char) primerCaracter);
        }
        fr5.close();


        // ============================================================
        // EJERCICIO 6 - ¿ACCESO SECUENCIAL O ALEATORIO?
        // ============================================================
        // TODO: EJERCICIO 6
        /*
        A) ACCESO SECUENCIAL: Se leen los elementos en orden estricto desde el primero hasta el último sin saltos.
        B) ACCESO ALEATORIO / DIRECTO: Permite posicionarse con seek() directamente en una ubicación específica sin recorrer el resto del fichero.
        C) ACCESO SECUENCIAL: Se necesita procesar cada byte/carácter en orden continuo de principio a fin para copiar todo el contenido.
        D) ACCESO ALEATORIO / DIRECTO: Permite modificar el contenido de una posición concreta en el fichero sin necesidad de reescribirlo entero.
        E) ACCESO SECUENCIAL: Para leer el fichero completo de principio a fin, el recorrido en orden secuencial es la forma natural e interactiva.
        */


        // ============================================================
        // EJERCICIO 7 - LECTURA DE BYTES
        // ============================================================
        // TODO: EJERCICIO 7
        FileWriter fwBytes = new FileWriter("bytes.txt");
        fwBytes.write("Texto de prueba");
        fwBytes.close();

        FileInputStream fis7 = new FileInputStream("bytes.txt");
        int byteLeido = fis7.read();
        System.out.println("\nValor en byte leído: " + byteLeido + " (carácter: " + (char) byteLeido + ")");
        fis7.close();


        // ============================================================
        // EJERCICIO 8 - ESCRITURA DE BYTES
        // ============================================================
        // TODO: EJERCICIO 8
        FileOutputStream fos8 = new FileOutputStream("salidaBytes.txt");
        fos8.write(68); 
        fos8.close();
        System.out.println("Byte 68 ('D') escrito correctamente en salidaBytes.txt");


        // ============================================================
        // EJERCICIO 9 - RandomAccessFile
        // ============================================================
        // TODO: EJERCICIO 9
        FileWriter fwLetras = new FileWriter("letras.txt");
        fwLetras.write("ABCDEFGHIJ");
        fwLetras.close();

        RandomAccessFile raf9 = new RandomAccessFile("letras.txt", "rw");
        raf9.seek(5); 
        int charRaf = raf9.read();
        System.out.println("\n--- RandomAccessFile ---");
        System.out.println("Carácter leído: " + (char) charRaf + " | Posición del puntero: " + raf9.getFilePointer());
        raf9.close();


        // ============================================================
        // EJERCICIO 10 - ESCRIBIR CON RandomAccessFile
        // ============================================================
        // TODO: EJERCICIO 10
        RandomAccessFile raf10 = new RandomAccessFile("letras.txt", "rw");
        raf10.seek(5);
        raf10.write(68); 
        raf10.close();


        // ============================================================
        // EJERCICIO 11 - LEER VARIOS BYTES
        // ============================================================
        // TODO: EJERCICIO 11
        RandomAccessFile raf11 = new RandomAccessFile("letras.txt", "r");
        byte[] buffer = new byte[10];
        int bytesLeidos = raf11.read(buffer, 0, 10);
        System.out.println("Bytes leídos: " + bytesLeidos);
        raf11.close();


        // ============================================================
        // EJERCICIO 12 - Ejercicio FINAL: GESTOR DE FICHEROS
        // ============================================================
        // TODO: Ejercicio FINAL
        System.out.println("\n=================================");
        System.out.println("    EJERCICIO FINAL: GESTOR      ");
        System.out.println("=================================");

        // 1. Crear el fichero si no existe
        File gestorFile = new File("alumnos_gestor.txt");
        if (!gestorFile.exists()) {
            gestorFile.createNewFile();
        }

        // 2. Mostrar información del fichero
        System.out.println("Nombre: " + gestorFile.getName());
        System.out.println("Ruta: " + gestorFile.getPath());
        System.out.println("Ruta absoluta: " + gestorFile.getAbsolutePath());
        System.out.println("¿Existe?: " + gestorFile.exists());
        System.out.println("¿Se puede leer?: " + gestorFile.canRead());
        System.out.println("¿Se puede escribir?: " + gestorFile.canWrite());

        // 3. Escribir en el fichero
        FileWriter fwGestor = new FileWriter(gestorFile);
        fwGestor.write("Juan\n");
        fwGestor.write("Ana\n");
        fwGestor.write("Pedro\n");
        fwGestor.write("Laura\n");
        fwGestor.close();

        // 4. Leer el primer carácter
        FileReader frGestor = new FileReader(gestorFile);
        int primerCharGestor = frGestor.read();
        if (primerCharGestor != -1) {
            System.out.println("Primer carácter en el fichero: " + (char) primerCharGestor);
        }
        frGestor.close();

        // 5. Crear directorio "backup"
        File dirBackupGestor = new File("backup");
        if (!dirBackupGestor.exists()) {
            dirBackupGestor.mkdir();
        }

        // 6. Mover el fichero a backup/alumnos.txt
        File destinoGestor = new File(dirBackupGestor, "alumnos.txt");
        if (destinoGestor.exists()) {
            destinoGestor.delete(); // Borramos previa copia si existiera para renombrar limpiamente
        }
        boolean exitoMovido = gestorFile.renameTo(destinoGestor);

        // 7. Mostrar la fecha de última modificación
        if (exitoMovido) {
            System.out.println("Fichero movido con éxito a: " + destinoGestor.getPath());
            System.out.println("Última modificación: " + new Date(destinoGestor.lastModified()));
        } else {
            System.out.println("Última modificación: " + new Date(gestorFile.lastModified()));
        }
    }
}    
    
    
    
    
    
    
    

