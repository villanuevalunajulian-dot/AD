# Gestor de reservas de aulas
Aplicación de consola en Java para gestionar las reservas de aulas de MEDAC Fuenlabrada, diseñada por alumno de 2DAM
Se conecta con JDBC a una base de datos PostgreSQL que se ejecuta en Docker.
Los datos se guardan en un volumen, así que no se pierden aunque se borre el contenedor.

## Requisitos a tener en cuenta
- Java 17
- Maven en NetBeans
- Docker Desktop

## Archivos del proyecto
- compose.yaml: configuración de PostgreSQL en Docker.
- crear_tabla.sql: script para crear la tabla reserva.
- pom.xml: configuración de Maven con el driver de PostgreSQL.
- Main.java: el menú y las operaciones con la base de datos.
- DatabaseConfig.java: la conexión con la base de datos.
- evidencias.pdf: capturas de las pruebas.

## Cómo ejecutarlo
1. Abrir una terminal en la carpeta del proyecto y definir la contraseña de la base de datos:

   $env:DB_PASSWORD="tu_contraseña"

2. Levantar PostgreSQL:

   docker compose up -d

   Para comprobar que está funcionando:

   docker compose ps

3. Crear la tabla. Entrar en PostgreSQL:

   docker exec -it postgres_medac psql -U alumno -d reservas

   Pegar el contenido de crear_tabla.sql y salir con \q.

4. En NetBeans, poner la contraseña para que la lea el programa:
   Properties > Actions > Run project > Set Properties y añadir:

   Env.DB_PASSWORD=tu_contraseña

5. Ejecutar el proyecto
La contraseña no está escrita en el código, el programa la lee de la variable de entorno DB_PASSWORD.

## Funcionamiento
Al ejecutar sale este menú:
1. Registrar una reserva
2. Mostrar todas las reservas
3. Buscar reservas por aula
4. Eliminar una reserva
0. Salir

- Opción 1: pide el nombre del alumno, el aula y la fecha (formato YYYY-MM-DD) y guarda la reserva. El ID se crea solo.
- Opción 2: muestra todas las reservas. Si no hay ninguna, lo avisa.
- Opción 3: pide un aula y muestra solo sus reservas. Si no tiene, lo avisa.
- Opción 4: pide el ID de una reserva y la elimina. Si no existe, lo avisa.
- Opción 0: cierra el programa.

El menú se repite hasta elegir la opción 0.

## Validaciones
- No se puede dejar vacío el nombre del alumno ni el aula.
- La fecha tiene que tener el formato YYYY-MM-DD y ser una fecha real.
- Si se escriben letras en el menú o en el ID, vuelve a preguntar.
- Si hay algún problema con la base de datos, se muestra un mensaje de error.

## Prueba de persistencia
1. Registrar varias reservas, mostrarlas, buscar por aula y eliminar una.
2. Cerrar la aplicación.
3. docker compose down
4. docker compose up -d
5. Volver a ejecutar la aplicación y mostrar las reservas: siguen estando.

No hay que usar docker compose down -v, porque eso borra el volumen y se pierden los datos.

## Evidencias
Las capturas de las pruebas están en evidencias.pdf.

