package avl.tree;

import avl.node.Order;

public class AvlTreeTest {

    public static void walkTest() {
        var avlPRE = new AvlTree(Order.PRE);
        walkTest(avlPRE);
        System.out.println("PRE OK");

        var avlPOS = new AvlTree(Order.POS);
        walkTest(avlPOS);
        System.out.println("POS OK");
        var avlEM = new AvlTree(Order.EM);
        walkTest(avlEM);
        System.out.println("EM OK");
    }

    private static void walkTest(AvlTree tree) {
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
}
