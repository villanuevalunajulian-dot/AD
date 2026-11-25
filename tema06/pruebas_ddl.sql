-- Punto 14: CREATE, ALTER, DROP y TRUNCATE
-- Se hace sobre una tabla de pruebas,no sobre la tabla reserva.

-- 14.1 Crear la tabla auxiliar
CREATE TABLE observacion_reserva (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    reserva_id BIGINT,
    observacion VARCHAR(250)
);
-- Añadir la columna de fecha de creacion
ALTER TABLE observacion_reserva
ADD COLUMN fecha_creacion DATE;
-- Ver como ha quedado la tabla
\d observacion_reserva

-- 14.2 Eliminar la tabla entera con DROP
DROP TABLE observacion_reserva;

-- 14.3 Meter varios registros de prueba
INSERT INTO observacion_reserva (reserva_id, observacion, fecha_creacion) VALUES (2, 'Necesita proyector', '2026-11-25');
INSERT INTO observacion_reserva (reserva_id, observacion, fecha_creacion) VALUES (3, 'Aula reservada toda la mañana', '2026-11-25');
INSERT INTO observacion_reserva (reserva_id, observacion, fecha_creacion) VALUES (4, 'Clase de repaso', '2026-11-25');
SELECT * FROM observacion_reserva;

-- Vaciar la tabla con TRUNCATE 
TRUNCATE TABLE observacion_reserva;
SELECT * FROM observacion_reserva;

-- Ahora da error porque la tabla ya no existe
SELECT * FROM observacion_reserva;
