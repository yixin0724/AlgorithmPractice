package com.numbertheory;

import java.util.Scanner;

/**
 * 问题：求两个整数的最大公约数。
 * 方法：使用欧几里得算法。
 * 解题思路：根据 gcd(a,b)=gcd(b,a mod b) 不断缩小问题规模。
 * 当 b 为 0 时，a 就是最大公约数。
 * 时间复杂度：O(log min(a,b))。
 * 空间复杂度：O(1)，使用迭代实现。
 */
public class GreatestCommonDivisorNumberTheory {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入两个整数：");
        int a = scanner.nextInt();
        int b = scanner.nextInt();
        System.out.println("最大公约数为：" + gcd(a, b));
    }

    public static int gcd(int a, int b) {
        a = Math.abs(a);
        b = Math.abs(b);
        while (b != 0) {
            int remainder = a % b;
            a = b;
            b = remainder;
        }
        return a;
    }
}
