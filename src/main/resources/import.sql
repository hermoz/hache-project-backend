/*Populate table user*/
insert into user (address,email,lastname,name,password,phone,username) values ("AdminAdress","admin@localhost","Admin","Admin","$2y$12$GI5PeRiFP8vq.INKO0Rp9OtGqEwlkYvUaZBq1205ErXTT.AsRH.z2","+34600000000","admin");
insert into user (address,email,lastname,name,password,phone,username) values ("ManagerAdress","manager@localhost","Manager","Manager","$2y$12$VGnhA80ruKCD5Gsx7.jMOerKojLQR1glAw43qnhslUzdY4DHLdQ02","+34620000000","manager");
insert into user (address,email,lastname,name,password,phone,username) values ("EmployeeAdress","employee@localhost","Employee","Employee","$2y$12$m3rZ9plHgwHERIzisHBV7eLd3cnV92hC9Nm2R8tF9gS54C5qjKeaC","+34630000000","employee");

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

/*Populate table users_roles*/
insert into users_roles(user_id,role_id) values (1,1);
insert into users_roles(user_id,role_id) values (2,2);
insert into users_roles(user_id,role_id) values (3,3);

/*Populate table roles_privileges*/

/*Privileges associated to role:administrator*/
insert into roles_privileges (role_id,privilege_id) values (1,1);
insert into roles_privileges (role_id,privilege_id) values (1,2);
insert into roles_privileges (role_id,privilege_id) values (1,3);
insert into roles_privileges (role_id,privilege_id) values (1,4);
insert into roles_privileges (role_id,privilege_id) values (1,5);
insert into roles_privileges (role_id,privilege_id) values (1,6);
insert into roles_privileges (role_id,privilege_id) values (1,7);
insert into roles_privileges (role_id,privilege_id) values (1,8);
insert into roles_privileges (role_id,privilege_id) values (1,9);
insert into roles_privileges (role_id,privilege_id) values (1,10);
insert into roles_privileges (role_id,privilege_id) values (1,11);
insert into roles_privileges (role_id,privilege_id) values (1,12);

/*Privileges associated to role:manager*/
insert into roles_privileges (role_id,privilege_id) values (2,2);
insert into roles_privileges (role_id,privilege_id) values (2,5);
insert into roles_privileges (role_id,privilege_id) values (2,6);
insert into roles_privileges (role_id,privilege_id) values (2,7);
insert into roles_privileges (role_id,privilege_id) values (2,8);
insert into roles_privileges (role_id,privilege_id) values (2,10);

/*Privileges associated to role:employee*/
insert into roles_privileges (role_id,privilege_id) values (3,2);
insert into roles_privileges (role_id,privilege_id) values (3,6);
insert into roles_privileges (role_id,privilege_id) values (3,10);


