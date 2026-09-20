# Design técnico — CRUD de clientes

## Arquitetura

```text
Navegador
   │ HTTP + HTML
   ▼
ClienteController ─────► templates Thymeleaf
   │ ClienteForm
   ▼
ClienteService ────────► Cliente / Empregador
   │
   ▼
ClienteRepository ─────► H2
```

O controller traduz HTTP e escolhe páginas. O service controla transação, CPF único e atualização segura da coleção de empregadores. O repositório cuida da persistência. As entidades mantêm o limite estrutural de até três empregadores. Os templates renderizam HTML sem acessar o banco.

## Decisões

### ADR-01 — Spring Boot com MVC renderizado no servidor

Spring MVC e Thymeleaf satisfazem diretamente o requisito de aplicação web Java com MVC e mantêm o projeto pequeno para apresentação. Uma API separada com frontend JavaScript aumentaria a quantidade de componentes sem agregar valor à rubrica atual.

### ADR-02 — H2 em memória na Sprint 2

O H2 torna a execução reproduzível e dispensa configuração externa. A interface `ClienteRepository` permite substituir o banco na Sprint 3 sem alterar controller e service.

### ADR-03 — Formulário separado das entidades

`ClienteForm` e `EmpregadorForm` concentram validação de entrada e evitam vincular diretamente parâmetros HTTP a entidades persistentes. A lista sempre oferece três posições, mas itens sem nome são descartados.

### ADR-04 — Exclusão com confirmação

A exclusão usa `POST /clientes/{id}/excluir`, evitando alteração de estado por uma requisição GET. A tela de detalhes exibe a confirmação antes do envio.

## Contrato HTTP

| Método | Caminho | Resultado |
| --- | --- | --- |
| GET | `/` | redireciona para `/clientes` |
| GET | `/clientes` | lista clientes |
| GET | `/clientes/novo` | formulário vazio |
| POST | `/clientes` | valida e cria cliente |
| GET | `/clientes/{id}` | mostra cliente e empregadores |
| GET | `/clientes/{id}/editar` | formulário preenchido |
| POST | `/clientes/{id}` | valida e atualiza cliente |
| POST | `/clientes/{id}/excluir` | exclui e retorna à lista |

Erros de validação retornam o formulário com HTTP 200 e mensagens ao lado dos campos. Recurso inexistente produz HTTP 404.

## Manifesto de arquivos

- `model/Cliente.java` e `model/Empregador.java`: entidades e invariantes.
- `dto/ClienteForm.java` e `dto/EmpregadorForm.java`: entrada validada.
- `repository/ClienteRepository.java`: acesso JPA.
- `service/ClienteService.java`: casos de uso do CRUD.
- `controller/ClienteController.java`: rotas MVC.
- `templates/clientes/*.html`: lista, formulário e detalhes.
- `static/css/app.css`: apresentação visual.
- `ClienteControllerTest.java`: fluxos HTTP essenciais.
- `ClienteServiceTest.java`: regras de unicidade e atualização.

## Estratégia de testes

- Teste de integração MVC com banco H2 para cadastrar, consultar, editar e excluir.
- Testes de validação para CPF duplicado, dados obrigatórios e mais de três empregadores.
- Inicialização completa da aplicação para detectar configuração inválida.

## Reversão

Cada parte será registrada em commit próprio. Se uma camada falhar, seu commit pode ser revertido sem apagar a documentação anterior. Como o banco é em memória, nenhuma migração permanente precisa ser revertida nesta sprint.

