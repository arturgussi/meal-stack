CREATE TABLE IF NOT EXISTS tb_tipo_usuario (
    id_tipo_usuario BIGINT AUTO_INCREMENT PRIMARY KEY,
    nm_tipo_usuario VARCHAR(50) NOT NULL,
    dt_criacao DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    dt_atualizacao DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_tipo_usuario_nome (nm_tipo_usuario)
);


START TRANSACTION;
    INSERT IGNORE INTO tb_tipo_usuario (nm_tipo_usuario) 
    VALUES ('CLIENTE'), 
           ('DONO_RESTAURANTE');

    ALTER TABLE tb_usuarios ADD COLUMN id_tipo_usuario BIGINT;

    UPDATE tb_usuarios u
    JOIN tb_tipo_usuario t ON u.tp_usuario = t.nm_tipo_usuario
    SET u.id_tipo_usuario = t.id_tipo_usuario;

COMMIT;

ALTER TABLE tb_usuarios DROP COLUMN tp_usuario;
ALTER TABLE tb_usuarios MODIFY COLUMN id_tipo_usuario BIGINT NOT NULL;
ALTER TABLE tb_usuarios ADD CONSTRAINT fk_usuario_tipo FOREIGN KEY (id_tipo_usuario) REFERENCES tb_tipo_usuario(id_tipo_usuario);
