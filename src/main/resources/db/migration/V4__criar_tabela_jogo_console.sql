CREATE TABLE jogo_console (
    jogo_id BIGINT,
    console_id BIGINT,
    PRIMARY KEY (jogo_id, console_id),
    CONSTRAINT fk_jogo FOREIGN KEY (jogo_id) REFERENCES jogos(id),
    CONSTRAINT fk_console FOREIGN KEY (console_id) REFERENCES consoles(id)
);