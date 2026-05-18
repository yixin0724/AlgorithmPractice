package com.bruteforce;

import java.util.Scanner;

/**
 * LeetCode Hot 100 - 1. Two Sum（两数之和）
 *
 * ═══════════════════════════════════════════════════════════
 * 人为思考过程：
 * ═══════════════════════════════════════════════════════════
 * 1. 题目要求在数组中找出两个数，使得它们的和等于目标值 target，
 *    并返回这两个数的下标。
 * 2. 最朴素的想法就是：把数组中所有可能的数对都试一遍。
 *    如果数组里有 n 个数，那总共就有大约 n*(n-1)/2 对数。
 * 3. 可以用两个变量 i 和 j 分别代表第一个数和第二个数的位置，
 *    i 从 0 到 n-1，j 从 i+1 到 n-1，这样就枚举了所有不重复的数对。
 * 4. 对于每一对 (i,j)，只需要检查 nums[i] + nums[j] == target，
 *    如果满足就直接返回 [i, j]，因为题目保证有唯一解。
 * 5. 这种"试一试所有可能"的方法虽然不够快，但它是最直接的思维方式，
 *    也是很多优化解法的基础。
 * 6. 如果循环结束还没有找到，说明不存在这样的两个数，返回 [-1, -1]
 *    表示无解。
 *
 * ═══════════════════════════════════════════════════════════
 * 具体措施（算法步骤）：
 * ═══════════════════════════════════════════════════════════
 * 1. 读取数组长度 n 和目标值 target。
 * 2. 读取数组元素 nums[0..n-1]。
 * 3. 外层循环：i 从 0 遍历到 n-1，固定第一个数。
 * 4. 内层循环：j 从 i+1 遍历到 n-1，枚举第二个数。
 * 5. 若 nums[i] + nums[j] == target，立即返回 new int[]{i, j}。
 * 6. 遍历结束仍未找到，返回 new int[]{-1, -1}。
 *
 * ═══════════════════════════════════════════════════════════
 * 复杂度分析：
 * ═══════════════════════════════════════════════════════════
 * 时间复杂度：O(n^2)
 *   - 外层循环执行 n 次，内层循环平均执行 n/2 次，
 *     总体约 n^2/2 次比较，量级为 O(n^2)。
 * 空间复杂度：O(1)
 *   - 只使用了 i、j 两个循环变量和常量大小的返回数组，
 *     不随输入规模增长。
 */
public class TwoSumBruteForce {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter array length and target:");
        int n = scanner.nextInt();
        int target = scanner.nextInt();

        int[] nums = new int[n];
        System.out.println("Please enter array elements:");
        for (int i = 0; i < n; i++) {
            nums[i] = scanner.nextInt();
        }

        int[] result = twoSum(nums, target);
        if (result[0] == -1 && result[1] == -1) {
            System.out.println("No answer found");
        } else {
            System.out.println(result[0] + " " + result[1]);
        }

        scanner.close();
    }

    public static int[] twoSum(int[] nums, int target) {
        // 外层循环：固定第一个数
        for (int i = 0; i < nums.length; i++) {
            // 内层循环：枚举第二个数，从 i+1 开始避免重复和自匹配
            for (int j = i + 1; j < nums.length; j++) {
                // 检查两数之和是否等于目标值
                if (nums[i] + nums[j] == target) {
                    return new int[]{i, j};
                }
            }
        }
        // 无解时返回哨兵值
        return new int[]{-1, -1};
    }
}
