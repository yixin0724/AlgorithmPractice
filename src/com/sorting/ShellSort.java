package com.sorting;

import java.util.Arrays;
import java.util.Scanner;

/**
 * 问题：对数组进行升序排序。
 * 方法：使用希尔排序，也叫缩小增量排序。
 * 解题思路：先按较大的 gap 将数组分组，对每组做插入排序，使数组逐步接近有序。
 * 然后不断缩小 gap，直到 gap 为 1 时进行最后一次插入排序，得到完全有序数组。
 * 时间复杂度：与 gap 序列有关，当前折半 gap 实现通常优于 O(n^2)，最坏仍可能为 O(n^2)。
 * 空间复杂度：O(1)，原地排序。
 */
public class ShellSort {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入数组长度：");
        int n = scanner.nextInt();
        int[] nums = new int[n];
        System.out.println("请输入数组元素：");
        for (int i = 0; i < n; i++) {
            nums[i] = scanner.nextInt();
        }
        shellSort(nums);
        System.out.println("希尔排序结果为：" + Arrays.toString(nums));
    }

    public static void shellSort(int[] nums) {
        for (int gap = nums.length / 2; gap > 0; gap /= 2) {
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
}
