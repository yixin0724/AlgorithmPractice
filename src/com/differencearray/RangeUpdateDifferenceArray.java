package com.differencearray;

import java.util.Arrays;
import java.util.Scanner;

/**
 * 问题：对数组执行多次区间加法操作，输出最终数组。
 * 方法：使用差分数组。
 * 解题思路：差分数组 diff 记录相邻元素变化量，对区间 [l,r] 加 value 时，只需要 diff[l]+=value，diff[r+1]-=value。
 * 所有更新完成后，对 diff 求前缀和即可还原每个位置的最终值。
 * 时间复杂度：O(n+q)，n 为数组长度，q 为区间更新次数。
 * 空间复杂度：O(n)，需要一个差分数组。
 */
public class RangeUpdateDifferenceArray {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入数组长度和操作次数：");
        int n = scanner.nextInt();
        int q = scanner.nextInt();
        int[] diff = new int[n + 1];
        System.out.println("请输入每次操作的左端点、右端点和增加值，端点从 0 开始：");
        for (int i = 0; i < q; i++) {
            int left = scanner.nextInt();
            int right = scanner.nextInt();
            int value = scanner.nextInt();
            diff[left] += value;
            if (right + 1 < n) {
                diff[right + 1] -= value;
            }
        }
        int[] result = new int[n];
        int current = 0;
        for (int i = 0; i < n; i++) {
            current += diff[i];
            result[i] = current;
        }
        System.out.println("更新后的数组为：" + Arrays.toString(result));
    }
}
