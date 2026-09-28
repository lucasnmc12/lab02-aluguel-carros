# Histórico de revisões da modelagem

## 21/09/2026 — Hierarquia de usuários e agentes

**Impacto:** médio.

### Origem

O professor apresentou no quadro as entidades do domínio e destacou a locadora como participante específico do sistema.

### Alteração

A antiga especialização `Empresa` foi substituída por `Locadora` nos diagramas de casos de uso e de classes. A hierarquia vigente é:

```text
Usuario
├── Cliente
└── Agente
    ├── Locadora
    └── Banco
```

### Justificativa

`Agente` reúne as operações comuns de análise e parecer. `Locadora` e `Banco` são os agentes concretos descritos para o processo de aluguel. `Empregador` continua como uma classe separada, pois representa uma fonte de renda do cliente.

### Impacto na implementação

Nenhuma alteração no CRUD de cliente foi necessária. A Sprint 2 implementa `Cliente` e `Empregador`; a hierarquia completa de usuários e agentes será materializada no código junto com autenticação, pedidos e pareceres na Sprint 3.

## 27/09/2026 — Revisão para o protótipo da Sprint III

**Origem:** enunciado e rubrica da Sprint III fornecidos pelo aluno. **Impacto:** alto, aditivo: novas contas, autenticação, pedidos e diagrama de implantação.

`Usuario` passou a existir no código como superclasse mapeada, e `Cliente` herda ID e hash de senha. O CPF identifica o login do cliente. `Automovel`, `PedidoAluguel`, `Modalidade` e `StatusPedido` representam o fluxo executável. Diagramas de classes, pacotes e componentes foram revistos; o diagrama de implantação foi acrescentado.

O plano anterior previa agentes, pareceres e contratos já na Sprint III. A implementação foi delimitada ao protótipo explicitamente cobrado nesta rubrica: cliente cadastrado cria e acompanha pedidos. Essas classes continuam nos diagramas com a marca `previsto`, evitando confundir modelagem final com código executável. A decisão preserva a hierarquia acordada com o professor; `Agente`, `Locadora` e `Banco` ainda não têm persistência nem telas. Não recebemos correções orais adicionais para os diagramas.

Os testes passaram a verificar login, dados próprios, criação/status, edição/cancelamento e isolamento entre clientes. O H2 segue em memória, logo dados e pedidos se perdem ao encerrar a aplicação.
