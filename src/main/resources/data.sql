-- =========================================
-- Script de Carga Inicial (Seed Data)
-- =========================================

-- Inserir usuário tipo CLIENTE
INSERT INTO tb_usuarios (
    nm_usuario, 
    ds_email, 
    ds_login, 
    ds_senha, 
    nr_cpf, 
    tp_usuario, 
    ds_endereco_rua, 
    nr_endereco_numero, 
    ds_endereco_cidade, 
    nr_endereco_cep, 
    dt_criacao, 
    dt_atualizacao
)
SELECT 
    'João Silva',
    'joao.silva@email.com',
    'joao.silva',
    'senha123',
    '12345678901',
    'CLIENTE',
    'Rua das Flores',
    '123',
    'São Paulo',
    '01234567',
    NOW(),
    NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM tb_usuarios WHERE ds_email = 'joao.silva@email.com'
);

-- Inserir usuário tipo DONO_RESTAURANTE
INSERT INTO tb_usuarios (
    nm_usuario, 
    ds_email, 
    ds_login, 
    ds_senha, 
    nr_cpf, 
    tp_usuario, 
    ds_endereco_rua, 
    nr_endereco_numero, 
    ds_endereco_cidade, 
    nr_endereco_cep, 
    dt_criacao, 
    dt_atualizacao
)
SELECT 
    'Maria Santos',
    'maria.santos@email.com',
    'maria.santos',
    'senha456',
    '98765432109',
    'DONO_RESTAURANTE',
    'Avenida Paulista',
    '1000',
    'São Paulo',
    '01310100',
    NOW(),
    NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM tb_usuarios WHERE ds_email = 'maria.santos@email.com'
);