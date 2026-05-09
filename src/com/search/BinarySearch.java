package com.search;

import java.util.Scanner;

/**
 * 问题：在有序数组中查找指定元素。
 * 方法：使用二分查找，每次比较中点后缩小搜索区间。
 * 解题思路：维护左右边界 low 和 high，每轮取中点 mid 与目标值比较。
 * 若目标更小则丢弃右半区间，若目标更大则丢弃左半区间，相等时返回下标；区间为空则表示不存在。
 * 时间复杂度：O(logn)，n 为数组长度。
 * 空间复杂度：O(1)，只使用常数个边界变量。
 */
public class BinarySearch {
    public static void main(String[] args) {
        BinarySearch binaryFind = new BinarySearch();
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入数组的长度：");
        int length = sc.nextInt();
        int[] arr = new int[length];
        System.out.println("请依次为数组赋值(使用折半数组必须是有序的)：");
        for (int i=0;i<length;i++){
            arr[i] = sc.nextInt();
        }
        System.out.println("请输入你要查找的数(-1代表没有找到该数)：");
        int i = sc.nextInt();
        int res = binaryFind.binarySearch(arr, i);
        System.out.println("查找的数索引为：" + res);
    }

    /*
     二分查找
     @param arr 要进行查找的数组，要求数组必须是有序的
     @param findElem 要查找的元素
     @return 返回要查找的元素在数组的索引位置， 返回-1表示没找到
     */
    public int binarySearch(int[] arr, int findElem) {
        int low = 0;
        int high = arr.length - 1;
        int mid;
        while (low <= high) {
            mid = (low + high) / 2;
            //如果要查找的元素findElem小于中间位置的元素mid，指向数组的较大端的high索引重新指向中间索引mid的左边（mid-1）
            if (findElem < arr[mid]) {
                high = mid - 1;
            }
            //如果要查找的元素findElem大于中间位置的元素mid，指向数组的较小端的low索引重新指向中间索引mid的右边（mid+1）
            if (findElem > arr[mid]) {
                low = mid + 1;
            }
            if (arr[mid] == findElem) {
                return mid;
            }
        }
        return -1;
    }
}
