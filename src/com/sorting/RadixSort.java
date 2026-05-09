package com.sorting;

import java.util.Arrays;
import java.util.Scanner;

/**
 * 问题：对非负整数数组进行升序排序。
 * 方法：使用基数排序。
 * 解题思路：从个位开始，按当前数位进行稳定的计数排序，然后依次处理十位、百位等更高数位。
 * 低位排序结果会被稳定保留，高位处理完成后，整体数字顺序就是升序。
 * 时间复杂度：O(d(n+r))，d 为最大数字位数，r 为基数，十进制下 r=10。
 * 空间复杂度：O(n+r)，需要临时数组和计数数组。
 */
public class RadixSort {
    private static final int RADIX = 10;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入非负整数数组长度：");
        int n = scanner.nextInt();
        int[] nums = new int[n];
        System.out.println("请输入非负整数数组元素：");
        for (int i = 0; i < n; i++) {
            nums[i] = scanner.nextInt();
            if (nums[i] < 0) {
                throw new IllegalArgumentException("基数排序示例只支持非负整数");
            }
        }
        radixSort(nums);
        System.out.println("基数排序结果为：" + Arrays.toString(nums));
    }

    public static void radixSort(int[] nums) {
        if (nums.length == 0) {
            return;
        }
        int max = nums[0];
        for (int num : nums) {
            max = Math.max(max, num);
        }
        for (int exp = 1; max / exp > 0; exp *= RADIX) {
            countingSortByDigit(nums, exp);
        }
    }

    private static void countingSortByDigit(int[] nums, int exp) {
        int[] output = new int[nums.length];
        int[] count = new int[RADIX];
        for (int num : nums) {
            count[(num / exp) % RADIX]++;
        }
        for (int i = 1; i < RADIX; i++) {
            count[i] += count[i - 1];
        }
        for (int i = nums.length - 1; i >= 0; i--) {
            int digit = (nums[i] / exp) % RADIX;
            output[count[digit] - 1] = nums[i];
            count[digit]--;
        }
        System.arraycopy(output, 0, nums, 0, nums.length);
    }
}
