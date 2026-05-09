package com.sorting;

import java.util.Arrays;
import java.util.Scanner;

/**
 * 问题：对整数数组进行升序排序。
 * 方法：使用计数排序。
 * 解题思路：先扫描数组找到最小值和最大值，确定计数数组范围。
 * 统计每个数出现次数，再按从小到大的顺序根据出现次数回填原数组。
 * 时间复杂度：O(n+k)，n 为数组长度，k 为数值范围大小。
 * 空间复杂度：O(k)，需要计数数组。
 */
public class CountingSort {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入数组长度：");
        int n = scanner.nextInt();
        int[] nums = new int[n];
        System.out.println("请输入数组元素：");
        for (int i = 0; i < n; i++) {
            nums[i] = scanner.nextInt();
        }
        countingSort(nums);
        System.out.println("计数排序结果为：" + Arrays.toString(nums));
    }

    public static void countingSort(int[] nums) {
        if (nums.length == 0) {
            return;
        }
        int min = nums[0];
        int max = nums[0];
        for (int num : nums) {
            min = Math.min(min, num);
            max = Math.max(max, num);
        }
        int[] count = new int[max - min + 1];
        for (int num : nums) {
            count[num - min]++;
        }
        int index = 0;
        for (int i = 0; i < count.length; i++) {
            while (count[i] > 0) {
                nums[index++] = i + min;
                count[i]--;
            }
        }
    }
}
