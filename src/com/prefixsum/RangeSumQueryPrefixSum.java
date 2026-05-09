package com.prefixsum;

import java.util.Scanner;

/**
 * 问题：多次查询数组区间和。
 * 方法：使用前缀和。
 * 解题思路：预处理 prefix，其中 prefix[i] 表示前 i 个元素的和。
 * 查询区间 [l,r] 时，答案为 prefix[r+1]-prefix[l]，避免每次重新遍历区间。
 * 时间复杂度：预处理 O(n)，每次查询 O(1)。
 * 空间复杂度：O(n)，需要前缀和数组。
 */
public class RangeSumQueryPrefixSum {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入数组长度和查询次数：");
        int n = scanner.nextInt();
        int q = scanner.nextInt();
        int[] prefix = new int[n + 1];
        System.out.println("请输入数组元素：");
        for (int i = 0; i < n; i++) {
            prefix[i + 1] = prefix[i] + scanner.nextInt();
        }
        System.out.println("请输入每次查询的左端点和右端点，端点从 0 开始：");
        for (int i = 0; i < q; i++) {
            int left = scanner.nextInt();
            int right = scanner.nextInt();
            System.out.println("区间和为：" + (prefix[right + 1] - prefix[left]));
        }
    }
}
