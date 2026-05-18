package com.bruteforce;

import java.util.*;

/**
 * LeetCode Hot 100 - 15. 三数之和 (3Sum)
 * 思考：
 * 1. 看到题目要求找出三个数之和为0，第一反应是：能不能像做"两数之和"那样枚举？
 *    两数之和用了两层循环枚举所有二元组，那三数之和很自然地想到用三层循环。
 * 2. 于是可以先写一个三重循环：
 *    for i = 0 ~ n-3
 *        for j = i+1 ~ n-2
 *            for k = j+1 ~ n-1
 *                if nums[i] + nums[j] + nums[k] == 0，就找到了一个解。
 * 3. 但题目要求不能包含重复的三元组。比如数组中有重复元素时，三层循环会枚举出
 *    重复的组合。怎么去重？—— 最简单的办法是用一个 Set，把每组解排序后放入
 *    Set，Set 会自动去重。
 * 4. 这个思路虽然直观，但很明显时间复杂度是 O(n^3)，在 n 较大时会超时。
 *    不过作为理解题意的第一步，暴力枚举是很好的起点。
 *
 * 具体措施（算法步骤）：
 * 1. 使用三重循环枚举所有可能的 (i, j, k) 组合，满足 i < j < k。
 * 2. 若 nums[i] + nums[j] + nums[k] == 0，将三元组排序后存入 HashSet 去重。
 * 3. 最后将 Set 转为 List 返回。
 *
 * 去重方式：将找到的三元组 [a, b, c] 排序（可使用 Arrays.sort 或 Math.min/max），
 * 然后拼成字符串 "a,b,c" 存入 Set。这样相同的三元组只会保留一份。
 * 也可以直接用 List<Integer> 配合 HashSet，但需要确保顺序一致。
 *
 * 时间复杂度：O(n^3)
 *   - 三重循环遍历所有组合，约为 C(n,3) = n(n-1)(n-2)/6 种，即 O(n^3)。
 *   - 每次去重排序开销 O(3*log3) = O(1)，可忽略。
 * 空间复杂度：O(n)
 *   - HashSet 最坏情况下存储所有不重复的三元组，但通常远小于 n^3。
 *   - 不考虑返回结果所占空间。
 */
public class ThreeSumBruteForce {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("请输入数组长度：");
        int n = scanner.nextInt();

        int[] nums = new int[n];
        System.out.println("请输入数组元素：");
        for (int i = 0; i < n; i++) {
            nums[i] = scanner.nextInt();
        }

        List<List<Integer>> result = threeSum(nums);

        System.out.println("所有和为0且不重复的三元组：");
        if (result.isEmpty()) {
            System.out.println("[]");
        } else {
            for (List<Integer> triplet : result) {
                System.out.println(triplet);
            }
        }

        scanner.close();
    }

    /**
     * 暴力枚举求解三数之和。
     * @param nums 输入整数数组
     * @return 所有和为0且不重复的三元组
     */
    public static List<List<Integer>> threeSum(int[] nums) {
        // 用于去重的集合：将排好序的三元组转成字符串"a,b,c"作为唯一标识
        Set<String> set = new HashSet<>();
        List<List<Integer>> res = new ArrayList<>();

        int n = nums.length;

        // ── 三重循环枚举所有三元组 ──
        // i 从 0 到 n-3，给 j,k 留出位置
        for (int i = 0; i < n - 2; i++) {
            // j 从 i+1 到 n-2，给 k 留出位置
            for (int j = i + 1; j < n - 1; j++) {
                // k 从 j+1 到 n-1
                for (int k = j + 1; k < n; k++) {
                    // 判断三数之和是否等于 0
                    if (nums[i] + nums[j] + nums[k] == 0) {
                        // ── 去重：将三个数排序后转为字符串作为唯一key ──
                        // 三个数的手动排序（避免频繁创建数组）
                        int a = nums[i], b = nums[j], c = nums[k];
                        // 通过比较和交换确保 a <= b <= c
                        if (a > b) { int t = a; a = b; b = t; }
                        if (b > c) { int t = b; b = c; c = t; }
                        if (a > b) { int t = a; a = b; b = t; }
                        // 构造唯一标识
                        String key = a + "," + b + "," + c;
                        if (!set.contains(key)) {
                            set.add(key);
                            res.add(Arrays.asList(a, b, c));
                        }
                    }
                }
            }
        }
        return res;
    }
}
