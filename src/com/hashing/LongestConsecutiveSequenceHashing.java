package com.hashing;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * LeetCode Hot 100 - 128. Longest Consecutive Sequence
 *
 * 思路：
 * 要求最长连续序列长度，并且题目要求时间复杂度为 O(n)，所以不能依赖排序。
 * 可以先把所有数字放入 HashSet，利用 HashSet 的 O(1) 平均查找能力判断某个数字是否存在。
 *
 * 关键点是：只从连续序列的起点开始统计。
 * 如果 num - 1 存在，说明 num 不是序列起点，直接跳过。
 * 如果 num - 1 不存在，说明 num 是一个连续序列的起点，然后不断查找 num + 1、num + 2 ...
 * 直到下一个数字不存在为止，这一段长度就是当前连续序列长度。
 *
 * 例如 [100, 4, 200, 1, 3, 2]：
 * 1 的前一个数字 0 不存在，所以从 1 开始找 2、3、4，长度为 4。
 * 2、3、4 都不是起点，因为它们的前一个数字存在，所以会被跳过。
 *
 * 时间复杂度：O(n)，每个数字最多被作为起点或连续查找的一部分处理。
 * 空间复杂度：O(n)，HashSet 保存数组中的数字。
 */
public class LongestConsecutiveSequenceHashing {
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
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            set.add(num);
        }

        int maxLength = 0;
        for (int num : set) {
            // 只有当前数字没有前驱时，才把它当作连续序列的起点。
            if (!set.contains(num - 1)) {
                int currentNum = num;
                int currentLength = 1;

                // 从起点开始，向后查找连续数字。
                while (set.contains(currentNum + 1)) {
                    currentNum++;
                    currentLength++;
                }

                maxLength = Math.max(maxLength, currentLength);
            }
        }
        return maxLength;
    }
}
