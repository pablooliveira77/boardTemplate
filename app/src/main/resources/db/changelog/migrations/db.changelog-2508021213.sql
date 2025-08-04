--liquibase formatted sql

--changeset pablo:2508021213
--comment: Create boards table

CREATE TABLE boards (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
) ENGINE=InnoDB;

--rollback DROP TABLE boards;
