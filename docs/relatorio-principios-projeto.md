
# Relatório — Aplicação dos Princípios de Projeto no Sistema ChampSched

## 1. Introdução

Este relatório apresenta uma análise do diagrama de classes e das User Stories (US) do projeto ChampSched. O objetivo é identificar a aplicação de princípios fundamentais de projeto de software e propor refatorações específicas para melhorar a manutenibilidade, flexibilidade e robustez do sistema. As sugestões focam na refatoração da classe `User` e na introdução de abstrações para reduzir o acoplamento, baseando-se em conceitos como Responsabilidade Única, Inversão de Dependência e Segregação de Interfaces.

---

## 2. Análise dos Princípios de Projeto Aplicáveis

A qualidade de um projeto de software é sustentada por princípios que guiam a decomposição de um problema complexo em partes menores e independentes. A seguir, analisamos como alguns desses princípios se aplicam ao projeto atual.

### 2.1. Princípio da Responsabilidade Única (SRP)

Este princípio afirma que uma classe deve ter uma, e apenas uma, razão para mudar. Essencialmente, ela deve ter uma única responsabilidade.

* **Aplicação no Projeto:** A classe `User` no diagrama atual possui um campo booleano `organizador`. Isso confere à classe duas responsabilidades distintas: representar um participante de um time (jogador) e representar um organizador de campeonatos. Se as regras de negócio para organizadores mudarem (ex: novas permissões de criação), a classe `User` precisará ser alterada. Se as regras para jogadores mudarem (ex: estatísticas de jogo), a mesma classe `User` também precisará de modificação. Essa dupla responsabilidade viola o SRP e torna a classe menos coesa e mais frágil.

### 2.2. Inversão de Dependências (Prefira Interfaces a Classes)

Este princípio sugere que módulos de alto nível não devem depender de módulos de baixo nível; ambos devem depender de abstrações. Na prática, isso significa que as classes devem depender de interfaces em vez de implementações concretas, pois abstrações são mais estáveis que implementações.

* **Aplicação no Projeto:** Atualmente, a classe `Time` depende diretamente da classe concreta `User` compor sua lista de `integrantes`. Isso cria um acoplamento forte. Se no futuro surgir um novo tipo de participante, como um "Técnico", que não seja exatamente um `User` padrão, a classe `Time` precisaria ser modificada. Depender de uma abstração (uma interface) tornaria o design mais flexível.

### 2.3. Segregação de Interfaces (ISP)

O ISP estabelece que as interfaces devem ser pequenas e específicas para cada tipo de cliente. Clientes não devem ser forçados a depender de métodos que não usam.

* **Aplicação no Projeto:** Este princípio é diretamente relevante para a refatoração da classe `User`. Se criarmos uma única interface "grande" para representar tanto jogadores quanto organizadores, classes que interagem apenas com jogadores (como a classe `Time`) seriam forçadas a conhecer métodos específicos de organizadores, o que é indesejável. Segregar as interfaces garante um design mais limpo e desacoplado.

### 2.4. Ocultamento de Informação (Information Hiding)

Este princípio, também chamado de encapsulamento, dita que as classes devem ocultar seus detalhes internos de implementação, expondo apenas uma interface pública e estável. Isso é crucial para permitir que a implementação interna de uma classe evolua sem impactar seus clientes.

* **Aplicação no Projeto:** O diagrama de classes aplica corretamente este princípio ao definir os atributos das classes como privados (`-`). Isso força a interação por meio de métodos públicos. A classe `Time` deve gerenciar sua lista de integrantes internamente, sem expor a coleção diretamente, permitindo que a estrutura de dados interna seja alterada no futuro sem quebrar o código cliente.

---

## 3. Sugestão de Melhoria: Refatoração com Interfaces

Com base na análise, a principal sugestão é refatorar o sistema para utilizar interfaces, segregando responsabilidades e reduzindo o acoplamento. Esta abordagem oferece maior flexibilidade e alinhamento com os princípios SOLID.

O plano de ação consiste nos seguintes passos:

1.  **Definição das Interfaces:** Serão criadas duas novas interfaces, `IParticipante` e `IOrganizador`. A primeira definirá o contrato para qualquer membro de um time, enquanto a segunda definirá as operações de um organizador de campeonatos. Esta ação aplica diretamente o **Princípio da Segregação de Interfaces**.

2.  **Modificação da Classe `User`:** A classe `User` será alterada para implementar as novas interfaces. Com isso, ela passa a servir como uma implementação concreta para ambos os papéis, mas as outras partes do sistema deixarão de depender dela diretamente, interagindo por meio das abstrações.

3.  **Refatoração da Classe `Time`:** A classe `Time` será modificada para depender da abstração `IParticipante` em vez da classe concreta `User`. Sua lista de integrantes passará a ser uma coleção de `IParticipante`, tornando a classe `Time` mais genérica e desacoplada, conforme preconiza o **Princípio da Inversão de Dependências**.

4.  **Refatoração da Classe `Campeonato`:** A classe `Campeonato` também será atualizada para interagir com as novas abstrações. Por exemplo, ao ser criada, ela poderá receber uma referência a um `IOrganizador`, em vez de depender de um `User` com uma flag booleana.

---

## 4. Conclusão

A aplicação dos princípios de projeto discutidos e a implementação da refatoração proposta trarão benefícios significativos ao projeto ChampSched. A separação de responsabilidades na classe `User` através de interfaces aumentará a **coesão** e a clareza do código. A introdução de abstrações para diminuir o **acoplamento** entre as classes `Time` e `User` tornará o sistema mais flexível, extensível e fácil de manter a longo prazo. Esta abordagem garante que as mudanças em um componente terão menor probabilidade de impactar outros, facilitando a implementação e a manutenção do sistema, que são objetivos centrais de um bom projeto de software.

