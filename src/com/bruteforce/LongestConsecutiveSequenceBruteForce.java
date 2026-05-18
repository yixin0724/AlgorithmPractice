package com.bruteforce;

import java.util.Arrays;
import java.util.Scanner;

/**
 * LeetCode Hot 100 - 128. Longest Consecutive Sequence（最长连续序列）
 *
 * ═══════════════════════════════════════════════════════════
 * 人为思考过程：
 * ═══════════════════════════════════════════════════════════
 * 1. 题目要求在未排序的数组中找到最长连续数字序列的长度。
 *    例如 [100, 4, 200, 1, 3, 2] 中最长连续序列是 [1,2,3,4]，长度为 4。
 * 2. 看到"连续"这个词，很自然会想到排序。
 *    如果数组排好序了，所有连续的数字就会紧挨在一起，
 *    这样只要扫一遍就能统计出连续段落的长度。
 * 3. 具体做法：先对数组排序，得到 [1, 2, 3, 4, 100, 200]。
 * 4. 然后从第二个元素开始，逐个检查当前元素是否比前一个元素大 1：
 *    - 如果正好大 1，说明序列在延续，currentLength++；
 *    - 如果相等，说明是重复数字，直接跳过不打断序列；
 *    - 如果差大于 1，说明连续序列断了，currentLength 重置为 1。
 * 5. 每走一步都用 maxLength 记录历史最大长度，
 *    保证即使序列在中间断开，也能记住之前见过的最大长度。
 * 6. 排序解法虽然时间复杂度是 O(n log n)，不如 O(n) 的哈希解法快，
 *    但思维过程更加自然，是拿到题目后的第一反应。
 *
 * ═══════════════════════════════════════════════════════════
 * 具体措施（算法步骤）：
 * ═══════════════════════════════════════════════════════════
 * 1. 若数组为空，直接返回 0。
 * 2. 对数组进行排序（升序）。
 * 3. 初始化 currentLength = 1，maxLength = 1。
 * 4. 从下标 1 开始遍历数组：
 *    a. 若 nums[i] == nums[i-1]（重复数字），跳过。
 *    b. 若 nums[i] == nums[i-1] + 1（连续），currentLength++。
 *    c. 否则（断开），currentLength = 1。
 *    d. maxLength = max(maxLength, currentLength)。
 * 5. 返回 maxLength。
 *
 * ═══════════════════════════════════════════════════════════
 * 复杂度分析：
 * ═══════════════════════════════════════════════════════════
 * 时间复杂度：O(n log n)
 *   - 排序需要 O(n log n)，后续单次遍历需要 O(n)，
 *     总复杂度由排序主导。
 * 空间复杂度：O(1)
 *   - 仅使用 currentLength、maxLength 等常数变量，
 *     不考虑排序算法内部使用的栈空间。
 */
public class LongestConsecutiveSequenceBruteForce {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter array length:");
        int n = scanner.nextInt();

        int[] nums = new int[n];
        System.out.println("Please enter array elements:");
        for (int i = 0; i < n; i++) {
            nums[i] = scanner.nextInt();
        }

        int result = longestConsecutive(nums);
        System.out.println(result);

        scanner.close();
    }

    public static int longestConsecutive(int[] nums) {
        // 空数组直接返回 0
        if (nums.length == 0) {
            return 0;
        }

        // 排序，让连续数字靠在一起
        Arrays.sort(nums);

        int currentLength = 1;
        int maxLength = 1;

        // 从第二个元素开始遍历
        for (int i = 1; i < nums.length; i++) {
            // 重复数字：不影响连续序列长度，直接跳过
            if (nums[i] == nums[i - 1]) {
                continue;
            }

            // 连续：长度加 1
            if (nums[i] == nums[i - 1] + 1) {
                currentLength++;
            } else {
                // 序列断开：重置当前长度为 1
                currentLength = 1;
            }

            // 更新历史最大长度
            maxLength = Math.max(maxLength, currentLength);
        }

        return maxLength;
    }
}
