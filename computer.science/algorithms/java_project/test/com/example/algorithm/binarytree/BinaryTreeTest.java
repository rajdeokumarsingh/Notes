package com.example.algorithm.binarytree;

import com.example.algorithm.Debug;
import com.example.algorithm.RandomUtility;
import junit.framework.TestCase;

public class BinaryTreeTest extends TestCase {
    public void testTraversal() {

        BinarySearchTree tree = new BinarySearchTree();

        tree.insert(20);
        tree.insert(10);
        tree.insert(11);
        tree.insert(15);
        tree.insert(13);
        tree.insert(30);
        tree.insert(23);
        tree.insert(28);
        tree.insert(25);
        tree.insert(21);
        tree.dump();

        tree.preorderTraversal();
        // tree.inorderTraversal();
        // tree.postorderTraversal();

        String nodes = "20, 10, null, 11, null, 15, 13, null, null, null, 30, 23, 21, null, null, 28, 25, null, null, null, null,";
        BinaryTree newTree = BinaryTree.buildTreePreorder(nodes);
        newTree.preorderTraversal();
    }

    public void testBuildTreePreorder() throws Exception {
        BinaryTree tree = BinaryTree.buildTreePreorder(null);
        assertNull(tree.getRoot());

        tree = BinaryTree.buildTreePreorder("");
        assertNull(tree.getRoot());

        tree = BinaryTree.buildTreePreorder("1");
        tree.dump();
        assertEquals(tree.getRoot().getValue(), 1);
        assertEquals(tree.getSize(), 1);

        tree = BinaryTree.buildTreePreorder("1, ");
        tree.dump();
        assertEquals(tree.getRoot().getValue(), 1);
        assertEquals(tree.getSize(), 1);

        BinarySearchTree aTree = new BinarySearchTree();
        aTree.insert(2);
        aTree.insert(1);
        aTree.insert(3);

        tree = BinaryTree.buildTreePreorder(aTree.preorderTraversal());
        tree.dump();
        assertEquals(tree.getSize(), 3);
        assertEquals(tree.preorderTraversal(), aTree.preorderTraversal());
    }

    public void testBuildTreePreorderRandom() throws Exception {
        BinarySearchTree aTree = new BinarySearchTree();
        int[] tempArray = RandomUtility.randomIntegerArray();
        for (int i : tempArray) {
            aTree.insert(i);
        }
        BinaryTree tree = BinaryTree.buildTreePreorder(aTree.preorderTraversal());
        assertEquals(tree.getSize(), tempArray.length);
        assertEquals(tree.preorderTraversal(), aTree.preorderTraversal());
    }

    public void testBuildTreeInorderRandom() throws Exception {
        BinarySearchTree aTree = new BinarySearchTree();
        int[] tempArray = RandomUtility.randomIntegerArray();
        for (int i : tempArray) {
            aTree.insert(i);
        }
        BinaryTree tree = BinaryTree.buildTreeInorder(aTree.inorderTraversal());
        assertEquals(tree.getSize(), tempArray.length);
        assertEquals(tree.inorderTraversal(), aTree.inorderTraversal());
    }

    public void testTraversalInorder() {
        BinarySearchTree tree = new BinarySearchTree();
        BinaryTree tree1 = BinaryTree.buildTreeInorder(tree.inorderTraversal());
        assertEquals(tree.inorderTraversal(), tree1.inorderTraversal());

        tree.insert(20);
        tree1 = BinaryTree.buildTreeInorder(tree.inorderTraversal());
        assertEquals(tree.inorderTraversal(), tree1.inorderTraversal());

        tree.insert(10);
        tree1 = BinaryTree.buildTreeInorder(tree.inorderTraversal());
        assertEquals(tree.inorderTraversal(), tree1.inorderTraversal());

        tree.insert(9);
        tree1 = BinaryTree.buildTreeInorder(tree.inorderTraversal());
        assertEquals(tree.inorderTraversal(), tree1.inorderTraversal());

        tree.insert(11);
        tree.insert(30);
        tree1 = BinaryTree.buildTreeInorder(tree.inorderTraversal());
        assertEquals(tree.inorderTraversal(), tree1.inorderTraversal());
    }

    public void testTraversalPostOrder() {
        BinarySearchTree tree = new BinarySearchTree();
        tree.insert(20);
        tree.insert(10);
        tree.insert(30);
        tree.insert(40);
        Debug.log(tree.postorderTraversal());
        /*
        BinaryTree tree1 = BinaryTree.buildTreeInorder(tree.inorderTraversal());
        assertEquals(tree.inorderTraversal(), tree1.inorderTraversal());

        tree.insert(20);
        tree1 = BinaryTree.buildTreeInorder(tree.inorderTraversal());
        assertEquals(tree.inorderTraversal(), tree1.inorderTraversal());

        tree.insert(10);
        tree1 = BinaryTree.buildTreeInorder(tree.inorderTraversal());
        assertEquals(tree.inorderTraversal(), tree1.inorderTraversal());

        tree.insert(9);
        tree1 = BinaryTree.buildTreeInorder(tree.inorderTraversal());
        assertEquals(tree.inorderTraversal(), tree1.inorderTraversal());

        tree.insert(11);
        tree.insert(30);
        tree1 = BinaryTree.buildTreeInorder(tree.inorderTraversal());
        assertEquals(tree.inorderTraversal(), tree1.inorderTraversal());
        */
    }
}
