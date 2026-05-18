package com.slidingwindow;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * LeetCode Hot 100 - 3. Longest Substring Without Repeating Characters（无重复字符的最长子串）
 *
 * ═══════════════════════════════════════════════════════════
 * 人为思考过程：
 * ═══════════════════════════════════════════════════════════
 * 1. 题目要求找出字符串中不含重复字符的最长子串长度。
 *    例如 "abcabcbb" 的最长无重复子串是 "abc"，长度为 3。
 * 2. 这个问题的一个直观理解是：用两个指针维护一个"窗口"，
 *    窗口内的所有字符都不重复。
 * 3. 右指针负责向右扩展窗口：每次把一个新的字符加入窗口。
 *    如果这个字符在窗口里已经出现过了，说明窗口不满足条件了，
 *    这时左指针需要向右移动，直到窗口内没有重复字符为止。
 * 4. 关键问题：左指针应该移动到哪里？
 *    最贪心的做法是直接跳到"重复字符上一次出现的位置 + 1"，
 *    因为在这之前的位置都会包含这个重复字符。
 * 5. 如何快速知道一个字符上一次出现的位置？
 *    用一个 HashMap<Character, Integer>，
 *    key 是字符，value 是该字符最后一次出现的下标。
 *    这样在 O(1) 时间内就能查到重复位置。
 * 6. 但要注意一个细节：重复字符的位置必须 >= 左指针的位置，
 *    才有意义（如果重复位置在左指针左边，说明它不在当前窗口内）。
 * 7. 每移动一次右指针，就用 right - left + 1 更新当前窗口大小，
 *    并用 answer 记录历史最大窗口大小。
 *
 * ═══════════════════════════════════════════════════════════
 * 具体措施（算法步骤）：
 * ═══════════════════════════════════════════════════════════
 * 1. 创建 HashMap<Character, Integer> lastIndex 记录字符最近出现位置。
 * 2. 初始化左指针 left = 0，答案 answer = 0。
 * 3. 右指针 right 从 0 遍历到字符串末尾：
 *    a. 获取当前字符 current = text.charAt(right)。
 *    b. 若 lastIndex 包含 current 且 lastIndex.get(current) >= left：
 *       更新 left = lastIndex.get(current) + 1（跳过重复字符）。
 *    c. 将 current 和 right 存入 lastIndex。
 *    d. 更新 answer = max(answer, right - left + 1)。
 * 4. 返回 answer。
 *
 * ═══════════════════════════════════════════════════════════
 * 复杂度分析：
 * ═══════════════════════════════════════════════════════════
 * 时间复杂度：O(n)
 *   - 右指针遍历字符串一次 O(n)，每次 HashMap 操作 O(1)。
 * 空间复杂度：O(min(n, m))
 *   - HashMap 最多存储 m 个不同字符（m 为字符集大小，如 ASCII 128），
 *     取 min(n, m)。
 */
public class LongestSubstringWithoutRepeatingSlidingWindow {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入字符串：");
        String text = scanner.nextLine();
        System.out.println("最长无重复子串长度为：" + lengthOfLongestSubstring(text));
    }

    public static int lengthOfLongestSubstring(String text) {
        // lastIndex: key 是字符，value 是它最后一次出现的下标
        Map<Character, Integer> lastIndex = new HashMap<Character, Integer>();
        int left = 0;       // 窗口左边界
        int answer = 0;     // 最长无重复子串长度

        for (int right = 0; right < text.length(); right++) {
            char current = text.charAt(right);
            // 若当前字符在窗口内出现过，将左指针移到重复字符之后
            if (lastIndex.containsKey(current) && lastIndex.get(current) >= left) {
                left = lastIndex.get(current) + 1;
            }
            // 更新当前字符的最新位置
            lastIndex.put(current, right);
            // 更新答案：当前窗口大小 = right - left + 1
            answer = Math.max(answer, right - left + 1);
        }
        return answer;
    }
}
