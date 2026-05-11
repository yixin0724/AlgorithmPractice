package com.sorting;

import java.util.Arrays;
import java.util.Scanner;

/**
 * 计数排序（Counting Sort）
 *
 * 问题：对整数数组进行升序排序。
 *
 * 常见实现：
 * 1. countingSort：稳定计数排序。本文件推荐使用该方法，main 也调用它。
 *    先统计次数，再把次数改成前缀和，最后从右向左写入 output。
 *    对 int 数组来说稳定性看不出来，但这个写法可以迁移到对象排序。
 * 2. countingSortSimple：直接回填版计数排序。
 *    只统计每个值出现次数，然后从小到大写回原数组。代码更短，但不稳定。
 *
 * 推荐方法 countingSort 的核心思路：
 * 1. 扫描数组，找到 min 和 max，用 num - min 作为 count 下标以支持负数。
 * 2. count[i] 先表示某个值出现次数。
 * 3. 对 count 做前缀和后，count[i] 表示该值在结果中的右边界位置。
 * 4. 从右向左遍历原数组，把元素放入 output[count[index] - 1]，再让 count[index]--。
 * 5. 最后把 output 拷贝回 nums。
 *
 * 时间复杂度：O(n + k)，k = max - min + 1。
 * 空间复杂度：稳定版 O(n + k)，直接回填版 O(k)。
 * 稳定性：稳定版稳定，直接回填版不稳定。
 */
public class CountingSort {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入数组长度：");
        int length = scanner.nextInt();
        int[] nums = new int[length];

        System.out.println("请依次输入数组元素：");
        for (int i = 0; i < nums.length; i++) {
            nums[i] = scanner.nextInt();
        }

        countingSort(nums);
        System.out.println("计数排序结果为：" + Arrays.toString(nums));
    }

    public static void countingSort(int[] nums) {
        if (nums == null || nums.length < 2) {
            return;
        }

        Range range = findRange(nums);
        int[] count = new int[checkedRangeLength(range)];
        for (int num : nums) {
            count[num - range.min]++;
        }
        for (int i = 1; i < count.length; i++) {
            count[i] += count[i - 1];
        }

        int[] output = new int[nums.length];
        for (int i = nums.length - 1; i >= 0; i--) {
            int countIndex = nums[i] - range.min;
            output[count[countIndex] - 1] = nums[i];
            count[countIndex]--;
        }

        System.arraycopy(output, 0, nums, 0, nums.length);
    }

    public static void countingSortSimple(int[] nums) {
        if (nums == null || nums.length < 2) {
            return;
        }

        Range range = findRange(nums);
        int[] count = new int[checkedRangeLength(range)];
        for (int num : nums) {
            count[num - range.min]++;
        }

        int index = 0;
        for (int i = 0; i < count.length; i++) {
            while (count[i] > 0) {
                nums[index++] = i + range.min;
                count[i]--;
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

    private static int checkedRangeLength(Range range) {
        long length = (long) range.max - range.min + 1;
        if (length > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("数值范围过大，不适合使用计数排序");
        }
        return (int) length;
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
