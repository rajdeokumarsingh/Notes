package lec9;

import com.example.algorithm.Debug;

class BinaryNode {
    int value;
    BinaryNode left;
    BinaryNode right;

    BinaryNode(int value) {
        this.value = value;
    }

    boolean hasChild() {
        return (left != null || right != null);
    }

    boolean hasTwoChildren() {
        return (left != null && right != null);
    }

    int getValue() {
        return value;
    }

    void setValue(int value) {
        this.value = value;
    }

    BinaryNode getLeft() {
        return left;
    }

    void setLeft(BinaryNode left) {
        this.left = left;
    }

    BinaryNode getRight() {
        return right;
    }

    void setRight(BinaryNode right) {
        this.right = right;
    }
}

public class BinarySearchTree {
    BinaryNode root;

    void insert(int value) {
        if (root == null) {
            root = new BinaryNode(value);
            return;
        }
        insertInternal(root, value);
    }

    private void insertInternal(BinaryNode node, int value) {
        if (value < node.getValue()) {
            if(node.getLeft() == null) {
                node.setLeft(new BinaryNode(value));
                return;
            } else {
                insertInternal(node.getLeft(), value);
                return;
            }
        } else {
            if(node.getRight() == null) {
                node.setRight(new BinaryNode(value));
                return;
            } else {
                insertInternal(node.getRight(), value);
                return;
            }
        }
    }

    public BinaryNode delete(int value) {
        return deleteInternal(null, root, value);
    }

    private BinaryNode deleteInternal(BinaryNode parent, BinaryNode node, int value) {
        if(node == null) {
            return null;
        }
        if(node.getValue() == value) {
            // delete node
            if(parent == null) {
                // todo
            } else {
                if(!node.hasChild()) {
                    // delete node
                    if (node == parent.getLeft()) {
                        parent.setLeft(null);
                    }
                    if (node == parent.getRight()) {
                        parent.setRight(null);
                    }
                    return node;
                } else if (node.hasTwoChildren()) {
                } else {
                    // node has one child
                    // todo:
                }
            }
            return true;
        }
        if (value < node.getValue()) {
            return deleteInternal(node, node.getLeft(), value);
        }
        if (value > node.getValue()) {
            return deleteInternal(node, node.getRight(), value);
        }
        return false;
    }

    public void printTree() {
        printTreeInternal(root);
    }

    private void printTreeInternal(BinaryNode node) {
        if(node == null) return;
        Debug.log("Node:" + node.getValue());

        Debug.logTest("Left tree: ");
        printTreeInternal(node.getLeft());

        Debug.logTest("Right tree: ");
        printTreeInternal(node.getRight());
    }
}
