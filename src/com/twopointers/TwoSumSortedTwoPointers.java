package com.twopointers;

import java.util.Scanner;

/**
 * LeetCode Hot 100 - 167. Two Sum II - Input Array Is Sorted（两数之和 II - 输入有序数组）
 *
 * ═══════════════════════════════════════════════════════════
 * 人为思考过程：
 * ═══════════════════════════════════════════════════════════
 * 1. 这是 Two Sum 的变体，输入变成了升序排列的数组，
 *    这个"有序"特性是解题的关键突破口。
 * 2. 回想一下：对于升序数组，两端的数分别是最小值和最大值。
 *    把它们加起来看看：
 *    - 如果和等于 target，恭喜，直接找到答案；
 *    - 如果和小于 target，说明当前最小的数"拖了后腿"，
 *      需要换一个更大的数来参与求和，所以左指针右移；
 *    - 如果和大于 target，说明当前最大的数"太过头了"，
 *      需要换一个更小的数，所以右指针左移。
 * 3. 这种"大了往左缩，小了往右进"的策略非常像折半查找的思想，
 *    利用有序性来逐步逼近目标值。
 * 4. 为什么双指针不会错过正确答案？
 *    因为每次移动都是排除了一个"不可能参与正确答案"的数：
 *    如果和太小，左指针指的数对任何右指针右边的数都太小；
 *    如果和太大，右指针指的数对任何左指针左边的数都太大。
 * 5. 相比暴力 O(n^2) 或哈希 O(n) 但需要额外空间，
 *    双指针在有序数组上做到了 O(n) 时间 + O(1) 空间。
 *
 * ═══════════════════════════════════════════════════════════
 * 具体措施（算法步骤）：
 * ═══════════════════════════════════════════════════════════
 * 1. 初始化左指针 left = 0，右指针 right = n - 1。
 * 2. while (left < right) 循环：
 *    a. 计算 sum = nums[left] + nums[right]。
 *    b. 若 sum == target，输出 left 和 right，结束。
 *    c. 若 sum < target，left++（需要更大的数）。
 *    d. 若 sum > target，right--（需要更小的数）。
 * 3. 循环结束仍未找到，输出"没有找到"。
 *
 * ═══════════════════════════════════════════════════════════
 * 复杂度分析：
 * ═══════════════════════════════════════════════════════════
 * 时间复杂度：O(n)
 *   - 每次循环移动一个指针，最多移动 n 次。
 * 空间复杂度：O(1)
 *   - 只使用 left、right 两个指针和 sum 临时变量。
 */
public class TwoSumSortedTwoPointers {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入升序数组长度和目标值：");
        int n = scanner.nextInt();
        int target = scanner.nextInt();
        int[] nums = new int[n];
        System.out.println("请输入升序数组元素：");
        for (int i = 0; i < n; i++) {
            nums[i] = scanner.nextInt();
        }
        // 双指针：left 从左端开始，right 从右端开始
        int left = 0;
        int right = n - 1;
        while (left < right) {
            int sum = nums[left] + nums[right];
            if (sum == target) {
                // 找到目标，输出下标
                System.out.println("找到下标：" + left + " " + right);
                return;
            } else if (sum < target) {
                // 和太小，左指针右移增大和
                left++;
            } else {
                // 和太大，右指针左移减小和
                right--;
            }
        }
        System.out.println("没有找到满足条件的两个数");
    }
}
