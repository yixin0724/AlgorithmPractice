package com.hashing;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * LeetCode Hot 100 - 1. Two Sum（两数之和）
 *
 * ═══════════════════════════════════════════════════════════
 * 人为思考过程：
 * ═══════════════════════════════════════════════════════════
 * 1. 暴力解法的瓶颈在于每次都要用内层循环去找"能与当前数配对的另一个数"，
 *    这个过程需要 O(n) 时间，导致整体 O(n^2)。
 * 2. 有没有办法能在 O(1) 时间内知道"需要的那个数"是否已经出现过？
 *    哈希表（HashMap）正好能做到这一点，它的查找是 O(1) 的。
 * 3. 关键问题来了：HashMap 的 key 和 value 分别应该存什么？
 *    key 存数组元素的值，value 存该元素的下标，
 *    这样就能通过 map.get(need) 直接拿到配对元素的下标。
 * 4. 遍历数组时，对于当前元素 nums[i]，
 *    我们需要的配对值是 need = target - nums[i]。
 * 5. 先检查 map 中是否已经存在 need：
 *    - 如果存在，说明之前已经遍历过了，直接返回 [map.get(need), i]；
 *    - 如果不存在，把当前元素 (nums[i], i) 存入 map，
 *      等后面遍历到它的配对者时自然能找到。
 * 6. 这样只需要一次遍历就能解决问题，因为"先检查后存入"的策略
 *    保证了不会用同一个元素两次，也保证了配对的双向性。
 *
 * ═══════════════════════════════════════════════════════════
 * 具体措施（算法步骤）：
 * ═══════════════════════════════════════════════════════════
 * 1. 创建 HashMap<Integer, Integer>，key 存元素值，value 存下标。
 * 2. 遍历数组 nums，对于每个下标 i：
 *    a. 计算 need = target - nums[i]。
 *    b. 若 map 中包含 need，返回 new int[]{map.get(need), i}。
 *    c. 否则将 (nums[i], i) 存入 map。
 * 3. 遍历结束仍未找到，返回 new int[]{-1, -1}。
 *
 * ═══════════════════════════════════════════════════════════
 * 复杂度分析：
 * ═══════════════════════════════════════════════════════════
 * 时间复杂度：O(n)
 *   - 只遍历数组一次，每次 HashMap 的 containsKey 和 put
 *     操作均为 O(1) 均摊。
 * 空间复杂度：O(n)
 *   - 最坏情况下需要将所有 n 个元素存入 HashMap。
 */
public class TwoSumHashing {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter array length and target:");
        int n = scanner.nextInt();
        int target = scanner.nextInt();

        int[] nums = new int[n];
        System.out.println("Please enter array elements:");
        for (int i = 0; i < n; i++) {
            nums[i] = scanner.nextInt();
        }

        int[] result = twoSum(nums, target);
        if (result[0] == -1 && result[1] == -1) {
            System.out.println("No answer found");
        } else {
            System.out.println(result[0] + " " + result[1]);
        }

        scanner.close();
    }

    public static int[] twoSum(int[] nums, int target) {
        // key: 数组元素值, value: 该元素的下标
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            // 计算需要与当前数配对的另一个值
            int need = target - nums[i];
            // 检查需要的值是否已经存在于 map 中
            if (map.containsKey(need)) {
                // 找到了，返回之前存的下标和当前下标
                return new int[]{map.get(need), i};
            }
            // 未找到，将当前元素存入 map，供后续元素查找配对
            map.put(nums[i], i);
        }

        // 无解
        return new int[]{-1, -1};
    }
}
