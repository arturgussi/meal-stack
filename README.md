# Tech Challenge Fase 1 - Sistema de Gestão de Restaurantes

Repositório do projeto da Tech Challenge Fase 1. API REST desenvolvida para gerenciar usuários (Clientes e Donos de Restaurante) em um sistema de gestão de restaurantes, servindo como base para as fases futuras.

## Sobre o Projeto

O objetivo principal desta fase é implementar o domínio de usuários com persistência, regras de negócio e API REST.

## Tecnologias Utilizadas

- Java 21 (LTS)
- Spring Boot 4.0.3 (Spring Framework 7)
- Spring JDBC (JdbcTemplate)
- MySQL 8.4
- Docker & Docker Compose
- JUnit 5 & Mockito
- SpringDoc OpenAPI (Swagger)

## Como Executar

### Pré-requisitos
- Docker e Docker Compose instalados
- Portas `8080` (aplicação) e `3306` (MySQL) disponíveis e `5005` (Remote Debug, se usar perfil de Dev).

### Passos para execução

1. **Clonar o repositório:**
   ```bash
   git clone https://github.com/arturgussi/meal-stack
   ```
   ```bash
   cd meal-stack
   ```

2. **Iniciar o ambiente com Docker Compose:**
   
   A arquitetura suporta dois perfis de execução. Escolha o mais adequado para o seu momento:

   #### A. Modo Produção (Padrão):
   - Cria imagens otimizadas, leves e sem ferramentas de build ou cache. Ideal para deploy.
   ```bash
   docker compose --profile prod up --build -d
   ```
   #### B. Modo Desenvolvimento (Hot Reload & Debug):
   Mapeia o código local para dentro do container, habilita o Spring DevTools (restarts automáticos ao salvar arquivos) e abre a porta 5005 para debug.
   ```bash
   docker compose --profile dev up --build -d
   ```

3. **Acessar a aplicação:**
   - API Base: `http://localhost:8080/v1/usuarios`
   - Documentação Swagger: `http://localhost:8080/swagger-ui.html`

## Executando Testes

A suite de testes inclui 29 testes automatizados cobrindo Services e Controllers.

**Via terminal local (Linux/Mac/Git Bash):**
```bash
./mvnw test
```
**Nota:** Requer JDK 21 instalado e configurado na variável de ambiente `JAVA_HOME`

**Via Docker - Modo desenvolvimento:**
```bash
docker compose --profile dev exec app-dev mvn clean test
```

## Links Úteis

| Recurso | URL |
|---------|-----|
| Swagger UI | http://localhost:8080/swagger-ui.html |
| OpenAPI Spec (JSON) | http://localhost:8080/v1/api-docs |
