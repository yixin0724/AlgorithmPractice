package com.simulation;

import java.util.Arrays;
import java.util.Scanner;

/**
 * LeetCode Hot 100 - 283. Move Zeroes
 *
 * 模拟 / 原地覆盖思路：
 * 先从左到右遍历数组，把所有不为 0 的元素按原来的相对顺序依次写到数组前面。
 * 遍历结束后，前面已经保存了全部非零元素，后面剩下的位置全部补成 0。
 *
 * 这种写法是在模拟“先收集非零元素，再补零”的过程，只是没有额外创建新数组。
 * 时间复杂度：O(n)
 * 空间复杂度：O(1)
 */
public class MoveZeroesSimulation {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) {
            nums[i] = scanner.nextInt();
        }

        moveZeroes(nums);
        System.out.println(Arrays.toString(nums));
    }

    public static void moveZeroes(int[] nums) {
        // write 指向下一个非零元素应该写入的位置。
        int write = 0;

        // 第一遍遍历：遇到非零元素，就按原相对顺序写到数组前面。
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                nums[write] = nums[i];
                write++;
            }
        }

        // 第二遍遍历：write 之后的位置都应该补成 0。
        for (int i = write; i < nums.length; i++) {
            nums[i] = 0;
        }
    }
}
