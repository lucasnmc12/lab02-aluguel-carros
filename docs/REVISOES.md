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
