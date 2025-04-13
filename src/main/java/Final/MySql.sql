CREATE DATABASE IF NOT EXISTS login_app_db;
USE login_app_db;

CREATE TABLE usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50),
    apellido VARCHAR(50),
    telefono VARCHAR(20),
    correo VARCHAR(100)
);

commit;

select * from usuarios;

