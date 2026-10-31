package avl;

import avl.node.*;
import avl.tree.AvlTreeTest;

public class Test {

    public static void main(String[] args) {
        NodeTest.insertTest();
        System.out.println("NodeTest.insertTest OK.");

        AvlTreeTest.walkTest();
        System.out.println("AvlTreeTest.walkTest OK.");
    }
}
