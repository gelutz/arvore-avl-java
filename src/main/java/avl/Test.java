package avl;

import avl.node.*;
import avl.tree.UnbalancedAvlTreeTest;

public class Test {

    public static void main(String[] args) {
        NodeTest.insertTest();
        System.out.println("NodeTest.insertTest OK.");

        UnbalancedAvlTreeTest.searchTest();
        System.out.println("UnbalancedAvlTreeTest.searchTest OK.");

        UnbalancedAvlTreeTest.removeTest();
        System.out.println("UnbalancedAvlTreeTest.removeTest OK.");

        UnbalancedAvlTreeTest.heightTest();
        System.out.println("UnbalancedAvlTreeTest.heightTest OK.");
    }
}
