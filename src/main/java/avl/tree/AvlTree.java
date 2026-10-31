package avl.tree;

import avl.node.Node;
import avl.node.Order;

public class AvlTree implements Tree {

    private Node root;
    private Order order;

    public AvlTree() {
        this.order = Order.PRE;
    }

    public AvlTree(Order order) {
        this.order = order;
    }

    @Override
    public Node search(int value) {
        return switch (this.order) {
            case PRE -> searchPRE(root, value);
            case POS -> searchPOS(root, value);
            case EM -> searchEM(root, value);
        };
    }

    private Node searchPRE(Node root, int value) {
        if (root == null) return null;
        if (root.getValue() == value) return root;
        var resultLeft = this.searchPRE(root.getLeft(), value);
        if (resultLeft != null) return resultLeft;
        return this.searchPRE(root.getRight(), value);
    }

    private Node searchPOS(Node root, int value) {
        if (root == null) return null;
        var resultLeft = this.searchPRE(root.getLeft(), value);
        if (resultLeft != null) return resultLeft;

        var resultRight = this.searchPRE(root.getRight(), value);
        if (resultRight != null) return resultRight;
        if (root.getValue() == value) return root;
        return null;
    }

    private Node searchEM(Node root2, int value) {
        if (root == null) return null;
        var resultLeft = this.searchPRE(root.getLeft(), value);
        if (resultLeft != null) return resultLeft;

        if (root.getValue() == value) return root;

        var resultRight = this.searchPRE(root.getRight(), value);
        if (resultRight != null) return resultRight;
        return null;
    }

    @Override
    public void insert(int value) {
        if (root == null) {
            root = new Node(value);
            return;
        }

        root.insert(value);
    }

    @Override
    public void remote(int value) {}

    public Node getRoot() {
        return root;
    }
}
