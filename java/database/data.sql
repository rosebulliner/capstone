BEGIN TRANSACTION;

INSERT INTO users (first_name,last_name,username,password_hash,role,email) VALUES ('userfirst','userlast','user','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER', 'user@email.com');
INSERT INTO users (first_name,last_name,username,password_hash,role,email) VALUES ('adminfirst', 'adminlast', 'admin','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_ADMIN', 'admin@email.com');

--adding base users
INSERT INTO users (first_name,last_name,username,password_hash,role,email)
VALUES
('Tia', 'Bulliner', 'tbull', '$2a$10$aa9aCqOGjD1LTk8oVe1YDOBhw8jDELkcM19BbzOVnH6qpiaE01JCW', 'ROLE_USER', 'tiabulliner@gmail.com'),
('Joy', 'Pilli', 'JPilli','$2a$10$aa9aCqOGjD1LTk8oVe1YDOBhw8jDELkcM19BbzOVnH6qpiaE01JCW','ROLE_USER','pilli_jyotsna@hotmail.com'),
('Jason', 'Stewart', 'JStew', '$2a$10$4iz/xz0hDFle8omC6YxrpumHWiiLx/ChoS.NWV.uX.be3BfjdZPri','ROLE_USER','
jasontay.stewart@gmail.com'),
('Ryan', 'Gies','ryangies8','$2a$10$mQ/2t.6HLF2GTf6.4aYpReTk8.8puoFtqThQstQSMjMzjlZcZ9vNW','ROLE_USER','ryangies8@hotmail.com');
--populating list table
INSERT INTO list(list_name, list_id, group_id)
VALUES
('Supplies',501,401),
('Groceries',502,402),
('Electronics', 503, 401);
--populating items
INSERT INTO item(item_name, item_id, quantity)
VALUES
('coffee', 601,4),
('wipes',602,2),
('pencils',603,10),
('erasers',604,10),
('milk',605,2),
('water',606,4),
('eggs',607,1),
('tomato',608,3),
('HDMI',609,3),
('mouse',610,2),
('moniter',611,1),
('laser pointer',612,1);
-- populating list_item

INSERT INTO list_item(list_id, item_id, is_completed)
VALUES
(501,601,true),
(501,602,false),
(501,603,true),
(501,604,true),
(502,605,false),
(502,606,true),
(502,607,true),
(502,608,false),
(503,609,false),
(503,610,false),
(503,611,false),
(503,612,true);

INSERT INTO diff_groups(group_id, group_name)
VALUES(401,'Office'),
(402,'Home'),
(403, 'BrunchSisters');

INSERT INTO group_user(user_id, group_id)
VALUES

(303,401),
(304,401),
(305,401),
(306,402),
(301,402),
(303,403),
(305,403),
(304,402),
(306,401);
COMMIT TRANSACTION;
