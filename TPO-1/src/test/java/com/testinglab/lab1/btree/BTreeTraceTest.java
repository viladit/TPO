package com.testinglab.lab1.btree;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Method;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BTreeTraceTest {

    @Test
    @DisplayName("Должен отклонять дерево со слишком маленькой вместимостью узла")
    void shouldRejectTooSmallNodeCapacity() {
        assertThrows(IllegalArgumentException.class, () -> new BTree(2));
    }

    @Test
    @DisplayName("Пустое дерево должно корректно отображаться и давать отрицательный поиск")
    void shouldHandleEmptyTreeSearch() {
        BTree tree = new BTree(4);

        assertEquals("[]", tree.toLevelOrderString());
        assertFalse(tree.search(10));
        assertEquals(List.of(
                "НАЧАЛО_ПОИСКА(10)",
                "КЛЮЧ_НЕ_НАЙДЕН(10)",
                "КОНЕЦ_ПОИСКА(10)"
        ), tree.getTraceAsText());
    }

    @Test
    @DisplayName("Должен трассировать вставку в пустое дерево")
    void shouldTraceInsertionIntoEmptyTree() {
        BTree tree = new BTree(4);

        tree.insert(10);

        assertEquals(List.of(
                "НАЧАЛО_ВСТАВКИ(10)",
                "СОЗДАН_КОРЕНЬ(10)",
                "ВСТАВКА_В_ЛИСТ(10)",
                "КОНЕЦ_ВСТАВКИ(10)"
        ), tree.getTraceAsText());
        assertEquals("[[10]]", tree.toLevelOrderString());
    }

    @Test
    @DisplayName("Должен вставлять меньший ключ в начало листа")
    void shouldInsertSmallerKeyAtBeginningOfLeaf() {
        BTree tree = new BTree(4);
        tree.insert(20);

        tree.insert(10);

        assertEquals(List.of(
                "НАЧАЛО_ВСТАВКИ(10)",
                "ПОСЕЩЕН_ЛИСТ([20])",
                "ВСТАВКА_В_ЛИСТ(10)",
                "КОНЕЦ_ВСТАВКИ(10)"
        ), tree.getTraceAsText());
        assertEquals("[[10, 20]]", tree.toLevelOrderString());
    }

    @Test
    @DisplayName("Должен трассировать разбиение корня при переполнении листа")
    void shouldTraceRootSplit() {
        BTree tree = new BTree(4);
        tree.insert(10);
        tree.insert(20);
        tree.insert(30);
        tree.insert(40);

        tree.insert(50);

        assertEquals(List.of(
                "НАЧАЛО_ВСТАВКИ(50)",
                "ПОСЕЩЕН_ЛИСТ([10, 20, 30, 40])",
                "ВСТАВКА_В_ЛИСТ(50)",
                "РАЗБИЕНИЕ_УЗЛА([10, 20, 30, 40, 50])",
                "ПОДНЯТИЕ_СРЕДНЕГО_КЛЮЧА(30)",
                "РАЗБИЕНИЕ_КОРНЯ(30)",
                "КОНЕЦ_ВСТАВКИ(50)"
        ), tree.getTraceAsText());
        assertEquals("[[30], [10, 20], [40, 50]]", tree.toLevelOrderString());
    }

    @Test
    @DisplayName("Должен трассировать разбиение потомка и подъём ключа в родителя")
    void shouldTraceChildSplit() {
        BTree tree = new BTree(4);
        tree.insert(10);
        tree.insert(20);
        tree.insert(30);
        tree.insert(40);
        tree.insert(50);
        tree.insert(60);
        tree.insert(70);

        tree.insert(80);

        assertEquals(List.of(
                "НАЧАЛО_ВСТАВКИ(80)",
                "ПОСЕЩЕН_ВНУТРЕННИЙ_УЗЕЛ([30])",
                "ПЕРЕХОД_К_ПОТОМКУ(1)",
                "ПОСЕЩЕН_ЛИСТ([40, 50, 60, 70])",
                "ВСТАВКА_В_ЛИСТ(80)",
                "РАЗБИЕНИЕ_УЗЛА([40, 50, 60, 70, 80])",
                "ПОДНЯТИЕ_СРЕДНЕГО_КЛЮЧА(60)",
                "ВСТАВКА_ПОДНЯТОГО_КЛЮЧА(60)",
                "КОНЕЦ_ВСТАВКИ(80)"
        ), tree.getTraceAsText());
        assertEquals("[[30, 60], [10, 20], [40, 50], [70, 80]]", tree.toLevelOrderString());
    }

    @Test
    @DisplayName("Должен отклонять повторную вставку ключа")
    void shouldRejectDuplicateKeyInsertion() {
        BTree tree = new BTree(4);
        tree.insert(10);

        assertThrows(IllegalArgumentException.class, () -> tree.insert(10));
    }

    @Test
    @DisplayName("Должен трассировать успешный поиск")
    void shouldTraceSuccessfulSearch() {
        BTree tree = prepareTreeForSearch();

        boolean found = tree.search(50);

        assertTrue(found);
        assertEquals(List.of(
                "НАЧАЛО_ПОИСКА(50)",
                "ПОСЕЩЕН_ВНУТРЕННИЙ_УЗЕЛ([30])",
                "ПЕРЕХОД_К_ПОТОМКУ(1)",
                "ПОСЕЩЕН_ЛИСТ([40, 50])",
                "КЛЮЧ_НАЙДЕН(50)",
                "КОНЕЦ_ПОИСКА(50)"
        ), tree.getTraceAsText());
    }

    @Test
    @DisplayName("Должен трассировать успешный поиск в левом потомке")
    void shouldTraceSuccessfulSearchInLeftChild() {
        BTree tree = prepareTreeForSearch();

        assertTrue(tree.search(10));
        assertEquals(List.of(
                "НАЧАЛО_ПОИСКА(10)",
                "ПОСЕЩЕН_ВНУТРЕННИЙ_УЗЕЛ([30])",
                "ПЕРЕХОД_К_ПОТОМКУ(0)",
                "ПОСЕЩЕН_ЛИСТ([10, 20])",
                "КЛЮЧ_НАЙДЕН(10)",
                "КОНЕЦ_ПОИСКА(10)"
        ), tree.getTraceAsText());
    }

    @Test
    @DisplayName("Должен находить ключ прямо во внутреннем узле")
    void shouldFindKeyInInternalNode() {
        BTree tree = prepareTreeForSearch();

        assertTrue(tree.search(30));
        assertEquals(List.of(
                "НАЧАЛО_ПОИСКА(30)",
                "ПОСЕЩЕН_ВНУТРЕННИЙ_УЗЕЛ([30])",
                "КЛЮЧ_НАЙДЕН(30)",
                "КОНЕЦ_ПОИСКА(30)"
        ), tree.getTraceAsText());
    }

    @Test
    @DisplayName("Должен трассировать неуспешный поиск")
    void shouldTraceUnsuccessfulSearch() {
        BTree tree = prepareTreeForSearch();

        boolean found = tree.search(35);

        assertFalse(found);
        assertEquals(List.of(
                "НАЧАЛО_ПОИСКА(35)",
                "ПОСЕЩЕН_ВНУТРЕННИЙ_УЗЕЛ([30])",
                "ПЕРЕХОД_К_ПОТОМКУ(1)",
                "ПОСЕЩЕН_ЛИСТ([40, 50])",
                "КЛЮЧ_НЕ_НАЙДЕН(35)",
                "КОНЕЦ_ПОИСКА(35)"
        ), tree.getTraceAsText());
    }

    @Test
    @DisplayName("Должен трассировать неуспешный поиск в левом потомке")
    void shouldTraceUnsuccessfulSearchInLeftChild() {
        BTree tree = prepareTreeForSearch();

        assertFalse(tree.search(15));
        assertEquals(List.of(
                "НАЧАЛО_ПОИСКА(15)",
                "ПОСЕЩЕН_ВНУТРЕННИЙ_УЗЕЛ([30])",
                "ПЕРЕХОД_К_ПОТОМКУ(0)",
                "ПОСЕЩЕН_ЛИСТ([10, 20])",
                "КЛЮЧ_НЕ_НАЙДЕН(15)",
                "КОНЕЦ_ПОИСКА(15)"
        ), tree.getTraceAsText());
    }

    @Test
    @DisplayName("Должен вставлять ключ в левый потомок без разбиения")
    void shouldInsertIntoLeftChildWithoutSplit() {
        BTree tree = prepareTreeForSearch();

        tree.insert(5);

        assertEquals(List.of(
                "НАЧАЛО_ВСТАВКИ(5)",
                "ПОСЕЩЕН_ВНУТРЕННИЙ_УЗЕЛ([30])",
                "ПЕРЕХОД_К_ПОТОМКУ(0)",
                "ПОСЕЩЕН_ЛИСТ([10, 20])",
                "ВСТАВКА_В_ЛИСТ(5)",
                "КОНЕЦ_ВСТАВКИ(5)"
        ), tree.getTraceAsText());
        assertEquals("[[30], [5, 10, 20], [40, 50]]", tree.toLevelOrderString());
    }

    @Test
    @DisplayName("После разбиения потомка корень должен иметь трёх детей")
    void shouldBuildRootWithThreeChildren() {
        BTree tree = new BTree(4);
        for (int key : new int[]{10, 20, 30, 40, 50, 60, 70, 80}) {
            tree.insert(key);
        }

        assertEquals("[[30, 60], [10, 20], [40, 50], [70, 80]]", tree.toLevelOrderString());
    }

    @Test
    @DisplayName("Коллектор должен покрывать все свои методы")
    void shouldCoverTraceCollectorMethods() {
        BTreeTraceCollector collector = new BTreeTraceCollector();

        collector.log(BTreeTracePoint.НАЧАЛО_ПОИСКА);
        collector.log(BTreeTracePoint.ПЕРЕХОД_К_ПОТОМКУ, 1);

        List<TraceStep> snapshot = collector.snapshot();
        assertEquals(2, snapshot.size());
        assertEquals(BTreeTracePoint.НАЧАЛО_ПОИСКА, snapshot.get(0).point());
        assertNull(snapshot.get(0).details());
        assertEquals("НАЧАЛО_ПОИСКА", snapshot.get(0).asText());
        assertEquals(BTreeTracePoint.ПЕРЕХОД_К_ПОТОМКУ, snapshot.get(1).point());
        assertEquals("1", snapshot.get(1).details());
        assertEquals("ПЕРЕХОД_К_ПОТОМКУ(1)", snapshot.get(1).asText());
        assertTrue(snapshot.get(1).toString().contains("details=1"));

        assertEquals(List.of("НАЧАЛО_ПОИСКА", "ПЕРЕХОД_К_ПОТОМКУ(1)"), collector.snapshotAsText());

        collector.clear();
        assertTrue(collector.snapshot().isEmpty());
    }

    @Test
    @DisplayName("Разбиение внутреннего узла должно корректно распределять ключи и детей")
    void shouldSplitInternalNode() throws Exception {
        BTree tree = new BTree(3);
        BTreeNode node = new BTreeNode();
        node.keys().addAll(List.of(30, 60, 90, 120));

        node.children().add(createLeafNode(10, 20));
        node.children().add(createLeafNode(40, 50));
        node.children().add(createLeafNode(70, 80));
        node.children().add(createLeafNode(100, 110));
        node.children().add(createLeafNode(130));

        Method splitMethod = BTree.class.getDeclaredMethod("split", BTreeNode.class);
        splitMethod.setAccessible(true);
        Object splitResult = splitMethod.invoke(tree, node);

        Method promotedKeyMethod = splitResult.getClass().getDeclaredMethod("promotedKey");
        Method leftMethod = splitResult.getClass().getDeclaredMethod("left");
        Method rightMethod = splitResult.getClass().getDeclaredMethod("right");
        promotedKeyMethod.setAccessible(true);
        leftMethod.setAccessible(true);
        rightMethod.setAccessible(true);

        assertEquals(90, promotedKeyMethod.invoke(splitResult));

        BTreeNode left = (BTreeNode) leftMethod.invoke(splitResult);
        BTreeNode right = (BTreeNode) rightMethod.invoke(splitResult);

        assertEquals(List.of(30, 60), left.keys());
        assertEquals(List.of(120), right.keys());
        assertEquals(List.of(List.of(10, 20), List.of(40, 50), List.of(70, 80)),
                left.children().stream().map(BTreeNode::keys).toList());
        assertEquals(List.of(List.of(100, 110), List.of(130)),
                right.children().stream().map(BTreeNode::keys).toList());
    }

    @Test
    @DisplayName("Вставка в переполненного потомка должна вызывать разбиение внутреннего узла")
    void shouldOverflowInternalNodeAfterChildSplit() throws Exception {
        BTree tree = new BTree(3);
        BTreeNode root = new BTreeNode();
        root.keys().addAll(List.of(30, 60, 90));

        root.children().add(createLeafNode(10, 20));
        root.children().add(createLeafNode(40, 50));
        root.children().add(createLeafNode(70, 80));
        root.children().add(createLeafNode(100, 110, 120));

        Method insertIntoNodeMethod = BTree.class.getDeclaredMethod("insertIntoNode", BTreeNode.class, int.class);
        insertIntoNodeMethod.setAccessible(true);
        Object splitResult = insertIntoNodeMethod.invoke(tree, root, 130);

        Method promotedKeyMethod = splitResult.getClass().getDeclaredMethod("promotedKey");
        Method leftMethod = splitResult.getClass().getDeclaredMethod("left");
        Method rightMethod = splitResult.getClass().getDeclaredMethod("right");
        promotedKeyMethod.setAccessible(true);
        leftMethod.setAccessible(true);
        rightMethod.setAccessible(true);

        assertEquals(90, promotedKeyMethod.invoke(splitResult));

        BTreeNode left = (BTreeNode) leftMethod.invoke(splitResult);
        BTreeNode right = (BTreeNode) rightMethod.invoke(splitResult);

        assertEquals(List.of(30, 60), left.keys());
        assertEquals(List.of(120), right.keys());
        assertEquals(List.of(List.of(10, 20), List.of(40, 50), List.of(70, 80)),
                left.children().stream().map(BTreeNode::keys).toList());
        assertEquals(List.of(List.of(100, 110), List.of(130)),
                right.children().stream().map(BTreeNode::keys).toList());
    }

    private static BTree prepareTreeForSearch() {
        BTree tree = new BTree(4);
        tree.insert(10);
        tree.insert(20);
        tree.insert(30);
        tree.insert(40);
        tree.insert(50);
        return tree;
    }

    private static BTreeNode createLeafNode(int... keys) {
        BTreeNode node = new BTreeNode();
        for (int key : keys) {
            node.keys().add(key);
        }
        return node;
    }
}
