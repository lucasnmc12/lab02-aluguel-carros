# Contribuições — Lucas Nogueira

## Sprint 3 — Lab02S03

Nesta sprint trabalhei sozinho. Revisei os diagramas para refletir a estrutura discutida em aula — `Usuario`, `Cliente`, `Agente`, `Locadora` e `Banco` — e a parte do sistema que está efetivamente rodando. Acrescentei o diagrama de implantação com o computador do cliente, a rede, o servidor Java, os arquivos da aplicação e o H2. Marquei como previstos os elementos do modelo completo que ainda não aparecem no protótipo, como avaliação financeira, parecer e contrato.

Implementei o cadastro com senha, login por CPF e sessão protegida. A senha fica armazenada como hash BCrypt. Depois de entrar, o cliente vê apenas seu cadastro e seus pedidos. Criei um pequeno catálogo de automóveis de demonstração para que seja possível fazer um pedido escolhendo veículo e modalidade. Um pedido novo recebe o status “Aguardando análise”; sua lista e página de detalhes exibem esse status. Enquanto aguarda análise, o dono pode editar ou cancelar o pedido. O cancelamento muda o status para “Cancelado”.

Mantive a divisão MVC: controllers recebem as requisições e escolhem páginas, services aplicam as regras, entidades representam os dados, repositories acessam o H2 e Thymeleaf monta as telas. Optei por Spring Security para autenticação, autorização das rotas e proteção CSRF dos formulários. A verificação de propriedade do pedido é feita no serviço usando o CPF da sessão; assim, conhecer o número de outro pedido não permite consultá-lo.

Testei o percurso de cadastro, login, criação, consulta, edição e cancelamento com MockMvc e H2, incluindo dados inválidos, falta de sessão, falta de CSRF e tentativa de acessar pedido de outro cliente. Fiz uma mutação temporária local retirando a restrição de dono do pedido: o teste de acesso cruzado falhou como esperado; em seguida restaurei a checagem. O protótipo ainda não registra pareceres nem cria contratos. O banco em memória foi mantido para uma demonstração simples e reproduzível.

Usei o Codex, da OpenAI, como apoio na implementação, atualização dos diagramas, documentação, escrita dos testes e execução das verificações. Revisei os resultados e organizei a evolução em commits com assuntos separados. As decisões de escopo e a apresentação do trabalho são minhas.

Em aula, em 28/09, defini que o usuário já inicia o percurso no cadastro. Com apoio do Codex, retirei o ator `Interessado` do diagrama de casos de uso, liguei `Cadastrar-se` a `Usuário`, atualizei a história correspondente e exportei novamente as imagens. Mantive as versões anteriores para registrar a mudança.
