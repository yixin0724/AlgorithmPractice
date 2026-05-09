package com.sorting;

import java.util.Arrays;
import java.util.Scanner;

/**
 * 问题：对数组进行升序排序。
 * 方法：使用堆排序，先建大顶堆，再反复取出堆顶最大值。
 * 解题思路：大顶堆保证堆顶是当前未排序区间的最大值。
 * 每轮将堆顶与未排序区间末尾交换，缩小堆范围，再向下调整堆顶恢复堆性质。
 * 时间复杂度：O(nlogn)，建堆 O(n)，每次调整 O(logn)。
 * 空间复杂度：O(1)，原地排序。
 */
public class HeapSort {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入数组长度：");
        int n = scanner.nextInt();
        int[] nums = new int[n];
        System.out.println("请输入数组元素：");
        for (int i = 0; i < n; i++) {
            nums[i] = scanner.nextInt();
        }
        heapSort(nums);
        System.out.println("堆排序结果为：" + Arrays.toString(nums));
    }

    public static void heapSort(int[] nums) {
        for (int i = nums.length / 2 - 1; i >= 0; i--) {
            heapify(nums, nums.length, i);
        }
        for (int end = nums.length - 1; end > 0; end--) {
            swap(nums, 0, end);
            heapify(nums, end, 0);
        }
    }

    private static void heapify(int[] nums, int heapSize, int root) {
        int largest = root;
        int left = root * 2 + 1;
        int right = root * 2 + 2;
        if (left < heapSize && nums[left] > nums[largest]) {
            largest = left;
        }
        if (right < heapSize && nums[right] > nums[largest]) {
            largest = right;
        }
        if (largest != root) {
            swap(nums, root, largest);
            heapify(nums, heapSize, largest);
        }
    }

    private static void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
