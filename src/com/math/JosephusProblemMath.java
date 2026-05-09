package com.math;

import java.util.Scanner;

/**
 * 问题：约瑟夫环，n 个人围成一圈，每次数到第 k 个人出局，求最后剩下的人的编号。
 * 方法：使用数学递推。
 * 解题思路：设 f(i) 表示 i 个人时最后幸存者的 0-based 编号。
 * 当人数从 i-1 增加到 i 时，编号会整体偏移 k，因此 f(i)=(f(i-1)+k)%i，最后再转为 1-based 编号。
 * 时间复杂度：O(n)，n 为人数。
 * 空间复杂度：O(1)，只保存当前递推结果。
 */
public class JosephusProblemMath {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入人数 n 和报数间隔 k：");
        int n = scanner.nextInt();
        int k = scanner.nextInt();
        int survivor = 0;
        for (int i = 2; i <= n; i++) {
            survivor = (survivor + k) % i;
        }
        System.out.println("最后剩下的人编号为：" + (survivor + 1));
    }
}
