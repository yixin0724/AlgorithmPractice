package com.twopointers;

import java.util.Scanner;

/**
 * LeetCode Hot 100 - 11. Container With Most Water
 * 个人思考：
 *  我们用双指针暴力枚举，两重循环，依次找出所有结果，但时间超时了
 *  这个时候想想有没有能走一圈就完成的办法。
 *  比如对向指针，上来宽最大，用变量存储当前宽最大时的容量。
 *  此时只有移动矮的一边，容量才有可能变大。(因为容量受最短的一边限制，若宽减小，面积一定减小)
 *  移动矮的一边后，比较当前容量和记录的最大容量，选择最大的。
 *  然后判断两边哪个矮，继续移动矮的，然后继续存储容量大的
 *  直到两个指针相遇。
 * 具体策略：
 * 1.左右指针 left=0, right=n-1，此时宽度最大
 * 2.每次计算面积，然后移动较矮的那根柱子的指针向内一步
 * 3.重复直到两指针相遇
 * 时间O(n)
 * 空间O(1)
 */
public class ContainerWithMostWaterTwoPointers {
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
        int left = 0;
        int right = height.length - 1;
        int maxArea = 0;

        while (left < right) {
            // 计算当前宽度：两根柱子之间的距离
            int width = right - left;

            // 容量由较矮的柱子决定（木桶效应）
            int currentHeight = Math.min(height[left], height[right]);
            int area = width * currentHeight;

            // 更新最大容量
            maxArea = Math.max(maxArea, area);

            // 核心：移动较矮的一边，才有可能让容量变大
            // 因为宽度在缩小，只有提高较短边的高度，面积才可能增大
            if (height[left] < height[right]) {
                left++;
            } else {
                right--;
            }
        }

        return maxArea;
    }
}
