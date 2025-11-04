# Eng-de-Software-UFRN

Repositório de exemplo para as atividades da disciplina de Engenharia de Software da UFRN.

## Índice

- [Sobre o Projeto](#sobre-o-projeto)
- [Como clonar ou baixar](#como-clonar-ou-baixar)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [Documentação](#documentação)
- [Licença](#licença)

## Sobre o Projeto

### Título
ChampSched

### Descrição
O ChampSched é um sistema web projetado para ser a plataforma para a organização e participação em campeonatos esportivos amadores. A aplicação permitirá que organizadores criem torneios customizáveis de diversas modalidades, definindo formatos como mata-mata ou pontos corridos, regras específicas e locais de jogo. Ao mesmo tempo, capitães poderão gerenciar seus times, cadastrar jogadores e inscrevê-los nas competições disponíveis de forma simples e intuitiva. O sistema automatizará tarefas complexas como a geração de partidas e a atualização em tempo real de tabelas de classificação, centralizando todas as informações do campeonato e servindo como um ponto de encontro digital para atletas e entusiastas do esporte.

### Componentes
- [Henrique Eduardo Costa da Silva](https://github.com/HenriqueEduardo1)
- [Murilo de Lima Barros](https://github.com/MuriloBarros304)
- [Ramon Vinícius Ferreira de Souza](https://github.com/r4mon-vinicius)

## Como clonar ou baixar

Você pode obter este repositório de três formas:

### Clonar via HTTPS

```bash
git clone https://github.com/HenriqueEduardo1/ChampSched.git
```

Isso criará uma cópia local do repositório em sua máquina.

### Clonar via SSH

Se você já configurou sua chave SSH no GitHub, pode clonar usando:

```bash
git clone git@github.com:HenriqueEduardo1/ChampSched.git
```

Isso criará uma cópia local do repositório em sua máquina.

### Baixar como ZIP

1. Acesse a página do repositório no GitHub:
   [https://github.com/HenriqueEduardo1/ChampSched/](https://github.com/HenriqueEduardo1/ChampSched/)
2. Clique no botão **Code** (verde).
3. Selecione **Download ZIP**.
4. Extraia o arquivo ZIP para o local desejado em seu computador.

## Estrutura do Projeto

```
ChampSched/
├── backend/                           
│   ├── src/main/java/com/champsched/
│   │   ├── controller/                
│   │   ├── service/                   
│   │   ├── repository/                
│   │   ├── model/                     
│   │   ├── dto/                       
│   │   └── config/                    
│   └── README.md
├── docs/
│   ├── padroes-projeto.md             
│   ├── relatorio-principios-projeto.md 
│   ├── documentacao-API.md            
│   └── us/                            
├── postman/                           
├── LICENSE
└── README.md
```

## Tecnologias Utilizadas

- **Backend**: Java 21 + Spring Boot 3.4.1
- **Banco de Dados**: PostgreSQL
- **ORM**: Spring Data JPA
- **Build**: Maven
- **Outras**: Lombok, Spring DevTools

## Funcionalidades Implementadas

### Gerenciamento de Usuários
- CRUD completo (Create, Read, Update, Delete)
- Usuários podem ser participantes e organizadores simultaneamente

### Gerenciamento de Times
- CRUD completo de times esportivos
- Relacionamento N:N com usuários (integrantes do time)
- Relacionamento N:N com campeonatos (participação em competições)

### Gerenciamento de Campeonatos
- CRUD completo com organizador responsável
- Adição/remoção dinâmica de times participantes
- Consulta de campeonatos com dados completos (organizador e times)

### API REST
- Endpoints RESTful completos
- CORS habilitado para integração com frontend
- Transações gerenciadas automaticamente
- Validação e tratamento de erros

## MVP (Demonstração)

Uma demonstração do sistema com frontend e backend funcionais está disponível [aqui](https://champ-sched-front.vercel.app/).  
Repositório do frontend: [ChampSched-Front](https://github.com/HenriqueEduardo1/ChampSched-Front)

**Credenciais de teste:**
- Usuário: `admin`  
- Senha: `123`

## Licença

Este projeto está licenciado sob a **Licença MIT**. Veja o arquivo `LICENSE` para mais detalhes.

## Documentação

- **[Padrões de Projeto GoF](docs/padroes-projeto.md)** - Análise dos padrões de projetos
- **[Princípios SOLID/GRASP](docs/relatorio-principios-projeto.md)** - Aplicação de princípios de design
- **[Documentação da API](docs/documentacao-API.md)** - Endpoints e exemplos de uso
- **[Backend Setup](backend/README.md)** - Configuração e execução do backend
- **[User Stories](user_stories.md)** - Requisitos e histórias de usuário