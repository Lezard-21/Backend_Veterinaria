#show databases;
create database veterinaria;
use veterinaria;
#show tables;
create table cliente (
					 id_cliente varchar(255) primary key,
                     nombre varchar(255) not null,
                     contraseña varchar(255) not null
                     );
create table dueño (
					 id_dueño varchar(255) primary key,
                     nombre varchar(255) not null,
                     telefono varchar(255) not null
                     );
create table animal (
					id_animal varchar(255) primary key,
                    especie varchar(255) not null,
                    nombre varchar(255),
                    motivo varchar(255),
                    id_dueño varchar(255),
                    foreign key (id_dueño) references dueño(id_dueño)
                    );
DELIMITER $$
CREATE TRIGGER delete_related_animals 
BEFORE DELETE ON dueño 
FOR EACH ROW 
BEGIN
   DELETE FROM animal WHERE id_dueño = OLD.id_dueño;
END$$
DELIMITER ;

insert into dueño (id_dueño,nombre,telefono) values ("30636641-8da9-4736-bfab-9997af9b4e49","pedro","2287773741");
insert into animal (id_animal,especie,nombre,id_dueño) values ("30636641-2sa-3ees-3dfr3-dss7af234e49","gato","El jiji","le duele el ojo","30636641-8da9-4736-bfab-9997af9b4e49");
insert into cliente (id_cliente,nombre,contraseña) values ("ss88798-8da9-4736-sd7a-j99as0d8","plutarco","12345");

#drop trigger delete_related_animals;

#drop table dueño;
#drop table animal;

#describe animal;
#describe cliente;
#describe dueño;

#select * from animal;
#select * from cliente;
#select * from dueño;
#SELECT * from cliente where nombre="david" and contraseña="12345";
#delete from dueño where id_dueño = "1234"; 
