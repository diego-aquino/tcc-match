# Guia de contribuição

Este é um documento colaborativo sobre a metodologia de desenvolvimento do
projeto. Sinta-se livre para fazer sugestões caso tenha ideias ou gostaria de
reavaliar alguma convenção.

## Convenções

### Branches

Este projeto conta com duas branches centrais:

- `main` como a branch principal, contendo a versão oficial e entregue do projeto.
- `projeto` como a branch secundária, contendo a versão testada e mais atualizada
  do projeto.

Durante o desenvolvimento, o padrão para criar branches é o seguinte:

- `feat/<branch-subject>`: branches para o desenvolvimento de uma nova
  funcionalidade
- `fix/<branch-subject>`: branches para correção de um problema ou bug
- `docs/<branch-subject>`: branches para mudanças na documentação do projeto
- `refactor/<branch-subject>`: branches para refatorações que não adicionam
  funcionalidades nem corrigem bugs
- `style/<branch-subject>`: branches para mudanças em estilos e lints

> Exemplos: `feat/create-student-endpoint`, `fix/updates-not-being-saved` e
> `docs/readme-installation-section`

### Mensagens de commit

Este projeto segue o padrão [conventional
commits](https://www.conventionalcommits.org), como forma de padronizar as
mensagens de commit. Por isso, cada commit deve estar em inglês (?) e seguir a
seguinte estrutura:

```
type(optional scope): subject
```

Os tipos disponíveis são:

- `feat`: mudanças que introduzem uma nova funcionalidade
- `fix`: mudanças que resolvem um problema ou bug
- `refactor`: refatorações que não adicionam funcionalidades nem corrigem problemas
- `docs`: mudanças na documentação do projeto
- `perf`: mudanças para melhorias de performance
- `build`: mudanças no processo de build ou em dependências externas
- `style`: mudanças de estilo de código e lint
- `revert`: mudanças que revertem a commits anteriores

> Exemplos: `feat: create model Student` e `fix(readme): add missing installation command`

### Issues

As tasks do projeto serão organizadas em issues, que facilitam o desenvolvimento no GitHub por
permitirem adicionar descrições, criar discussões e atribuir tasks. Além disso, elas ficam
centralizadas no próprio repositório e são de simples administração.

### Pull requests

Pull requests devem ser direcionados à branch `projeto`. É recomendado que eles tenham um título no
mesmo padrão dos commits, além de conter uma descrição do que foi modificado. Ao criar um pull
request, o template padrão está em [pull_request_template](./.github/pull_request_template.md).

- Exemplos de títulos

  - `feat: <funcionalidade adicionada>`
  - `fix: <fix realizado>`
  - `docs: <mudanças na documentação>`
  - ...

  > Seções que não foram modificadas podem ser omitidas do corpo do pull request. Por exemplo, se
  > não houve mudanças em documentação, não é preciso incluir a seção `Docs`.
