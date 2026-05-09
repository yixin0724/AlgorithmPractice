package com.sorting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * 问题：对整数数组进行升序排序。
 * 方法：使用桶排序。
 * 解题思路：根据最小值、最大值和桶大小，把元素分配到不同桶中。
 * 每个桶内部单独排序，最后按桶顺序依次合并结果。
 * 时间复杂度：平均 O(n+k)，最坏情况下所有元素落入同一桶并退化为桶内排序复杂度。
 * 空间复杂度：O(n+k)，n 为数组长度，k 为桶数量。
 */
public class BucketSort {
    private static final int BUCKET_SIZE = 5;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入数组长度：");
        int n = scanner.nextInt();
        int[] nums = new int[n];
        System.out.println("请输入数组元素：");
        for (int i = 0; i < n; i++) {
            nums[i] = scanner.nextInt();
        }
        bucketSort(nums);
        System.out.println("桶排序结果为：" + Arrays.toString(nums));
    }

    public static void bucketSort(int[] nums) {
        if (nums.length == 0) {
            return;
        }
        int min = nums[0];
        int max = nums[0];
        for (int num : nums) {
            min = Math.min(min, num);
            max = Math.max(max, num);
        }
        int bucketCount = (max - min) / BUCKET_SIZE + 1;
        List<List<Integer>> buckets = new ArrayList<List<Integer>>();
        for (int i = 0; i < bucketCount; i++) {
            buckets.add(new ArrayList<Integer>());
        }
        for (int num : nums) {
            buckets.get((num - min) / BUCKET_SIZE).add(num);
        }
        int index = 0;
        for (List<Integer> bucket : buckets) {
            Collections.sort(bucket);
            for (int num : bucket) {
                nums[index++] = num;
            }
        }
    }
}
