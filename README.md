# Sistema de Aluguel de Carros

Projeto individual da disciplina de Laboratório de Desenvolvimento de Software — Laboratório 02.

O sistema apoia a gestão de pedidos e contratos de aluguel de automóveis. Clientes mantêm seus dados e pedidos, enquanto empresas e bancos analisam os pedidos. O projeto final será uma aplicação web em Java com arquitetura MVC.

## Entregas

- Sprint 1: casos de uso, histórias de usuário, diagrama de classes e diagrama de pacotes.
- Sprint 2: revisão dos modelos, diagrama de componentes e CRUD web de clientes em Java com MVC.
- Sprint 3: protótipo completo, comparação entre os modelos e o código e atualização dos diagramas.

## Executar o CRUD de clientes

Requer Java 21. O Maven Wrapper baixa o Maven na primeira execução.

```bash
./mvnw spring-boot:run
```

Abra `http://localhost:8080/clientes`. Os dados ficam no banco H2 em memória e são reiniciados quando a aplicação encerra.

## Verificar

```bash
./mvnw test
```

## Estrutura

```text
docs/                       requisitos, decisões, diagramas e contribuições
src/main/java/              aplicação MVC
src/main/resources/         páginas Thymeleaf e configuração
src/test/java/              testes do CRUD
```

Os arquivos-fonte PlantUML e suas exportações estão em `docs/diagramas/`.

