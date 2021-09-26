CREATE TABLE Currency (
	id INT NOT NULL PRIMARY KEY auto_increment, 
	code VARCHAR(20) NOT NULL, 
	chinese_name VARCHAR(80) NOT NULL, 
	creator VARCHAR(50) NOT NULL, create_time TIMESTAMP NOT NULL,
	constraint code_unique UNIQUE (code)
	);