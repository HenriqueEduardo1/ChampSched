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
├── LICENSE
├── README.md
├── docs/
│   └── relatorio-principios-projeto.md
└── user_stories.md
```
- backend/: MVP funcional feito em Java com SpringBoot e React.
- LICENSE: termos da licença do projeto (MIT).
- README.md: este arquivo de apresentação.
- docs/: documentação adicional do projeto.
- docs/relatorio-principios-projeto.md: relatório sobre princípios de projeto (SOLID, GRASP, etc.) e aplicação ao ChampSched.
- user_stories.md: Estórias de usuários para serem implementadas

### MVP

Uma demonstração do sistema com frontend e backend funcionais está disponível [aqui](https://champ-sched-front.vercel.app/), a interface gráfica está neste [repositório](https://github.com/HenriqueEduardo1/ChampSched-Front).

#### Login
Usuário: `admin`  
Senha: `123`

## Licença

Este projeto está licenciado sob a **Licença MIT**. Veja o arquivo `LICENSE` para mais detalhes.

## Documentação

- Relatório de Princípios de Projeto: [docs/relatorio-principios-projeto.md](docs/relatorio-principios-projeto.md)