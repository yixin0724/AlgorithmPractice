package com.sorting;

import java.util.Arrays;
import java.util.Scanner;

/**
 * 选择排序（Selection Sort）
 *
 * 问题：对 int 数组进行升序排序。
 *
 * 常见实现：
 * 1. selectionSort：普通选择排序。本文件推荐使用该方法，main 也调用它。
 *    每轮从未排序区间中选择最小值，并把它交换到当前轮起点。
 *    写法简单、空间 O(1)，是选择排序最常见写法。
 * 2. stableSelectionSort：稳定选择排序。
 *    找到最小值后，不直接交换，而是把中间元素整体右移，再把最小值插到前面。
 *    它能保持相等元素的相对顺序，但移动次数更多。
 *
 * 推荐方法 selectionSort 的核心思路：
 * 1. 左侧维护已排序区间，右侧维护未排序区间。
 * 2. 第 i 轮在 [i, n) 中找到最小元素下标 minIndex。
 * 3. 如果 minIndex 不是 i，就交换 nums[i] 和 nums[minIndex]。
 * 4. 每一轮确定一个最终位置，执行 n - 1 轮后数组有序。
 *
 * 时间复杂度：最好、平均、最坏都是 O(n^2)。
 * 空间复杂度：普通版 O(1)，稳定版 O(1)。
 * 稳定性：普通版不稳定，稳定版稳定。
 */
public class SelectionSort {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入数组长度：");
        int length = scanner.nextInt();
        int[] nums = new int[length];

        System.out.println("请依次输入数组元素：");
        for (int i = 0; i < nums.length; i++) {
            nums[i] = scanner.nextInt();
        }

        selectionSort(nums);
        System.out.println("选择排序结果为：" + Arrays.toString(nums));
    }

    public static void selectionSort(int[] nums) {
        if (nums == null || nums.length < 2) {
            return;
        }

        for (int i = 0; i < nums.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[j] < nums[minIndex]) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                swap(nums, i, minIndex);
            }
        }
    }

    public static void stableSelectionSort(int[] nums) {
        if (nums == null || nums.length < 2) {
            return;
        }

        for (int i = 0; i < nums.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[j] < nums[minIndex]) {
                    minIndex = j;
                }
            }

            int minValue = nums[minIndex];
            while (minIndex > i) {
                nums[minIndex] = nums[minIndex - 1];
                minIndex--;
            }
            nums[i] = minValue;
        }
    }

    public void selectSort(int[] nums) {
        selectionSort(nums);
    }

    private static void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
