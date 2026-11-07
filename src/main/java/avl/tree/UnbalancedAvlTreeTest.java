package avl.tree;

import avl.node.Order;

public class UnbalancedAvlTreeTest {

    public static void searchTest() {
        var avlPRE = new AvlTree(Order.PRE);
        searchTest(avlPRE);
        System.out.println("search - PRE OK");

        var avlPOS = new AvlTree(Order.POS);
        searchTest(avlPOS);
        System.out.println("search - POS OK");
        var avlEM = new AvlTree(Order.EM);
        searchTest(avlEM);
        System.out.println("search - EM OK");
    }

    private static void searchTest(AvlTree tree) {
        tree.insert(0);
        assert tree.getRoot().getValue() == 0;

        tree.insert(1);
        assert tree.getRoot().getRight().getValue() == 1;

        tree.insert(-1);
        assert tree.getRoot().getLeft().getValue() == -1;
        tree.insert(2);
        var result = tree.search(1);

        assert result.getValue() == 1;
        assert result.getRight().getValue() == 2;
        tree.insert(4);
        tree.insert(3);
        tree.insert(5);
        tree.insert(6);

        var nodeValor4 = tree.search(4);
        assert nodeValor4.getLeft().getValue() == 3;
    }

    public static void removeTest() {
        var avlPRE = new AvlTree(Order.PRE);
        removeTest(avlPRE);
        System.out.println("remove - PRE OK");
        var avlPOS = new AvlTree(Order.POS);
        removeTest(avlPOS);
        System.out.println("remove - POS OK");
        var avlEM = new AvlTree(Order.EM);
        removeTest(avlEM);
        System.out.println("remove - EM OK");
    }

    private static void removeTest(AvlTree tree) {
        tree.insert(50);
        tree.insert(25);
        tree.insert(12);
        tree.insert(6);

        tree.remove(6);
        assert tree.getRoot().getLeft().getLeft().getLeft() == null;

        tree.insert(75);
        tree.insert(90);

        tree.remove(75);
        assert tree.getRoot().getRight().getValue() == 90;
    }

    public static void heightTest() {
        AvlTree tree = new AvlTree(Order.PRE);

        heightTest(tree);
    }

    private static void heightTest(AvlTree tree) {
        tree.insert(0);
        assert tree.getRoot().getHeight() == 0;
        tree.insert(1);
        assert tree.getRoot().getHeight() == 1;

        tree.insert(2);
        tree.insert(3);
        tree.insert(4);
        tree.insert(5);
        tree.insert(6);

        assert tree.getRoot().getHeight() == 6;
        assert tree.getRoot().getRight().getHeight() == 5;

        tree.remove(4);

        assert tree.getRoot().getHeight() == 5;
        assert tree.getRoot().getRight().getHeight() == 4;
    }
}
