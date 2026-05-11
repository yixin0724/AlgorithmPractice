package com.sorting;

import java.util.Arrays;
import java.util.Scanner;

/**
 * 堆排序（Heap Sort）
 *
 * 问题：对 int 数组进行升序排序。
 *
 * 常见实现：
 * 1. heapSort：循环下沉版堆排序。本文件推荐使用该方法，main 也调用它。
 *    建大顶堆后，反复把堆顶最大值交换到末尾，再用循环恢复堆结构。
 * 2. heapSortRecursiveHeapify：递归下沉版堆排序。
 *    整体流程相同，只是 heapify 使用递归写法，更贴近树的定义。
 *
 * 推荐方法 heapSort 的核心思路：
 * 1. 从最后一个非叶子节点 n / 2 - 1 开始向前建大顶堆。
 * 2. 大顶堆堆顶 nums[0] 是当前未排序区间最大值。
 * 3. 交换 nums[0] 和 nums[end]，最大值进入最终位置。
 * 4. 缩小堆范围，对堆顶做下沉调整，恢复大顶堆。
 *
 * 时间复杂度：O(nlogn)，建堆 O(n)，每次调整 O(logn)。
 * 空间复杂度：循环版 O(1)，递归版有 O(logn) 调用栈。
 * 稳定性：不稳定。
 */
public class HeapSort {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入数组长度：");
        int length = scanner.nextInt();
        int[] nums = new int[length];

        System.out.println("请依次输入数组元素：");
        for (int i = 0; i < nums.length; i++) {
            nums[i] = scanner.nextInt();
        }

        heapSort(nums);
        System.out.println("堆排序结果为：" + Arrays.toString(nums));
    }

    public static void heapSort(int[] nums) {
        if (nums == null || nums.length < 2) {
            return;
        }

        buildMaxHeap(nums);
        for (int end = nums.length - 1; end > 0; end--) {
            swap(nums, 0, end);
            heapifyIterative(nums, end, 0);
        }
    }

    public static void heapSortRecursiveHeapify(int[] nums) {
        if (nums == null || nums.length < 2) {
            return;
        }

        for (int i = nums.length / 2 - 1; i >= 0; i--) {
            heapifyRecursive(nums, nums.length, i);
        }
        for (int end = nums.length - 1; end > 0; end--) {
            swap(nums, 0, end);
            heapifyRecursive(nums, end, 0);
        }
    }

    private static void buildMaxHeap(int[] nums) {
        for (int i = nums.length / 2 - 1; i >= 0; i--) {
            heapifyIterative(nums, nums.length, i);
        }
    }

    private static void heapifyIterative(int[] nums, int heapSize, int root) {
        while (true) {
            int largest = root;
            int left = root * 2 + 1;
            int right = root * 2 + 2;

            if (left < heapSize && nums[left] > nums[largest]) {
                largest = left;
            }
            if (right < heapSize && nums[right] > nums[largest]) {
                largest = right;
            }
            if (largest == root) {
                return;
            }

            swap(nums, root, largest);
            root = largest;
        }
    }

    private static void heapifyRecursive(int[] nums, int heapSize, int root) {
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
            heapifyRecursive(nums, heapSize, largest);
        }
    }

    private static void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
