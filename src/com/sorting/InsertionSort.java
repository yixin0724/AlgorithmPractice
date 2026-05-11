package com.sorting;

import java.util.Arrays;
import java.util.Scanner;

/**
 * 插入排序（Insertion Sort）
 *
 * 问题：对 int 数组进行升序排序。
 *
 * 常见实现：
 * 1. insertionSort：直接插入排序。本文件推荐使用该方法，main 也调用它。
 *    从右向左扫描已排序区间，边找位置边移动元素。代码最直观，实际复习最常用。
 * 2. binaryInsertionSort：折半插入排序。
 *    用二分查找确定插入位置，可以减少比较次数，但元素移动次数仍然是 O(n^2)。
 *
 * 推荐方法 insertionSort 的核心思路：
 * 1. 默认 nums[0] 已经有序。
 * 2. 从 i = 1 开始，把 nums[i] 保存为 current。
 * 3. 在左侧已排序区间中，从右向左移动所有大于 current 的元素。
 * 4. 移动结束后，j + 1 就是 current 的插入位置。
 *
 * 时间复杂度：最好 O(n)，平均和最坏 O(n^2)。
 * 空间复杂度：O(1)。
 * 稳定性：稳定。
 */
public class InsertionSort {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入数组长度：");
        int length = scanner.nextInt();
        int[] nums = new int[length];

        System.out.println("请依次输入数组元素：");
        for (int i = 0; i < nums.length; i++) {
            nums[i] = scanner.nextInt();
        }

        insertionSort(nums);
        System.out.println("插入排序结果为：" + Arrays.toString(nums));
    }

    public static void insertionSort(int[] nums) {
        if (nums == null || nums.length < 2) {
            return;
        }

        for (int i = 1; i < nums.length; i++) {
            int current = nums[i];
            int j = i - 1;
            while (j >= 0 && nums[j] > current) {
                nums[j + 1] = nums[j];
                j--;
            }
            nums[j + 1] = current;
        }
    }

    public static void binaryInsertionSort(int[] nums) {
        if (nums == null || nums.length < 2) {
            return;
        }

        for (int i = 1; i < nums.length; i++) {
            int current = nums[i];
            int insertIndex = findInsertIndex(nums, 0, i - 1, current);

            for (int j = i - 1; j >= insertIndex; j--) {
                nums[j + 1] = nums[j];
            }
            nums[insertIndex] = current;
        }
    }

    private static int findInsertIndex(int[] nums, int left, int right, int target) {
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] <= target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return left;
    }
}
