package com.monotonicstack;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.Scanner;

/**
 * LeetCode Hot 100 - 496. Next Greater Element I (下一个更大元素)
 *
 * ═══════════════════════════════════════════════════════════
 * 人为思考过程：
 * ═══════════════════════════════════════════════════════════
 * 1. 问题本质：对数组中每个元素，找到其右侧第一个比它大的元素值，
 *    若不存在则返回 -1。
 * 2. 暴力思路：对每个元素，向右扫描找到第一个更大的值，O(n^2)。
 *    但可以优化。
 * 3. 关键观察：当我们从右向左扫描数组时，遇到一个元素 x，我们需要知道
 *    它右侧有哪些"候选者"是比 x 大的。而且一旦某个候选者小于等于 x，
 *    它对于更左侧的元素也一定没用（因为 x 不仅更大，还更靠左，会先被
 *    遇到）。
 * 4. 这正好是单调栈的场景：维护一个从栈顶到栈底递增的栈，栈中存放右侧
 *    尚未匹配到更大元素的候选值。
 * 5. 遍历到新元素时，把栈中所有小于等于它的值都弹出（它们对新元素及
 *    左侧元素都无效了），此时栈顶（若存在）就是该元素的"下一个更大元素"。
 *    然后将当前元素压入栈，因为它是左侧元素的潜在候选。
 *
 * ═══════════════════════════════════════════════════════════
 * 具体措施（算法步骤）：
 * ═══════════════════════════════════════════════════════════
 * 1. 初始化结果数组 result（与输入等长）和单调栈 stack。
 * 2. 从右向左遍历数组（i = n-1 到 0）：
 *    a. 维护单调递减栈：弹出栈中所有 <= nums[i] 的元素。
 *    b. 若栈为空，则 result[i] = -1（右侧没有更大的）。
 *       否则 result[i] = stack.peek()（栈顶即最近且比当前大的元素）。
 *    c. 将 nums[i] 压入栈中。
 * 3. 返回 result 数组。
 *
 * ═══════════════════════════════════════════════════════════
 * 复杂度分析：
 * ═══════════════════════════════════════════════════════════
 * 时间复杂度：O(n)
 *   - 每个元素最多入栈一次、出栈一次，均摊 O(1) 每次操作。
 *   - 总操作次数为 O(n)。
 * 空间复杂度：O(n)
 *   - 栈在最坏情况下（数组严格递减）会存储所有 n 个元素。
 *   - 结果数组 result 长度 n 是必须的输出空间。
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
        // 调用单调栈解法
        System.out.println("右侧第一个更大元素为：" + Arrays.toString(nextGreaterElements(nums)));
    }

    public static int[] nextGreaterElements(int[] nums) {
        int[] result = new int[nums.length];
        // 单调栈：从栈顶到栈底元素值递增
        Deque<Integer> stack = new ArrayDeque<Integer>();
        // 从右向左遍历
        for (int i = nums.length - 1; i >= 0; i--) {
            // 弹出栈中所有不大于当前值的元素（它们对左侧元素无效）
            while (!stack.isEmpty() && stack.peek() <= nums[i]) {
                stack.pop();
            }
            // 栈顶即为当前元素右侧第一个更大的值，若栈空说明不存在
            result[i] = stack.isEmpty() ? -1 : stack.peek();
            // 当前元素压入栈，作为左侧元素的候选
            stack.push(nums[i]);
        }
        return result;
    }
}
