package com.hashing;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * LeetCode Hot 100 - 128. Longest Consecutive Sequence（最长连续序列）
 *
 * ═══════════════════════════════════════════════════════════
 * 人为思考过程：
 * ═══════════════════════════════════════════════════════════
 * 1. 暴力解法用了排序，时间复杂度 O(n log n)。
 *    题目要求 O(n)，说明不能排序，得另找办法。
 * 2. 核心观察：连续序列有一个关键性质——
 *    每个连续序列都有一个唯一的"起点"。
 *    起点就是"前一个数不存在"的那个数。
 * 3. 例如序列 [1, 2, 3, 4] 中，1 是起点，因为 0 不在数组中。
 *    而 2、3、4 都不是起点，因为 1、2、3 在数组中。
 * 4. 所以策略是：先把所有数字放入 HashSet，
 *    然后只从"起点"开始往后探索连续数字。
 * 5. 对于每个数 num，先检查 num-1 在不在 HashSet 中：
 *    - 如果在，num 不是起点，跳过；
 *    - 如果不在，num 是起点，从 num 开始不断找 num+1, num+2...，
 *      直到找不到下一个为止，记录这段连续序列的长度。
 * 6. 这个做法的巧妙之处在于：每个数字最多被访问两次
 *    （一次作为 num 被检查，一次作为连续序列的一部分被遍历），
 *    所以整体是 O(n) 的。
 * 7. 用 HashSet 而非排序的原因：HashSet 的查找是 O(1)，
 *    正好满足 O(n) 的要求。
 *
 * ═══════════════════════════════════════════════════════════
 * 具体措施（算法步骤）：
 * ═══════════════════════════════════════════════════════════
 * 1. 将所有数字存入 HashSet<Integer>。
 * 2. 初始化 maxLength = 0。
 * 3. 遍历 HashSet 中的每个数字 num：
 *    a. 若 set.contains(num - 1)，说明 num 不是起点，跳过。
 *    b. 否则，num 是起点：
 *       - 初始化 currentNum = num, currentLength = 1。
 *       - while(set.contains(currentNum + 1))：
 *         currentNum++, currentLength++。
 *       - maxLength = max(maxLength, currentLength)。
 * 4. 返回 maxLength。
 *
 * ═══════════════════════════════════════════════════════════
 * 复杂度分析：
 * ═══════════════════════════════════════════════════════════
 * 时间复杂度：O(n)
 *   - 构建 HashSet 需要 O(n)。
 *   - 每个数字最多被作为起点检查一次，
 *     也最多在 while 循环中作为连续序列的一部分被访问一次，
 *     所有 while 循环的总体执行次数也是 O(n)。
 * 空间复杂度：O(n)
 *   - HashSet 中存储了所有 n 个数字。
 */
public class LongestConsecutiveSequenceHashing {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter array length:");
        int n = scanner.nextInt();

        int[] nums = new int[n];
        System.out.println("Please enter array elements:");
        for (int i = 0; i < n; i++) {
            nums[i] = scanner.nextInt();
        }

        int result = longestConsecutive(nums);
        System.out.println(result);

        scanner.close();
    }

    public static int longestConsecutive(int[] nums) {
        // 将所有数字存入 HashSet，提供 O(1) 的查找能力
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            set.add(num);
        }

        int maxLength = 0;
        for (int num : set) {
            // 只有当前数字没有前驱时，才把它当作连续序列的起点
            // 这样可以避免从序列中间重复计算
            if (!set.contains(num - 1)) {
                int currentNum = num;
                int currentLength = 1;

                // 从起点开始，向后查找连续数字
                while (set.contains(currentNum + 1)) {
                    currentNum++;
                    currentLength++;
                }

                // 更新全局最大长度
                maxLength = Math.max(maxLength, currentLength);
            }
        }
        return maxLength;
    }
}
