# ChampSched Backend

Sistema de agendamento de campeonatos - Backend Spring Boot

## Tecnologias

- Java 11
- Spring Boot 2.7.18
- Spring Web
- Spring Data JPA
- Spring Boot DevTools
- Lombok
- Validation
- PostgreSQL

## Estrutura do Projeto

```
com.champsched
├── controller    # Controladores REST
├── service       # Lógica de negócio
├── repository    # Acesso a dados (JPA)
├── model         # Entidades do banco de dados
├── dto           # Data Transfer Objects
└── config        # Configurações
```

## Banco de Dados PostgreSQL

O projeto usa PostgreSQL como banco de dados. 

### 📋 Configuração Rápida

1. **Crie um arquivo `.env`** na pasta `backend` (já existe um template)
2. **Preencha com suas credenciais** do PostgreSQL
3. **Execute o projeto**: `./mvnw spring-boot:run`

**📖 [Guia Completo de Configuração](CONFIGURACAO_BANCO.md)** ← Siga este guia passo a passo

### Plataformas gratuitas recomendadas:
- **Render.com** ⭐ (Mais fácil)
- **Supabase** (PostgreSQL + API)
- **Neon.tech** (Serverless)
- **ElephantSQL**
- **Railway.app**

### Configuração

**Opção 1: Variáveis de ambiente (Recomendado)**

Crie um arquivo `.env` na raiz do projeto backend:

```bash
DB_URL=jdbc:postgresql://seu-host.com:5432/seu_banco
DB_USERNAME=seu_usuario
DB_PASSWORD=sua_senha
SERVER_PORT=8080
```

**Opção 2: Editar application.properties**

Edite `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://seu-host.com:5432/seu_banco
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
```

### Exemplo com banco externo (Render.com):

```properties
spring.datasource.url=jdbc:postgresql://dpg-xxxxx.oregon-postgres.render.com:5432/champscheddb
spring.datasource.username=champsched_user
spring.datasource.password=abc123xyz
```

## Como executar

```bash
# Executar a aplicação
./mvnw spring-boot:run
```

Ou com Maven instalado:

```bash
mvn spring-boot:run
```

## Endpoints

API disponível em: http://localhost:8080

## Criando o Banco de Dados

### PostgreSQL local (opcional):

```bash
# Conectar ao PostgreSQL
psql -U postgres

# Criar o banco
CREATE DATABASE champscheddb;

# Sair
\q
```

**Nota:** Para serviços cloud (Render, Supabase, etc.), o banco já vem criado automaticamente.
