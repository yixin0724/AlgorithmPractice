package com.bitmanipulation;

import java.util.Scanner;

/**
 * 问题：数组中只有一个数字出现一次，其他数字都出现两次，找出只出现一次的数字。
 * 方法：使用位运算中的异或性质。
 * 解题思路：相同数字异或结果为 0，任何数字与 0 异或仍为自身。
 * 将数组中所有数字依次异或，成对出现的数字会互相抵消，最后剩下的就是只出现一次的数字。
 * 时间复杂度：O(n)，n 为数组长度。
 * 空间复杂度：O(1)，只使用一个异或结果变量。
 */
public class SingleNumberBitManipulation {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入数组长度：");
        int n = scanner.nextInt();
        int result = 0;
        System.out.println("请输入数组元素：");
        for (int i = 0; i < n; i++) {
            result ^= scanner.nextInt();
        }
        System.out.println("只出现一次的数字为：" + result);
    }
}
