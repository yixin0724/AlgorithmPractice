package com.bruteforce;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * LeetCode Hot 100 - 49. Group Anagrams（字母异位词分组）
 *
 * ═══════════════════════════════════════════════════════════
 * 人为思考过程：
 * ═══════════════════════════════════════════════════════════
 * 1. 题目要求把一组字符串按照"字母异位词"分组。
 *    所谓字母异位词，就是两个字符串包含的字母种类和数量完全相同，
 *    只是排列顺序不同，例如 "eat" 和 "tea"。
 * 2. 先把大问题拆成两个小问题：
 *    第一，如何判断两个字符串是不是字母异位词？
 *    第二，如何把互为异位词的字符串放到同一组？
 * 3. 判断异位词最直观的方法：把两个字符串各自排序，
 *    排序后如果相等就是异位词。例如 "eat" 排序后是 "aet"，
 *    "tea" 排序后也是 "aet"，它们相等。
 *    另一种方法是统计 26 个字母各自出现的次数。
 * 4. 分组策略：用一个布尔数组 used[] 标记每个字符串是否已被分组。
 *    遍历每个字符串 i，如果它还没被分组，就新建一个 group，
 *    然后把 i 放进去作为组的代表。
 * 5. 接下来遍历 i 后面的所有字符串 j，
 *    如果 j 没被分组且与 i 是异位词，就把 j 也加入这个 group。
 * 6. 这种两两比较的暴力方法思路清晰，
 *    但每次都要对两个字符串排序来判断异位词，效率较低。
 *
 * ═══════════════════════════════════════════════════════════
 * 具体措施（算法步骤）：
 * ═══════════════════════════════════════════════════════════
 * 1. 创建布尔数组 used[n]，标记每个字符串是否已分组。
 * 2. 外层循环 i：遍历每个字符串，若 used[i] 为真则跳过。
 * 3. 创建新 group，将 strs[i] 加入 group，标记 used[i] = true。
 * 4. 内层循环 j（从 i+1 开始）：若 !used[j] 且 isAnagram(strs[i], strs[j]) 为真，
 *    将 strs[j] 加入 group，标记 used[j] = true。
 * 5. 将 group 加入结果列表。
 * 6. 判断异位词的方法（isAnagram）：对两个字符串排序后比较是否相等。
 *
 * ═══════════════════════════════════════════════════════════
 * 复杂度分析：
 * ═══════════════════════════════════════════════════════════
 * 时间复杂度：O(n^2 * k log k)
 *   - 外层循环 O(n)，内层循环 O(n)，每对比较需要排序 O(k log k)，
 *     其中 n 是字符串数量，k 是字符串平均长度。
 * 空间复杂度：O(n * k)
 *   - 需要保存分组结果，used 数组 O(n)，
 *     排序时的临时数组 O(k)。
 */
public class GroupAnagramsBruteForce {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter string count:");
        int n = scanner.nextInt();

        String[] strs = new String[n];
        System.out.println("Please enter strings:");
        for (int i = 0; i < n; i++) {
            strs[i] = scanner.next();
        }

        List<List<String>> result = groupAnagrams(strs);
        if (result.isEmpty()) {
            System.out.println("No groups found");
        } else {
            for (List<String> group : result) {
                System.out.println(group);
            }
        }

        scanner.close();
    }

    public static List<List<String>> groupAnagrams(String[] strs) {
        List<List<String>> result = new ArrayList<>();
        // used 用来记录某个字符串是否已经被分过组，避免重复分组
        boolean[] used = new boolean[strs.length];

        // 依次尝试用每个字符串作为一个新组的代表
        for (int i = 0; i < strs.length; i++) {
            if (used[i]) {
                continue;       // 已分组，跳过
            }

            // 创建新组，将当前字符串作为组的第一个元素
            List<String> group = new ArrayList<>();
            group.add(strs[i]);
            used[i] = true;

            // 在后面的字符串中寻找与当前字符串互为异位词的
            for (int j = i + 1; j < strs.length; j++) {
                // 必须是还没被分组的，且与代表字符串是异位词
                if (!used[j] && isAnagram(strs[i], strs[j])) {
                    group.add(strs[j]);
                    used[j] = true;
                }
            }

            result.add(group);
        }

        return result;
    }

    /**
     * 判断两个字符串是否为字母异位词。
     * 方法：对两个字符串排序后比较是否相等。
     */
    public static boolean isAnagram(String first, String second) {
        // 长度不同肯定不是异位词
        if (first.length() != second.length()) {
            return false;
        }

        char[] firstChars = first.toCharArray();
        char[] secondChars = second.toCharArray();

        // 排序后比较
        Arrays.sort(firstChars);
        Arrays.sort(secondChars);

        return new String(firstChars).equals(new String(secondChars));
    }
}
