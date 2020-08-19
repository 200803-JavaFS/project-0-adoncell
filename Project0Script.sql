CREATE TABLE accounts (
	account_id serial PRIMARY KEY,
	first_name VARCHAR(50),
	last_name VARCHAR(50),
	account_type VARCHAR(20),
	username VARCHAR(50),
	user_password VARCHAR(50),
	balance INT,
	status VARCHAR(20)
);

CREATE TABLE employees (
	employee_id serial PRIMARY KEY,
	first_name varchar(50),
	last_name varchar(50),
	username varchar(50),
	user_password varchar (50),
	status varchar(20)
);

CREATE TABLE admins (
	admin_id serial PRIMARY KEY,
	first_name varchar(50),
	last_name varchar(50),
	username varchar(50),
	user_password varchar(50),
	status varchar(20)
);

INSERT INTO admins (first_name, last_name, username, user_password, status) 
VALUES ('ANNABELLA', 'DONCELL', 'annabee8898', 'revature@208003', 'Approved');

