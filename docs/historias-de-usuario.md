# Histórias de usuário

As histórias abaixo correspondem aos casos de uso do diagrama da Sprint 1. Como o projeto é individual, todas são de responsabilidade de Lucas Nogueira.

## Acesso e cadastro

### HU01 — Cadastrar-se (UC01)

Como interessado, quero cadastrar meus dados e credenciais, para utilizar o sistema de aluguel.

Critérios de aceitação:

- CPF, RG, nome, endereço e profissão são obrigatórios para o cliente.
- O CPF identifica um único cliente.
- O cliente pode informar no máximo três empregadores, cada um com nome e rendimento não negativo.

### HU02 — Autenticar-se (UC02)

Como usuário cadastrado, quero entrar com minhas credenciais, para acessar as funções do meu perfil.

Critérios de aceitação:

- Credenciais inválidas não concedem acesso.
- Cliente e agente acessam somente as funções destinadas ao seu perfil.

### HU03 — Manter dados do cliente (UC03)

Como cliente, quero consultar e atualizar meus dados cadastrais, para mantê-los corretos durante a análise financeira.

Critérios de aceitação:

- O cliente visualiza seus dados de identificação, profissão, empregadores e rendimentos.
- Continuam valendo a unicidade do CPF e o limite de três empregadores.

## Pedidos

### HU04 — Criar pedido de aluguel (UC04)

Como cliente, quero criar um pedido informando o automóvel e a modalidade, para solicitar o aluguel.

Critérios de aceitação:

- A modalidade é locação, assinatura ou leasing.
- O automóvel escolhido possui placa, ano, marca e modelo cadastrados.
- Um pedido novo recebe a situação `AGUARDANDO_ANALISE` e pertence ao cliente autenticado.

### HU05 — Consultar pedidos próprios (UC05)

Como cliente, quero consultar meus pedidos e seus estados, para acompanhar a análise e as decisões pendentes.

Critérios de aceitação:

- O cliente não visualiza pedidos de outros clientes.
- A consulta mostra modalidade, automóvel, situação e parecer, quando existente.

### HU06 — Alterar pedido (UC06)

Como cliente, quero alterar um pedido ainda não avaliado, para corrigir a modalidade ou o automóvel pretendido.

Critérios de aceitação:

- Somente o dono do pedido pode alterá-lo.
- O pedido deixa de aceitar alterações assim que recebe um parecer.

### HU07 — Cancelar pedido (UC07)

Como cliente, quero cancelar um pedido ainda não avaliado, para desistir da solicitação.

Critérios de aceitação:

- Somente o dono pode cancelar o pedido.
- Pedidos já avaliados não podem ser cancelados por esse fluxo.

## Análise e contratação

### HU08 — Consultar pedidos recebidos (UC08)

Como agente, quero consultar os pedidos enviados para minha instituição, para selecionar os que precisam de análise.

### HU09 — Avaliar pedido (UC09)

Como agente, quero registrar um parecer financeiro no pedido, para aprová-lo ou rejeitá-lo.

Critérios de aceitação:

- O parecer informa resultado positivo ou negativo, justificativa, agente e data.
- Um parecer negativo encerra o pedido como rejeitado.
- Um parecer positivo encaminha o pedido à decisão do cliente.

### HU10 — Decidir contratação (UC10)

Como cliente, quero aceitar ou recusar um pedido com parecer positivo, para controlar se o aluguel seguirá para contrato.

Critérios de aceitação:

- A decisão só aparece ao dono do pedido aprovado.
- Ao aceitar, o sistema gera o contrato adequado à modalidade.
- Ao recusar, o pedido é encerrado sem contrato.

### HU11 — Emitir contrato de aluguel (UC11)

Como sistema, quero registrar o contrato aceito pelo cliente, para formalizar a modalidade, o prazo, o valor e a propriedade do veículo.

Critérios de aceitação:

- Locação prevê prazo determinado e devolução.
- Assinatura prevê uso recorrente e mensalidade.
- Leasing prevê longo prazo e contrato de crédito.
- O proprietário do automóvel é compatível com a modalidade e pode ser cliente, empresa ou banco.

### HU12 — Conceder crédito do leasing (UC12)

Como agente bancário, quero conceder o crédito associado ao leasing, para viabilizar essa modalidade.

Critérios de aceitação:

- Apenas banco agente concede crédito.
- O crédito referencia exatamente um contrato de leasing e registra valor, prazo e taxa.

