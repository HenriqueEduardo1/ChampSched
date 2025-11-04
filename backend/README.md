# ChampSched Backend

API REST desenvolvida com Spring Boot para gerenciamento de campeonatos esportivos.


## Configuração do Ambiente

### 1. Banco de Dados

Crie um banco PostgreSQL e configure as credenciais em um arquivo `.env` na raiz do backend:

```env
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/champsched
SPRING_DATASOURCE_USERNAME=seu_usuario
SPRING_DATASOURCE_PASSWORD=sua_senha
PORT=8080
```

### 2. Executar a aplicação

Com Maven Wrapper (recomendado):
```bash
./mvnw spring-boot:run
```

Com Maven instalado:
```bash
mvn spring-boot:run
```

### Testes
```bash
./mvnw test
```

---
