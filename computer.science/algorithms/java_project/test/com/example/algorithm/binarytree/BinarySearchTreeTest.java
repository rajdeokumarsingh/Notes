package com.example.algorithm.binarytree;

import junit.framework.TestCase;

public class BinarySearchTreeTest extends TestCase {
    public void testInsertRoot() throws Exception {
        BinarySearchTree tree = new BinarySearchTree();
        assertNull(tree.getRoot());

        tree.insert(20);
        assertNotNull(tree.getRoot());
        assertEquals(tree.getRoot().getValue(), 20);
    }

    public void testInsertNode() throws Exception {
        BinarySearchTree tree = new BinarySearchTree();

        // insert root
        tree.insert(20);
        BinaryNode root = tree.getRoot();
        assertEquals(root.getValue(), 20);

        // insert left tree of root
        tree.insert(10);
        BinaryNode left1 = root.getLeft();
        assertEquals(left1.getValue(), 10);

        tree.insert(5);
        BinaryNode left2 = left1.getLeft();
        assertEquals(left2.getValue(), 5);

        // insert right tree of root
        tree.insert(30);
        BinaryNode right1 = root.getRight();
        assertEquals(right1.getValue(), 30);

        tree.insert(40);
        BinaryNode right2 = right1.getRight();
        assertEquals(right2.getValue(), 40);

        // insert right of left tree
        tree.insert(15);
        BinaryNode left3 = left1.getRight();
        assertEquals(left3.getValue(), 15);

        tree.insert(7);
        BinaryNode left4 = left2.getRight();
        assertEquals(left4.getValue(), 7);

        // insert left of right tree
        tree.insert(25);
        assertEquals(right1.getLeft().getValue(), 25);

        tree.insert(35);
        assertEquals(right2.getLeft().getValue(), 35);

        tree.dump();
    }

    public void testInsert() throws Exception {
        int[] array = new int[]{3, 1, 8, 2, 6, 7, 5};

        BinarySearchTree tree = new BinarySearchTree();
        for (int i = 0; i < array.length; i++) {
            tree.insert(array[i]);
        }
        tree.dump();
    }

    public void testDeleteBoundaryTest() throws Exception {
        BinarySearchTree tree = new BinarySearchTree();

        BinaryNode node = tree.delete(10);
        assertNull(node);

        tree.insert(20);
        node = tree.delete(10);
        assertNull(node);
    }

    public void testDeleteRoot() throws Exception {
        BinarySearchTree tree = new BinarySearchTree();

        // delete root
        tree.insert(20);
        tree.delete(20);
        assertNull(tree.getRoot());

        tree.insert(20);
        tree.insert(10);
        tree.delete(20);
        assertEquals(tree.getRoot().getValue(), 10);
        tree.delete(10);
        assertNull(tree.getRoot());

        tree.insert(20);
        tree.insert(10);
        tree.insert(30);
        tree.delete(20);
        assertEquals(tree.getRoot().getValue(), 30);
        tree.delete(10);
        tree.delete(20);
        tree.delete(30);
    }

    public void testDeleteRootComplex() throws Exception {
        int[] array = new int[]{3, 1, 8, 2, 6, 7, 5};

        BinarySearchTree tree = new BinarySearchTree();
        for (int i = 0; i < array.length; i++) {
            tree.insert(array[i]);
        }
        tree.dump();

        BinaryNode node =tree.delete(3);
        assertEquals(node.getValue(), 3);
        assertEquals(tree.getRoot().getValue(), 5);
        tree.dump();
    }

    public void testDeleteRootComplex2() throws Exception {
        int[] array = new int[]{8, 1, 3, 2, 6, 7, 5};

        BinarySearchTree tree = new BinarySearchTree();
        for (int i = 0; i < array.length; i++) {
            tree.insert(array[i]);
        }
        tree.dump();

        BinaryNode node =tree.delete(8);
        assertEquals(node.getValue(), 8);
        assertEquals(tree.getRoot().getValue(), 1);
        tree.dump();
    }

    public void testDeleteRootComplex3() throws Exception {
        int[] array = new int[]{1, 8, 3, 2, 6, 7, 5};

        BinarySearchTree tree = new BinarySearchTree();
        for (int i = 0; i < array.length; i++) {
            tree.insert(array[i]);
        }
        tree.dump();

        BinaryNode node =tree.delete(1);
        assertEquals(node.getValue(), 1);
        assertEquals(tree.getRoot().getValue(), 8);
        tree.dump();
    }

    public void testDeleteLeaf() {
        BinarySearchTree tree = new BinarySearchTree();
        tree.insert(30);
        tree.insert(20);
        tree.insert(10);
        tree.insert(25);
        tree.dump();

        BinaryNode node = tree.delete(10);
        assertEquals(node.getValue(), 10);
        tree.dump();

        node = tree.delete(25);
        assertEquals(node.getValue(), 25);
        tree.dump();
    }

    public void testDeleteNodeWithOneChild() {
        BinarySearchTree tree = new BinarySearchTree();
        tree.insert(30);
        tree.insert(20);
        tree.insert(10);
        tree.insert(7);
        tree.insert(25);
        tree.insert(28);
        tree.dump();

        BinaryNode node = tree.delete(10);
        assertEquals(node.getValue(), 10);
        tree.dump();

        node = tree.delete(25);
        assertEquals(node.getValue(), 25);
        tree.dump();
    }

    public void testDeleteNodeRecursive() {
        BinarySearchTree tree = new BinarySearchTree();
        tree.insert(30);
        tree.insert(20);
        tree.insert(10);
        tree.insert(11);
        tree.insert(15);
        tree.insert(13);
        tree.insert(21);
        tree.insert(28);
        tree.insert(25);
        tree.insert(23);
        tree.dump();

        BinaryNode node = tree.delete(20);
        assertEquals(node.getValue(), 20);
        tree.dump();
    }

    public void testDeleteNodeRecursive1() {
        BinarySearchTree tree = new BinarySearchTree();
        tree.insert(30);
        tree.insert(20);
        tree.insert(10);
        tree.insert(11);
        tree.insert(15);
        tree.insert(13);
        tree.insert(23);
        tree.insert(28);
        tree.insert(25);
        tree.insert(21);
        tree.dump();

        BinaryNode node = tree.delete(20);
        assertEquals(node.getValue(), 20);
        tree.dump();
    }

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
        tree.inorderTraversal();
        tree.postorderTraversal();

    }

    public void testLayerTraversal() {
        BinarySearchTree tree = new BinarySearchTree();

        tree.insert(20);
        tree.insert(10);
        tree.insert(11);
        tree.insert(15);
        tree.insert(13);
        tree.insert(23);
        tree.insert(30);
        tree.insert(28);
        tree.insert(25);
        tree.insert(21);
        tree.dump();
        tree.layerOrderTraversal();

        int[] array = new int[]{3, 1, 8, 2, 6, 7, 5};
        tree = new BinarySearchTree();
        for (int i = 0; i < array.length; i++) {
            tree.insert(array[i]);
        }
        tree.dump();
        tree.layerOrderTraversal();
    }
}
