DROP TABLE cidadaos IF EXISTS;

CREATE TABLE cidadaos  (
    id BIGINT IDENTITY NOT NULL PRIMARY KEY,
    primeiro_nome VARCHAR(20),
    ultimo_nome VARCHAR(20)
);