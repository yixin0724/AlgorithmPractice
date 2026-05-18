package com.slidingwindow;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * LeetCode Hot 100 - 438. 找到字符串中所有字母异位词 (Find All Anagrams in a String)
 *
 * ═══════════════════════════════════════════════════════════
 * 人为思考过程：
 * ═══════════════════════════════════════════════════════════
 * 1. 暴力枚举每次截取子串再排序比较，做了大量重复计算。
 *    相邻两个位置的子串只相差两个字符（左边去掉一个，右边新增一个），
 *    完全没必要重新排序整个子串。
 *
 * 2. 字母异位词的本质是"字符频率相同"。题目限定只有小写字母 a-z，
 *    我们完全可以用一个长度为 26 的 int 数组来统计频率。
 *
 * 3. 固定长度窗口的滑动技巧：
 *    - 先统计 p 中每个字符的出现次数，存入 countP[26]。
 *    - 再用另一个数组 countWindow[26] 维护当前窗口的字符频率。
 *    - 窗口大小 = p.length()，始终固定不变。
 *    - 窗口右移一步：新字符加入 countWindow，旧字符从 countWindow 移除。
 *
 * 4. 首轮先把 s 的前 m 个字符塞进 countWindow，与 countP 比较。
 *    之后每次窗口右移一步，只做两步更新（加入新字符、移除左边界字符），
 *    再比较两个数组是否相等。
 *
 * 5. 比较两个长度为 26 的数组只需 O(26) = O(1)，总体 O(n)。
 *    这个思路把"滑动窗口"和"固定频率数组"结合，非常高效。
 *
 * ═══════════════════════════════════════════════════════════
 * 具体措施（算法步骤）：
 * ═══════════════════════════════════════════════════════════
 * 1. 特判：若 n < m，直接返回空列表。
 * 2. 用 countP[26] 统计 p 中每个字符的出现次数。
 * 3. 初始化窗口 countWindow[26]，统计 s[0..m-1] 的字符频率。
 * 4. 若 countWindow 与 countP 相等，记录位置 0。
 * 5. 滑动窗口（i 从 m 开始到 n-1，左边界 left = i-m）：
 *    a. 新进入窗口的字符 s[i] 计数 +1。
 *    b. 离开窗口的字符 s[i-m] 计数 -1。
 *    c. 若更新后的 countWindow 与 countP 相等，记录位置 left+1。
 * 6. 返回结果列表。
 *
 * ═══════════════════════════════════════════════════════════
 * 复杂度分析：
 * ═══════════════════════════════════════════════════════════
 * 时间复杂度：O(n)
 *   - 预处理 p 和初始窗口各 O(m)，滑动过程 O(n-m)，
 *     每次比较数组 O(26) = O(1)，总体 O(n)。
 * 空间复杂度：O(1)
 *   - 只使用两个固定大小 26 的 int 数组，不随输入规模变化。
 */
public class FindAllAnagramsSlidingWindow {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("请输入字符串 s：");
        String s = scanner.nextLine();

        System.out.println("请输入字符串 p：");
        String p = scanner.nextLine();

        List<Integer> result = findAnagrams(s, p);
        System.out.println("所有字母异位词的起始位置：" + result);

        scanner.close();
    }

    /**
     * 滑动窗口 + 数组计数 求解所有字母异位词的起始位置。
     * @param s 源字符串
     * @param p 目标模式串
     * @return 所有异位词起始下标的列表
     */
    public static List<Integer> findAnagrams(String s, String p) {
        List<Integer> result = new ArrayList<>();
        int n = s.length();
        int m = p.length();

        if (n < m) {
            return result;
        }

        // ── 统计 p 中每个字符的出现次数 ──
        int[] countP = new int[26];
        for (char c : p.toCharArray()) {
            countP[c - 'a']++;
        }

        // ── 初始化窗口：统计 s 前 m 个字符的频率 ──
        int[] countWindow = new int[26];
        for (int i = 0; i < m; i++) {
            countWindow[s.charAt(i) - 'a']++;
        }

        // ── 检查初始窗口是否为异位词 ──
        if (arraysEqual(countWindow, countP)) {
            result.add(0);
        }

        // ── 滑动窗口：窗口大小固定为 m，每次右移一步 ──
        for (int i = m; i < n; i++) {
            // 新字符进入窗口（右边界扩展）
            char inChar = s.charAt(i);
            countWindow[inChar - 'a']++;

            // 旧字符离开窗口（左边界收缩）
            char outChar = s.charAt(i - m);
            countWindow[outChar - 'a']--;

            // 检查当前窗口是否为异位词，起始位置 = i - m + 1
            if (arraysEqual(countWindow, countP)) {
                result.add(i - m + 1);
            }
        }

        return result;
    }

    /**
     * 比较两个长度为 26 的 int 数组是否完全相等。
     * 复杂度 O(26) = O(1)。
     */
    private static boolean arraysEqual(int[] a, int[] b) {
        for (int i = 0; i < 26; i++) {
            if (a[i] != b[i]) {
                return false;
            }
        }
        return true;
    }
}
