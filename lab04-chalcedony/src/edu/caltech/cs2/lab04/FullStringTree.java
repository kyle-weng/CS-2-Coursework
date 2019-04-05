package edu.caltech.cs2.lab04;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FullStringTree {
    protected static class StringNode {
        public final String data;
        public StringNode left;
        public StringNode right;

        public StringNode(String data) {
            this(data, null, null);
        }

        public StringNode(String data, StringNode left, StringNode right) {
            this.data = data;
            this.left = left;
            this.right = right;
            // Ensures that the StringNode is either a leaf or has two child nodes.
            if ((this.left == null || this.right == null) && !this.isLeaf()) {
                throw new IllegalArgumentException("StringNodes must represent nodes in a full binary tree");
            }
        }

        // Returns true if the StringNode has no child nodes.
        public boolean isLeaf() {
            return left == null && right == null;
        }
    }

    public StringNode root;
    public FullStringTree(Scanner in) {
        this.root = FullStringTree(in);
    }

    private StringNode FullStringTree(Scanner in) {
        String node = in.nextLine();
        if (node.substring(0,1).equals("L")) {
            return new StringNode(node.substring(3));
        } else {
            StringNode interval = new StringNode(node.substring(3), FullStringTree(in), FullStringTree(in));
            return interval;
        }
    }

    private StringNode deserialize(Scanner in) {
        return null;
    }

    private List<String> list = new ArrayList<>();

    public List<String> explore() {
        return explore(this.root);
    }

    private List<String> explore(StringNode node) {

        if (node.left == null && node.right == null) {
            this.list.add("L: " + node.data);
            return this.list;
        } else if (node.left == null) {
            this.list.add("I: " + node.data);
            this.list = explore(node.right);
        } else if (node.right == null) {
            this.list.add("I: " + node.data);
            this.list = explore(node.left);
        } else {
            this.list.add("I: " + node.data);
            this.list = explore(node.left);
            this.list = explore(node.right);
        }
        return this.list;
    }

    public void serialize(PrintStream output) {
        List<String> streamOutput = new ArrayList<>();
        streamOutput = explore();
        for (int i = 0; i < streamOutput.size(); i++) {
            output.println(streamOutput.get(i));
        }
    }
}