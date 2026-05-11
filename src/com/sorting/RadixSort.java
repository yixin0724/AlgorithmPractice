package com.sorting;

import java.util.Arrays;
import java.util.Scanner;

/**
 * 基数排序（Radix Sort）
 *
 * 问题：对整数数组进行升序排序。
 *
 * 常见实现：
 * 1. radixSort：支持所有 int 的 LSD 基数排序。本文件推荐使用该方法，main 也调用它。
 *    先找最小值 min，把每个数映射成 key = num - min，这样 key 一定非负且顺序不变。
 *    然后按 key 的个位、十位、百位等做稳定计数排序。
 * 2. radixSortNonNegative：非负整数 LSD 基数排序。
 *    只接受非负整数，代码更接近教科书写法。
 *
 * 推荐方法 radixSort 的核心思路：
 * 1. 对每个原始值 nums[i] 计算非负 long key：nums[i] - min。
 * 2. key 的大小顺序和原始值完全一致。
 * 3. 对 key 做按位稳定计数排序，同时同步移动原始 nums。
 * 4. 最高位处理完后，nums 就按原始值升序排列。
 *
 * 时间复杂度：O(d * (n + r))，d 是最大 key 的位数，十进制 r = 10。
 * 空间复杂度：O(n + r)。
 * 稳定性：稳定。
 */
public class RadixSort {
    private static final int RADIX = 10;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入数组长度：");
        int length = scanner.nextInt();
        int[] nums = new int[length];

        System.out.println("请依次输入数组元素：");
        for (int i = 0; i < nums.length; i++) {
            nums[i] = scanner.nextInt();
        }

        radixSort(nums);
        System.out.println("基数排序结果为：" + Arrays.toString(nums));
    }

    public static void radixSort(int[] nums) {
        if (nums == null || nums.length < 2) {
            return;
        }

        int min = nums[0];
        int max = nums[0];
        for (int num : nums) {
            min = Math.min(min, num);
            max = Math.max(max, num);
        }

        long maxKey = (long) max - min;
        long[] keys = new long[nums.length];
        for (int i = 0; i < nums.length; i++) {
            keys[i] = (long) nums[i] - min;
        }

        radixSortByKeys(nums, keys, maxKey);
    }

    public static void radixSortNonNegative(int[] nums) {
        if (nums == null || nums.length == 0) {
            return;
        }

        int max = nums[0];
        for (int num : nums) {
            if (num < 0) {
                throw new IllegalArgumentException("该方法只支持非负整数");
            }
            max = Math.max(max, num);
        }
        if (nums.length < 2) {
            return;
        }

        long[] keys = new long[nums.length];
        for (int i = 0; i < nums.length; i++) {
            keys[i] = nums[i];
        }

        radixSortByKeys(nums, keys, max);
    }

    private static void radixSortByKeys(int[] nums, long[] keys, long maxKey) {
        int[] outputNums = new int[nums.length];
        long[] outputKeys = new long[keys.length];

        for (long exp = 1; maxKey / exp > 0; exp *= RADIX) {
            int[] count = new int[RADIX];

            for (long key : keys) {
                count[(int) ((key / exp) % RADIX)]++;
            }
            for (int i = 1; i < RADIX; i++) {
                count[i] += count[i - 1];
            }
            for (int i = nums.length - 1; i >= 0; i--) {
                int digit = (int) ((keys[i] / exp) % RADIX);
                int outputIndex = --count[digit];
                outputNums[outputIndex] = nums[i];
                outputKeys[outputIndex] = keys[i];
            }

            System.arraycopy(outputNums, 0, nums, 0, nums.length);
            System.arraycopy(outputKeys, 0, keys, 0, keys.length);
        }
    }
}
