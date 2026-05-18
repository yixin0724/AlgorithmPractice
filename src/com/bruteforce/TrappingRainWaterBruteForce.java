package com.bruteforce;

import java.util.Scanner;

/**
 * LeetCode Hot 100 - 42. 接雨水 (Trapping Rain Water)
 *
 * 思考：
 * 1. 看到这个题目，首先要理解"接雨水"的物理含义：
 *    每个位置能接多少水，取决于它左边最高的柱子和右边最高的柱子——就像一个"木桶"，
 *    两侧的"挡板"决定了中间能装多少水。
 * 2. 具体到每个位置 i：
 *    - 往左看，找到左边最高的柱子高度 maxLeft
 *    - 往右看，找到右边最高的柱子高度 maxRight
 *    - 水的高度 = min(maxLeft, maxRight)，即由较矮的挡板决定
 *    - 位置 i 能接的水 = max(0, min(maxLeft, maxRight) - height[i])
 *    （如果柱子本身比水面高，该位置接不到水）
 * 3. 最直接的暴力做法：对每个位置 i，分别向左和向右扫描，找出左右最高柱子，
 *    然后计算该位置的积水量，累加即可。
 * 4. 这个想法完全符合直觉，虽然 O(n^2) 效率不高，但能清晰体现问题的本质。
 *
 * 算法步骤：
 * 1. 遍历每个位置 i（0 到 n-1）。
 * 2. 对于位置 i，向左扫描 [0, i] 找到最高的柱子 maxLeft。
 * 3. 向右扫描 [i, n-1] 找到最高的柱子 maxRight。
 * 4. 计算 min(maxLeft, maxRight) - height[i]，若为正则累加到结果中。
 *
 * 注意：首尾两个位置（i=0 和 i=n-1）无法接水，因为没有两侧挡板，但公式
 *       本身会得到 0 或负值，不影响结果。
 *
 * 时间复杂度：O(n^2)
 *   - 外层遍历 n 个位置，每个位置向左右各扫描 O(n)，总计 O(n^2)。
 * 空间复杂度：O(1)
 *   - 只使用了常数级额外变量。
 */
public class TrappingRainWaterBruteForce {
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
     * 暴力枚举计算接雨水总量。
     * @param height 柱子高度数组
     * @return 能接的雨水总量
     */
    public static int trap(int[] height) {
        int n = height.length;
        if (n == 0) {
            return 0;
        }

        int totalWater = 0;

        // ── 遍历每个位置，计算该位置能接雨水量 ──
        for (int i = 0; i < n; i++) {

            // ── 向左扫描，找左边最高柱子 ──
            int maxLeft = 0;
            for (int j = 0; j <= i; j++) {
                if (height[j] > maxLeft) {
                    maxLeft = height[j];
                }
            }

            // ── 向右扫描，找右边最高柱子 ──
            int maxRight = 0;
            for (int j = i; j < n; j++) {
                if (height[j] > maxRight) {
                    maxRight = height[j];
                }
            }

            // ── 水量由较矮的一侧挡板决定，减去自身高度 ──
            int water = Math.min(maxLeft, maxRight) - height[i];
            if (water > 0) {
                totalWater += water;
            }
        }

        return totalWater;
    }
}
