package avl.node;

public class Node {

    Node parent;
    Node left;
    Node right;
    int value;
    int height = 0;

    public Node(int value) {
        this.value = value;
    }

    public Node(int value, Node parent) {
        this.value = value;
        this.parent = parent;
    }

    public void insert(int value) {
        if (value == this.value) {
            throw new RuntimeException("Valor duplicado.");
        }

        if (this.value > value) {
            this.left = upsert(this.left, value, this);
        } else {
            this.right = upsert(this.right, value, this);
        }

        this.updateHeight();
    }

    private Node upsert(Node node, int value, Node parent) {
        if (node == null) {
            return new Node(value, parent);
        }

        node.insert(value);
        return node;
    }

    private static int heightOf(Node node) {
        return node == null ? -1 : node.height;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Node getSuccessor() {
        if (this.left == null) {
            return null;
        } else if (this.left.right == null) {
            return this.left;
        } else {
            return this.left.highest();
        }
    }

    public Node getPredecessor() {
        if (this.right == null) {
            return null;
        } else if (this.right.left == null) {
            return this.right;
        } else {
            return this.right.lowest();
        }
    }

    private Node highest() {
        var p = this.right;
        while (p.right != null) {
            p = p.right;
        }

        return p;
    }

    private Node lowest() {
        var p = this.left;
        while (p.left != null) {
            p = p.left;
        }

        return p;
    }

    public void updateHeight() {
        this.height = Math.max(heightOf(this.left), heightOf(this.right)) + 1;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node node) {
        this.parent = node;
    }

    public Node getLeft() {
        return this.left;
    }

    public Node getRight() {
        return this.right;
    }

    public int getValue() {
        return this.value;
    }

    public void setLeft(Node value) {
        this.left = value;
    }

    public void setRight(Node value) {
        this.right = value;
    }

    public int getHeight() {
        return this.height;
    }
}
