CREATE DATABASE chat;

DROP TABLE chat.user;
DROP TABLE chat.chatroom;
DROP TABLE chat.category;
DROP TABLE chat.subscription;

CREATE TABLE IF NOT EXISTS chat.user (
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	username VARCHAR(100) UNIQUE NOT NULL,
	password VARCHAR(100) NOT NULL,
	email VARCHAR(100) NOT NULL
);
INSERT INTO chat.user (username, password, email) VALUES ('e@kth.se', md5('testtest'), 'e@kth.se');

CREATE TABLE IF NOT EXISTS chat.subscription (
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	user_id INT NOT NULL,
	FOREIGN KEY (user_id) REFERENCES user(id),
	mail_message VARCHAR(10),
	chatroom_id INT NOT NULL,
	FOREIGN KEY (chatroom_id) REFERENCES chatroom(id),
	category_id INT NOT NULL,
	FOREIGN KEY (category_id) REFERENCES category(id)
);

CREATE TABLE IF NOT EXISTS chat.chatroom (
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(10),
	status INT,
	user_count INT
);

CREATE TABLE IF NOT EXISTS chat.category (
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	category VARCHAR(100)
);
INSERT INTO chat.category (category) VALUES ('Apples Category');
INSERT INTO chat.category (category) VALUES ('Bananas Category');

//CREATE TABLE IF NOT EXISTS chat.chatroom_category (
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	chatroom_id INT NOT NULL,
	FOREIGN KEY (chatroom_id) REFERENCES chatroom(id),
	category_id INT NOT NULL,
	FOREIGN KEY (category_id) REFERENCES category(id)
);

CREATE TABLE IF NOT EXISTS chat.chatroom_user (
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	user_id INT NOT NULL,
	FOREIGN KEY (user_id) REFERENCES user(id),
	chatroom_id INT NOT NULL,
	FOREIGN KEY (chatroom_id) REFERENCES chatroom(id),
	admin BOOLEAN,
	role_id VARCHAR(100) NOT NULL,
	FOREIGN KEY (role_id) REFERENCES role(id),
	favorite VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS chat.role (
	id VARCHAR(100) NOT NULL AUTO_INCREMENT PRIMARY KEY,
	role VARCHAR(100)
);


