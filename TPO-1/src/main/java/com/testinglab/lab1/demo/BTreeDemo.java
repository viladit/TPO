package com.testinglab.lab1.demo;

import com.testinglab.lab1.btree.BTree;

public final class BTreeDemo {

    private BTreeDemo() {
    }

    public static void main(String[] args) {
        BTree tree = new BTree(4);
        int[] keys = {10, 20, 30, 40, 50, 60, 70, 80};

        System.out.println("Демонстрация B-дерева");
        System.out.println("---------------------");
        for (int key : keys) {
            tree.insert(key);
            System.out.println("После вставки " + key + ": " + tree.toLevelOrderString());
            System.out.println("Трасса: " + tree.getTraceAsText());
            System.out.println();
        }

        boolean found = tree.search(50);
        System.out.println("Результат поиска 50: " + found);
        System.out.println("Трасса поиска: " + tree.getTraceAsText());
        System.out.println();

        boolean missing = tree.search(35);
        System.out.println("Результат поиска 35: " + missing);
        System.out.println("Трасса поиска: " + tree.getTraceAsText());
    }
}
