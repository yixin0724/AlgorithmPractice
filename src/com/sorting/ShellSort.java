package com.sorting;

import java.util.Arrays;
import java.util.Scanner;

/**
 * 希尔排序（Shell Sort）
 *
 * 问题：对 int 数组进行升序排序。
 *
 * 常见实现：
 * 1. shellSort：Knuth 增量序列版本。本文件推荐使用该方法，main 也调用它。
 *    gap 序列为 1, 4, 13, 40...，通常比简单折半 gap 表现更稳定。
 * 2. shellSortHalfGap：折半增量版本。
 *    gap 从 n / 2 开始，每轮 gap /= 2，最容易理解。
 *
 * 推荐方法 shellSort 的核心思路：
 * 1. 先用较大的 gap 分组，对每组做插入排序。
 * 2. gap 越大，元素可以越快跨距离移动，让数组整体接近有序。
 * 3. 不断缩小 gap，最后 gap = 1 时做普通插入排序。
 *
 * 时间复杂度：与 gap 序列有关，通常优于 O(n^2)，最坏仍可能退化。
 * 空间复杂度：O(1)。
 * 稳定性：不稳定。
 */
public class ShellSort {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入数组长度：");
        int length = scanner.nextInt();
        int[] nums = new int[length];

        System.out.println("请依次输入数组元素：");
        for (int i = 0; i < nums.length; i++) {
            nums[i] = scanner.nextInt();
        }

        shellSort(nums);
        System.out.println("希尔排序结果为：" + Arrays.toString(nums));
    }

    public static void shellSort(int[] nums) {
        if (nums == null || nums.length < 2) {
            return;
        }

        int gap = 1;
        while (gap < nums.length / 3) {
            gap = gap * 3 + 1;
        }

        while (gap >= 1) {
            shellInsert(nums, gap);
            gap /= 3;
        }
    }

    public static void shellSortHalfGap(int[] nums) {
        if (nums == null || nums.length < 2) {
            return;
        }

        for (int gap = nums.length / 2; gap > 0; gap /= 2) {
            shellInsert(nums, gap);
        }
    }

    private static void shellInsert(int[] nums, int gap) {
        for (int i = gap; i < nums.length; i++) {
            int current = nums[i];
            int j = i - gap;
            while (j >= 0 && nums[j] > current) {
                nums[j + gap] = nums[j];
                j -= gap;
            }
            nums[j + gap] = current;
        }
    }
}
