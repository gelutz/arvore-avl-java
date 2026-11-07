package avl;

import avl.node.*;
import avl.tree.BalancedAvlTreeTest;

public class Test {

    public static void main(String[] args) {
        NodeTest.insertTest();
        System.out.println("NodeTest.insertTest OK.");

        BalancedAvlTreeTest.balancedWalkTest();
        System.out.println("BalancedAvlTreeTest.balancedWalkTest OK.");

        BalancedAvlTreeTest.rotationTest();
        System.out.println("BalancedAvlTreeTest.rotationTest OK.");

        BalancedAvlTreeTest.heightTest();
        System.out.println("BalancedAvlTreeTest.heightTest OK.");

        BalancedAvlTreeTest.removeTest();
        System.out.println("BalancedAvlTreeTest.removeTest OK.");

        BalancedAvlTreeTest.randomOperationsTest();
        System.out.println("BalancedAvlTreeTest.randomOperationsTest OK.");
    }
}
