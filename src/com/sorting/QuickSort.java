package com.sorting;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/**
 * 问题：对数组进行升序排序。
 * 方法：使用快速排序，选择基准元素分区后递归排序左右区间。
 * 解题思路：选择区间起点作为基准，通过左右指针交换元素，使基准左侧不大于基准、右侧不小于基准。
 * 基准归位后，递归处理左右两个子区间。
 * 时间复杂度：平均 O(nlogn)，最坏 O(n^2)。
 * 空间复杂度：平均 O(logn)，最坏 O(n)，主要来自递归调用栈。
 */
public class QuickSort {            //快速排序
    public static void main(String[] args) {
        QuickSort quickSort = new QuickSort();
        Random r = new Random();
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入数组的长度：");
        int length = sc.nextInt();
        int[] a = new int[length];
        System.out.println("得到的随机数组为：");
        for (int i = 0; i < a.length; i++) {
            a[i] = r.nextInt(20);
        }
//        for (int i = 0; i < a.length; i++) {
//            System.out.print(a[i] + ",");
//        }
        System.out.println(Arrays.toString(a));
        System.out.println("\n");
        System.out.println("经过快速排序过后的数组为：");
        quickSort.quickSort(a, 0, length - 1);
//        for (int i = 0; i < length; i++) {
//            System.out.print(a[i] + ",");
//        }
        System.out.println(Arrays.toString(a));
    }

    public void quickSort(int[] a, int i, int j) {
        if (i >= j) {        //先判断区间是否是1个以上
            return;
        }
        int low = i;
        int high = j;
        int key = i;
        int temp = 0;        //值的辅助交换
        while (i < j) {
            //利用while循环进行左边序列的选择
            while (i < j && a[j] >= a[key]) {
                j--;
            }
            while (i < j && a[i] <= a[key]) {
                i++;
            }
            //此时i=j，即基准位置已经确定，进行a[i]和a[j]的值交换
            temp = a[i];
            a[i] = a[j];
            a[j] = temp;
        }
        //确定基准位置
        temp = a[key];
        a[key] = a[j];
        a[j] = temp;
        key = j;
        //交换完基准的值和位置以后，进行其他子序列的选择
        quickSort(a, low, key - 1);
        quickSort(a, key + 1, high);
    }

}
