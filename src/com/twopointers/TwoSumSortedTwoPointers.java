package com.twopointers;

import java.util.Scanner;

/**
 * 问题：在升序数组中找出两个数，使它们的和等于目标值。
 * 方法：使用双指针。
 * 解题思路：左指针从数组头部开始，右指针从数组尾部开始。
 * 若两数之和小于目标值，左指针右移增大和；若大于目标值，右指针左移减小和；相等时输出答案。
 * 时间复杂度：O(n)，n 为数组长度。
 * 空间复杂度：O(1)，只使用左右指针。
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
        int left = 0;
        int right = n - 1;
        while (left < right) {
            int sum = nums[left] + nums[right];
            if (sum == target) {
                System.out.println("找到下标：" + left + " " + right);
                return;
            } else if (sum < target) {
                left++;
            } else {
                right--;
            }
        }
        System.out.println("没有找到满足条件的两个数");
    }
}
