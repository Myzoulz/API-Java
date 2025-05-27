CREATE TABLE consoles (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(255) NOT NULL,
    data_lancamento DATE,
    empresa VARCHAR(255)
);