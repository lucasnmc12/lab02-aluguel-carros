# Sistema de Aluguel de Carros

Projeto individual da disciplina de Laboratório de Desenvolvimento de Software — Laboratório 02.

O sistema apoia a gestão de pedidos e contratos de aluguel de automóveis. Clientes mantêm seus dados e pedidos, enquanto empresas e bancos analisam os pedidos. O projeto final será uma aplicação web em Java com arquitetura MVC.

## Entregas

- Sprint 1: casos de uso, histórias de usuário, diagrama de classes e diagrama de pacotes.
- Sprint 2: revisão dos modelos, diagrama de componentes e CRUD web de clientes em Java com MVC.
- Sprint 3: revisão dos modelos, diagrama de implantação e protótipo para cadastrar clientes, criar pedidos e acompanhar seu status.

## Executar o protótipo

Requer Java 21. O Maven Wrapper baixa o Maven na primeira execução.

```bash
./mvnw spring-boot:run
```

Abra `http://localhost:8080`. Cadastre um cliente com CPF e senha, entre, escolha um dos três automóveis de demonstração e crie um pedido. A lista e o detalhe mostram o status. É possível editar ou cancelar o pedido enquanto aguarda análise.

Os dados ficam no banco H2 em memória e são reiniciados quando a aplicação encerra. O protótipo implementa o fluxo do cliente. Análise por locadora/banco e contratos permanecem na modelagem, sem telas executáveis nesta entrega.

## Verificar

```bash
./mvnw test
```

## Estrutura

```text
docs/                       requisitos, decisões, diagramas e contribuições
src/main/java/              aplicação MVC
src/main/resources/         páginas Thymeleaf e configuração
src/test/java/              testes do CRUD, login e pedidos
```

Os arquivos-fonte PlantUML e suas exportações estão em `docs/diagramas/`.
O diagrama de implantação está em [docs/diagramas/diagrama-de-implantacao.puml](docs/diagramas/diagrama-de-implantacao.puml) e a revisão da Sprint III em [docs/SPRINT3.md](docs/SPRINT3.md).
