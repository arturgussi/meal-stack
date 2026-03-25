# Tech Challenge Fase 2 - Sistema de Gestão de Restaurantes (MealStack)

Repositório do projeto da Tech Challenge Fase 2. Esta aplicação é uma API REST robusta para gestão de restaurantes, incluindo cadastro de usuários, tipos de usuários, restaurantes e itens do cardápio, seguindo os princípios da **Clean Architecture**.

## 🚀 Novidades da Fase 2
- **Arquitetura Limpa (Clean Architecture)**: Separação clara entre Domínio, Aplicação e Infraestrutura.
- **Domínio Expandido**: CRUD completo para Restaurantes e Itens do Cardápio.
- **Qualidade**: Cobertura de testes superior a 80% (Domínio e Aplicação).
- **Documentação**: API versionada (/v1) e documentada via Swagger/OpenAPI.

## 🛠️ Tecnologias Utilizadas
- **Java 21 (LTS)**
- **Spring Boot 3.4.x** (Spring Framework 6)
- **Spring Data JPA** (Hibernate)
- **MySQL 8.4**
- **Flyway** (Migração de banco de dados)
- **Docker & Docker Compose**
- **JUnit 5, Mockito & AssertJ**
- **JaCoCo** (Relatórios de cobertura)

## 🏗️ Arquitetura
A aplicação segue os princípios da Clean Architecture:
- `domain`: Entidades de negócio e regras fundamentais.
- `application`: Casos de uso e interfaces de gateways.
- `infrastructure`: Implementações de persistência, controllers (API), configurações e exceções.

## 🚀 Como Executar

### Pré-requisitos
- Docker e Docker Compose instalados.
- Portas `8080`, `3306` e `5005` disponíveis.

### Passos
1. **Clonar e Entrar**:
   ```bash
   git clone https://github.com/arturgussi/meal-stack
   cd meal-stack
   ```
2. **Subir com Docker**:
   ```bash
   # Modo Produção
   docker compose --profile prod up --build -d
   ```
3. **Acessar**:
   - **Swagger UI**: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
   - **API Base**: `http://localhost:8080/v1/`

## 🧪 Testes e Cobertura
A suite de testes conta com **131 testes automatizados**.

**Rodar testes locais**:
```bash
./mvnw clean test
```
**Ver relatório de cobertura**:
Após rodar os testes, abra `target/site/jacoco/index.html`.

## 📦 Entrega
- **Postman Collection**: Disponível na raiz do projeto como `postman_collection.json`.
- **Vídeos e Documentação Adicional**: Verifique a pasta `docs/` (se disponível).
