package com.sorting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * 桶排序（Bucket Sort）
 *
 * 问题：对整数数组进行升序排序。
 *
 * 常见实现：
 * 1. bucketSort：固定桶宽版本。本文件推荐使用该方法，main 也调用它。
 *    每个桶负责固定大小的数值区间，桶宽由 BUCKET_SIZE 控制。
 * 2. bucketSortByBucketCount：固定桶数版本。
 *    调用者指定桶数量，再根据 min、max 把值映射到不同桶。
 *
 * 推荐方法 bucketSort 的核心思路：
 * 1. 扫描数组得到 min 和 max。
 * 2. bucketCount = (max - min) / BUCKET_SIZE + 1。
 * 3. 每个 num 放入 (num - min) / BUCKET_SIZE 对应的桶。
 * 4. 每个桶内部单独排序，再按桶顺序写回原数组。
 *
 * 时间复杂度：平均接近 O(n + k)，最坏退化为桶内排序复杂度。
 * 空间复杂度：O(n + k)。
 * 稳定性：本实现桶内使用 Collections.sort，对 List<Integer> 是稳定排序。
 */
public class BucketSort {
    private static final int BUCKET_SIZE = 5;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入数组长度：");
        int length = scanner.nextInt();
        int[] nums = new int[length];

        System.out.println("请依次输入数组元素：");
        for (int i = 0; i < nums.length; i++) {
            nums[i] = scanner.nextInt();
        }

        bucketSort(nums);
        System.out.println("桶排序结果为：" + Arrays.toString(nums));
    }

    public static void bucketSort(int[] nums) {
        if (nums == null || nums.length < 2) {
            return;
        }

        Range range = findRange(nums);
        long bucketCountLong = ((long) range.max - range.min) / BUCKET_SIZE + 1;
        if (bucketCountLong > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("数值范围过大，不适合使用当前桶排序实现");
        }

        List<List<Integer>> buckets = createBuckets((int) bucketCountLong);
        for (int num : nums) {
            int bucketIndex = (int) (((long) num - range.min) / BUCKET_SIZE);
            buckets.get(bucketIndex).add(num);
        }
        collectBuckets(nums, buckets);
    }

    public static void bucketSortByBucketCount(int[] nums, int bucketCount) {
        if (nums == null || nums.length < 2) {
            return;
        }
        if (bucketCount <= 0) {
            throw new IllegalArgumentException("桶数量必须大于 0");
        }

        Range range = findRange(nums);
        List<List<Integer>> buckets = createBuckets(bucketCount);
        long valueRange = (long) range.max - range.min + 1;
        for (int num : nums) {
            int bucketIndex = (int) (((long) num - range.min) * bucketCount / valueRange);
            if (bucketIndex == bucketCount) {
                bucketIndex--;
            }
            buckets.get(bucketIndex).add(num);
        }
        collectBuckets(nums, buckets);
    }

    private static List<List<Integer>> createBuckets(int bucketCount) {
        List<List<Integer>> buckets = new ArrayList<List<Integer>>(bucketCount);
        for (int i = 0; i < bucketCount; i++) {
            buckets.add(new ArrayList<Integer>());
        }
        return buckets;
    }

    private static void collectBuckets(int[] nums, List<List<Integer>> buckets) {
        int index = 0;
        for (List<Integer> bucket : buckets) {
            Collections.sort(bucket);
            for (int num : bucket) {
                nums[index++] = num;
            }
        }
    }

    private static Range findRange(int[] nums) {
        int min = nums[0];
        int max = nums[0];
        for (int num : nums) {
            min = Math.min(min, num);
            max = Math.max(max, num);
        }
        return new Range(min, max);
    }

    private static class Range {
        private final int min;
        private final int max;

        private Range(int min, int max) {
            this.min = min;
            this.max = max;
        }
    }
}
