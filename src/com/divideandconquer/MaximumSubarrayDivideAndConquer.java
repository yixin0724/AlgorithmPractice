package com.divideandconquer;

import java.util.Scanner;

/**
 * @Author YiXin
 * @CreateTime 2022/5/23  12:45
 * 分治法
 * 依旧是以center=(left + right)/2为划分进行不断划分直到长度为1，然后进行一一求解，最后在进行合并
 */

/**
 * 问题：求数组的最大连续子数组和。
 * 方法：使用分治法，分别求左半区间、右半区间和跨中点区间的最大值。
 * 解题思路：将区间按中点拆成左右两半，最大子数组只可能完全在左侧、完全在右侧，或跨过中点。
 * 递归求左右答案，再从中点向两边扫描得到跨中点最大和，三者取最大值。
 * 时间复杂度：O(nlogn)，每层递归需要线性扫描跨中点区间。
 * 空间复杂度：O(logn)，主要来自递归调用栈。
 */
public class MaximumSubarrayDivideAndConquer {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入子段长度：");
        int n = sc.nextInt();
        int[] a = new int[n];
        System.out.println("输入子段：");
        for (int i = 0; i < n; i++) {
            a[i] = sc.nextInt();
        }
        if (MaxSum(a, 0, n - 1) < 0) {              //当子段和小于0结果无意义，因此直接输出0即可
            System.out.println("最大字段和小于0故结果为" + 0);
        } else {
            System.out.println("最大子段和为：" + MaxSum(a, 0, n - 1));
        }
    }

    public static int MaxSum(int[] a, int left, int right) {        //求数组[left]到[right]的最大子段和
        int sum = 0;
        int midSum = 0 ;
        int leftSum = 0;
        int rightSum = 0;
        int center, s1, s2, lefts, rights;
        if (left == right) {                                //当子段和的长度为1的时候直接求解即可
            sum = a[left];
        } else {
            center = (left + right) / 2;                       //采用分治法进行划分
            leftSum = MaxSum(a, left, center);                  //对左半部分进行再次调用方法划分求解
            rightSum = MaxSum(a, center + 1, right);        //对右半部分进行再次调用方法划分求解
            s1 = 0;
            lefts = 0;
            for (int i = center; i >= left; i--) {              //s1代表最半部分最大的子段和
                lefts += a[i];
                if (lefts > s1) {
                    s1 = lefts;
                }
            }
            s2 = 0;
            rights = 0;                                         //同样在求出s2，他俩之和就是最大的
            for (int j = center + 1; j <= right; j++) {
                rights += a[j];
                if (rights > s2) {
                    s2 = rights;
                }
            }
            midSum = s1 + s2;
            if (midSum < leftSum) {                             //从里面选取最大者
                sum = leftSum;
            } else {
                sum = midSum;
            }
            if (sum < rightSum) {
                sum = rightSum;
            }
        }
        return sum;                                     //最后直接返回最大和
    }
}
