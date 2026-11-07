package avl.tree;

import avl.node.Order;
import java.util.List;

public class AvlTreePathTest {

    //         30
    //      20    40
    //    10  25 35  50
    //   5
    private static AvlTree sample() {
        var tree = new AvlTree();
        for (int value : new int[] { 30, 20, 40, 10, 25, 35, 50, 5 }) {
            tree.insert(value);
        }
        return tree;
    }

    public static void searchPathTest() {
        var tree = sample();
        assert tree.searchPath(25).equals(List.of(30, 20, 25));
        assert tree.searchPath(30).equals(List.of(30));
        // o caminho para no último nó antes de onde o valor estaria
        assert tree.searchPath(27).equals(List.of(30, 20, 25));
        assert tree.searchPath(1).equals(List.of(30, 20, 10, 5));
        assert new AvlTree().searchPath(1).isEmpty();
    }

    public static void removalPathTest() {
        var tree = sample();
        // filho esquerdo sem direita: o substituto é o próprio filho
        assert tree.removalPath(20).equals(List.of(30, 20, 10));
        // desce à esquerda e depois tudo à direita
        assert tree.removalPath(30).equals(List.of(30, 20, 25));
        // sem dois filhos, não tem substituto
        assert tree.removalPath(10).equals(List.of(30, 20, 10));
        assert tree.removalPath(5).equals(List.of(30, 20, 10, 5));
        // valor ausente: só a descida
        assert tree.removalPath(27).equals(List.of(30, 20, 25));
        assert new AvlTree().removalPath(1).isEmpty();
    }

    // o fim do caminho precisa ser o valor que o remove de verdade usa como substituto
    public static void removalMatchesRealRemoveTest() {
        var tree = sample();
        int replacement = tree.removalPath(30).getLast();
        tree.remove(30);
        assert tree.getRoot().getValue() == replacement;
        assert tree.searchPath(30).getLast() != 30;
    }

    public static void traversalTest() {
        var tree = sample();
        assert tree.traversal(Order.PRE).equals(List.of(30, 20, 10, 5, 25, 40, 35, 50));
        assert tree.traversal(Order.EM).equals(List.of(5, 10, 20, 25, 30, 35, 40, 50));
        assert tree.traversal(Order.POS).equals(List.of(5, 10, 25, 20, 35, 50, 40, 30));
        assert new AvlTree().traversal(Order.EM).isEmpty();
    }
}
