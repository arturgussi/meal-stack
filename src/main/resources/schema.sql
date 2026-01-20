CREATE TABLE IF NOT EXISTS tb_usuarios (
    id_usuario BIGINT AUTO_INCREMENT PRIMARY KEY,
    nm_usuario VARCHAR(100) NOT NULL,
    ds_email VARCHAR(100) NOT NULL UNIQUE,
    ds_login VARCHAR(50) NOT NULL UNIQUE,
    ds_senha VARCHAR(255) NOT NULL,
    nr_cpf VARCHAR(11) NOT NULL UNIQUE,
    tp_usuario VARCHAR(20) NOT NULL,
    ds_endereco_rua VARCHAR(200),
    nr_endereco_numero VARCHAR(10),
    ds_endereco_cidade VARCHAR(100),
    nr_endereco_cep VARCHAR(8),
    dt_criacao DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    dt_atualizacao DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_usuarios_tipo (tp_usuario)
);