create database MyUCA;
-- creado por merino
use MyUCA;
create table Coordinador(
	idC int unique not null AUTO_INCREMENT,
    nombres varchar(45) not null,
    apellidos varchar(45) not null,
    fechaNac date not null,
    titulo varchar(10) not null,
    email varchar(45),
    facultad varchar(65),
    PRIMARY KEY (idC)
);