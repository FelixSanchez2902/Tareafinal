
CREATE DATABASE IF NOT EXISTS registros;


USE registros;


CREATE TABLE usuarios (
    nombre_usuario VARCHAR(100) PRIMARY KEY, 
    nombre VARCHAR(100) NOT NULL,           
    apellido VARCHAR(100) NOT NULL,         
    telefono VARCHAR(20),                   
    correo VARCHAR(100),                    
    contrasena VARCHAR(100) NOT NULL        
);


CREATE TABLE productos (
    id INT AUTO_INCREMENT PRIMARY KEY,     
    nombre VARCHAR(100) NOT NULL,           
    marca VARCHAR(100) NOT NULL,           
    categoria VARCHAR(100) NOT NULL,        
    precio DOUBLE NOT NULL,                
    cantidad_disponible INT NOT NULL        
);

select * from usuarios;
select * from productos;
commit;



