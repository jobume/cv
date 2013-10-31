CREATE TABLE coverimage (
	id INT(11) NOT NULL AUTO_INCREMENT,
	name varchar(255),
	PRIMARY KEY (id)
);

CREATE TABLE layout (
	id INT(11) NOT NULL AUTO_INCREMENT,
	name varchar(255),
	xsl_stylesheet TEXT,
	PRIMARY KEY (id)
);