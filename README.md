![tonnieJava](https://github.com/user-attachments/assets/ab8fa440-d6e5-4964-9ff9-cfe47d5c8dc7)

# ☕ Abstraindo um Bootcamp com Orientação a Objetos em Java

> **Bootcamp TONNIE — Java and AI in Europe | DIO**

---

## 1. 🧩 Problema de Negócio

Qualquer desenvolvedor já ouviu os quatro pilares da POO: Abstração, Encapsulamento, Herança e Polimorfismo. A maioria consegue defini-los. Poucos conseguem responder a pergunta que realmente importa em uma entrevista:

> **"Me dê um exemplo real de quando você precisou usar cada um desses pilares juntos para resolver um problema de modelagem."**

Esse é o gap entre quem estudou POO e quem pratica POO. Tutoriais ensinam cada pilar isoladamente — `Animal` herda de `Ser Vivo`, `Cachorro` faz `Som`. Exemplos que ninguém encontra no mundo real e que não preparam o desenvolvedor para modelar um domínio de verdade.

> **O que este projeto resolve:** modelar o domínio de um Bootcamp educacional completo — com cursos, mentorias, devs, inscrições e progressão de XP — usando os quatro pilares da POO de forma integrada, onde cada decisão de design tem uma justificativa técnica clara.

---

## 2. 📌 Contexto

O projeto foi desenvolvido como parte do Bootcamp TONNIE — Java and AI in Europe, como resposta ao desafio da DIO de **abstrair o próprio contexto de um bootcamp em código Java**.

A escolha do domínio é estratégica: um bootcamp tem entidades com relacionamentos reais e regras de negócio que forçam decisões de modelagem concretas.

- `Curso` e `Mentoria` são tipos diferentes de conteúdo, mas ambos pertencem a um bootcamp e geram XP — isso exige **abstração e herança**;
- Um `Dev` pode se inscrever em um bootcamp, progredir pelos conteúdos e acumular XP — o saldo de XP precisa ser protegido de modificações externas diretas — isso exige **encapsulamento**;
- O cálculo de XP de um `Curso` é diferente do cálculo de XP de uma `Mentoria`, mas o código que totaliza o XP do Dev não precisa saber qual tipo está processando — isso é **polimorfismo**.

O resultado é um sistema que simula o ciclo completo: criação do bootcamp, inscrição de devs, progressão pelos conteúdos e consulta do XP acumulado.

---

## 3. 📐 Premissas da Solução

As seguintes premissas guiaram cada decisão de modelagem:

- **`Conteudo` é abstrata por necessidade, não por convenção** — ela nunca será instanciada diretamente porque "conteúdo genérico" não existe no domínio. Todo conteúdo é sempre um `Curso` ou uma `Mentoria`. Classes abstratas existem para modelar conceitos que só fazem sentido em suas especializações;
- **XP não é um campo público** — o acumulado de XP de um `Dev` só pode crescer através do método `progredir()`. Nenhum código externo pode setar o XP diretamente, porque isso violaria a regra de negócio de que XP só é ganho ao concluir conteúdos;
- **`LinkedHashSet` para os conteúdos do bootcamp** — a ordem de inserção é preservada (o dev cursa os conteúdos na sequência em que foram adicionados) e duplicatas são automaticamente rejeitadas, sem nenhum código de validação adicional;
- **`calcularXp()` é abstrato em `Conteudo`** — cada especialização implementa sua própria lógica de XP. O `Dev` não precisa distinguir se está processando um `Curso` ou uma `Mentoria` ao calcular o total.

---

## 4. ⚙️ Estratégia da Solução

A construção seguiu uma modelagem progressiva, do mais abstrato ao mais concreto:

**Passo 1 — Identificar a hierarquia do domínio**
Antes de qualquer linha de código, mapear as entidades: o que é um conceito genérico (`Conteudo`) e o que são suas especializações (`Curso`, `Mentoria`). Essa distinção define onde a abstração e a herança se aplicam.

**Passo 2 — Definir os contratos abstratos**
`Conteudo` declara `calcularXp()` como método abstrato — cada subclasse é obrigada a implementar sua lógica de XP. Título e descrição são atributos comuns encapsulados com `private` e acessados via getters.

**Passo 3 — Especializar com regras próprias**
`Curso` adiciona `cargaHoraria` e calcula XP com base nela. `Mentoria` adiciona `data` e tem um XP fixo com bônus. Cada classe tem sua identidade sem duplicar o que é comum.

**Passo 4 — Modelar o `Dev` com progressão controlada**
`Dev` possui dois `LinkedHashSet`: `conteudosInscritos` (o que ainda precisa cursar) e `conteudosConcluidos` (o que já cursou). O método `progredir()` move o primeiro conteúdo pendente para concluídos e soma o XP — garantindo que a progressão seja sequencial e controlada.

**Passo 5 — Montar a simulação em `Main`**
Criação de cursos e mentorias, configuração do bootcamp, inscrição de devs, chamadas a `progredir()` e exibição do XP acumulado — validando o comportamento completo do modelo.

---

## 5. 💡 Insights Técnicos

Os aprendizados mais reveladores vieram das decisões de design que pareciam pequenas mas tinham impacto direto na modelagem:

- **`abstract` é uma restrição que protege o domínio.** Ao tornar `Conteudo` abstrata, fica impossível criar um "conteúdo genérico" por engano — o compilador rejeita `new Conteudo()`. Isso não é apenas estilo; é uma regra de negócio expressa pelo próprio sistema de tipos da linguagem.

- **`calcularXp()` abstrato eliminou um `instanceof`.** A primeira versão do código que calcula o XP total do Dev usava `if (conteudo instanceof Curso)` e `else if (conteudo instanceof Mentoria)`. Tornar o método abstrato e deixar cada subclasse implementar transformou essa lógica condicional em polimorfismo real — sem `instanceof`, sem cast, sem código que precisa ser modificado quando um terceiro tipo de conteúdo for adicionado.

- **`LinkedHashSet` foi uma escolha deliberada, não aleatória.** `List` permitiria duplicatas — um `Dev` poderia se inscrever duas vezes no mesmo curso. `HashSet` rejeitaria duplicatas mas não garantiria a ordem de progressão. `LinkedHashSet` resolve os dois problemas com uma única estrutura. Conhecer as nuances das coleções Java e escolher a certa para cada contexto é uma diferença que aparece em revisões de código e entrevistas.

- **Encapsulamento do XP revelou um problema de design na progressão.** A primeira versão tinha `setXpTotal()` público. Ao torná-lo privado e forçar o XP a crescer somente via `progredir()`, ficou claro que o método precisava lançar uma exceção quando não há conteúdo pendente — porque chamar `progredir()` em um dev sem inscrições é um estado inválido, não apenas uma operação sem efeito.

- **O domínio do bootcamp é um modelo de grafo unidirecional.** `Bootcamp` contém `Conteudo`. `Dev` referencia `Bootcamp` indiretamente através dos `LinkedHashSet`. Nenhuma entidade sabe o que está dentro do outro além do necessário. Esse baixo acoplamento entre as classes foi consequência natural de encapsular bem — não foi uma decisão explícita de arquitetura.

---

## 6. 📊 Resultados

**Os quatro pilares aplicados com rastreabilidade:**

| Pilar | Onde está no código | Por que foi necessário |
|---|---|---|
| **Abstração** | Classe `Conteudo` — abstrata, nunca instanciada | "Conteúdo genérico" não existe no domínio; só `Curso` e `Mentoria` existem |
| **Encapsulamento** | XP em `Dev` com `private` + método `progredir()` | XP só pode crescer ao concluir conteúdos — regra de negócio protegida pelo design |
| **Herança** | `Curso` e `Mentoria` estendem `Conteudo` | Reutilização de título e descrição; especialização com cargaHoraria e data |
| **Polimorfismo** | `calcularXp()` em cada subclasse | O Dev calcula XP total sem saber se está processando `Curso` ou `Mentoria` |

**Estrutura de classes:**

| Classe | Tipo | Responsabilidade |
|---|---|---|
| `Conteudo` | Abstrata | Contrato base com título, descrição e `calcularXp()` abstrato |
| `Curso` | Concreta | Especialização com `cargaHoraria` e XP proporcional à carga |
| `Mentoria` | Concreta | Especialização com `data` e XP fixo com bônus |
| `Bootcamp` | Concreta | Agrega conteúdos e devs inscritos |
| `Dev` | Concreta | Inscrição, progressão sequencial e acúmulo de XP |
| `Main` | Executável | Simulação completa do ciclo do bootcamp |

---

## 7. 🚀 Próximos Passos

O modelo bem estruturado abre caminho para evoluções sem refatoração destrutiva:

- [ ] **Novo tipo de conteúdo: `Desafio`** — adicionar uma terceira especialização de `Conteudo` com pontuação baseada em dificuldade, sem modificar nenhuma das classes existentes — demonstrando Open/Closed Principle na prática;
- [ ] **Persistência com Spring Data JPA** — mapear as entidades com `@Entity` e `@OneToMany` para persistir o estado dos devs e seus progressos em banco de dados MySQL;
- [ ] **Ranking de devs por XP** — implementar `Comparable<Dev>` ou um `Comparator` externo para ordenar e exibir o ranking do bootcamp;
- [ ] **Validações com Bean Validation** — adicionar `@NotBlank` e `@Positive` nas entidades para tornar as validações declarativas e testáveis;
- [ ] **Testes unitários com JUnit 5** — validar `calcularXp()` de cada subclasse, o comportamento de `progredir()` com e sem conteúdos pendentes, e a rejeição de duplicatas no `LinkedHashSet`.

---

## 🛠️ Tecnologias Utilizadas

| Tecnologia | Função no projeto |
|---|---|
| Java 17+ | Linguagem principal |
| Classes abstratas | Abstração do conceito de `Conteudo` |
| Herança (`extends`) | Especialização em `Curso` e `Mentoria` |
| Polimorfismo | `calcularXp()` com implementação distinta por subclasse |
| `LinkedHashSet` | Coleção ordenada sem duplicatas para progressão do Dev |
| Encapsulamento (`private`) | Proteção do XP contra modificação externa |

---

## 🔧 Como Executar

**Pré-requisito:** Java 17+ instalado.

**1. Clone o repositório**
```bash
git clone https://github.com/Santosdevbjj/pooDesafioJava.git
cd pooDesafioJava
```

**2. Compile o projeto**
```bash
javac -d out src/**/*.java
```

**3. Execute a simulação**
```bash
java -cp out Main
```

> A saída exibirá os devs inscritos, seus progressos e o XP acumulado após cada avanço no bootcamp.

---

## 📚 Aprendizados

Este projeto foi onde os quatro pilares da POO deixaram de ser definições e viraram **ferramentas de tomada de decisão**.

O maior aprendizado foi entender que encapsulamento não é sobre esconder dados — é sobre **proteger invariantes do domínio**. Quando tornei o XP privado e exigi que crescesse apenas via `progredir()`, não estava apenas seguindo boas práticas de estilo. Estava garantindo que nenhum trecho de código futuro pudesse colocar o Dev em um estado impossível — com XP maior do que os conteúdos concluídos justificariam.

O segundo aprendizado foi o `instanceof` que desapareceu. Ver o `calcularXp()` abstrato eliminar o condicional que distinguia `Curso` de `Mentoria` foi o momento em que polimorfismo saiu do papel e virou refatoração real. Esse é o tipo de coisa que aparece em code review de uma empresa — e que separa quem usa POO de quem pensa com POO.

Se fosse recomeçar, começaria pelas perguntas de design antes de abrir o IDE: "Quais entidades do domínio nunca existem de forma genérica?" — a resposta a essa pergunta indica onde usar classes abstratas. Foi a pergunta que não me fiz na primeira versão e que me custou uma refatoração.

---

## 👤 Autor

**Sérgio Santos**



[![Portfólio](https://img.shields.io/badge/Portfólio-Sérgio_Santos-111827?style=for-the-badge&logo=githubpages&logoColor=00eaff)](https://portfoliosantossergio.vercel.app)
[![LinkedIn](https://img.shields.io/badge/LinkedIn-Sérgio_Santos-0A66C2?style=for-the-badge&logo=linkedin&logoColor=white)](https://linkedin.com/in/santossergioluiz)

---


