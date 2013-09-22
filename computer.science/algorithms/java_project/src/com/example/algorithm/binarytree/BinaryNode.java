package com.example.algorithm.binarytree;

class BinaryNode {
    int value;
    BinaryNode left;
    BinaryNode right;

    BinaryNode(int value) {
        this.value = value;
    }

    void copy(BinaryNode node) {
        if(node == null)
            throw new IllegalArgumentException("Argument should not be null");

        this.value = node.value;
        this.left = node.left;
        this.right = node.right;
    }

    boolean hasChild() {
        return (left != null || right != null);
    }

    boolean hasLeftChild() {
        return (left != null);
    }

    boolean hasRightChild() {
        return (right != null);
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
