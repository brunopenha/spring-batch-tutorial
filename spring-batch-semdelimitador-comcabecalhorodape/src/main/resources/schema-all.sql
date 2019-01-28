DROP TABLE cidadaos IF EXISTS;
DROP TABLE remessas IF EXISTS;

CREATE TABLE remessas  (
    id BIGINT IDENTITY NOT NULL PRIMARY KEY,
    tipo VARCHAR(20),
    data DATE,
    descricao VARCHAR(20)
);

CREATE TABLE cidadaos  (
    id BIGINT IDENTITY NOT NULL PRIMARY KEY,
    tipo VARCHAR(20),
    primeiro_nome VARCHAR(20),
    ultimo_nome VARCHAR(20)
);