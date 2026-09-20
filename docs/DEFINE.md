# Definição de requisitos

## Problema

Clientes precisam solicitar aluguel de veículos pela internet e agentes financeiros precisam analisar essas solicitações antes da contratação.

## Usuários

- **Cliente individual:** quer manter seus dados e controlar pedidos próprios antes da avaliação; precisa compreender o parecer e decidir se contrata.
- **Agente de empresa:** recebe pedidos, analisa a viabilidade e registra o parecer.
- **Agente bancário:** além de avaliar pedidos, concede o crédito vinculado ao leasing.

## Prioridades

### MUST

- Exigir cadastro prévio para utilizar o sistema.
- Permitir ao cliente criar, consultar, alterar e cancelar apenas pedidos próprios não avaliados.
- Permitir aos agentes analisar pedidos e registrar parecer positivo ou negativo.
- Permitir ao cliente aprovar ou recusar a contratação depois de parecer positivo.
- Modelar locação, assinatura e leasing, incluindo crédito bancário no leasing.
- Registrar cliente, seus dados pessoais, profissão e no máximo três empregadores com rendimento.
- Registrar placa, ano, marca e modelo do automóvel.
- Entregar aplicação web Java em MVC.
- Implementar na Sprint 2 o CRUD completo de clientes.

### SHOULD

- Validar CPF e RG obrigatórios e impedir CPF repetido.
- Apresentar mensagens de confirmação e de validação nas páginas.
- Manter modelo e implementação com os mesmos nomes e responsabilidades.

### COULD

- Persistir dados em banco externo e implementar autenticação na Sprint 3.
- Incluir paginação, pesquisa e auditoria.

### WON'T nesta sprint

- Implementar o fluxo completo de pedido, parecer, contrato, crédito e autenticação.
- Integrar instituições externas ou processar pagamentos.

## Critérios de sucesso da Sprint 2

1. A aplicação abre no navegador e lista clientes.
2. É possível cadastrar, consultar, editar e excluir um cliente.
3. Um cliente aceita de zero a três empregadores; o quarto é recusado.
4. CPF duplicado e campos obrigatórios inválidos são recusados com mensagem compreensível.
5. Controller, modelo, repositório e templates permanecem separados.
6. Todos os diagramas obrigatórios têm fonte PlantUML e imagem exportada.

## Escopo técnico

- Backend: Spring Boot, Spring MVC, Bean Validation e Spring Data JPA.
- Frontend: páginas renderizadas no servidor com Thymeleaf e CSS próprio.
- Banco: H2 em memória nesta sprint.
- IA: fora do produto. Codex foi usado como apoio ao desenvolvimento e está registrado nos documentos de contribuição.
- Autorização: o isolamento por usuário pertence à Sprint 3; nesta sprint as rotas do CRUD não exigem login.

## Clareza

Pontuação: **14/15**. A rubrica, os usuários, os dados do cliente, a plataforma e o limite da Sprint 2 estão explícitos. A única incerteza é o conteúdo do feedback oral da Sprint 1, que não foi fornecido; a revisão foi feita por consistência interna e aderência ao enunciado.

