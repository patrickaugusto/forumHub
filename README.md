# ForumHub API 📚💬

Bem-vindo ao **ForumHub API**, um sistema de discussão e gerenciamento de fóruns desenvolvido em Java com Spring Boot. Este projeto visa fornecer funcionalidades completas para a gestão de tópicos, usuários, respostas e autenticação.

---

## Funcionalidades Principais ✨

- **Autenticação de Usuários** 🔒
- **Cadastro, Listagem, Atualização e Exclusão de Tópicos** 📝
- **Gerenciamento de Respostas** 💬
- **Suporte a Swagger para Documentação** 📖

---

## Tecnologias Utilizadas 🛠️

- **Java 17** ☕
- **Spring Boot 3** 🌱
- **Spring Security** 🔐
- **Hibernate (JPA)** 🗃️
- **PostgreSQL** 🐘
- **Swagger/OpenAPI** 📘

---

## Endpoints Principais 🌐

### Autenticação 🔑

- **POST** `/auth/login` - Autentica um usuário e retorna um token JWT.
- **POST** `/auth/cadastrar` - Cadastra um novo usuário.

### Tópicos 📝

- **GET** `/topicos` - Lista todos os tópicos.
- **GET** `/topicos/{data}` - Listar topicos em uma determinada data.
- **GET** `/topicos/{id}` - Buscar um topico pelo id.
- **POST** `/topicos` - Cadastra um novo tópico.
- **PUT** `/topicos/{topicoId}/{usuarioId}` - Atualiza os dados de um tópico.
- **DELETE** `/topicos/{topicoId}/{usuarioId}` - Remove um tópico.

### Respostas 💬

- **GET** `/respostas` - Lista todas as respostas.
- **GET** `/respostas/{id}` - Buscar uma resposta pelo id.
- **POST** `/respostas` - Cadastra uma nova resposta.
- **DELETE** `/respostas/{respostaId}/{usuarioId}` - Remove uma resposta.
- **PUT** `/respostas/{respostaId}/{usuarioId}` 


