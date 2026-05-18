package com.bruteforce;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * LeetCode Hot 100 - 438. 找到字符串中所有字母异位词 (Find All Anagrams in a String)
 *
 * ═══════════════════════════════════════════════════════════
 * 人为思考过程：
 * ═══════════════════════════════════════════════════════════
 * 1. 题目要求：给定字符串 s 和 p，找到 s 中所有是 p 的字母异位词的子串
 *    的起始位置。字母异位词 = 重新排列字母后能变成 p 的子串。
 *
 * 2. 最直观的理解：遍历 s 的每个位置 i，检查从 i 开始、长度为 p.length()
 *    的子串，是否和 p 由相同的字符组成（即字符频率一致）。
 *
 * 3. 如何判断两个字符串是否互为字母异位词？
 *    - 方法一：分别排序，比较排序后是否相等 → O(m log m)
 *    - 方法二：统计字符频率，比较两个频率数组 → O(m)
 *    这里选用排序法，更直观易理解。
 *
 * 4. 枚举所有可能的起点 i（0 到 n-m），每次截取等长子串 s[i..i+m-1]，
 *    与 p 分别排序后比较。一致则记录 i。
 *
 * 5. 显然这种每个子串都独立排序的做法效率很低，时间复杂度 O(n * m log m)，
 *    但思路直接，有助于理解"异位词=字符频率相同"这个核心概念。
 *
 * ═══════════════════════════════════════════════════════════
 * 具体措施（算法步骤）：
 * ═══════════════════════════════════════════════════════════
 * 1. 特判：若 s 长度 < p 长度，直接返回空列表。
 * 2. 对 p 排序，得到标准参照串 sortedP。
 * 3. 遍历起点 i（0 到 n - m）：
 *    a. 截取 s 的子串 s[i .. i+m-1]。
 *    b. 对该子串排序，得到 sortedSub。
 *    c. 若 sortedSub 与 sortedP 相等，说明位置 i 是一个解，加入结果列表。
 * 4. 返回结果列表。
 *
 * ═══════════════════════════════════════════════════════════
 * 复杂度分析：
 * ═══════════════════════════════════════════════════════════
 * 时间复杂度：O(n * m log m)
 *   - 遍历 n-m+1 个起点 ≈ O(n)
 *   - 每个子串排序 O(m log m)
 * 空间复杂度：O(m)
 *   - 排序所需的临时字符数组。
 */
public class FindAllAnagramsBruteForce {
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
     * 暴力枚举求解所有字母异位词的起始位置。
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

        // ── 将 p 排序，作为参照标准 ──
        // 异位词排序后必然完全相同
        char[] pChars = p.toCharArray();
        Arrays.sort(pChars);
        String sortedP = new String(pChars);

        // ── 枚举每个可能的起点 i ──
        for (int i = 0; i <= n - m; i++) {
            // 截取与 p 等长的子串
            String sub = s.substring(i, i + m);

            // 排序后与标准参照串比较
            char[] subChars = sub.toCharArray();
            Arrays.sort(subChars);
            String sortedSub = new String(subChars);

            if (sortedSub.equals(sortedP)) {
                result.add(i);
            }
        }

        return result;
    }
}
