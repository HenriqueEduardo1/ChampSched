# Padrões de Projeto GoF - Análise ChampSched MVP

## Introdução

Este documento analisa a implementação dos padrões de projeto GoF (Gang of Four) no MVP do sistema ChampSched, um sistema de agendamento e gerenciamento de campeonatos esportivos desenvolvido com Spring Boot.

### Padrões Analisados
1. Facade
2. Proxy
3. Factory Method
4. Singleton
5. Observer
6. Adapter
7. Strategy
8. Template Method
9. Visitor

---

## Padrões Implementados no MVP

### 1. Singleton Pattern

**Tipo**: Padrão Criacional

**Descrição**: Garante que uma classe tenha apenas uma única instância e fornece um ponto global de acesso a ela.

**Como está implementado no ChampSched:**

O Spring Framework implementa o padrão Singleton por padrão para todos os beans gerenciados. Cada `@Service`, `@Repository` e `@Controller` é criado como singleton.

**Exemplo prático:**

```java
@Service
@RequiredArgsConstructor
public class CampeonatoService {
    private final CampeonatoRepository campeonatoRepository;
    private final UserRepository userRepository;
    private final TimeRepository timeRepository;
    
    // Esta classe tem apenas UMA instância em toda a aplicação
    // Todas as requisições usam a mesma instância
}
```

**Por que utilizamos:**
- Reduz consumo de memória (evita criar múltiplas instâncias)
- Melhora performance (sem overhead de criação repetida)
- Services são stateless, então uma instância pode atender múltiplas requisições
- Garante estado consistente em toda a aplicação

---

### 2. Factory Method Pattern

**Tipo**: Padrão Criacional

**Descrição**: Define uma interface para criar objetos, mas permite que subclasses decidam qual classe instanciar.

**Como está implementado no ChampSched:**

1. **Spring IoC Container** atua como uma fábrica que cria e gerencia beans
2. **JpaRepository** usa factory methods para criar implementações
3. **Construtores de DTOs** funcionam como factory methods

**Exemplo prático:**

```java
// DTO como Factory Method
@Data
public class CampeonatoResponseDTO {
    private int id;
    private String nome;
    private String esporte;
    private LocalDate data;
    
    // Factory method que cria DTO a partir de entidade
    public CampeonatoResponseDTO(Campeonato campeonato) {
        this.id = campeonato.getId();
        this.nome = campeonato.getNome();
        this.esporte = campeonato.getEsporte();
        this.data = campeonato.getData();
    }
}
```

**Por que utilizamos:**
- Centraliza criação de objetos complexos
- Permite configuração via anotações
- Facilita troca de implementações

---

### 3. Proxy Pattern

**Tipo**: Padrão Estrutural

**Descrição**: Fornece um substituto ou placeholder para controlar acesso a um objeto.

**Como está implementado no ChampSched:**

Spring AOP cria proxies dinâmicos para interceptar chamadas de métodos e adicionar comportamentos transversais.

**Exemplo prático:**

```java
@Service
public class CampeonatoService {
    
    @Transactional  // Spring cria um PROXY para este método!
    public CampeonatoResponseDTO createCampeonato(CampeonatoRequestDTO request) {
        // ANTES: Spring Proxy inicia transação
        // EXECUTA: Lógica de negócio
        // DEPOIS: Spring Proxy faz commit (ou rollback se houver erro)
        
        User organizador = userRepository.findById(request.getOrganizadorId())
            .orElseThrow();
        
        Campeonato campeonato = new Campeonato();
        // ... configuração
        
        return new CampeonatoResponseDTO(campeonatoRepository.save(campeonato));
    }
}
```

**Por que utilizamos:**
- Gerenciamento automático de transações
- Rollback automático em caso de exceção
- Código limpo sem poluição com lógica de transação

---

### 4. Strategy Pattern

**Tipo**: Padrão Comportamental

**Descrição**: Define uma família de algoritmos/comportamentos encapsulados e intercambiáveis.

**Como está implementado no ChampSched:**

Interfaces `IParticipante` e `IOrganizador` definem diferentes estratégias que `User` implementa.

**Exemplo prático:**

```java
// Estratégia 1: Comportamento de Participante
public interface IParticipante {
    String getNome();
    String getContato();
}

// Estratégia 2: Comportamento de Organizador
public interface IOrganizador {
    Campeonato criarCampeonato(String nome, String esporte, LocalDate data);
    List<Campeonato> getCampeonatos();
}

// Classe implementa AMBAS estratégias
@Entity
public class User implements IParticipante, IOrganizador {
    // Pode atuar como participante E organizador
}
```

**Por que utilizamos:**
- Um `User` pode ter múltiplos papéis simultaneamente
- Fácil adicionar novos papéis (ex: `IArbitro`)
- Segue princípio Open/Closed

---

## Padrões NÃO Implementados

### 5. Facade Pattern

**Por que NÃO foi implementado:**

- MVP possui arquitetura simples sem múltiplos subsistemas complexos
- Services já atuam como abstração suficiente
- Controllers interagem diretamente com Services
- Adicionar Facade aumentaria complexidade desnecessária


---

### 6. Observer Pattern

**Por que NÃO foi implementado:**

- MVP não possui requisitos de notificação em tempo real
- Sem eventos assíncronos complexos
- Sistema é majoritariamente request-response síncrono
- Não há necessidade de publicar/subscrever eventos

---

### 7. Adapter Pattern

**Por que NÃO foi implementado:**

- Sem integrações com APIs externas incompatíveis
- Spring Data JPA já fornece adaptação entre Java e banco de dados
- DTOs fazem adaptação simples suficiente
- Controlamos todo o código fonte

---

### 8. Template Method Pattern

**Por que NÃO foi implementado:**

- Sem algoritmos com estrutura comum mas passos variáveis
- Operações CRUD são diretas sem necessidade de template
- Sem hierarquias de classes compartilhando fluxo
- Spring já fornece templates quando necessário


---

### 9. Visitor Pattern

**Por que NÃO foi implementado:**

- Estrutura de objetos é simples e estável
- Sem necessidade de adicionar operações dinamicamente
- Modelo de domínio não possui hierarquias complexas
- Seria over-engineering para o MVP
