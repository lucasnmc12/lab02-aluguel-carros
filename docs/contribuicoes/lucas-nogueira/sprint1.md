# Contribuições — Lucas Nogueira

## Sprint 1 — Lab02S01

### Contribuição

Como estou desenvolvendo o Laboratório 02 sozinho, fiquei responsável por todos os artefatos desta sprint. Li o enunciado e transformei a descrição em doze casos de uso, agrupados em acesso, gestão de pedidos e gestão de contratos. Modelei os atores Cliente, Agente, Locadora e Banco, usando generalização para deixar explícito que locadora e banco são tipos de agente e que cliente e agente são usuários cadastrados.

Escrevi uma história para cada caso de uso com critérios de aceitação para as regras que mais afetam o comportamento: o cliente só manipula pedidos próprios ainda não avaliados, o contrato só nasce depois de um parecer positivo aceito pelo cliente e apenas um banco concede o crédito do leasing.

No diagrama de classes, separei pedido, parecer e contrato porque representam momentos diferentes do processo. Modelei os três tipos de contrato como especializações de `ContratoAluguel`, pois locação, assinatura e leasing compartilham dados básicos, mas possuem regras próprias. O contrato de crédito ficou em composição com o leasing e associado ao banco que o concedeu. Também representei a propriedade do automóvel por uma associação com `Usuario`, que permite cliente, locadora ou banco conforme a modalidade.

Por fim, preparei a visão lógica em pacotes, separando `controller`, `service`, `repository`, `model`, `dto`, `config` e as páginas. Essa estrutura antecipa a arquitetura MVC exigida pelo laboratório sem misturar telas, regras e persistência.

### Decisões e revisão

Tratei o cadastro como o único caso de uso que pode ser iniciado por um interessado ainda não autenticado. As demais funções dependem de autenticação. Não transformei “internet” ou “servidor central” em atores, pois são partes da arquitetura, representadas depois nos diagramas de pacotes e componentes.

O enunciado usa “usuários individuais” e “contratantes”; adotei `Cliente` como nome único para evitar duas classes com o mesmo papel. Um empregador é parte dos dados financeiros do cliente, com limite de três, mas continua sendo uma entidade própria porque possui nome e rendimento.

Não recebi feedback oral da apresentação desta sprint, pois ela não foi entregue no prazo. Para a revisão exigida na Sprint 2, conferi os modelos diretamente contra cada parágrafo do enunciado e contra as histórias de usuário. Registrei essa limitação de forma explícita em vez de afirmar que incorporei correções que não foram fornecidas.

### Uso do Codex

Usei o Codex, da OpenAI, como apoio para estruturar os requisitos, discutir as fronteiras entre pedido, parecer e contrato, escrever os arquivos PlantUML e revisar a consistência entre diagramas e histórias. Também pedi que a ferramenta exportasse os diagramas e verificasse se os arquivos PlantUML compilavam. Revisei o conteúdo e consigo explicar as decisões acima.

### Revisão posterior

Após a explicação do professor em aula, substituí o ator e a classe genérica `Empresa` por `Locadora`. A hierarquia consolidada passou a ser `Usuario` como generalização de `Cliente` e `Agente`, sendo `Locadora` e `Banco` especializações concretas de `Agente`. Mantive `Empregador` como conceito separado, pois ele representa a fonte de renda informada pelo cliente, não um agente do aluguel.
