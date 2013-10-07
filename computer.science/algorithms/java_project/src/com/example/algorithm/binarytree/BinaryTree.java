package com.example.algorithm.binarytree;

import java.util.*;

public class BinaryTree {
    private static StringBuilder traversalString;
    private static String[] buildTreeNodes;
    private static int buildTreeIndex;
    private static BinaryNode inorderRoot;

    // for layer traversal
    private HashMap<Integer, List<BinaryNode>> layerMap;

    BinaryNode root;

    // for dump debug information
    private int currentLevel = -1;

    public BinaryNode getRoot() {
        return root;
    }

    private void printTreeLevel(BinaryNode node, int level) {
        currentLevel++;
        try {
            if(node == null) return;
            if(currentLevel == level) {
                StringBuilder sb = new StringBuilder(node.getValue() + "(");
                if (node.getLeft() != null) {
                    sb.append(node.getLeft().getValue() + ",");
                } else {
                    sb.append("null,");
                }
                if (node.getRight() != null) {
                    sb.append(node.getRight().getValue() + ")  ");
                } else {
                    sb.append("null)  ");
                }
                System.out.print(sb.toString());
                return;
            }
            if (currentLevel > level) {
                return;
            }
            printTreeLevel(node.getLeft(), level);
            printTreeLevel(node.getRight(), level);
        } finally {
            currentLevel--;
        }
    }

    public void dump() {
        currentLevel = -1;
        System.out.println("tree dump:");

        for (int i = 0; i <= getHeight(); i++) {
            printTreeLevel(root, i);
            System.out.println();
        }
    }

    public int getHeight() {
        if (root == null) return 0;
        return height(root);
    }

    public String preorderTraversal() {
        traversalString = new StringBuilder();
        preorderTraversalInternal(root);
        return traversalString.toString();
    }

    public static BinaryTree buildTreePreorder(String orderString) {
        BinaryTree tree = new BinaryTree();
        if(orderString == null || orderString.length() == 0) return tree;

        buildTreeNodes = orderString.split(", ");
        buildTreeIndex = 0;

        tree.root = buildTreePreorderInternal();
        return tree;
    }

    private static BinaryNode buildTreePreorderInternal() {
        BinaryNode node = getNextBuildNode();
        if(node == null) return null;

        node.setLeft(buildTreePreorderInternal());
        node.setRight(buildTreePreorderInternal());
        return node;
    }

    /*
    public static BinaryTree buildTreePostOrder(String orderString) {
        BinaryTree tree = new BinaryTree();
        if(orderString == null || orderString.length() == 0) return tree;

        buildTreeNodes = orderString.split(", ");
        buildTreeIndex = 0;

        tree.root = buildTreePostOrderInternal();
        return tree;
    }

    // null, null, 10, null, null, null, 40, 30, 20,
    private static BinaryNode buildTreePostOrderInternal() {
        BinaryNode left = null;
        BinaryNode right = null;
        BinaryNode node = null;
        while (buildNodeLeft()) {
            left = buildSubTreePost();
            right = buildSubTreePost();
            node = getNextBuildNode();
            if(node == null) return left;
            node.setLeft(left);
            node.setRight(right);
        }
        return node;
    }

    private static BinaryNode buildSubTreePost() {
        BinaryNode left = getNextBuildNode();
        BinaryNode right = getNextBuildNode();
        BinaryNode node = getNextBuildNode();

        if(node == null) return null;
        node.setLeft(left);
        node.setRight(right);
        return node;
    }
    */

    public static BinaryTree buildTreeInorder(String orderString) {
        BinaryTree tree = new BinaryTree();
        if(orderString == null || orderString.length() == 0) return tree;

        buildTreeNodes = orderString.split(", ");
        buildTreeIndex = 0;

        tree.root = buildTreeInorderInternal();
        return tree;
    }

    // null, 9, null, 10, null, 11, null, 20, null, 30, null,
    private static BinaryNode buildTreeInorderInternal() {
        BinaryNode node = buildSubTree();
        if (node == null) return null;

        BinaryNode left = null;
        BinaryNode right = null;
        while (buildNodeLeft()) {
            left = node;
            node = getNextBuildNode();
            right = buildSubTree();
            node.setLeft(left);
            node.setRight(right);
        }
        return node;
    }

    private static BinaryNode buildSubTree() {
        BinaryNode left = getNextBuildNode();
        BinaryNode node = getNextBuildNode();
        BinaryNode right = getNextBuildNode();

        if(node == null) return null;
        node.setLeft(left);
        node.setRight(right);
        return node;
    }

    private static boolean buildNodeLeft() {
        if (buildTreeNodes == null) {
            throw new IllegalStateException("nodes should not be null");
        }
        if(buildTreeIndex >= buildTreeNodes.length)  return false;

        return true;
    }

    private static BinaryNode getNextBuildNode() {
        if (buildTreeNodes == null) {
            throw new IllegalStateException("nodes should not be null");
        }
        if(buildTreeIndex >= buildTreeNodes.length) {
            return null;
        }
        String token = buildTreeNodes[buildTreeIndex];
        buildTreeIndex++;
        if (token.contains("null")) return null;

        return new BinaryNode(Integer.parseInt(token));
    }

    public String inorderTraversal() {
        traversalString = new StringBuilder();
        inorderTraversalInternal(root);
        return traversalString.toString();
    }

    public String postorderTraversal() {
        traversalString = new StringBuilder();
        postorderTraversalInternal(root);
        return traversalString.toString();
    }

    private void postorderTraversalInternal(BinaryNode node) {
        if (node == null) {
            traversalString.append("null, ");
            return;
        }
        postorderTraversalInternal(node.getLeft());
        postorderTraversalInternal(node.getRight());
        traversalString.append(node.getValue() + ", ");
    }

    private void inorderTraversalInternal(BinaryNode node) {
        if (node == null) {
            traversalString.append("null, ");
            return;
        }

        inorderTraversalInternal(node.getLeft());
        traversalString.append(node.getValue() + ", ");
        inorderTraversalInternal(node.getRight());
    }

    private void preorderTraversalInternal(BinaryNode node) {
        if (node == null) {
            traversalString.append("null, ");
            return;
        }

        traversalString.append(node.getValue() + ", ");
        preorderTraversalInternal(node.getLeft());
        preorderTraversalInternal(node.getRight());
    }

    public void layerOrderTraversal() {
        layerMap = new HashMap<Integer, List<BinaryNode>>();
        currentLevel = -1;

        layerOrderTraversalInternal(root);

        System.out.println("layer order: ");

        // sort hash map by its keys
        Set<Integer> key = layerMap.keySet();
        ArrayList<Integer> list = new ArrayList<Integer>(key);
        Collections.sort(list);

        for (Integer layer : list) {
            for (BinaryNode node : layerMap.get(layer)) {
                StringBuilder sb = new StringBuilder(node.getValue() + "(");
                if (node.getLeft() != null) {
                    sb.append(node.getLeft().getValue() + ",");
                } else {
                    sb.append("null,");
                }
                if (node.getRight() != null) {
                    sb.append(node.getRight().getValue() + ");  ");
                } else {
                    sb.append("null);  ");
                }
                System.out.print(sb.toString());
            }
            System.out.println();
        }
    }

    private void layerOrderTraversalInternal(BinaryNode node) {
        if (node == null) return;

        currentLevel++;

        List<BinaryNode> list = layerMap.get(Integer.valueOf(currentLevel));
        if (list == null) {
            list = new ArrayList<BinaryNode>();
            layerMap.put(currentLevel, list);
        }
        list.add(node);

        layerOrderTraversalInternal(node.getLeft());
        layerOrderTraversalInternal(node.getRight());

        currentLevel--;
    }
    private int height(BinaryNode node) {
        return heightInternal(node) - 1;
    }

    private int heightInternal(BinaryNode node) {
        if (node == null)  return 0;
        return 1 + Math.max(heightInternal(node.getLeft()), heightInternal(node.getRight()));
    }

    public int getSize() {
        return size(root);
    }

    private int size(BinaryNode node) {
        if(node == null) return 0;
        return 1 + size(node.getLeft()) + size(node.getRight());
    }
}
