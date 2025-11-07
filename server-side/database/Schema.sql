START TRANSACTION;

DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS property;



CREATE TABLE users (
	id serial NOT NULL,
	username VARCHAR (50) NOT NULL UNIQUE,
	password_hash VARCHAR (200) NOT NULL,
	role VARCHAR (50) NOT NULL,
	CONSTRAINT PK_users PRIMARY KEY (id)
);

CREATE TABLE property(
	id serial NOT NULL,
	title VARCHAR (150) NOT NULL,
	type VARCHAR (50) NOT NULL,
	status VARCHAR (50) NOT NULL,
	address VARCHAR (150) NOT NULL,
	city VARCHAR (50) NOT NULL,
	state VARCHAR (40) NOT NULL,
	bedroom int NOT NULL,
	bathroom int NOT NULL,
	sqft int NOT NULL,
	price NUMERIC (12,2) CHECK (price > 0) NOT NULL,
	arv NUMERIC (12,2) CHECK (arv > 0) NOT NULL,
	days_on_market int NOT NULL,
	image_url VARCHAR (500) NOT NULL DEFAULT 'https://homeye.sdsu.edu/static/house_explore/virltor/houses/house0.jpg',
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	CONSTRAINT PK_property PRIMARY KEY (id)
);

COMMIT TRANSACTION;
	
	