package com.monotonicqueue;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.Scanner;

/**
 * 问题：给定数组和窗口大小 k，求每个滑动窗口中的最大值。
 * 方法：使用单调队列。
 * 解题思路：队列中保存数组下标，并保证对应值从队头到队尾单调递减。
 * 新元素进入时弹出队尾所有更小元素；窗口左端右移时弹出过期下标，队头始终是当前窗口最大值下标。
 * 时间复杂度：O(n)，每个元素最多入队和出队一次。
 * 空间复杂度：O(k)，队列最多保存一个窗口内的下标。
 */
public class SlidingWindowMaximumMonotonicQueue {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入数组长度和窗口大小 k：");
        int n = scanner.nextInt();
        int k = scanner.nextInt();
        int[] nums = new int[n];
        System.out.println("请输入数组元素：");
        for (int i = 0; i < n; i++) {
            nums[i] = scanner.nextInt();
        }
        System.out.println("每个窗口最大值为：" + Arrays.toString(maxSlidingWindow(nums, k)));
    }

    public static int[] maxSlidingWindow(int[] nums, int k) {
        int[] result = new int[nums.length - k + 1];
        Deque<Integer> deque = new ArrayDeque<Integer>();
        for (int i = 0; i < nums.length; i++) {
            while (!deque.isEmpty() && deque.peekFirst() <= i - k) {
                deque.pollFirst();
            }
            while (!deque.isEmpty() && nums[deque.peekLast()] <= nums[i]) {
                deque.pollLast();
            }
            deque.offerLast(i);
            if (i >= k - 1) {
                result[i - k + 1] = nums[deque.peekFirst()];
            }
        }
        return result;
    }
}
