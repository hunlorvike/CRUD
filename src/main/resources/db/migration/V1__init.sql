CREATE TABLE students
(
    id         INT AUTO_INCREMENT NOT NULL,
    fullname   VARCHAR(255)       NOT NULL,
    phone      VARCHAR(255)       NULL,
    address    VARCHAR(255)       NULL,
    point      DOUBLE             NULL,
    created_at datetime           NOT NULL,
    updated_at datetime           NOT NULL,
    CONSTRAINT pk_students PRIMARY KEY (id)
);


CREATE TABLE users
(
    id         INT AUTO_INCREMENT NOT NULL,
    fullname   VARCHAR(255)       NOT NULL,
    username   VARCHAR(255)       NOT NULL,
    password   VARCHAR(255)       NOT NULL,
    created_at datetime           NOT NULL,
    updated_at datetime           NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

ALTER TABLE users
    ADD CONSTRAINT uc_users_username UNIQUE (username);