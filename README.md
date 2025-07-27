## Abstraindo um Bootcamp Usando Orientação a Objetos em Java.




# Desafio POO Bootcamp (Java)

Projeto para aprendizado prático dos pilares da Programação Orientada a Objetos (POO) em Java:

- Abstração  
- Encapsulamento  
- Herança  
- Polimorfismo  

Inspirado no desafio da Digital Innovation One 💛🧡 1

## Estrutura do Projeto

- `Conteudo`: classe abstrata com atributos título e descrição  
- `Curso`, `Mentoria`: especializações de `Conteudo`  
- `Bootcamp`: reúne conteúdos e desenvolvedores  
- `Dev`: inscrição, progressão e cálculo de XP  
- `Main`: simula execução, inscrição e progresso

## Diagrama UML (ASCII) 




ASCII)

Conteudo (abstract)
    ├─ titulo: String
    ├─ descricao: String
    └─ calcularXp(): double

   /          \

Curso             Mentoria ├─ cargaHoraria     ├─ data: LocalDate └─ calcularXp()     └─ calcularXp()

Bootcamp

├─ nome ├─ descricao ├─ dataInicial ├─ dataFinal ├─ conteudos: Set<Conteudo> └─ devsInscritos: Set<Dev>

Dev

├─ nome ├─ inscritos: Set<Conteudo> ├─ concluidos: Set<Conteudo> ├─ inscreverBootcamp() ├─ progredir() └─ calcularTotalXp()

Main

└─ instancia conteúdos, bootcamp e devs, simula progresso 





.





