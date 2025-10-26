# ChampSched Backend

Sistema de agendamento de campeonatos - Backend Spring Boot

## Tecnologias

- Java 21 (LTS)
- Spring Boot 3.4.1
- Spring Web
- Spring Data JPA
- Spring Boot DevTools
- Lombok
- PostgreSQL

## Estrutura do Projeto

```
com.champsched
├── controller    
├── service       
├── repository    
├── model         
│   ├── User.java           
│   ├── Time.java           
│   ├── Campeonato.java     
│   ├── IParticipante.java  
│   └── IOrganizador.java  
├── dto          
└── config        
```

## Modelagem de Entidades

### User
- Implementa `IParticipante` e `IOrganizador`
- Pode ser integrante de times
- Pode organizar campeonatos

### Time
- Possui vários integrantes (Users)
- Pode participar de vários campeonatos

### Campeonato
- Possui um organizador (User)
- Possui vários times participantes

### Relacionamentos
- User 1:N Campeonato (como organizador)
- Time N:N User (integrantes)
- Time N:N Campeonato (participação)

## Banco de Dados PostgreSQL

O projeto usa PostgreSQL como banco de dados. 



### Configuração

Crie um arquivo `.env` na raiz do projeto backend:

```bash
DB_URL=jdbc:postgresql://seu-host.com:5432/seu_banco
DB_USERNAME=seu_usuario
DB_PASSWORD=sua_senha
SERVER_PORT=8080
```

## Como executar

```bash
./mvnw spring-boot:run
```

Ou com Maven instalado:

```bash
mvn spring-boot:run
```

## Endpoints

API disponível em: http://localhost:8080
