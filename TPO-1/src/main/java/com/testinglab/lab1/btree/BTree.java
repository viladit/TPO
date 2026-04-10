package com.testinglab.lab1.btree;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class BTree {

    private final int maxKeysPerNode;
    private final BTreeTraceCollector traceCollector;
    private BTreeNode root;

    public BTree(int maxKeysPerNode) {
        if (maxKeysPerNode < 3) {
            throw new IllegalArgumentException("Узел B-дерева должен вмещать минимум 3 ключа.");
        }
        this.maxKeysPerNode = maxKeysPerNode;
        this.traceCollector = new BTreeTraceCollector();
    }

    public void insert(int key) {
        traceCollector.clear();
        traceCollector.log(BTreeTracePoint.НАЧАЛО_ВСТАВКИ, key);

        if (root == null) {
            root = new BTreeNode();
            root.keys().add(key);
            traceCollector.log(BTreeTracePoint.СОЗДАН_КОРЕНЬ, key);
            traceCollector.log(BTreeTracePoint.ВСТАВКА_В_ЛИСТ, key);
            traceCollector.log(BTreeTracePoint.КОНЕЦ_ВСТАВКИ, key);
            return;
        }

        SplitResult splitResult = insertIntoNode(root, key);
        if (splitResult != null) {
            BTreeNode newRoot = new BTreeNode();
            newRoot.keys().add(splitResult.promotedKey());
            newRoot.children().add(splitResult.left());
            newRoot.children().add(splitResult.right());
            root = newRoot;
            traceCollector.log(BTreeTracePoint.РАЗБИЕНИЕ_КОРНЯ, splitResult.promotedKey());
        }

        traceCollector.log(BTreeTracePoint.КОНЕЦ_ВСТАВКИ, key);
    }

    public boolean search(int key) {
        traceCollector.clear();
        traceCollector.log(BTreeTracePoint.НАЧАЛО_ПОИСКА, key);
        boolean found = search(root, key);
        traceCollector.log(BTreeTracePoint.КОНЕЦ_ПОИСКА, key);
        return found;
    }

    public List<String> getTraceAsText() {
        return traceCollector.snapshotAsText();
    }

    public String toLevelOrderString() {
        if (root == null) {
            return "[]";
        }

        List<String> levels = new ArrayList<>();
        ArrayDeque<BTreeNode> queue = new ArrayDeque<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            List<String> nodes = new ArrayList<>();
            for (int i = 0; i < levelSize; i++) {
                BTreeNode current = queue.remove();
                nodes.add(current.keys().toString());
                queue.addAll(current.children());
            }
            levels.add(String.join(", ", nodes));
        }

        return "[" + String.join(", ", levels) + "]";
    }

    private SplitResult insertIntoNode(BTreeNode node, int key) {
        Objects.requireNonNull(node, "Узел не должен быть null");
        ensureKeyDoesNotExist(node, key);

        if (node.isLeaf()) {
            traceCollector.log(BTreeTracePoint.ПОСЕЩЕН_ЛИСТ, node.keys());
            insertKeySorted(node.keys(), key);
            traceCollector.log(BTreeTracePoint.ВСТАВКА_В_ЛИСТ, key);

            if (node.isOverflow(maxKeysPerNode)) {
                return split(node);
            }
            return null;
        }

        traceCollector.log(BTreeTracePoint.ПОСЕЩЕН_ВНУТРЕННИЙ_УЗЕЛ, node.keys());
        int childIndex = findChildIndex(node.keys(), key);
        traceCollector.log(BTreeTracePoint.ПЕРЕХОД_К_ПОТОМКУ, childIndex);

        SplitResult childSplitResult = insertIntoNode(node.children().get(childIndex), key);
        if (childSplitResult == null) {
            return null;
        }

        node.children().set(childIndex, childSplitResult.left());
        node.children().add(childIndex + 1, childSplitResult.right());
        node.keys().add(childIndex, childSplitResult.promotedKey());
        traceCollector.log(BTreeTracePoint.ВСТАВКА_ПОДНЯТОГО_КЛЮЧА, childSplitResult.promotedKey());

        if (node.isOverflow(maxKeysPerNode)) {
            return split(node);
        }
        return null;
    }

    private SplitResult split(BTreeNode node) {
        traceCollector.log(BTreeTracePoint.РАЗБИЕНИЕ_УЗЛА, node.keys());

        int medianIndex = node.keys().size() / 2;
        int promotedKey = node.keys().get(medianIndex);
        traceCollector.log(BTreeTracePoint.ПОДНЯТИЕ_СРЕДНЕГО_КЛЮЧА, promotedKey);

        BTreeNode left = new BTreeNode();
        left.keys().addAll(node.keys().subList(0, medianIndex));

        BTreeNode right = new BTreeNode();
        right.keys().addAll(node.keys().subList(medianIndex + 1, node.keys().size()));

        if (!node.isLeaf()) {
            left.children().addAll(node.children().subList(0, medianIndex + 1));
            right.children().addAll(node.children().subList(medianIndex + 1, node.children().size()));
        }

        return new SplitResult(promotedKey, left, right);
    }

    private boolean search(BTreeNode node, int key) {
        if (node == null) {
            traceCollector.log(BTreeTracePoint.КЛЮЧ_НЕ_НАЙДЕН, key);
            return false;
        }

        traceCollector.log(node.isLeaf() ? BTreeTracePoint.ПОСЕЩЕН_ЛИСТ : BTreeTracePoint.ПОСЕЩЕН_ВНУТРЕННИЙ_УЗЕЛ, node.keys());

        int keyPosition = node.keys().indexOf(key);
        if (keyPosition >= 0) {
            traceCollector.log(BTreeTracePoint.КЛЮЧ_НАЙДЕН, key);
            return true;
        }

        if (node.isLeaf()) {
            traceCollector.log(BTreeTracePoint.КЛЮЧ_НЕ_НАЙДЕН, key);
            return false;
        }

        int childIndex = findChildIndex(node.keys(), key);
        traceCollector.log(BTreeTracePoint.ПЕРЕХОД_К_ПОТОМКУ, childIndex);
        return search(node.children().get(childIndex), key);
    }

    private void ensureKeyDoesNotExist(BTreeNode node, int key) {
        if (node.keys().contains(key)) {
            throw new IllegalArgumentException("Повторяющиеся ключи не поддерживаются: " + key);
        }
    }

    private void insertKeySorted(List<Integer> keys, int key) {
        int position = 0;
        while (position < keys.size() && keys.get(position) < key) {
            position++;
        }
        keys.add(position, key);
    }

    private int findChildIndex(List<Integer> keys, int key) {
        int index = 0;
        while (index < keys.size() && key > keys.get(index)) {
            index++;
        }
        return index;
    }

    private record SplitResult(int promotedKey, BTreeNode left, BTreeNode right) {
    }
}
