package com.simulation;

import java.util.Scanner;

/**
 * LeetCode Hot 100 - 42. 接雨水 (Trapping Rain Water)
 * 思考：
 * 1. 常规解法都是从垂直角度思考（每列能接多少水），能不能换个角度？
 *    把整张图看成俯视图，想象从上往下看，只看"实心部分"。
 * 2. 换个视角：把蓝色（水）和黑色（柱子）都涂成同一种颜色，
 *    整个图形就变成了一个层层堆叠的"实心阶梯"。
 *    那么——实心总面积 - 柱子总面积 = 水的面积。
 * 3. 怎么求出这个"实心图形"的总面积？
 *    从最底层（h=1）开始，逐层向上计算每层的"宽度"：
 *    - 第 h 层：找到最左边高度 ≥ h 的柱子（left），最右边高度 ≥ h 的柱子（right）
 *    - 该层面积 = right - left + 1
 * 4. 用双指针从两边向中间收缩来找左右边界——只要柱子高度不够 h，就继续往里走。
 *    当 left 和 right 都停下（即两边都找到了 ≥ h 的柱子），
 *    这一层的面积就确定了。
 * 5. 逐层累加所有层的面积，最后减去所有柱子高度的总和，剩下的就是积水量。
 *
 * 算法步骤：
 * 1. 先求出柱子高度的总和 sumHeights，以及最大高度 maxH。
 * 2. 对于每一层 h = 1, 2, ..., maxH：
 *    a. left 从 0 开始向右移动，直到 height[left] >= h（找到左边"围墙"）
 *    b. right 从 n-1 开始向左移动，直到 height[right] >= h（找到右边"围墙"）
 *    c. 若 left > right，说明这一层无法形成容器，跳过剩余层。
 *    d. 该层面积 = right - left + 1，累加到 totalArea。
 * 3. 结果 = totalArea - sumHeights。
 *
 * 复杂度分析：
 * 时间复杂度：O(n * maxH)
 *   - maxH = 数组中的最大高度。
 *   - 每一层需要从两端向中间扫描找到边界，最坏 O(n)。
 *   - 注：若 maxH 很大（如 10^9），该方法会超时，适合高度值较小的场景，
 *     或作为理解问题的可视化思路。
 * 空间复杂度：O(1)
 *   - 仅使用常数级变量。
 */
public class TrappingRainWaterSimulation {
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
     * 逐层模拟法计算接雨水总量。
     * 核心思想：实心图形总面积 - 柱子总面积 = 雨水面积
     * @param height 柱子高度数组
     * @return 能接的雨水总量
     */
    public static int trap(int[] height) {
        int n = height.length;
        if (n == 0) {
            return 0;
        }

        // ── 计算柱子高度总和与最大高度 ──
        int sumHeights = 0;
        int maxH = 0;
        for (int h : height) {
            sumHeights += h;
            if (h > maxH) {
                maxH = h;
            }
        }

        int totalArea = 0; // 实心图形的总面积

        // ── 逐层计算：从第 1 层到最高层 ──
        for (int h = 1; h <= maxH; h++) {
            // 双指针从两端向中间收缩，找出本层的左右边界
            int left = 0;
            int right = n - 1;

            // 左指针：向右移动，直到找到高度 ≥ h 的柱子（本层左侧"围墙"）
            while (left <= right && height[left] < h) {
                left++;
            }

            // 右指针：向左移动，直到找到高度 ≥ h 的柱子（本层右侧"围墙"）
            while (left <= right && height[right] < h) {
                right--;
            }

            // 若左指针超过了右指针，说明这一层无法形成容器
            // 更高层同样无法形成容器（因为柱子只会越来越少），可以直接结束
            if (left > right) {
                break;
            }

            // 本层面积 = 左右墙之间的宽度（包含两端的柱子）
            totalArea += (right - left + 1);
        }

        // ── 核心公式：水面积 = 实心总面积 - 柱子总面积 ──
        return totalArea - sumHeights;
    }
}
