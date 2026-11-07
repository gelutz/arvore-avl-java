package avl.tree;

import avl.node.Node;

/**
 * - método de busca de um determinado elemento
 * - método de inserção de um determinado elemento
 * - método de remoção de um determinado elemento
 * - caminhos na árvore: Pré-Ordem, Pós-Ordem e Em-Ordem (pesquise sobre estas formas de caminhamento, caso necessário)
 */
public interface Tree {
    Node search(int value);
    void insert(int value);
    void remove(int value);
}
