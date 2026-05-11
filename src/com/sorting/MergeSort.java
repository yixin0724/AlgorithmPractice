package com.sorting;

import java.util.Arrays;
import java.util.Scanner;

/**
 * 归并排序（Merge Sort）
 *
 * 问题：对 int 数组进行升序排序。
 *
 * 常见实现：
 * 1. mergeSort：自顶向下递归归并。本文件推荐使用该方法，main 也调用它。
 *    递归拆分区间，再合并两个有序子区间；写法清晰，最适合复现分治思路。
 *    本实现复用同一个 temp 数组，并在左右区间已经整体有序时跳过合并。
 * 2. mergeSortBottomUp：自底向上迭代归并。
 *    先合并长度为 1 的区间，再合并长度为 2、4、8... 的区间，不需要递归。
 *
 * 推荐方法 mergeSort 的核心思路：
 * 1. 递归排序 [left, mid] 和 [mid + 1, right]。
 * 2. 两个子区间有序后，用双指针把它们合并到 temp。
 * 3. 再把 temp 中的结果复制回原数组对应区间。
 *
 * 时间复杂度：最好、平均、最坏都是 O(nlogn)。
 * 空间复杂度：O(n)。
 * 稳定性：稳定。相等时优先取左区间元素。
 */
public class MergeSort {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入数组长度：");
        int length = scanner.nextInt();
        int[] nums = new int[length];

        System.out.println("请依次输入数组元素：");
        for (int i = 0; i < nums.length; i++) {
            nums[i] = scanner.nextInt();
        }

        mergeSort(nums);
        System.out.println("归并排序结果为：" + Arrays.toString(nums));
    }

    public static void mergeSort(int[] nums) {
        if (nums == null || nums.length < 2) {
            return;
        }

        int[] temp = new int[nums.length];
        mergeSort(nums, 0, nums.length - 1, temp);
    }

    public static void mergeSortBottomUp(int[] nums) {
        if (nums == null || nums.length < 2) {
            return;
        }

        int[] temp = new int[nums.length];
        for (int size = 1; size < nums.length; size *= 2) {
            for (int left = 0; left < nums.length - size; left += size * 2) {
                int mid = left + size - 1;
                int right = Math.min(left + size * 2 - 1, nums.length - 1);
                if (nums[mid] > nums[mid + 1]) {
                    merge(nums, left, mid, right, temp);
                }
            }
            if (size > nums.length / 2) {
                break;
            }
        }
    }

    public void Merge_Sort(int[] nums, int start, int end) {
        if (nums == null || nums.length < 2 || start >= end) {
            return;
        }
        if (start < 0 || end >= nums.length) {
            throw new IllegalArgumentException("排序区间越界");
        }

        int[] temp = new int[nums.length];
        mergeSort(nums, start, end, temp);
    }

    public void Merge(int[] nums, int start, int mid, int end) {
        if (nums == null || nums.length == 0) {
            return;
        }
        if (start < 0 || start > mid || mid >= end || end >= nums.length) {
            throw new IllegalArgumentException("合并区间不合法");
        }

        int[] temp = new int[nums.length];
        merge(nums, start, mid, end, temp);
    }

    private static void mergeSort(int[] nums, int left, int right, int[] temp) {
        if (left >= right) {
            return;
        }

        int mid = left + (right - left) / 2;
        mergeSort(nums, left, mid, temp);
        mergeSort(nums, mid + 1, right, temp);

        if (nums[mid] <= nums[mid + 1]) {
            return;
        }
        merge(nums, left, mid, right, temp);
    }

    private static void merge(int[] nums, int left, int mid, int right, int[] temp) {
        int i = left;
        int j = mid + 1;
        int index = left;

        while (i <= mid && j <= right) {
            if (nums[i] <= nums[j]) {
                temp[index++] = nums[i++];
            } else {
                temp[index++] = nums[j++];
            }
        }
        while (i <= mid) {
            temp[index++] = nums[i++];
        }
        while (j <= right) {
            temp[index++] = nums[j++];
        }
        for (int k = left; k <= right; k++) {
            nums[k] = temp[k];
        }
    }
}
