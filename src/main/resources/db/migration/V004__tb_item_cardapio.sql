CREATE TABLE IF NOT EXISTS tb_item_cardapio (
    id_item_cardapio BIGINT AUTO_INCREMENT PRIMARY KEY,
    nm_item_cardapio VARCHAR(100) NOT NULL,
    ds_item_cardapio VARCHAR(255) NOT NULL,
    vl_preco DECIMAL(19, 2) NOT NULL,
    bl_apenas_no_restaurante BOOLEAN NOT NULL DEFAULT FALSE,
    ds_caminho_foto VARCHAR(255),
    id_restaurante BIGINT NOT NULL,
    dt_criacao DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    dt_atualizacao DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_item_cardapio_restaurante FOREIGN KEY (id_restaurante) REFERENCES tb_restaurante (id_restaurante)
);
