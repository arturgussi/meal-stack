CREATE TABLE IF NOT EXISTS tb_restaurante (
    id_restaurante BIGINT AUTO_INCREMENT PRIMARY KEY,
    nm_restaurante VARCHAR(100) NOT NULL,
    ds_tipo_cozinha VARCHAR(255) NOT NULL,
    ds_horario_funcionamento VARCHAR(100) NOT NULL,
    ds_endereco_rua VARCHAR(200),
    nr_endereco_numero VARCHAR(10),
    ds_endereco_cidade VARCHAR(100),
    nr_endereco_cep VARCHAR(8),
    id_dono BIGINT NOT NULL,
    dt_criacao DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    dt_atualizacao DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_restaurante_dono FOREIGN KEY (id_dono) REFERENCES tb_usuarios (id_usuario)
);
