package com.twopointers;

import java.util.Arrays;
import java.util.Scanner;

/**
 * LeetCode Hot 100 - 283. Move Zeroes
 *
 * 双指针思路：
 * 使用 slow 和 fast 两个指针从左到右扫描数组。
 * fast 负责寻找非零元素，slow 负责记录“下一个非零元素应该放到的位置”。
 * 当 fast 遇到非零元素时，就把 nums[fast] 和 nums[slow] 交换，然后 slow 向后移动。
 *
 * 这样可以把非零元素逐步交换到数组前面，同时 0 会被自然换到后面。
 * 时间复杂度：O(n)
 * 空间复杂度：O(1)
 */
public class MoveZeroesTwoPointers {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) {
            nums[i] = scanner.nextInt();
        }

        moveZeroes(nums);
        System.out.println(Arrays.toString(nums));
    }

    public static void moveZeroes(int[] nums) {
        // slow 指向下一个非零元素应该交换到的位置。
        int slow = 0;

        // fast 负责向后扫描，寻找每一个非零元素。
        for (int fast = 0; fast < nums.length; fast++) {
            if (nums[fast] != 0) {
                // 把当前非零元素交换到 slow 位置，使非零元素集中到数组前面。
                int temp = nums[slow];
                nums[slow] = nums[fast];
                nums[fast] = temp;

                // slow 后移，等待下一个非零元素放入。
                slow++;
            }
        }
    }
}
