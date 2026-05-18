package com.trie;

import java.util.Scanner;

/**
 * LeetCode Hot 100 - 208. Implement Trie (Prefix Tree)（实现 Trie，前缀树）
 *
 * ═══════════════════════════════════════════════════════════
 * 人为思考过程：
 * ═══════════════════════════════════════════════════════════
 * 面对"需要高效判断一批字符串中是否存在某单词/某前缀"的问题，哈希表可以 O(1) 查单词，但查前缀却很困难。
 *
 * 1. 思考：如果能用一个"树"来存储所有字符串，让每个节点代表一个字符，从根到某个节点的路径就代表一个前缀，问题就迎刃而解。
 * 2. 想象：把所有单词摆在面前，发现它们共享大量公共前缀（比如 "app", "apple", "application" 都以 "app" 开头）。
 * 3. 如果用树结构，就可以把这些公共前缀只存一份，节省空间，同时查找前缀也变得自然——顺着字符路径一路走即可。
 * 4. 每个节点需要两个关键信息：
 *    - 子节点指针：因为只有小写字母，可以用长度为 26 的数组来索引，省去哈希表的开销。
 *    - 单词结束标记：因为 "app" 和 "apple" 共享前缀，需要一个布尔标记来区分"app"是完整单词还是一个前缀。
 * 5. 插入操作：逐字符创建路径，在最后一个字符节点打上结束标记。
 * 6. 搜索操作：沿着字符路径走，如果走到底且结束标记为 true，则单词存在。
 * 7. 前缀查询：与搜索类似，但不需要检查结束标记，只要路径存在即可。
 *
 * ═══════════════════════════════════════════════════════════
 * 具体措施（算法步骤）：
 * ═══════════════════════════════════════════════════════════
 * 1. 定义 TrieNode：
 *    - children[26]：子节点数组，通过 c - 'a' 索引。
 *    - wordEnd：布尔标记，标识此处是否是某个完整单词的结尾。
 * 2. insert(String word)：
 *    a) 从根节点出发，遍历 word 每个字符。
 *    b) 计算字符索引，若对应子节点为空则创建。
 *    c) 移动到子节点，处理下一个字符。
 *    d) 在最后一个字符节点上设置 wordEnd = true。
 * 3. search(String word)：
 *    a) 调用 findNode 沿路径查找，返回终点节点或 null。
 *    b) 返回 node != null && node.wordEnd。
 * 4. startsWith(String prefix)：
 *    a) 调用 findNode，返回 node != null。
 *
 * ═══════════════════════════════════════════════════════════
 * 复杂度分析：
 * ═══════════════════════════════════════════════════════════
 * 时间复杂度：
 *   - 插入：O(L)，L 为单词长度，每个字符 O(1)。
 *   - 搜索/前缀查询：O(L)，L 为查询字符串长度。
 * 空间复杂度：O(S)
 *   - S 为所有插入单词的字符总数（每个字符对应一个节点）。
 *   - 最坏情况下所有单词无公共前缀，每个字符都需要创建一个新节点。
 */
public class TriePrefixSearch {

    /**
     * 字典树节点
     */
    static class TrieNode {
        /** 子节点数组，索引为 'a'..'z'（0..25） */
        TrieNode[] children = new TrieNode[26];
        /** 标记 当前节点是否为某个完整单词的结尾 */
        boolean wordEnd;
    }

    /** 字典树的根节点 */
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

    /**
     * 向字典树中插入一个单词
     */
    public void insert(String word) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            int index = c - 'a';
            // 子节点不存在则创建
            if (node.children[index] == null) {
                node.children[index] = new TrieNode();
            }
            node = node.children[index];
        }
        // 标记单词结束
        node.wordEnd = true;
    }

    /**
     * 查询完整单词是否存在
     */
    public boolean search(String word) {
        TrieNode node = findNode(word);
        // 不仅路径要存在，还要有完整的单词结束标记
        return node != null && node.wordEnd;
    }

    /**
     * 查询前缀是否存在
     */
    public boolean startsWith(String prefix) {
        // 只要路径存在即可，不需要结束标记
        return findNode(prefix) != null;
    }

    /**
     * 沿字典树查找指定字符串对应的节点，若路径中断则返回 null
     */
    private TrieNode findNode(String text) {
        TrieNode node = root;
        for (char c : text.toCharArray()) {
            int index = c - 'a';
            if (node.children[index] == null) {
                return null;  // 路径中断
            }
            node = node.children[index];
        }
        return node;
    }
}
