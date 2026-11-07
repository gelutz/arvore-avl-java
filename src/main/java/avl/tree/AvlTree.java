package avl.tree;

import avl.node.Node;
import avl.node.Order;
import java.util.ArrayList;
import java.util.List;

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
        var resultLeft = this.searchPOS(root.getLeft(), value);
        if (resultLeft != null) return resultLeft;

        var resultRight = this.searchPOS(root.getRight(), value);
        if (resultRight != null) return resultRight;
        if (root.getValue() == value) return root;
        return null;
    }

    private Node searchEM(Node root, int value) {
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

        root = root.insert(value);
    }

    @Override
    public void remove(int value) {
        Node node = search(value);
        if (node == null) {
            return;
        }

        removeNode(node);
    }

    public Node getRoot() {
        return root;
    }

    // descida binária a partir da raiz: termina no valor ou onde ele deveria estar
    public List<Integer> searchPath(int value) {
        List<Integer> path = new ArrayList<>();
        Node p = root;
        while (p != null) {
            path.add(p.getValue());
            if (p.getValue() == value) break;
            p = value < p.getValue() ? p.getLeft() : p.getRight();
        }
        return path;
    }

    // com dois filhos, o removeNode ainda desce até o substituto antes de remover
    public List<Integer> removalPath(int value) {
        List<Integer> path = searchPath(value);
        Node node = search(value);
        if (node != null && node.getLeft() != null && node.getRight() != null) {
            Node substituto = node.getSuccessor();
            for (Node p = node.getLeft(); p != substituto; p = p.getRight()) {
                path.add(p.getValue());
            }
            path.add(substituto.getValue());
        }
        return path;
    }

    public List<Integer> traversal(Order order) {
        List<Integer> values = new ArrayList<>();
        traversal(root, order, values);
        return values;
    }

    private void traversal(Node node, Order order, List<Integer> values) {
        if (node == null) return;
        if (order == Order.PRE) values.add(node.getValue());
        traversal(node.getLeft(), order, values);
        if (order == Order.EM) values.add(node.getValue());
        traversal(node.getRight(), order, values);
        if (order == Order.POS) values.add(node.getValue());
    }

    private void removeNode(Node node) {
        // se tiver dois filhos, copia o valor do filho para si
        // e manda remover o filho (recursivo)
        if (node.getLeft() != null && node.getRight() != null) {
            Node substituto = node.getSuccessor();
            node.setValue(substituto.getValue());
            removeNode(substituto);
            return;
        }

        // salva o pai para rebalancear a partir dele depois
        Node parent = node.getParent();
        // quando o nó não tem dois filhos, troca o filho com o nó
        Node child = node.getLeft() != null ? node.getLeft() : node.getRight();
        swapNode(node, child);

        rebalancear(parent);
    }

    private void rebalancear(Node node) {
        while (node != null) {
            // precisa ser antes do rebalance, a rotação muda o parent do nó
            Node parent = node.getParent();
            Node newRoot = node.rebalance();

            if (parent == null) {
                root = newRoot;
            } else if (parent.getLeft() == node) {
                parent.setLeft(newRoot);
            } else {
                parent.setRight(newRoot);
            }

            node = parent;
        }
    }

    private void swapNode(Node node, Node child) {
        // pega o pai do nó em questão
        Node parent = node.getParent();

        // se existir filho, o pai do filho vira o pai do nó atual
        if (child != null) {
            child.setParent(parent);
        }

        if (parent == null) {
            root = child;
        } else if (parent.getLeft() == node) {
            parent.setLeft(child);
        } else {
            parent.setRight(child);
        }
    }
}
