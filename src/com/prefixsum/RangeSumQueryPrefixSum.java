package com.prefixsum;

import java.util.Scanner;

/**
 * LeetCode Hot 100 - 303. Range Sum Query - Immutable（区域和检索 - 数组不可变）
 *
 * ═══════════════════════════════════════════════════════════
 * 人为思考过程：
 * ═══════════════════════════════════════════════════════════
 * 面对"多次查询数组区间和"的问题，最直接的想法就是每次查询时遍历区间求和——但如果查询次数成百上千，重复遍历的代价就会变得非常大。
 *
 * 1. 思考：能不能把"求和"这件事提前做掉，做到"一次计算、随时查询"？
 * 2. 联想：假设我要经常问"前 i 个元素的和是多少"，如果预先算好存下来，每次查询就是 O(1)。
 * 3. 那么区间和 [l, r] 和前 i 项和之间有什么关系？显然 sum[l..r] = sum[0..r] - sum[0..l-1]。
 * 4. 这正是前缀和（prefix sum）的核心思想——把区间求和转换成两个前缀和的差值。
 * 5. 实现时，为了避免处理左边界为 0 的特殊情况，可以令 prefix[0] = 0，然后 prefix[i+1] = prefix[i] + nums[i]。
 * 6. 这样区间 [l, r] 的和就是 prefix[r+1] - prefix[l]，统一且优雅。
 *
 * ═══════════════════════════════════════════════════════════
 * 具体措施（算法步骤）：
 * ═══════════════════════════════════════════════════════════
 * 1. 构建前缀和数组 prefix，长度为 n+1，令 prefix[0] = 0。
 * 2. 遍历原数组：prefix[i+1] = prefix[i] + nums[i]。
 * 3. 对于每次查询 [l, r]，直接返回 prefix[r+1] - prefix[l]。
 *
 * ═══════════════════════════════════════════════════════════
 * 复杂度分析：
 * ═══════════════════════════════════════════════════════════
 * 时间复杂度：
 *   - 构建前缀和：O(n)
 *   - 每次查询：O(1)
 * 空间复杂度：O(n)
 *   - 需要一个长度为 n+1 的前缀和数组。
 */
public class RangeSumQueryPrefixSum {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入数组长度和查询次数：");
        int n = scanner.nextInt();
        int q = scanner.nextInt();

        // 构建前缀和数组，prefix[i] 表示原数组前 i 个元素之和
        int[] prefix = new int[n + 1];
        System.out.println("请输入数组元素：");
        for (int i = 0; i < n; i++) {
            prefix[i + 1] = prefix[i] + scanner.nextInt();
        }

        System.out.println("请输入每次查询的左端点和右端点，端点从 0 开始：");
        for (int i = 0; i < q; i++) {
            int left = scanner.nextInt();
            int right = scanner.nextInt();

            // 区间和 = 前缀和[right+1] - 前缀和[left]
            System.out.println("区间和为：" + (prefix[right + 1] - prefix[left]));
        }
    }
}
