CREATE TABLE category (
 category_id CHAR(255) NOT NULL,
 category CHAR(255) NOT NULL
);

ALTER TABLE category ADD CONSTRAINT PK_category PRIMARY KEY (category_id);


CREATE TABLE chatroom (
 chatroom_id CHAR(255) NOT NULL,
 name VARCHAR(255) NOT NULL,
 status INT NOT NULL,
 user_count INT
);

ALTER TABLE chatroom ADD CONSTRAINT PK_chatroom PRIMARY KEY (chatroom_id);


CREATE TABLE chatroom_category (
 chatroom_id CHAR(255) NOT NULL,
 category_id CHAR(255) NOT NULL
);

ALTER TABLE chatroom_category ADD CONSTRAINT PK_chatroom_category PRIMARY KEY (chatroom_id,category_id);


CREATE TABLE file (
 file_id CHAR(255) NOT NULL,
 file CHAR(255) NOT NULL
);

ALTER TABLE file ADD CONSTRAINT PK_file PRIMARY KEY (file_id);


CREATE TABLE image (
 image_id CHAR(255) NOT NULL,
 file_id CHAR(255) NOT NULL,
 image CHAR(255)
);

ALTER TABLE image ADD CONSTRAINT PK_image PRIMARY KEY (image_id,file_id);


CREATE TABLE role (
 role_id CHAR(255) NOT NULL,
 role CHAR(255)
);

ALTER TABLE role ADD CONSTRAINT PK_role PRIMARY KEY (role_id);


CREATE TABLE user (
 user_id INT NOT NULL,
 username VARCHAR(255) NOT NULL,
 password VARCHAR(255) NOT NULL,
 email VARCHAR(255) NOT NULL
);

ALTER TABLE user ADD CONSTRAINT PK_user PRIMARY KEY (user_id);


CREATE TABLE user_file (
 user_id INT NOT NULL,
 file_id CHAR(255) NOT NULL
);

ALTER TABLE user_file ADD CONSTRAINT PK_user_file PRIMARY KEY (user_id,file_id);


CREATE TABLE chatroom_user (
 user_id INT NOT NULL,
 chatroom_id CHAR(255) NOT NULL,
 admin INT NOT NULL,
 role_id CHAR(255),
 favorite CHAR(255)
);

ALTER TABLE chatroom_user ADD CONSTRAINT PK_chatroom_user PRIMARY KEY (user_id,chatroom_id);


CREATE TABLE document (
 document_id CHAR(255) NOT NULL,
 file_id CHAR(255) NOT NULL,
 document CHAR(255)
);

ALTER TABLE document ADD CONSTRAINT PK_document PRIMARY KEY (document_id,file_id);


CREATE TABLE friend (
 user_id_2 INT NOT NULL,
 user_id_1 INT NOT NULL,
 date DATE
);

ALTER TABLE friend ADD CONSTRAINT PK_friend PRIMARY KEY (user_id_2,user_id_1);


CREATE TABLE message (
 user_id INT NOT NULL,
 chatroom_id CHAR(255) NOT NULL,
 message_id CHAR(255) NOT NULL,
 message VARCHAR(255) NOT NULL,
 file_id CHAR(255),
 date DATE
);

ALTER TABLE message ADD CONSTRAINT PK_message PRIMARY KEY (user_id,chatroom_id,message_id);


CREATE TABLE subscription (
 user_id INT NOT NULL,
 sub_id CHAR(255) NOT NULL,
 mail_message VARCHAR(255),
 chatroom_id CHAR(255) NOT NULL,
 category_id CHAR(255) NOT NULL
);

ALTER TABLE subscription ADD CONSTRAINT PK_subscription PRIMARY KEY (user_id,sub_id);


CREATE TABLE direct_message (
 user_id_2 INT NOT NULL,
 user_id_1 INT NOT NULL,
 message CHAR(255)
);

ALTER TABLE direct_message ADD CONSTRAINT PK_direct_message PRIMARY KEY (user_id_2,user_id_1);


ALTER TABLE chatroom_category ADD CONSTRAINT FK_chatroom_category_0 FOREIGN KEY (chatroom_id) REFERENCES chatroom (chatroom_id);
ALTER TABLE chatroom_category ADD CONSTRAINT FK_chatroom_category_1 FOREIGN KEY (category_id) REFERENCES category (category_id);


ALTER TABLE image ADD CONSTRAINT FK_image_0 FOREIGN KEY (file_id) REFERENCES file (file_id);


ALTER TABLE user_file ADD CONSTRAINT FK_user_file_0 FOREIGN KEY (user_id) REFERENCES user (user_id);
ALTER TABLE user_file ADD CONSTRAINT FK_user_file_1 FOREIGN KEY (file_id) REFERENCES file (file_id);


ALTER TABLE chatroom_user ADD CONSTRAINT FK_chatroom_user_0 FOREIGN KEY (user_id) REFERENCES user (user_id);
ALTER TABLE chatroom_user ADD CONSTRAINT FK_chatroom_user_1 FOREIGN KEY (chatroom_id) REFERENCES chatroom (chatroom_id);
ALTER TABLE chatroom_user ADD CONSTRAINT FK_chatroom_user_2 FOREIGN KEY (role_id) REFERENCES role (role_id);


ALTER TABLE document ADD CONSTRAINT FK_document_0 FOREIGN KEY (file_id) REFERENCES file (file_id);


ALTER TABLE friend ADD CONSTRAINT FK_friend_0 FOREIGN KEY (user_id_2) REFERENCES user (user_id);
ALTER TABLE friend ADD CONSTRAINT FK_friend_1 FOREIGN KEY (user_id_1) REFERENCES user (user_id);


ALTER TABLE message ADD CONSTRAINT FK_message_0 FOREIGN KEY (user_id,chatroom_id) REFERENCES chatroom_user (user_id,chatroom_id);
ALTER TABLE message ADD CONSTRAINT FK_message_1 FOREIGN KEY (user_id,file_id) REFERENCES user_file (user_id,file_id);


ALTER TABLE subscription ADD CONSTRAINT FK_subscription_0 FOREIGN KEY (user_id) REFERENCES user (user_id);
ALTER TABLE subscription ADD CONSTRAINT FK_subscription_1 FOREIGN KEY (chatroom_id,category_id) REFERENCES chatroom_category (chatroom_id,category_id);


ALTER TABLE direct_message ADD CONSTRAINT FK_direct_message_0 FOREIGN KEY (user_id_2,user_id_1) REFERENCES friend (user_id_2,user_id_1);


