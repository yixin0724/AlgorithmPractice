package com.monotonicqueue;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.Scanner;

/**
 * LeetCode Hot 100 - 239. Sliding Window Maximum (滑动窗口最大值)
 *
 * ═══════════════════════════════════════════════════════════
 * 人为思考过程：
 * ═══════════════════════════════════════════════════════════
 * 1. 问题本质：给定数组 nums 和窗口大小 k，窗口从左向右滑动，每次找出
 *    窗口内的最大值。最暴力做法是每个窗口都遍历 k 个元素，O(n*k)。
 * 2. 能不能在窗口滑动时复用之前的信息？每次窗口移动，最左的元素离开，
 *    最右的新元素加入。我们只关心"当前窗口的最大值"，不需要知道所有
 *    元素的具体顺序。
 * 3. 关键观察：如果窗口里来了一个更大的新元素，那么之前所有更小的元素
 *    在后续窗口中永远不可能成为最大值（因为它们既比新元素小，又会在
 *    新元素之前离开窗口）。所以我们不需要保留它们。
 * 4. 这就引出了"单调队列"：维护一个双端队列，从队头到队尾元素值单调递减，
 *    队头始终是当前窗口的最大值。新元素入队时，从队尾弹出所有小于它的
 *    元素（因为它们没用了）。窗口左端滑出时，若离开的元素正好是队头，
 *    则弹出队头。
 * 5. 这样每个元素最多入队一次、出队一次，总操作次数是 O(n)，非常高效。
 *
 * ═══════════════════════════════════════════════════════════
 * 具体措施（算法步骤）：
 * ═══════════════════════════════════════════════════════════
 * 1. 初始化结果数组 result（长度 n - k + 1）和双端队列 deque（存下标）。
 * 2. 遍历数组 nums，对于每个位置 i：
 *    a. 移除过期元素：若队头下标 <= i - k，说明已滑出窗口，弹出队头。
 *    b. 维护单调递减：从队尾弹出所有 nums[队尾] <= nums[i] 的元素。
 *    c. 将当前下标 i 加入队尾。
 *    d. 窗口成型（i >= k - 1）：队头对应值写入 result[i - k + 1]。
 * 3. 返回 result 数组。
 *
 * ═══════════════════════════════════════════════════════════
 * 复杂度分析：
 * ═══════════════════════════════════════════════════════════
 * 时间复杂度：O(n)
 *   - 每个元素最多入队一次、出队一次（包括 pollFirst 和 pollLast），
 *     均摊 O(1) 每次操作，总计 O(n)。
 * 空间复杂度：O(k)
 *   - 双端队列最多保存 k 个下标（一个窗口的大小）。
 *   - 结果数组 result 长度 n - k + 1 是必须的输出。
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
        // 调用单调队列解法
        System.out.println("每个窗口最大值为：" + Arrays.toString(maxSlidingWindow(nums, k)));
    }

    public static int[] maxSlidingWindow(int[] nums, int k) {
        int[] result = new int[nums.length - k + 1];
        // 双端队列存储下标，保证对应值从队头到队尾单调递减
        Deque<Integer> deque = new ArrayDeque<Integer>();
        for (int i = 0; i < nums.length; i++) {
            // 1. 移除已经滑出窗口的元素（头部过期元素）
            while (!deque.isEmpty() && deque.peekFirst() <= i - k) {
                deque.pollFirst();
            }
            // 2. 维护单调递减：弹出队尾所有不大于当前值的元素
            while (!deque.isEmpty() && nums[deque.peekLast()] <= nums[i]) {
                deque.pollLast();
            }
            // 3. 将当前下标加入队尾
            deque.offerLast(i);
            // 4. 窗口成型后记录结果（队头即当前窗口最大值）
            if (i >= k - 1) {
                result[i - k + 1] = nums[deque.peekFirst()];
            }
        }
        return result;
    }
}
