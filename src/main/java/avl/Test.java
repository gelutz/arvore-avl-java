package avl;

import avl.node.*;
import avl.tree.AvlTreePathTest;
import avl.tree.BalancedAvlTreeTest;
import avl.view.CommandTest;
import avl.view.TreeViewTest;

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

        AvlTreePathTest.searchPathTest();
        System.out.println("AvlTreePathTest.searchPathTest OK.");

        AvlTreePathTest.removalPathTest();
        System.out.println("AvlTreePathTest.removalPathTest OK.");

        AvlTreePathTest.removalMatchesRealRemoveTest();
        System.out.println("AvlTreePathTest.removalMatchesRealRemoveTest OK.");

        AvlTreePathTest.traversalTest();
        System.out.println("AvlTreePathTest.traversalTest OK.");

        CommandTest.parseTest();
        System.out.println("CommandTest.parseTest OK.");

        CommandTest.invalidTest();
        System.out.println("CommandTest.invalidTest OK.");

        TreeViewTest.layoutTest();
        System.out.println("TreeViewTest.layoutTest OK.");

        TreeViewTest.underlineTest();
        System.out.println("TreeViewTest.underlineTest OK.");
    }
}
