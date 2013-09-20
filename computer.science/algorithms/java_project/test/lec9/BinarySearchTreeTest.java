package lec9;

import junit.framework.TestCase;

public class BinarySearchTreeTest extends TestCase {
    public void testInsert() throws Exception {
        int[] array = new int[]{3, 1, 8, 2, 6, 7, 5};

        BinarySearchTree tree = new BinarySearchTree();
        for (int i = 0; i < array.length; i++) {
            tree.insert(array[i]);
        }
        tree.printTree();
    }
}
