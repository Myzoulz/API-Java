CREATE TABLE desenvolvedores (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(255) NOT NULL,
    data_fundacao DATE,
    website VARCHAR(255),
    sede VARCHAR(255)
);