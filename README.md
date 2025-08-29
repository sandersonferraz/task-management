# Task Management Application

Aplicação completa para **gestão de tarefas**, composta por **backend em Spring Boot** e **frontend em Angular**.

Funcionalidades:

* **Criar, listar, filtrar e excluir tarefas**.
* **Associar tarefas a projetos**.
* **Paginar resultados e controlar estados das tarefas**.

---

## Tecnologias Utilizadas

### Backend

* **Java 21** + **Spring Boot 3**

  * Spring Web (APIs REST)
  * Spring Data JPA (persistência)
  * H2 Database (banco em memória)
* **Lombok** (redução de boilerplate)
* **Maven** (build e gerenciamento de dependências)

### Frontend

* **Angular 20**
* **Angular Signals** (estado reativo)
* **Bootstrap + ng-bootstrap** (layout e modais)
* **RxJS** (requisições assíncronas)

### Infraestrutura

* **Docker + Docker Compose** (empacotamento e execução)

---

## Estrutura do Projeto

```
BIP-Technical-Challenge/
│── task-management-backend/   # Código fonte do backend
│── task-management-frontend/  # Código fonte do frontend
│── README.md                  # Este arquivo
│── docker-compose.yml         # Subida dos serviços
```

---

## Como Rodar o Projeto

### 1. Clonar o repositório

```bash
git clone https://github.com/seu-repositorio.git
cd seu-repositorio
```

### 2. Rodar com Docker Compose

```bash
docker-compose up --build
```

Acessos:

* Backend: [http://localhost:8080](http://localhost:8080)
* Frontend: [http://localhost:4200](http://localhost:4200)
* Console H2 (opcional): [http://localhost:8080/h2-console](http://localhost:8080/h2-console)

---

## API Endpoints Principais

### TaskController

| Método | Endpoint        | Descrição                                                       |
| ------ | --------------- | --------------------------------------------------------------- |
| GET    | /tasks          | Lista tarefas, filtros opcionais por projeto, suporta paginação |
| POST   | /tasks          | Cria uma nova tarefa                                            |
| DELETE | /tasks/{taskId} | Remove uma tarefa existente                                     |

**Notas:**

* Paginação com `PageRequest`.
* Se `projectId` não for informado, retorna todas as tarefas.
* Valida existência do projeto antes de salvar.

### ProjectController

| Método | Endpoint  | Descrição                                              |
| ------ | --------- | ------------------------------------------------------ |
| GET    | /projects | Retorna todos os projetos (para filtros e formulários) |

---

## Frontend – Componentes Principais

### TaskList

* Carrega **tarefas** e **projetos** via serviços REST.
* Permite **filtros por projeto**, **paginação** e **exclusão de tarefas**.
* Usa **Angular Signals** (`tasks`, `projects`, `isLoading`) para estado reativo.

### TaskForm e TaskFormAdd

* Formulários para **adicionar tarefas**.
* Validações de título e projeto obrigatório.
* Integração com **modais do ng-bootstrap**.

---

## Docker Compose

Arquivo `docker-compose.yml`:

```yaml
services:
  backend:
    build: ./task-management-backend
    container_name: task-backend
    ports:
      - "8080:8080"
    networks:
      - task-net

  frontend:
    build: ./task-management-frontend
    container_name: task-frontend
    ports:
      - "4200:80"
    networks:
      - task-net
    depends_on:
      - backend

networks:
  task-net:
    driver: bridge
```

---

## Dockerfiles

### Backend (`task-management-backend/Dockerfile`)

```dockerfile
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
```

### Frontend (`task-management-frontend/Dockerfile`)

```dockerfile
FROM node:22
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
EXPOSE 4200
CMD ["npm", "run", "start", "--", "--host", "0.0.0.0", "--port", "4200"]
```


