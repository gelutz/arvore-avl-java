package avl.node;

public class NodeTest {

    public static void insertTest() {
        Node n1 = new Node(0);
        n1.insert(1);

        assert n1.right.value == 1;
        assert n1.value == 0;
        assert n1.left == null;

        n1.insert(-3);

        assert n1.value == 0;
        assert n1.left.value == -3;

        n1.insert(4);

        Node n2 = n1.right;
        assert n2.right.value == 4;
        assert n2.left == null;
    }
}
