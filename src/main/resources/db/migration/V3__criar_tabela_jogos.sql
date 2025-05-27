CREATE TABLE jogos (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(255) NOT NULL,
    descricao TEXT,
    data_lancamento DATE,
    website VARCHAR(255),
    genero VARCHAR(255),
    urlcapa VARCHAR(255),
    desenvolvedor_id BIGINT,
    CONSTRAINT fk_desenvolvedor FOREIGN KEY (desenvolvedor_id) REFERENCES desenvolvedores(id)
);