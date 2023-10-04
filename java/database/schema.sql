BEGIN TRANSACTION;
DROP TABLE IF EXISTS users, diff_groups, list, item, group_user, list_item CASCADE;

DROP SEQUENCE IF EXISTS seq_item_id, seq_group_id, seq_user_id, seq_list_id CASCADE;

-- Sequence to start user_id values at 301
CREATE SEQUENCE seq_user_id
  INCREMENT BY 1
  START WITH 310
  NO MAXVALUE;
--user table creation
CREATE TABLE users (
    user_id int NOT NULL DEFAULT
nextval('seq_user_id'),
	first_name varchar(50) NOT NULL,
	last_name varchar(50) NOT NULL,
    username varchar(50) NOT NULL UNIQUE,
    password_hash varchar(200) NOT NULL,
    role varchar(50) NOT NULL,
    email varchar(75) NOT NULL,
    CONSTRAINT PK_user PRIMARY KEY (user_id),
	CONSTRAINT UQ_username UNIQUE (username)

);

CREATE SEQUENCE seq_group_id
  INCREMENT BY 1
  START WITH 405
  NO MAXVALUE;
CREATE TABLE diff_groups(
    group_id int NOT NULL DEFAULT
nextval('seq_group_id'),
    group_name varchar(50) NOT NULL,
    PRIMARY KEY(group_id)
     );

CREATE TABLE group_user(
	group_id int,
	user_id int,
	PRIMARY KEY(group_id, user_id),
	CONSTRAINT fk_group_id FOREIGN KEY (group_id) REFERENCES diff_groups(group_id)
--	CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES users(user_id)
);

--Sequence to start list_id values at 501
CREATE SEQUENCE seq_list_id
  INCREMENT BY 1
  START WITH 505
  NO MAXVALUE;

CREATE TABLE list (
	list_id int NOT NULL DEFAULT
nextval('seq_list_id'),
    group_id int,
	list_name varchar(50) NOT NULL,
	PRIMARY KEY (list_id)
	--CONSTRAINT fk_group_id FOREIGN KEY (group_id) REFERENCES diff_groups(group_id)
);

-- Sequence to start item_id values at 601

CREATE SEQUENCE seq_item_id
  INCREMENT BY 1
  START WITH 615
  NO MAXVALUE;

CREATE TABLE item(
	item_id int NOT NULL DEFAULT
nextval('seq_item_id'),
	item_name varchar(75),
	quantity int,
	PRIMARY KEY (item_id)
);

CREATE TABLE list_item(
	item_id int,
	list_id int,
	is_completed boolean,
	PRIMARY KEY (item_id, list_id),
	CONSTRAINT fk_list_id FOREIGN KEY (list_id) REFERENCES list(list_id),
	CONSTRAINT fk_item_id FOREIGN KEY (item_id) REFERENCES item(item_id));

--create alter statement for the commented foreign keys
COMMIT TRANSACTION;