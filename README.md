# Tech Challenge Fase 1 - Sistema de Gestão de Restaurantes

Repositório do projeto da Tech Challenge Fase 1. API REST desenvolvida para gerenciar usuários (Clientes e Donos de Restaurante) em um sistema de gestão de restaurantes, servindo como base para as fases futuras.

## Sobre o Projeto

O objetivo principal desta fase é implementar o domínio de usuários com persistência, regras de negócio e API REST.

## Tecnologias Utilizadas

- Java 21 (LTS)
- Spring Boot 3.2.1
- Spring JDBC (JdbcTemplate)
- MySQL 8.4
- Docker & Docker Compose
- JUnit 5 & Mockito
- SpringDoc OpenAPI (Swagger)

## Como Executar

### Pré-requisitos
- Docker e Docker Compose instalados
- Portas `8080` (aplicação) e `3306` (MySQL) disponíveis

### Passos para execução

1. **Clonar o repositório:**
   ```bash
   git clone https://github.com/arturgussi/meal-stack
   cd meal-stack
   ```

2. **Iniciar o ambiente com Docker Compose:**
   
   O comando a seguir compila o projeto (multi-stage build), cria as imagens e inicia os containers.
   ```bash
   docker compose up --build -d
   ```
   
   A primeira execução pode levar alguns minutos para download das dependências do Maven.

3. **Acessar a aplicação:**
   - API Base: `http://localhost:8080/v1/usuarios`
   - Documentação Swagger: `http://localhost:8080/swagger-ui.html`

## Executando Testes

A suite de testes inclui 29 testes automatizados cobrindo Services e Controllers.

**Via Docker:**
```bash
docker compose exec app mvn test
```

**Via terminal local (Linux/Mac/Git Bash):**
```bash
./mvnw test
```

**Via Windows (CMD/Powershell):**
```cmd
mvnw.cmd test
```

## Links Úteis

| Recurso | URL |
|---------|-----|
| Swagger UI | http://localhost:8080/swagger-ui.html |
| OpenAPI Spec (JSON) | http://localhost:8080/v1/api-docs |