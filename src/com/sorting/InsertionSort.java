package com.sorting;

import java.util.Arrays;
import java.util.Scanner;

/**
 * 问题：对数组进行升序排序。
 * 方法：使用插入排序，将未排序元素插入到前面已排序区间的正确位置。
 * 解题思路：从第二个元素开始，把当前位置元素作为待插入值，向左扫描已排序区间。
 * 凡是比待插入值大的元素都向右移动一位，直到找到插入位置，再放入待插入值。
 * 时间复杂度：平均和最坏 O(n^2)，最好情况下数组已有序为 O(n)。
 * 空间复杂度：O(1)，原地排序。
 */
public class InsertionSort {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入数组长度：");
        int n = scanner.nextInt();
        int[] nums = new int[n];
        System.out.println("请输入数组元素：");
        for (int i = 0; i < n; i++) {
            nums[i] = scanner.nextInt();
        }
        insertionSort(nums);
        System.out.println("插入排序结果为：" + Arrays.toString(nums));
    }

    public static void insertionSort(int[] nums) {
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
}
