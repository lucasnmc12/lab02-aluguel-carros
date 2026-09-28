# Relatório de implementação — Sprint III

## Resultado

O protótipo permite que um cliente se cadastre, entre, crie um pedido e consulte seu status na lista e no detalhe. O pedido nasce em `AGUARDANDO_ANALISE`; enquanto estiver nesse estado, o cliente pode alterar a modalidade e o automóvel ou cancelá-lo. O cancelamento aparece como `CANCELADO`.

## Rastreabilidade da rubrica

| Critério | Implementação | Verificação |
| --- | --- | --- |
| Revisão dos diagramas | PlantUML de casos de uso, classes, pacotes e componentes atualizados; distinção entre executável e previsto | `plantuml -tpng docs/diagramas/*.puml` concluiu sem erros; imagens revisadas |
| Diagrama de implantação | `docs/diagramas/diagrama-de-implantacao.puml` e JPG/PNG em `docs/entregas` | exportação PlantUML e inspeção visual dos nós, artefatos e conexões |
| Criar pedido | login, `PedidoController`, `PedidoService`, JPA, formulário Thymeleaf | `PedidoFluxoTest`: cadastro, login e criação com automóvel e modalidade |
| Visualizar status | lista e detalhe próprios, `StatusPedido` | `PedidoFluxoTest`: estado inicial, mudança para cancelado e isolamento |
| Histórico de commits | commits separados para código, testes, diagramas e documentação | `git log` do repositório; horário real dos commits, sem datas artificiais |

## Verificações executadas

- `./mvnw -q test`: **9 testes**, nenhuma falha, nenhum erro. Os testes carregam a aplicação Spring MVC, Spring Security, Spring Data JPA e H2.
- Casos cobertos: CRUD da própria conta, validação de cadastro, CPF duplicado, pedido novo e status, edição, cancelamento, automóvel inexistente, login incorreto, acesso cruzado, rota privada e token CSRF nos formulários.
- Mutação local da consulta de pedido próprio: ao substituir temporariamente `findByIdAndClienteCpf` por `findById`, o teste de acesso cruzado falhou (`esperado 404, recebido 200`). A restrição foi restaurada e a suíte completa passou. Nenhuma mutação foi commitada.
- `git diff --check`: sem erros de whitespace.

## Limitações e operação

O banco H2 está em memória; uma reinicialização apaga clientes e pedidos. Três automóveis de exemplo são carregados a cada início. Apenas o fluxo do cliente está disponível. A análise por agentes, pareceres e contratos aparecem como previstos na modelagem e não foram implementados nesta sprint. A publicação na internet exigiria HTTPS e banco persistente.

Para demonstrar: `./mvnw spring-boot:run`, abrir `http://localhost:8080`, cadastrar cliente, entrar, criar pedido e abrir a lista/detalhe. A versão local usa HTTP no computador do aluno.
