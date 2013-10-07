package com.example.algorithm.binarytree;

import java.util.*;

public class BinarySearchTree extends BinaryTree {

    public void insert(int value) {
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
        BinaryNode sentry = new BinaryNode(Integer.MIN_VALUE);
        sentry.setRight(root);

        BinaryNode ret =  deleteInternal(sentry, sentry.getRight(), value);
        root = sentry.getRight();
        return ret;
    }

    private BinaryNode deleteInternal(BinaryNode parent, BinaryNode node, int value) {
        if(node == null) return null;

        if(node.getValue() == value) {
            if (node.hasTwoChildren()) {
                return deleteNodeWithTwoChild(node);
            } else if (node.hasLeftChild() || node.hasRightChild()) {
                // node has one child
                BinaryNode child = null;
                if(node.hasLeftChild()) {
                    child = node.getLeft();
                } else {
                    child = node.getRight();
                }
                if (node == parent.getLeft()) {
                    parent.setLeft(child);
                } else if (node == parent.getRight()) {
                    parent.setRight(child);
                }
            } else {
                // node has no child
                if (node == parent.getLeft()) {
                    parent.setLeft(null);
                } else if (node == parent.getRight()) {
                    parent.setRight(null);
                }
            }
            return node;
        } else if (value < node.getValue()) {
            return deleteInternal(node, node.getLeft(), value);
        } else {
            // (value > node.getValue())
            return deleteInternal(node, node.getRight(), value);
        }
    }

    private BinaryNode deleteNodeWithTwoChild(BinaryNode node) {
        int ret = node.getValue();
        BinaryNode p = node;
        BinaryNode q = node.getRight();
        while (q.hasLeftChild()) {
            p = q;
            q = q.getLeft();
        }
        node.setValue(q.getValue());
        if(p.getLeft() == q) {
            p.setLeft(q.getRight());
        } else if (p.getRight() == q) {
            p.setRight(q.getRight());
        }
        return new BinaryNode(ret);
    }
}
