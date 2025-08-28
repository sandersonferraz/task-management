INSERT INTO projects (name) VALUES ('Plataforma de Pesquisa em IA');
INSERT INTO projects (name) VALUES ('Sistema de Gestão de Clínicas');

-- Projeto 1: Plataforma de Pesquisa em IA
INSERT INTO tasks (title, description, status, creation_date, project_id)
VALUES ('Configurar Ambiente de Desenvolvimento', 'Instalar IDE e configurar dependências', 'Concluída', CURRENT_TIMESTAMP, 1);

INSERT INTO tasks (title, description, status, creation_date, project_id)
VALUES ('Modelar Banco de Dados', 'Criar diagrama ER e tabelas', 'Em andamento', CURRENT_TIMESTAMP, 1);

INSERT INTO tasks (title, description, status, creation_date, project_id)
VALUES ('Implementar Autenticação', 'Desenvolver login e registro de usuários', 'Aberta', CURRENT_TIMESTAMP, 1);

INSERT INTO tasks (title, description, status, creation_date, project_id)
VALUES ('Documentação da API', 'Escrever especificações OpenAPI', 'Cancelada', CURRENT_TIMESTAMP, 1);

INSERT INTO tasks (title, description, status, creation_date, project_id)
VALUES ('Testes de Integração', 'Adicionar testes para endpoints REST', 'Em andamento', CURRENT_TIMESTAMP, 1);

INSERT INTO tasks (title, description, status, creation_date, project_id)
VALUES ('Otimizar Modelos de ML', 'Melhorar a velocidade de inferência', 'Aberta', CURRENT_TIMESTAMP, 1);

INSERT INTO tasks (title, description, status, creation_date, project_id)
VALUES ('Design da Interface Web', 'Criar componentes React para o painel', 'Em andamento', CURRENT_TIMESTAMP, 1);

INSERT INTO tasks (title, description, status, creation_date, project_id)
VALUES ('Scripts de Deploy', 'Criar pipeline Docker e CI/CD', 'Concluída', CURRENT_TIMESTAMP, 1);

-- Projeto 2: Sistema de Gestão de Clínicas
INSERT INTO tasks (title, description, status, creation_date, project_id)
VALUES ('Tela de Cadastro de Pacientes', 'Desenvolver formulário no frontend', 'Aberta', CURRENT_TIMESTAMP, 2);

INSERT INTO tasks (title, description, status, creation_date, project_id)
VALUES ('Módulo de Agendamento de Médicos', 'Implementar gestão de agenda', 'Em andamento', CURRENT_TIMESTAMP, 2);

INSERT INTO tasks (title, description, status, creation_date, project_id)
VALUES ('Migração de Banco de Dados', 'Migrar registros antigos de pacientes', 'Concluída', CURRENT_TIMESTAMP, 2);

INSERT INTO tasks (title, description, status, creation_date, project_id)
VALUES ('Serviço de Faturamento', 'Desenvolver microsserviço de cobrança', 'Aberta', CURRENT_TIMESTAMP, 2);

INSERT INTO tasks (title, description, status, creation_date, project_id)
VALUES ('Testes Unitários', 'Adicionar testes JUnit para os serviços', 'Cancelada', CURRENT_TIMESTAMP, 2);

INSERT INTO tasks (title, description, status, creation_date, project_id)
VALUES ('Envio de Notificações por Email', 'Enviar lembretes para pacientes', 'Em andamento', CURRENT_TIMESTAMP, 2);

INSERT INTO tasks (title, description, status, creation_date, project_id)
VALUES ('Auditoria de Segurança', 'Revisar papéis e permissões de usuários', 'Concluída', CURRENT_TIMESTAMP, 2);
