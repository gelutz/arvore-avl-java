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

    // retorna a raiz da subárvore, que pode ter mudado por causa de uma rotação
    public Node insert(int value) {
        if (value == this.value) {
            throw new RuntimeException("Valor duplicado.");
        }

        if (this.value > value) {
            this.left = upsert(this.left, value, this);
        } else {
            this.right = upsert(this.right, value, this);
        }

        return this.rebalance();
    }

    private Node upsert(Node node, int value, Node parent) {
        if (node == null) {
            return new Node(value, parent);
        }

        return node.insert(value);
    }

    private static int heightOf(Node node) {
        return node == null ? -1 : node.height;
    }

    public int balanceFactor() {
        return heightOf(this.left) - heightOf(this.right);
    }

    // recalcula a altura e rotaciona se o fator chegou em +2 ou -2
    // o filho com fator 0 só aparece na remoção, e ali a rotação simples resolve
    public Node rebalance() {
        this.updateHeight();
        int balance = this.balanceFactor();

        if (balance > 1) {
            if (this.left.balanceFactor() < 0) {
                this.left = this.left.rotateLeft();
            }
            return this.rotateRight();
        }

        if (balance < -1) {
            if (this.right.balanceFactor() > 0) {
                this.right = this.right.rotateRight();
            }
            return this.rotateLeft();
        }

        return this;
    }

    // quem chamou precisa religar o pai na nova raiz (só o parent da raiz é ajustado aqui)
    private Node rotateRight() {
        Node newRoot = this.left;
        Node moved = newRoot.right;

        newRoot.right = this;
        this.left = moved;

        newRoot.parent = this.parent;
        this.parent = newRoot;
        if (moved != null) {
            moved.parent = this;
        }

        // this agora está embaixo de newRoot, então vem primeiro
        this.updateHeight();
        newRoot.updateHeight();
        return newRoot;
    }

    private Node rotateLeft() {
        Node newRoot = this.right;
        Node moved = newRoot.left;

        newRoot.left = this;
        this.right = moved;

        newRoot.parent = this.parent;
        this.parent = newRoot;
        if (moved != null) {
            moved.parent = this;
        }

        this.updateHeight();
        newRoot.updateHeight();
        return newRoot;
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
