package com.sorting;

import java.util.Arrays;
import java.util.Scanner;

/**
 * 冒泡排序（Bubble Sort）
 *
 * 问题：对 int 数组进行升序排序。
 *
 * 常见实现：
 * 1. bubbleSort：优化版冒泡排序。本文件推荐使用该方法，main 也调用它。
 *    每一轮记录是否发生交换，如果一整轮没有交换，说明数组已经有序，直接结束。
 *    最好时间复杂度可以达到 O(n)。
 * 2. bubbleSortBasic：基础版冒泡排序。
 *    固定执行 n - 1 轮，不判断数组是否提前有序，逻辑最直观，但最好情况仍是 O(n^2)。
 *
 * 推荐方法 bubbleSort 的核心思路：
 * 1. 每一轮从左到右比较相邻元素。
 * 2. 如果 nums[j] > nums[j + 1]，就交换它们。
 * 3. 一轮结束后，当前未排序区间中的最大值会被放到区间末尾。
 * 4. 下一轮不再处理末尾已排好元素，所以内层循环右边界逐轮左移。
 * 5. 如果某一轮没有发生交换，数组已经整体有序，可以提前结束。
 *
 * 时间复杂度：
 * - 优化版最好 O(n)，平均和最坏 O(n^2)。
 * - 基础版最好、平均、最坏都是 O(n^2)。
 *
 * 空间复杂度：O(1)。
 * 稳定性：稳定。相等元素不会交换。
 */
public class BubbleSort {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入数组长度：");
        int length = scanner.nextInt();
        int[] nums = new int[length];

        System.out.println("请依次输入数组元素：");
        for (int i = 0; i < nums.length; i++) {
            nums[i] = scanner.nextInt();
        }

        bubbleSort(nums);
        System.out.println("冒泡排序结果为：" + Arrays.toString(nums));
    }

    public static void bubbleSort(int[] nums) {
        if (nums == null || nums.length < 2) {
            return;
        }

        for (int i = 0; i < nums.length - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < nums.length - 1 - i; j++) {
                if (nums[j] > nums[j + 1]) {
                    swap(nums, j, j + 1);
                    swapped = true;
                }
            }
            if (!swapped) {
                return;
            }
        }
    }

    public static void bubbleSortBasic(int[] nums) {
        if (nums == null || nums.length < 2) {
            return;
        }

        for (int i = 0; i < nums.length - 1; i++) {
            for (int j = 0; j < nums.length - 1 - i; j++) {
                if (nums[j] > nums[j + 1]) {
                    swap(nums, j, j + 1);
                }
            }
        }
    }

    public void sort(int[] nums) {
        bubbleSort(nums);
    }

    private static void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
