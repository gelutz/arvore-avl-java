package avl.tree;

import avl.node.Node;
import avl.node.Order;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.TreeSet;

public class BalancedAvlTreeTest {

    public static void balancedWalkTest() {
        var avlPRE = new AvlTree(Order.PRE);
        balancedWalkTest(avlPRE);
        System.out.println("PRE OK");
    }

    /**
     * Assim como o walkTest, mas espera que a árvore esteja balanceada depois de cada ação
     * @param tree
     */
    private static void balancedWalkTest(AvlTree tree) {
        tree.insert(50);
        tree.insert(25);
        tree.insert(12);

        var rootNode = tree.getRoot().getValue();
        assert rootNode == 25;
    }

    // os 4 casos: nos três valores, a raiz final é sempre o do meio
    public static void rotationTest() {
        rotationTest(new int[] { 50, 25, 12 }, 25, 12, 50);
        rotationTest(new int[] { 12, 25, 50 }, 25, 12, 50);
        rotationTest(new int[] { 50, 25, 30 }, 30, 25, 50);
        rotationTest(new int[] { 50, 75, 60 }, 60, 50, 75);
    }

    private static void rotationTest(int[] values, int root, int left, int right) {
        var tree = new AvlTree();
        for (int value : values) {
            tree.insert(value);
            assertValid(tree);
        }

        assert tree.getRoot().getValue() == root;
        assert tree.getRoot().getLeft().getValue() == left;
        assert tree.getRoot().getRight().getValue() == right;
    }

    public static void heightTest() {
        var tree = new AvlTree();
        tree.insert(0);
        assert tree.getRoot().getHeight() == 0;
        tree.insert(1);
        assert tree.getRoot().getHeight() == 1;

        // em ordem crescente, sem balanceamento daria altura 6
        for (int value = 2; value <= 6; value++) {
            tree.insert(value);
            assertValid(tree);
        }
        assert tree.getRoot().getHeight() == 2;
    }

    public static void removeTest() {
        // fator 0 no filho pesado, só acontece na remoção
        var tree = new AvlTree();
        for (int value : new int[] { 50, 25, 75, 60, 90 }) {
            tree.insert(value);
        }
        tree.remove(25);
        assertValid(tree);
        assert tree.getRoot().getValue() == 75;
        assert tree.getRoot().getLeft().getValue() == 50;
        assert tree.getRoot().getLeft().getRight().getValue() == 60;
        assert tree.getRoot().getRight().getValue() == 90;

        // nó com dois filhos
        tree = new AvlTree();
        for (int value : new int[] { 30, 20, 40, 10, 25 }) {
            tree.insert(value);
        }
        tree.remove(30);
        assertValid(tree);
        assert tree.search(30) == null;
        assert tree.getRoot().getValue() == 25;

        // duas rotações na mesma remoção: a primeira encurta a subárvore e desbalanceia a raiz
        tree = new AvlTree();
        for (int value : new int[] { 8, 5, 11, 3, 7, 10, 12, 2, 4, 6, 9, 1 }) {
            tree.insert(value);
        }
        tree.remove(12);
        assertValid(tree);
        assert tree.getRoot().getValue() == 5;

        // valor que não existe não muda nada
        tree.remove(999);
        assertValid(tree);

        // remover a última raiz deixa a árvore vazia
        tree = new AvlTree();
        tree.insert(1);
        tree.remove(1);
        assert tree.getRoot() == null;
    }

    // compara com um TreeSet e confere as invariantes depois de cada operação
    public static void randomOperationsTest() {
        var random = new Random(42);
        var tree = new AvlTree();
        var expected = new TreeSet<Integer>();

        for (int i = 0; i < 5000; i++) {
            int value = random.nextInt(300);

            if (random.nextBoolean() && !expected.contains(value)) {
                tree.insert(value);
                expected.add(value);
            } else {
                tree.remove(value);
                expected.remove(value);
            }

            assertValid(tree);
            assert inOrder(tree.getRoot()).equals(new ArrayList<>(expected));
        }
    }

    private static void assertValid(AvlTree tree) {
        assertValid(tree.getRoot(), null);

        // em ordem precisa sair crescente, é isso que prova a BST inteira
        // (comparar só com os filhos diretos não pega um valor perdido mais abaixo)
        List<Integer> values = inOrder(tree.getRoot());
        for (int i = 1; i < values.size(); i++) {
            assert values.get(i - 1) < values.get(i);
        }
    }

    private static void assertValid(Node node, Node parent) {
        if (node == null) return;

        assert node.getParent() == parent;
        assert Math.abs(node.balanceFactor()) <= 1;

        int leftHeight = node.getLeft() == null ? -1 : node.getLeft().getHeight();
        int rightHeight = node.getRight() == null ? -1 : node.getRight().getHeight();
        assert node.getHeight() == Math.max(leftHeight, rightHeight) + 1;

        assertValid(node.getLeft(), node);
        assertValid(node.getRight(), node);
    }

    private static List<Integer> inOrder(Node node) {
        List<Integer> values = new ArrayList<>();
        if (node == null) return values;

        values.addAll(inOrder(node.getLeft()));
        values.add(node.getValue());
        values.addAll(inOrder(node.getRight()));
        return values;
    }
}
