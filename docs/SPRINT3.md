# Sprint III — design e verificação

## Escopo do protótipo

O cliente se cadastra, entra com CPF e senha, escolhe um automóvel e uma modalidade e acompanha o status do pedido. A própria conta é o vínculo de autoria do pedido. O CRUD do cliente da Sprint II continua disponível para a própria conta. O catálogo de três automóveis é carregado no início para permitir uma demonstração imediata.

## Arquitetura executada

```text
Navegador → Spring Security → Controllers MVC → Services → Repositories JPA → H2
                                      └──────→ Thymeleaf → HTML
```

O Spring Security controla a sessão, protege as rotas, exige token CSRF nos formulários e compara a senha com hash BCrypt. `Usuario` é uma superclasse JPA mapeada; `Cliente` herda ID e hash. `PedidoService` consulta os pedidos pelo CPF autenticado, de modo que a URL de outro cliente resulta em 404. A verificação de dono ocorre antes de alterar ou cancelar.

## Contratos principais

| Método | Rota | Resultado |
| --- | --- | --- |
| GET | `/clientes/novo` | formulário público de cadastro |
| POST | `/clientes` | cria a conta e envia para login |
| GET/POST | `/entrar` | formulário e autenticação |
| POST | `/sair` | encerra a sessão |
| GET | `/pedidos` | lista apenas os pedidos do cliente |
| GET | `/pedidos/novo` | formulário com automóveis e modalidades |
| POST | `/pedidos` | cria pedido em `AGUARDANDO_ANALISE` |
| GET | `/pedidos/{id}` | detalhe e status do pedido próprio |
| GET/POST | `/pedidos/{id}/editar`, `/pedidos/{id}` | edição antes da análise |
| POST | `/pedidos/{id}/cancelar` | muda status para `CANCELADO` |

## Revisão dos modelos

- Casos de uso: a hierarquia `Usuario → Cliente/Agente → Locadora/Banco` já incorpora o feedback informado pelo professor. Nesta entrega, UC01–UC07 estão acessíveis pelo fluxo do cliente; UC08–UC12 representam etapas futuras.
- Classes: `Usuario`, `Cliente`, `Empregador`, `Automovel`, `PedidoAluguel`, `Modalidade` e `StatusPedido` têm correspondentes no código. Agentes, parecer e contratos foram marcados como previstos. A propriedade do automóvel também é uma regra futura.
- Pacotes: acrescentados controllers, services, repositories, formulários e configuração reais da Sprint III.
- Componentes: substituído o componente planejado de pedidos pelo fluxo executável com segurança, MVC, Thymeleaf, JPA e H2.
- Implantação: acrescentados os nós físicos, artefatos e canais de comunicação. Localmente o navegador usa HTTP; HTTPS é requisito de uma publicação em servidor.

## Limites

O H2 é volátil. Não há análise por locadora/banco, parecer, decisão do cliente nem contrato executável. O diagrama de classes conserva essas classes como modelo alvo; não as apresenta como funcionalidades concluídas. Não houve novo feedback oral específico além da hierarquia de usuários comunicada pelo professor.

## Demonstração para a apresentação

1. Iniciar com `./mvnw spring-boot:run` e abrir `http://localhost:8080`.
2. Cadastrar um cliente e entrar com CPF e senha.
3. Criar um pedido de locação para um automóvel do catálogo.
4. Abrir lista e detalhe, mostrar `Aguardando análise`.
5. Alterar a modalidade e depois cancelar, mostrando `Cancelado`.
6. Comparar as telas com os diagramas de classes, componentes e implantação, explicando os elementos ainda previstos.
