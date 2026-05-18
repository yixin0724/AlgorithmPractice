package com.twopointers;

import java.util.Scanner;

/**
 * LeetCode Hot 100 - 42. 接雨水 (Trapping Rain Water)
 * 思考：
 * 1. 暴力枚举是 O(n^2)，每个位置都要向左右扫描找最高柱子，做了大量重复计算。
 *    有没有办法用 O(n) 解决？
 * 2. 回想暴力法中，我们真正关心的是"左右两边各有一个最高挡板"这个信息。
 *    关键是：当我们从两边向中间逼近时，不需要知道"全局"左右最高，
 *    只需要知道"目前已经扫过的区域"中的左右最高。
 * 3. 考虑用双指针 left 和 right 分别从数组两端出发：
 *    - 维护 leftMax = [0, left] 区间内的最大高度
 *    - 维护 rightMax = [right, n-1] 区间内的最大高度
 * 4. 核心洞察（比较 leftMax 和 rightMax）：
 *    - 如果 leftMax < rightMax：
 *      说明当前位置 left 的水位由 leftMax 决定（因为右边至少有一个 rightMax
 *      更高的挡板兜底，水不会从右边溢出）。于是：
 *      water += leftMax - height[left]，然后 left 右移一位。
 *    - 如果 leftMax >= rightMax：
 *      同理，位置 right 的水位由 rightMax 决定：
 *      water += rightMax - height[right]，然后 right 左移一位。
 * 5. 这个思路的巧妙之处在于：我们不关心远处未知的柱子，
 *    只需要比较"当前已知"的左右最大高度。因为较低的那一侧
 *    决定了当前水位上限，即使对面还有更高的柱子，也不会改变
 *    较低侧的水位。
 * 6. 双指针相遇时，整个数组扫描完毕，总水量也就求出来了。
 *
 * 算法步骤：
 * 1. 初始化 left = 0, right = n-1, leftMax = 0, rightMax = 0。
 * 2. 当 left < right 时循环：
 *    a. 更新 leftMax = max(leftMax, height[left])
 *    b. 更新 rightMax = max(rightMax, height[right])
 *    c. 若 leftMax < rightMax：
 *       → 位置 left 的接水量 = leftMax - height[left]，left 右移
 *       （因为 rightMax 是更大的挡板，left 侧水位由 leftMax 决定）
 *    d. 否则：
 *       → 位置 right 的接水量 = rightMax - height[right]，right 左移
 *       （因为 leftMax 是更大或相等的挡板，right 侧水位由 rightMax 决定）
 *
 * 复杂度分析：
 * 时间复杂度：O(n)
 *   - left 和 right 各移动最多 n 步，每步 O(1)。
 * 空间复杂度：O(1)
 *   - 只使用了 left, right, leftMax, rightMax 等常数变量。
 */
public class TrappingRainWaterTwoPointers {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("请输入数组长度：");
        int n = scanner.nextInt();

        int[] height = new int[n];
        System.out.println("请输入柱子高度数组：");
        for (int i = 0; i < n; i++) {
            height[i] = scanner.nextInt();
        }

        int result = trap(height);
        System.out.println("能接的雨水总量：" + result);

        scanner.close();
    }

    /**
     * 双指针法计算接雨水总量。
     * @param height 柱子高度数组
     * @return 能接的雨水总量
     */
    public static int trap(int[] height) {
        int n = height.length;
        if (n == 0) {
            return 0;
        }

        int left = 0;           // 左指针
        int right = n - 1;      // 右指针
        int leftMax = 0;        // 当前已扫描的左侧最高柱子
        int rightMax = 0;       // 当前已扫描的右侧最高柱子
        int totalWater = 0;     // 总积水量

        // ── 双指针从两端向中间逼近 ──
        while (left < right) {
            // ── 更新左右已扫描区域的最高高度 ──
            leftMax = Math.max(leftMax, height[left]);
            rightMax = Math.max(rightMax, height[right]);

            // ── 核心判断：哪一侧的挡板更低，就处理哪一侧 ──
            // 原因：较低侧当前的水位已经确定，不会被对面更高的柱子抬高
            if (leftMax < rightMax) {
                // leftMax 为当前左侧最高挡板，水高即 leftMax
                // 位置 left 的积水量 = leftMax - 自身高度
                totalWater += leftMax - height[left];
                left++; // 左指针右移，处理下一个位置
            } else {
                // rightMax 为当前右侧最高挡板，水高即 rightMax
                // 位置 right 的积水量 = rightMax - 自身高度
                totalWater += rightMax - height[right];
                right--; // 右指针左移，处理下一个位置
            }
        }

        return totalWater;
    }
}
