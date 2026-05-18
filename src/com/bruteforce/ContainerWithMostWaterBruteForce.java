package com.bruteforce;

import java.util.Scanner;

/**
 * LeetCode Hot 100 - 11. Container With Most Water
 * 暴力枚举思路：
 * 枚举所有可能的两条竖线 height[i] 和 height[j]，其中 i < j。
 * 每一组竖线能形成的容器面积为：
 * 面积 = 两条线之间的距离 * 两条线中较短的一条
 * area = (j - i) * min(height[i], height[j])
 * 在枚举过程中维护最大面积即可。
 *
 * 时间复杂度：O(n^2)
 * 空间复杂度：O(1)
 */
public class ContainerWithMostWaterBruteForce {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        int[] height = new int[n];
        for (int i = 0; i < n; i++) {
            height[i] = scanner.nextInt();
        }

        int result = maxArea(height);
        System.out.println(result);
    }

    public static int maxArea(int[] height) {
        int maxValue = 0;
        for (int i = 0; i < height.length; i++) {
            for (int j = i + 1; j < height.length; j++) {
                int value = calcArea(height, i, j);
                if (value > maxValue) maxValue = value;
            }
        }
        return maxValue;
    }

    public static int calcArea(int[] height, int a, int b) {
        return (b - a) * Math.min(height[a], height[b]);
    }
}
