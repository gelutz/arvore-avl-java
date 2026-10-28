package avl.node;

public class Node {

    Node left;
    Node right;
    int value;

    public Node(int value) {
        this.value = value;
    }

    public void insert(int value) {
        if (this.value > value) {
            this.left = upsert(this.left, value);
        } else {
            this.right = upsert(this.right, value);
        }

        if (value == this.value) {
            throw new RuntimeException("Valor duplicado.");
        }
    }

    private Node upsert(Node node, int value) {
        if (node == null) {
            return new Node(value);
        }

        node.insert(value);
        return node;
    }
}
