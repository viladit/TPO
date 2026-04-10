package com.testinglab.lab1.btree;

import java.util.ArrayList;
import java.util.List;

final class BTreeNode {

    private final List<Integer> keys = new ArrayList<>();
    private final List<BTreeNode> children = new ArrayList<>();

    List<Integer> keys() {
        return keys;
    }

    List<BTreeNode> children() {
        return children;
    }

    boolean isLeaf() {
        return children.isEmpty();
    }

    boolean isOverflow(int maxKeys) {
        return keys.size() > maxKeys;
    }
}
