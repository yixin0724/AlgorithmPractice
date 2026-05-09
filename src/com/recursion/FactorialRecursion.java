package com.recursion;

import java.util.Scanner;

/**
 * 问题：求 n 的阶乘。
 * 方法：使用递归。
 * 解题思路：n! 可以拆成 n*(n-1)!，因此递归求解规模更小的阶乘。
 * 当 n 为 0 或 1 时直接返回 1，作为递归终止条件。
 * 时间复杂度：O(n)，n 为输入数字。
 * 空间复杂度：O(n)，递归调用栈深度为 n。
 */
public class FactorialRecursion {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入 n：");
        int n = scanner.nextInt();
        System.out.println(n + " 的阶乘为：" + factorial(n));
    }

    public static long factorial(int n) {
        if (n <= 1) {
            return 1;
        }
        return n * factorial(n - 1);
    }
}
