package com.divideandconquer;

import java.util.Scanner;

/**
 * LeetCode Hot 100 - 53. Maximum Subarray (最大子数组和)
 *
 * ═══════════════════════════════════════════════════════════
 * 人为思考过程：
 * ═══════════════════════════════════════════════════════════
 * 1. 问题本质：给定一个整数数组 nums，找出一个具有最大和的连续子数组
 *    （子数组最少包含一个元素），返回其最大和。
 * 2. 暴力思路：枚举所有子数组起点和终点，计算和，O(n^2)。不够高效。
 * 3. 分治视角：把数组从中间位置切分成左右两半，那么最大子数组只有三种
 *    可能：完全在左半部分、完全在右半部分、或者横跨中点。
 * 4. 关键观察：对于跨中点的子数组，可以从中点出发分别向左和向右扫描，
 *    记录向左的最大后缀和与向右的最大前缀和，两者相加即跨中点最大和。
 * 5. 递归求左半和右半的最大子数组和，然后取三者中的最大值即可。
 * 6. 这本质上是归并排序的"求最值"变体：把问题不断二分直到长度为 1
 *    （此时最大和就是该元素本身），然后在合并层计算跨中点的情况。
 *
 * ═══════════════════════════════════════════════════════════
 * 具体措施（算法步骤）：
 * ═══════════════════════════════════════════════════════════
 * 1. 实现 MaxSum(a, left, right) 递归函数，返回 [left, right] 区间
 *    内的最大子数组和。
 * 2. 终止条件：若 left == right（区间长度为 1），直接返回 a[left]。
 * 3. 分：计算中点 center = (left + right) / 2，分别递归求左右答案。
 * 4. 治（跨中点）：
 *    a. 从中点向左扫描，累加求最大后缀和 s1。
 *    b. 从中点+1 向右扫描，累加求最大前缀和 s2。
 *    c. 跨中点最大和 midSum = s1 + s2。
 * 5. 返回 leftSum、rightSum、midSum 三者的最大值。
 * 6. 若最终结果 < 0，按题意输出 0。
 *
 * ═══════════════════════════════════════════════════════════
 * 复杂度分析：
 * ═══════════════════════════════════════════════════════════
 * 时间复杂度：O(n log n)
 *   - 递归树深度 O(log n)。
 *   - 每层需要 O(n) 扫描跨中点区间（向左 + 向右各约 n/2 次）。
 *   - 总复杂度 T(n) = 2T(n/2) + O(n) = O(n log n)。
 * 空间复杂度：O(log n)
 *   - 递归调用栈深度为 O(log n)。
 *   - 不需要额外数组，仅常数个辅助变量。
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
        // 调用分治法求最大子数组和
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
        // 终止条件：区间长度为 1
        if (left == right) {                                //当子段和的长度为1的时候直接求解即可
            sum = a[left];
        } else {
            center = (left + right) / 2;                       //采用分治法进行划分
            leftSum = MaxSum(a, left, center);                  //对左半部分进行再次调用方法划分求解
            rightSum = MaxSum(a, center + 1, right);        //对右半部分进行再次调用方法划分求解
            // 计算跨中点最大和：从中点向左扫描最大后缀和
            s1 = 0;
            lefts = 0;
            for (int i = center; i >= left; i--) {              //s1代表最半部分最大的子段和
                lefts += a[i];
                if (lefts > s1) {
                    s1 = lefts;
                }
            }
            // 计算跨中点最大和：从中点+1向右扫描最大前缀和
            s2 = 0;
            rights = 0;                                         //同样在求出s2，他俩之和就是最大的
            for (int j = center + 1; j <= right; j++) {
                rights += a[j];
                if (rights > s2) {
                    s2 = rights;
                }
            }
            midSum = s1 + s2;
            // 三者取最大值
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
