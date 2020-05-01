/*Populate table user*/
insert into user (address,email,lastname,name,password,phone,username)
values ("UserAdress","demo@demo","Admin","Admin","$2y$12$k/FnEbc9oCVfYIoIV6PboelpGylrXLAA0Sx1N.DGqO.Sl5KpwjyJG","+34600000000","admin");

/*Populate table customer*/
insert into customer (cif,email,name,phone,population,tax_residence) values ("F00000000","customer@customer.com","CustomerName","+34123456789","Sevilla","Calle Customer, nº2, 9000");
insert into customer (cif,email,name,phone,population,tax_residence) values ("G00000000","custom@customer.com","CustomName","+341234243","Cádiz","Calle Nueva, nº23, 23000");

/*Populate table role*/
insert into role (id,name) values (1,"ADMINISTRATOR");
insert into role (id,name) values (2,"MANAGER");
insert into role (id,name) values (3,"EMPLOYEE");

/*Populate table privilege*/

/*Privileges associated to users*/
insert into privilege (id,name) values (1,"CREATE_USERS");
insert into privilege (id,name) values (2,"READ_USERS");
insert into privilege (id,name) values (3,"UPDATE_USERS");
insert into privilege (id,name) values (4,"DELETE_USERS");

/*Privileges associated to projects*/
insert into privilege (id,name) values (5,"CREATE_PROJECTS");
insert into privilege (id,name) values (6,"READ_PROJECTS");
insert into privilege (id,name) values (7,"UPDATE_PROJECTS");
insert into privilege (id,name) values (8,"DELETE_PROJECTS");

/*Privileges associated to customers*/
insert into privilege (id,name) values (9,"CREATE_CUSTOMERS");
insert into privilege (id,name) values (10,"READ_CUSTOMERS");
insert into privilege (id,name) values (11,"UPDATE_CUSTOMERS");
insert into privilege (id,name) values (12,"DELETE_CUSTOMERS");
