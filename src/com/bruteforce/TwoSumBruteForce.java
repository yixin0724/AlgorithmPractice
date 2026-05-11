package com.bruteforce;

import java.util.Scanner;

/**
 * LeetCode Hot 100 - 1. Two Sum
 * 思路：
 * 先尝试暴力枚举，可以尝试用两个变量，如i和j。
 * 其中i从数组的第一个元素开始，依次与后面的元素相加，当本轮完成后，继续从第二个元素开始重复上述行为。
 * 直到两数之和等于目标值时，返回i和j。
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
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target){
                    return new int[]{i , j};
                }
            }
        }
        return new int[]{-1, -1};
    }
}
