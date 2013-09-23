package com.example.algorithm.binarytree;

import junit.framework.TestCase;

public class BinarySearchTreeTest2 extends TestCase {
    public void testHeight() throws Exception {
        BinarySearchTree tree = new BinarySearchTree();
        assertEquals(tree.getHeight(), 0);

        tree.insert(50);
        assertEquals(tree.getHeight(), 0);

        tree.insert(30);
        assertEquals(tree.getHeight(), 1);

        tree.insert(40);
        assertEquals(tree.getHeight(), 2);

        tree.insert(60);
        assertEquals(tree.getHeight(), 2);

        tree.insert(70);
        assertEquals(tree.getHeight(), 2);

        tree.insert(80);
        assertEquals(tree.getHeight(), 3);

        tree.dump();
    }

    public void testSize() throws Exception {
        BinarySearchTree tree = new BinarySearchTree();
        assertEquals(tree.getSize(), 0);

        tree.insert(50);
        assertEquals(tree.getSize(), 1);

        tree.insert(30);
        assertEquals(tree.getSize(), 2);

        tree.insert(40);
        assertEquals(tree.getSize(), 3);

        tree.insert(60);
        assertEquals(tree.getSize(), 4);

        tree.insert(70);
        assertEquals(tree.getSize(), 5);

        tree.insert(80);
        assertEquals(tree.getSize(), 6);

        tree.dump();
    }
}
