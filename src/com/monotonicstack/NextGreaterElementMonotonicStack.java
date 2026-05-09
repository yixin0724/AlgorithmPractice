package com.monotonicstack;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.Scanner;

/**
 * 问题：给定数组，求每个元素右侧第一个比它大的元素，不存在则为 -1。
 * 方法：使用单调栈。
 * 解题思路：从右向左遍历数组，栈中维护一个从栈顶到栈底递增的候选元素集合。
 * 当前元素会弹出所有小于等于它的元素，弹出后栈顶就是右侧第一个更大元素。
 * 时间复杂度：O(n)，每个元素最多入栈和出栈一次。
 * 空间复杂度：O(n)，栈最多保存 n 个元素。
 */
public class NextGreaterElementMonotonicStack {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入数组长度：");
        int n = scanner.nextInt();
        int[] nums = new int[n];
        System.out.println("请输入数组元素：");
        for (int i = 0; i < n; i++) {
            nums[i] = scanner.nextInt();
        }
        System.out.println("右侧第一个更大元素为：" + Arrays.toString(nextGreaterElements(nums)));
    }

    public static int[] nextGreaterElements(int[] nums) {
        int[] result = new int[nums.length];
        Deque<Integer> stack = new ArrayDeque<Integer>();
        for (int i = nums.length - 1; i >= 0; i--) {
            while (!stack.isEmpty() && stack.peek() <= nums[i]) {
                stack.pop();
            }
            result[i] = stack.isEmpty() ? -1 : stack.peek();
            stack.push(nums[i]);
        }
        return result;
    }
}
