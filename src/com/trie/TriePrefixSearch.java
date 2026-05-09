package com.trie;

import java.util.Scanner;

/**
 * 问题：维护一组单词，支持查询某个单词是否存在，以及是否存在指定前缀。
 * 方法：使用字典树。
 * 解题思路：字典树的每条边代表一个字符，从根节点到某个节点的路径代表一个前缀。
 * 插入单词时沿字符路径创建节点，查询单词或前缀时沿路径查找；单词查询还需要判断终止标记。
 * 时间复杂度：插入和查询均为 O(L)，L 为单词或前缀长度。
 * 空间复杂度：O(S)，S 为所有插入单词的字符总数。
 */
public class TriePrefixSearch {
    static class TrieNode {
        TrieNode[] children = new TrieNode[26];
        boolean wordEnd;
    }

    private final TrieNode root = new TrieNode();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TriePrefixSearch trie = new TriePrefixSearch();
        System.out.println("请输入要插入的单词数量：");
        int n = scanner.nextInt();
        System.out.println("请输入小写英文单词：");
        for (int i = 0; i < n; i++) {
            trie.insert(scanner.next());
        }
        System.out.println("请输入要查询的单词和前缀：");
        String word = scanner.next();
        String prefix = scanner.next();
        System.out.println("单词是否存在：" + trie.search(word));
        System.out.println("前缀是否存在：" + trie.startsWith(prefix));
    }

    public void insert(String word) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            int index = c - 'a';
            if (node.children[index] == null) {
                node.children[index] = new TrieNode();
            }
            node = node.children[index];
        }
        node.wordEnd = true;
    }

    public boolean search(String word) {
        TrieNode node = findNode(word);
        return node != null && node.wordEnd;
    }

    public boolean startsWith(String prefix) {
        return findNode(prefix) != null;
    }

    private TrieNode findNode(String text) {
        TrieNode node = root;
        for (char c : text.toCharArray()) {
            int index = c - 'a';
            if (node.children[index] == null) {
                return null;
            }
            node = node.children[index];
        }
        return node;
    }
}
