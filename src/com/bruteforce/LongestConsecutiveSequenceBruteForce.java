package com.bruteforce;

import java.util.Arrays;
import java.util.Scanner;

/**
 * LeetCode Hot 100 - 128. Longest Consecutive Sequence
 *
 * 思路：
 * 最容易想到的办法是先把数组排序。排序之后，原来分散的连续数字会靠在一起，
 * 例如 [100, 4, 200, 1, 3, 2] 排序后变成 [1, 2, 3, 4, 100, 200]。
 *
 * 接着从第二个元素开始遍历，统计当前连续序列的长度 currentLength，
 * 并用 maxLength 记录遍历过程中见过的最大连续长度。
 *
 * 遍历时需要分三种情况：
 * 1. 当前数字等于前一个数字：说明是重复数字，跳过，不增加长度，也不断开序列。
 * 2. 当前数字等于前一个数字 + 1：说明连续，currentLength 加 1。
 * 3. 其他情况：说明连续序列断开，currentLength 重置为 1。
 *
 * 时间复杂度：O(n log n)，主要来自排序。
 * 空间复杂度：O(1)，不考虑排序内部使用的额外空间。
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
        if (nums.length == 0) {
            return 0;
        }

        Arrays.sort(nums);

        int currentLength = 1;
        int maxLength = 1;

        for (int i = 1; i < nums.length; i++) {
            // 重复数字不影响连续序列长度，直接跳过。
            if (nums[i] == nums[i - 1]) {
                continue;
            }

            if (nums[i] == nums[i - 1] + 1) {
                currentLength++;
            } else {
                // 当前数字无法接在前一个数字后面，说明连续序列断开。
                currentLength = 1;
            }

            maxLength = Math.max(maxLength, currentLength);
        }

        return maxLength;
    }
}
