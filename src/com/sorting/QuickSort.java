package com.sorting;

import java.util.Arrays;
import java.util.Scanner;

/**
 * 快速排序（Quick Sort）
 *
 * 问题：对 int 数组进行升序排序。
 *
 * 常见实现：
 * 1. quickSort：三路分区快排。本文件推荐使用该方法，main 也调用它。
 *    把区间分成 < pivot、= pivot、> pivot 三段，重复元素多时能减少大量递归。
 * 2. quickSortTwoWay：双边分区快排。
 *    左右指针向中间移动，左边找大值，右边找小值，找到后交换。
 *
 * 推荐方法 quickSort 的核心思路：
 * 1. 选择中间元素作为 pivot。
 * 2. less 左边放小于 pivot 的元素，greater 右边放大于 pivot 的元素。
 * 3. index 扫描未知区间，遇小值换到左边，遇大值换到右边，遇等值直接跳过。
 * 4. 分区后只递归处理小于区间和大于区间。
 *
 * 时间复杂度：平均 O(nlogn)，最坏 O(n^2)。
 * 空间复杂度：平均 O(logn)，最坏 O(n)。
 * 稳定性：不稳定。
 */
public class QuickSort {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入数组长度：");
        int length = scanner.nextInt();
        int[] nums = new int[length];

        System.out.println("请依次输入数组元素：");
        for (int i = 0; i < nums.length; i++) {
            nums[i] = scanner.nextInt();
        }

        quickSort(nums);
        System.out.println("快速排序结果为：" + Arrays.toString(nums));
    }

    public static void quickSort(int[] nums) {
        if (nums == null || nums.length < 2) {
            return;
        }
        quickSortThreeWay(nums, 0, nums.length - 1);
    }

    public static void quickSortTwoWay(int[] nums) {
        if (nums == null || nums.length < 2) {
            return;
        }
        quickSortTwoWay(nums, 0, nums.length - 1);
    }

    public void quickSort(int[] nums, int left, int right) {
        if (nums == null || nums.length < 2 || left >= right) {
            return;
        }
        if (left < 0 || right >= nums.length) {
            throw new IllegalArgumentException("排序区间越界");
        }
        quickSortThreeWay(nums, left, right);
    }

    private static void quickSortThreeWay(int[] nums, int left, int right) {
        if (left >= right) {
            return;
        }

        int pivot = nums[left + (right - left) / 2];
        int less = left;
        int index = left;
        int greater = right;

        while (index <= greater) {
            if (nums[index] < pivot) {
                swap(nums, less++, index++);
            } else if (nums[index] > pivot) {
                swap(nums, index, greater--);
            } else {
                index++;
            }
        }

        quickSortThreeWay(nums, left, less - 1);
        quickSortThreeWay(nums, greater + 1, right);
    }

    private static void quickSortTwoWay(int[] nums, int left, int right) {
        if (left >= right) {
            return;
        }

        int pivot = nums[left + (right - left) / 2];
        int i = left;
        int j = right;
        while (i <= j) {
            while (nums[i] < pivot) {
                i++;
            }
            while (nums[j] > pivot) {
                j--;
            }
            if (i <= j) {
                swap(nums, i++, j--);
            }
        }

        quickSortTwoWay(nums, left, j);
        quickSortTwoWay(nums, i, right);
    }

    private static void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
