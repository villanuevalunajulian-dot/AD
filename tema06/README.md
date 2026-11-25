# Gestor de reservas de aulas (parte 2)
Aplicación de consola en Java para gestionar las reservas de aulas de MEDAC Fuenlabrada, diseñada por un alumno de 2DAM.
Se conecta con JDBC a una base de datos PostgreSQL que se ejecuta en Docker.
Los datos se guardan en un volumen, así que no se pierden aunque se borre el contenedor.

En esta segunda parte se han añadido consultas con filtros, la modificación de reservas, búsquedas con LIKE, una prueba de transacciones y ejercicios con CREATE, ALTER, DROP y TRUNCATE.

## Requisitos a tener en cuenta
- Java 17
- Maven en NetBeans
- Docker Desktop

## Archivos del proyecto
- compose.yaml: configuración de PostgreSQL en Docker.
- crear_tabla.sql: script para crear la tabla reserva.
- pruebas_ddl.sql: ejercicios de CREATE, ALTER, TRUNCATE y DROP con una tabla de pruebas.
- pom.xml: configuración de Maven con el driver de PostgreSQL.
- Main.java: el menú y las operaciones con la base de datos.
- DatabaseConfig.java: la conexión con la base de datos.
- Evidencias pt2.pdf: respuestas a las preguntas y capturas de las pruebas.

## Cómo ejecutarlo
1. Abrir una terminal en la carpeta del proyecto y definir la contraseña de la base de datos:

   $env:DB_PASSWORD="tu_contraseña"

2. Levantar PostgreSQL:

   docker compose up -d

   Para comprobar que está funcionando:

   docker compose ps

3. Crear la tabla (solo si no existe). Entrar en PostgreSQL:

   docker exec -it postgres_medac psql -U alumno -d reservas

   Pegar el contenido de crear_tabla.sql y salir con \q.

4. En NetBeans, poner la contraseña para que la lea el programa:
   Properties > Actions > Run project > Set Properties y añadir:

   Env.DB_PASSWORD=tu_contraseña

5. Ejecutar el proyecto.

La contraseña no está escrita en el código, el programa la lee de la variable de entorno DB_PASSWORD.

## Funcionamiento
Al ejecutar sale este menú:
1. Registrar una reserva
2. Mostrar todas las reservas
3. Buscar reservas por aula
4. Eliminar una reserva
5. Modificar una reserva
6. Buscar reservas de un alumno
7. Buscar alumno por texto (LIKE)
8. Buscar reservas de una fecha
9. Buscar por aula y fecha (AND)
10. Buscar entre dos fechas (BETWEEN)
11. Buscar reservas que no sean de un aula (NOT)
12. Probar transaccion (SAVEPOINT)
0. Salir

- Opción 1: pide el nombre del alumno, el aula y la fecha y guarda la reserva con INSERT. El ID se crea solo.
- Opción 2: muestra todas las reservas ordenadas por fecha (ORDER BY fecha ASC).
- Opción 3: muestra las reservas de un aula (WHERE).
- Opción 4: elimina una reserva por su ID con DELETE. Si no existe, lo avisa.
- Opción 5: pide el ID, comprueba que existe y deja cambiar el alumno, el aula o la fecha con UPDATE. Si el ID no existe, lo avisa.
- Opción 6: muestra las reservas de un alumno concreto.
- Opción 7: busca alumnos que contengan un texto. Por ejemplo, "Mar" encuentra Maria y Mario (LIKE).
- Opción 8: muestra las reservas de un día.
- Opción 9: muestra las reservas de un aula en un día concreto (AND).
- Opción 10: muestra las reservas entre dos fechas (BETWEEN).
- Opción 11: muestra todas las reservas menos las de un aula (NOT).
- Opción 12: prueba de transacción, explicada más abajo.
- Opción 0: cierra el programa.

En todas las consultas que usan datos del usuario se usa PreparedStatement con ?, nunca se pega el texto directamente en el SQL.

## Transacción (opción 12)
1. Se desactiva el autocommit con setAutoCommit(false).
2. Se cambia la fecha de la reserva.
3. Se crea un SAVEPOINT.
4. Se cambia el aula.
5. Se hace rollback hasta el SAVEPOINT, así que se deshace el cambio de aula.
6. Se hace commit, así que se guarda el cambio de fecha.
7. Se muestra la reserva para ver cómo ha quedado.

Si hay un error antes del commit se hace rollback de todo. Al final se vuelve a activar el autocommit.

## Ejercicios de CREATE, ALTER, DROP y TRUNCATE
Están en pruebas_ddl.sql. Se hacen sobre una tabla de pruebas (observacion_reserva), no sobre la tabla reserva.
Para ejecutarlos, entrar en PostgreSQL y pegar el contenido del archivo:

   docker exec -it postgres_medac psql -U alumno -d reservas

El último SELECT da error a propósito, porque después del DROP la tabla ya no existe.

## Validaciones
- No se puede dejar vacío el nombre del alumno ni el aula.
- La fecha tiene que tener el formato YYYY-MM-DD y ser una fecha real.
- Si se escriben letras en el menú o en el ID, vuelve a preguntar.
- Si hay algún problema con la base de datos, se muestra un mensaje de error.

## Prueba de persistencia
1. Registrar varias reservas, hacer las búsquedas, modificar una y eliminar otra.
2. Cerrar la aplicación.
3. docker compose down
4. docker compose up -d
5. Volver a ejecutar la aplicación y mostrar las reservas: siguen estando.

No hay que usar docker compose down -v, porque eso borra el volumen y se pierden los datos.

## Evidencias
Las respuestas a las preguntas y las capturas de las pruebas están en Evidencias pt2.pdf.
