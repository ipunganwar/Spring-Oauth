create table s_users(
	id int primary key AUTO_INCREMENT,
	username varchar(50) unique not null,
	password varchar(50) not null,
	active boolean not null
);

insert into s_users(username, password, active) 
	values ("ipung", "123", true);
insert into s_users(username, password, active) 
	values ("adam", "123", true);
	
	
create table s_permisions (
	id int primary key AUTO_INCREMENT,
	id_users varchar(50) not null references s_users(id),
	user_role varchar(50) not null
);

insert into s_permisions(id_users, user_role)
	values (1, 'ROLE_SUPERVISOR');
insert into s_permisions(id_users, user_role)
	values (1, 'ROLE_OPERATOR');
insert into s_permisions(id_users, user_role)
	values (2, 'ROLE_OPERATOR');

