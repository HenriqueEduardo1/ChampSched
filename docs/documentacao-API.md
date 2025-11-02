# Documentação da API - ChampSched

## Sumário
- [Usuários (Users)](#usuários-users)
- [Times](#times)
- [Campeonatos](#campeonatos)

---

## Base URL
```
http://localhost:8080
```

---

## Usuários (Users)

Gerenciamento de usuários do sistema. Usuários podem ser participantes de times ou organizadores de campeonatos.

### Endpoints

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/api/users` | Criar novo usuário |
| GET | `/api/users` | Listar todos os usuários |
| GET | `/api/users/{id}` | Buscar usuário por ID |
| GET | `/api/users/{id}/times` | Listar times do usuário |
| GET | `/api/users/{id}/campeonatos` | Listar campeonatos do usuário |
| PUT | `/api/users/{id}` | Atualizar usuário |
| DELETE | `/api/users/{id}` | Deletar usuário |

---

### 1. Criar Usuário
**POST** `/api/users`

Cria um novo usuário no sistema.

**Request Body:**
```json
{
    "nome": "João Silva",
    "contato": "joao.silva@email.com"
}
```

**Validações:**
- `nome`: Obrigatório, não pode ser vazio
- `contato`: Obrigatório, não pode ser vazio

**Response:** `201 Created`
```json
{
    "id": 1,
    "nome": "João Silva",
    "contato": "joao.silva@email.com"
}
```

---

### 2. Listar Todos os Usuários
**GET** `/api/users`

Retorna uma lista com todos os usuários cadastrados.

**Response:** `200 OK`
```json
[
    {
        "id": 1,
        "nome": "João Silva",
        "contato": "joao.silva@email.com"
    },
    {
        "id": 2,
        "nome": "Maria Santos",
        "contato": "maria.santos@email.com"
    }
]
```

---

### 3. Buscar Usuário por ID
**GET** `/api/users/{id}`

Retorna os dados de um usuário específico.

**Parâmetros:**
- `id` (path): ID do usuário

**Response:** `200 OK`
```json
{
    "id": 1,
    "nome": "João Silva",
    "contato": "joao.silva@email.com"
}
```

---

### 4. Listar Times do Usuário
**GET** `/api/users/{id}/times`

Retorna todos os times que o usuário participa como integrante.

**Parâmetros:**
- `id` (path): ID do usuário

**Response:** `200 OK`
```json
[
    {
        "id": 1,
        "nome": "Os Vencedores",
        "contato": "osvencedores@email.com",
        "integrantes": [
            {
                "id": 1,
                "nome": "João Silva",
                "contato": "joao.silva@email.com"
            },
            {
                "id": 2,
                "nome": "Maria Santos",
                "contato": "maria.santos@email.com"
            }
        ]
    }
]
```

---

### 5. Listar Campeonatos do Usuário
**GET** `/api/users/{id}/campeonatos`

Retorna todos os campeonatos que o usuário está participando (através dos times).

**Parâmetros:**
- `id` (path): ID do usuário

**Response:** `200 OK`
```json
[
    {
        "id": 1,
        "nome": "Copa ChampSched 2025",
        "esporte": "Futebol",
        "data": "2025-12-15",
        "organizador": {
            "id": 3,
            "nome": "Carlos Organizador",
            "contato": "carlos@email.com"
        },
        "times": [
            {
                "id": 1,
                "nome": "Os Vencedores",
                "contato": "osvencedores@email.com",
                "integrantesIds": [1, 2]
            }
        ]
    }
]
```

---

### 6. Atualizar Usuário
**PUT** `/api/users/{id}`

Atualiza os dados de um usuário existente.

**Parâmetros:**
- `id` (path): ID do usuário

**Request Body:**
```json
{
    "nome": "João Silva Santos",
    "contato": "joao.santos@email.com"
}
```

**Response:** `200 OK`
```json
{
    "id": 1,
    "nome": "João Silva Santos",
    "contato": "joao.santos@email.com"
}
```

---

### 7. Deletar Usuário
**DELETE** `/api/users/{id}`

Remove um usuário do sistema.

**Parâmetros:**
- `id` (path): ID do usuário

**Response:** `204 No Content`

---

## Times

Gerenciamento de times. Times podem ter múltiplos integrantes (usuários) e participar de campeonatos.

### Endpoints

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/api/times` | Criar novo time |
| GET | `/api/times/{id}` | Buscar time por ID |
| GET | `/api/times` | Listar todos os times |
| PUT | `/api/times/{id}` | Atualizar time |
| DELETE | `/api/times/{id}` | Deletar time |
| POST | `/api/times/{timeId}/integrantes/{userId}` | Adicionar integrante ao time |
| DELETE | `/api/times/{timeId}/integrantes/{userId}` | Remover integrante do time |

---

### 1. Criar Time
**POST** `/api/times`

Cria um novo time, opcionalmente com integrantes.

**Request Body:**
```json
{
    "nome": "Os Vencedores",
    "contato": "osvencedores@email.com",
    "integrantesIds": [1, 2, 3]
}
```

**Campos:**
- `nome`: Obrigatório - Nome do time
- `contato`: Opcional - Email ou telefone de contato
- `integrantesIds`: Opcional - Array com IDs dos usuários integrantes

**Response:** `201 Created`
```json
{
    "id": 1,
    "nome": "Os Vencedores",
    "contato": "osvencedores@email.com",
    "integrantes": [
        {
            "id": 1,
            "nome": "João Silva",
            "contato": "joao.silva@email.com"
        },
        {
            "id": 2,
            "nome": "Maria Santos",
            "contato": "maria.santos@email.com"
        },
        {
            "id": 3,
            "nome": "Pedro Oliveira",
            "contato": "pedro.oliveira@email.com"
        }
    ]
}
```

---

### 2. Buscar Time por ID
**GET** `/api/times/{id}`

Retorna os dados de um time específico, incluindo seus integrantes.

**Parâmetros:**
- `id` (path): ID do time

**Response:** `200 OK`
```json
{
    "id": 1,
    "nome": "Os Vencedores",
    "contato": "osvencedores@email.com",
    "integrantes": [
        {
            "id": 1,
            "nome": "João Silva",
            "contato": "joao.silva@email.com"
        }
    ]
}
```

---

### 3. Listar Todos os Times
**GET** `/api/times`

Retorna uma lista com todos os times cadastrados.

**Response:** `200 OK`
```json
[
    {
        "id": 1,
        "nome": "Os Vencedores",
        "contato": "osvencedores@email.com",
        "integrantes": [...]
    },
    {
        "id": 2,
        "nome": "Campeões FC",
        "contato": "campeoes@email.com",
        "integrantes": [...]
    }
]
```

---

### 4. Atualizar Time
**PUT** `/api/times/{id}`

Atualiza os dados de um time existente.

**Parâmetros:**
- `id` (path): ID do time

**Request Body:**
```json
{
    "nome": "Os Super Vencedores",
    "contato": "supertime@email.com",
    "integrantesIds": [1, 2, 3, 4]
}
```

**Response:** `200 OK`
```json
{
    "id": 1,
    "nome": "Os Super Vencedores",
    "contato": "supertime@email.com",
    "integrantes": [...]
}
```

---

### 5. Deletar Time
**DELETE** `/api/times/{id}`

Remove um time do sistema.

**Parâmetros:**
- `id` (path): ID do time

**Response:** `204 No Content`

---

### 6. Adicionar Integrante ao Time
**POST** `/api/times/{timeId}/integrantes/{userId}`

Adiciona um usuário como integrante de um time.

**Parâmetros:**
- `timeId` (path): ID do time
- `userId` (path): ID do usuário

**Response:** `200 OK`
```json
{
    "id": 1,
    "nome": "Os Vencedores",
    "contato": "osvencedores@email.com",
    "integrantes": [
        {
            "id": 1,
            "nome": "João Silva",
            "contato": "joao.silva@email.com"
        },
        {
            "id": 5,
            "nome": "Ana Costa",
            "contato": "ana.costa@email.com"
        }
    ]
}
```

**Observação:** Se o usuário já for integrante, não será adicionado novamente.

---

### 7. Remover Integrante do Time
**DELETE** `/api/times/{timeId}/integrantes/{userId}`

Remove um usuário da lista de integrantes de um time.

**Parâmetros:**
- `timeId` (path): ID do time
- `userId` (path): ID do usuário

**Response:** `200 OK`
```json
{
    "id": 1,
    "nome": "Os Vencedores",
    "contato": "osvencedores@email.com",
    "integrantes": [
        {
            "id": 1,
            "nome": "João Silva",
            "contato": "joao.silva@email.com"
        }
    ]
}
```

---

## Campeonatos

Gerenciamento de campeonatos. Campeonatos têm um organizador (usuário) e podem ter múltiplos times participantes.

### Endpoints

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/api/campeonatos` | Criar novo campeonato |
| GET | `/api/campeonatos/{id}` | Buscar campeonato por ID |
| GET | `/api/campeonatos` | Listar todos os campeonatos |
| PUT | `/api/campeonatos/{id}` | Atualizar campeonato |
| DELETE | `/api/campeonatos/{id}` | Deletar campeonato |
| POST | `/api/campeonatos/{campeonatoId}/times/{timeId}` | Adicionar time ao campeonato |
| DELETE | `/api/campeonatos/{campeonatoId}/times/{timeId}` | Remover time do campeonato |

---

### 1. Criar Campeonato
**POST** `/api/campeonatos`

Cria um novo campeonato com organizador e times participantes.

**Request Body:**
```json
{
    "nome": "Copa ChampSched 2025",
    "esporte": "Futebol",
    "data": "2025-12-15",
    "organizadorId": 1,
    "timesIds": [1, 2, 3, 4]
}
```

**Campos:**
- `nome`: Nome do campeonato
- `esporte`: Tipo de esporte (ex: Futebol, Basquete, Vôlei)
- `data`: Data do campeonato (formato: YYYY-MM-DD)
- `organizadorId`: ID do usuário organizador
- `timesIds`: Array com IDs dos times participantes (opcional)

**Response:** `201 Created`
```json
{
    "id": 1,
    "nome": "Copa ChampSched 2025",
    "esporte": "Futebol",
    "data": "2025-12-15",
    "organizador": {
        "id": 1,
        "nome": "João Silva",
        "contato": "joao.silva@email.com"
    },
    "times": [
        {
            "id": 1,
            "nome": "Os Vencedores",
            "contato": "osvencedores@email.com",
            "integrantesIds": [1, 2, 3]
        },
        {
            "id": 2,
            "nome": "Campeões FC",
            "contato": "campeoes@email.com",
            "integrantesIds": [4, 5]
        }
    ]
}
```

---

### 2. Buscar Campeonato por ID
**GET** `/api/campeonatos/{id}`

Retorna os dados completos de um campeonato.

**Parâmetros:**
- `id` (path): ID do campeonato

**Response:** `200 OK`
```json
{
    "id": 1,
    "nome": "Copa ChampSched 2025",
    "esporte": "Futebol",
    "data": "2025-12-15",
    "organizador": {
        "id": 1,
        "nome": "João Silva",
        "contato": "joao.silva@email.com"
    },
    "times": [
        {
            "id": 1,
            "nome": "Os Vencedores",
            "contato": "osvencedores@email.com",
            "integrantesIds": [1, 2, 3]
        }
    ]
}
```

---

### 3. Listar Todos os Campeonatos
**GET** `/api/campeonatos`

Retorna uma lista com todos os campeonatos cadastrados.

**Response:** `200 OK`
```json
[
    {
        "id": 1,
        "nome": "Copa ChampSched 2025",
        "esporte": "Futebol",
        "data": "2025-12-15",
        "organizador": {...},
        "times": [
            {
                "id": 1,
                "nome": "Os Vencedores",
                "contato": "osvencedores@email.com",
                "integrantesIds": [1, 2, 3]
            }
        ]
    },
    {
        "id": 2,
        "nome": "Torneio de Basquete",
        "esporte": "Basquete",
        "data": "2025-11-20",
        "organizador": {...},
        "times": [
            {
                "id": 2,
                "nome": "Campeões FC",
                "contato": "campeoes@email.com",
                "integrantesIds": [4, 5]
            }
        ]
    }
]
```

---

### 4. Atualizar Campeonato
**PUT** `/api/campeonatos/{id}`

Atualiza os dados de um campeonato existente.

**Parâmetros:**
- `id` (path): ID do campeonato

**Request Body:**
```json
{
    "nome": "Copa ChampSched 2025 - Final Edition",
    "esporte": "Futsal",
    "data": "2025-12-20",
    "organizadorId": 1,
    "timesIds": [1, 2, 3, 4, 5, 6]
}
```

**Response:** `200 OK`
```json
{
    "id": 1,
    "nome": "Copa ChampSched 2025 - Final Edition",
    "esporte": "Futsal",
    "data": "2025-12-20",
    "organizador": {...},
    "times": [
        {
            "id": 1,
            "nome": "Os Vencedores",
            "contato": "osvencedores@email.com",
            "integrantesIds": [1, 2, 3]
        }
    ]
}
```

---

### 5. Deletar Campeonato
**DELETE** `/api/campeonatos/{id}`

Remove um campeonato do sistema.

**Parâmetros:**
- `id` (path): ID do campeonato

**Response:** `204 No Content`

**Observação:** Remove também o relacionamento com os times, mas não deleta os times.

---

### 6. Adicionar Time ao Campeonato
**POST** `/api/campeonatos/{campeonatoId}/times/{timeId}`

Adiciona um time a um campeonato existente.

**Parâmetros:**
- `campeonatoId` (path): ID do campeonato
- `timeId` (path): ID do time

**Response:** `200 OK`
```json
{
    "id": 1,
    "nome": "Copa ChampSched 2025",
    "esporte": "Futebol",
    "data": "2025-12-15",
    "organizador": {...},
    "times": [
        {
            "id": 1,
            "nome": "Os Vencedores",
            "contato": "osvencedores@email.com",
            "integrantesIds": [1, 2, 3]
        },
        {
            "id": 5,
            "nome": "Novo Time",
            "contato": "novotime@email.com",
            "integrantesIds": [7, 8]
        }
    ]
}
```

**Observação:** Se o time já estiver no campeonato, não será adicionado novamente.

---

### 7. Remover Time do Campeonato
**DELETE** `/api/campeonatos/{campeonatoId}/times/{timeId}`

Remove um time de um campeonato.

**Parâmetros:**
- `campeonatoId` (path): ID do campeonato
- `timeId` (path): ID do time

**Response:** `200 OK`
```json
{
    "id": 1,
    "nome": "Copa ChampSched 2025",
    "esporte": "Futebol",
    "data": "2025-12-15",
    "organizador": {...},
    "times": [
        {
            "id": 1,
            "nome": "Os Vencedores",
            "contato": "osvencedores@email.com",
            "integrantesIds": [1, 2, 3]
        }
    ]
}
```

**Observação:** O time não é deletado, apenas removido do campeonato.

---