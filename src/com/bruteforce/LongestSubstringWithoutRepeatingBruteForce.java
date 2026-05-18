package com.bruteforce;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * LeetCode Hot 100 - 3. 无重复字符的最长子串 (Longest Substring Without Repeating Characters)
 *
 * ═══════════════════════════════════════════════════════════
 * 人为思考过程：
 * ═══════════════════════════════════════════════════════════
 * 1. 题目要求：在一个字符串中，找出不含重复字符的"最长连续子串"的长度。
 *    例如 "abcabcbb" → 最长无重复子串 "abc"，长度 3。
 *
 * 2. 面对这类"子串"问题，最直接的想法就是枚举所有可能的子串。
 *    子串由起点和终点确定：起点 i 从 0 到 n-1，终点 j 从 i 到 n-1，
 *    这样就能枚举出所有连续子串。
 *
 * 3. 对每一个枚举出的子串 s[i..j]，判断它是否"没有重复字符"：
 *    - 最简单的方法：把子串的字符逐个加入一个集合（Set），
 *      如果发现要加入的字符已经在集合中，说明有重复。
 *
 * 4. 由于子串一个接一个地枚举，可以边扩展边检查：
 *    固定起点 i，然后 j 从 i 开始向右扩展，
 *    每次加入 s[j]，若 s[j] 已存在于当前集合，则说明从 i 开始、
 *    以 j 结尾的子串有重复，i 起点的后续子串也必然有重复，直接结束本轮。
 *    否则，更新 maxLen = max(maxLen, j - i + 1)。
 *
 * 5. 这个思路虽然直观，但时间复杂度为 O(n^2)（枚举所有起点+扩展），
 *    面对长字符串会超时。不过它清晰地展示了问题的结构，
 *    为后续用滑动窗口优化提供了思路。
 *
 * ═══════════════════════════════════════════════════════════
 * 具体措施（算法步骤）：
 * ═══════════════════════════════════════════════════════════
 * 1. 遍历每个起点 i（0 到 n-1）。
 * 2. 从 i 开始，j 逐步向右扩展：
 *    a. 若 s[j] 已存在于当前子串的字符集合中 → 说明出现重复，
 *       结束本轮（i 起点的扩展到此为止）。
 *    b. 否则将 s[j] 加入集合，更新 maxLen = max(maxLen, j - i + 1)。
 * 3. 返回 maxLen。
 *
 * ═══════════════════════════════════════════════════════════
 * 复杂度分析：
 * ═══════════════════════════════════════════════════════════
 * 时间复杂度：O(n^2)
 *   - 外层循环 n 次（起点 i），内层 j 右移最多 n 次，
 *     每次 Set 操作 O(1)，总计 O(n^2)。
 * 空间复杂度：O(min(n, m))
 *   - HashSet 存储当前子串的字符，最多 m 个（m 为字符集大小）。
 */
public class LongestSubstringWithoutRepeatingBruteForce {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("请输入字符串：");
        String s = scanner.nextLine();

        int result = lengthOfLongestSubstring(s);
        System.out.println("最长无重复子串长度：" + result);

        scanner.close();
    }

    /**
     * 暴力枚举求解最长无重复字符子串。
     * @param s 输入字符串
     * @return 最长无重复子串的长度
     */
    public static int lengthOfLongestSubstring(String s) {
        int n = s.length();
        if (n == 0) {
            return 0;
        }

        int maxLen = 0;

        // ── 枚举每个起点 i ──
        for (int i = 0; i < n; i++) {
            // 用于记录从 i 开始的当前子串中出现过的字符
            Set<Character> seen = new HashSet<>();

            // ── 从 i 开始，j 向右扩展 ──
            for (int j = i; j < n; j++) {
                char c = s.charAt(j);

                // 若字符已存在，说明出现重复，结束当前起点的扩展
                if (seen.contains(c)) {
                    break;
                }

                // 标记为已出现，并更新最大长度
                seen.add(c);
                maxLen = Math.max(maxLen, j - i + 1);
            }
        }

        return maxLen;
    }
}
