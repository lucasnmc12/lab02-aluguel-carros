# Contribuições — Lucas Nogueira

## Sprint 2 — Lab02S02

### Contribuição

Nesta sprint continuei como único integrante. Comecei revisando os diagramas anteriores por rastreabilidade com o enunciado. Mantive as doze histórias, as modalidades como especializações de contrato e a separação dos pacotes MVC. Como não houve entrega nem apresentação da Sprint 1, não havia uma lista de correções do professor disponível; por isso, a revisão foi técnica e documental.

Criei o diagrama de componentes mostrando os computadores de clientes e agentes acessando, pela internet, o servidor central. Dentro do servidor representei controller, serviço, validação, persistência, construção dinâmica das páginas e o componente de pedidos e contratos previsto para a Sprint 3. O banco H2 aparece como decisão provisória da Sprint 2.

Implementei o CRUD completo de clientes como aplicação web em Java 21 com Spring Boot, Spring MVC, Thymeleaf, Spring Data JPA, Bean Validation e H2. A interface permite listar, cadastrar, consultar, editar e excluir clientes. O cadastro inclui RG, CPF, nome, endereço, profissão e até três empregadores com rendimento. O sistema normaliza o CPF, impede duplicidade, valida os campos obrigatórios e só aceita um empregador quando nome e rendimento são informados juntos.

Organizei a implementação em entidades, DTOs de formulário, repositório, serviço, controllers e templates. A regra de CPF único e a transação ficam no serviço; a entidade `Cliente` protege o limite de três empregadores; o controller lida com HTTP e navegação; e as páginas apenas apresentam o modelo. Assim, a implementação segue a visão de pacotes e o MVC exigido na rubrica.

### Decisões

Escolhi páginas renderizadas pelo servidor porque Thymeleaf se encaixa diretamente no MVC solicitado e permite demonstrar o fluxo inteiro sem depender de um segundo projeto frontend. Usei DTOs para não vincular parâmetros HTTP diretamente às entidades persistidas. A exclusão é feita por `POST` e exige confirmação no navegador, evitando que uma simples visita a um endereço remova dados.

O H2 está em memória para que o professor consiga executar o projeto sem instalar um banco. A troca por persistência permanente pode ser feita na Sprint 3 mantendo a interface do repositório. Autenticação e isolamento dos dados por cliente foram modelados, mas ficaram fora da implementação atual porque a rubrica desta sprint pede especificamente o CRUD de cliente.

### Verificação

Criei quatro testes de integração que inicializam a aplicação e exercitam as rotas MVC com o banco H2. Eles cobrem o fluxo completo de criação, consulta, edição e exclusão; campos obrigatórios; CPF duplicado; e cliente inexistente. Durante os testes encontrei e corrigi expressões inválidas em dois títulos Thymeleaf. A execução final de `./mvnw test` terminou com quatro testes aprovados e nenhuma falha.

### Uso do Codex

Usei o Codex, da OpenAI, como apoio para elaborar a especificação técnica, implementar o projeto Spring, escrever a interface, criar os testes e executar as verificações. A ferramenta também identificou os erros de template durante o teste e fez as correções. Mantive commits separados para modelagem, arquitetura, domínio, rotas, interface e testes para que a evolução possa ser apresentada e revisada.

### Revisão conforme orientação em aula

Depois que o professor apresentou as entidades do domínio no quadro, revisei a hierarquia de usuários para `Usuario > Cliente` e `Usuario > Agente > Locadora/Banco`. A mudança aproxima a nomenclatura do projeto da utilizada em aula e esclarece que a locadora e o banco analisam pedidos como agentes. O CRUD desta sprint não precisou mudar, porque sua implementação está limitada a `Cliente` e `Empregador`; as entidades de agente serão implementadas com os demais fluxos na Sprint 3.
