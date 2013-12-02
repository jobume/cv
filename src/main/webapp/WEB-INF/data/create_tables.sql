DROP TABLE IF EXISTS coverimage;

DROP TABLE IF EXISTS filerecord;
CREATE TABLE filerecord (
	id INT(11) NOT NULL AUTO_INCREMENT,
	name varchar(255),
	filetype varchar(255),
	url varchar(255),
	PRIMARY KEY (id)
);

DROP TABLE IF EXISTS layout;
CREATE TABLE layout (
	id INT(11) NOT NULL AUTO_INCREMENT,
	name varchar(255),
	xsl_stylesheet TEXT,
	PRIMARY KEY (id)
);