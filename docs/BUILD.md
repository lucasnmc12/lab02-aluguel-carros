# Relatório de implementação — Sprint 2

## Resultado

O CRUD web de clientes está implementado em Java 21, Spring Boot e MVC. A aplicação oferece criação, listagem, detalhes, edição e exclusão, incluindo até três empregadores por cliente.

## Alinhamento com os modelos

- `Cliente` e `Empregador` implementam os dados e a cardinalidade `0..3` do diagrama de classes.
- Controller, service, repository, DTO e templates seguem o diagrama de pacotes.
- Spring MVC, Thymeleaf, Bean Validation, Spring Data JPA e H2 correspondem aos componentes da Sprint 2.
- Pedido, parecer, modalidades de contrato, crédito, agentes e autenticação estão modelados e reservados para a Sprint 3.

## Validações implementadas

- campos pessoais obrigatórios;
- CPF com 11 dígitos, com ou sem máscara;
- CPF único depois da remoção da máscara;
- até três empregadores;
- rendimento não negativo;
- nome e rendimento do empregador preenchidos em conjunto;
- HTTP 404 ao consultar cliente inexistente.

## Verificação executada

Comando: `./mvnw test`

Resultado: **4 testes executados, 0 falhas e 0 erros**.

Os testes usam a aplicação completa com MockMvc, JPA e H2 e cobrem o CRUD, validação obrigatória, CPF duplicado e recurso inexistente.

## Limitações conhecidas

- O banco é reiniciado junto com a aplicação.
- Não há autenticação nesta sprint.
- Pedidos, pareceres e contratos ainda não possuem implementação executável.
- A revisão dos diagramas não incorpora feedback oral do professor porque a Sprint 1 não foi apresentada e nenhuma lista de correções foi fornecida.

