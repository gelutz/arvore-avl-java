<div align="center">

# Árvore AVL em Java

![Java 25](https://img.shields.io/badge/Java-25-orange?logo=openjdk&logoColor=white)
![Gradle](https://img.shields.io/badge/build-Gradle-02303A?logo=gradle&logoColor=white)
![Tipo](https://img.shields.io/badge/projeto-acad%C3%AAmico-blue)

</div>

Árvore AVL de inteiros com inserção, remoção, busca e percursos (pré, pós e em-ordem). O enunciado está em `enunciado.md`.

## Rodar

```sh
make run
```

(ou `./gradlew run`)

## View

```sh
make view
```

Abre a árvore no terminal (feita com **TamboUI**), redesenhando a cada comando. A árvore começa vazia e vai mudando conforme os comandos entram.

| Comando | O que faz |
| --- | --- |
| `i <n>` | insere `n` e mostra a árvore resultante |
| `r <n>` | remove `n` e mostra a árvore resultante |
| `b <n>` | busca `n`, mostra o caminho percorrido e se achou ou não |
| `pre` | percurso **pré-ordem** |
| `em` | percurso **em-ordem** |
| `pos` | percurso **pós-ordem** |

`n` é sempre inteiro. Exemplo: `i 45` insere o 45, `r 78` remove o 78, `b 9` procura o 9 (e mostra por onde passou até achar, ou até desistir).

## Testar

```sh
make stub
```

(ou `./gradlew stub`, roda `avl.Test` com assertions ligadas)

## Autor

Gabriel Lutz ([@gelutz](https://github.com/gelutz))
