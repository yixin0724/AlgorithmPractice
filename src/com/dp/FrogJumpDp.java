package com.dp;

import java.util.Scanner;

/**
 * 题⽬⼆：⻘蛙跳台阶问题
 * 描述
 * ⼀只⻘蛙⼀次可以跳上1级台阶，也可以跳上2级台阶。求该⻘蛙跳上⼀个 n 级的台阶总共有多少
 * 种跳法。
 * 解析
 * 动态规划-递推DP式，本题和求解斐波那契数列的思路⼀致，第n阶只能从第n-1或n-2阶上⾯跳来，
 * 所以跳到第n阶的⽅法， 是跳到第n-1阶和第n-2阶⽅法之和。⼀次扫描遍历只需维护三个变量即可。
 * 时间复杂度O(n)
 * 空间复杂度O(1)
 */


/**
 * 问题：青蛙每次跳 1 或 2 阶，求跳到第 n 阶的方案数。
 * 方法：使用动态规划的滚动变量优化，状态转移与斐波那契数列一致。
 * 解题思路：跳到第 i 阶只能来自第 i-1 阶或第 i-2 阶，因此 dp[i]=dp[i-1]+dp[i-2]。
 * 代码只保留前两个状态，用滚动变量不断向后推进，避免保存完整 dp 数组。
 * 时间复杂度：O(n)，n 为台阶数量。
 * 空间复杂度：O(1)，只使用常数个状态变量。
 */
public class FrogJumpDp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入台阶的个数：");
        int n = scanner.nextInt();
        if (n == 0) {
            System.out.println("总共有" + 1 + "种跳法");
        } else {
            int one = 1;
            int two = 2;
            int res = 0;
            for (int i = 3; i <= n; i++) {
                res = one + two;
                one = two;
                two = res;
            }
            System.out.println("总共有" + res + "种跳法");
        }
    }
}



//    public static void main(String args[]) {
//        Scanner sc = new Scanner(System.in);
//        String s = sc.next();
//        if (isHuiWei(s)) {
//            System.out.println(s);
//            System.exit(0);
//        } else {
//            String s1 = "";
//            String res = "";
//            for (int i = s.length() - 1; i >= 0; i--) {
//                s1 += s.charAt(i);
//            }
//            for (int i = 0; i < s.length(); i++) {
//                int j = s.length() - 1 - i;
//                String x = s.substring(i + 1);
//                String y = s1.substring(0, j);
//                if (x.equals(y)) {
//                    res = s + s1.substring(j);
//                    break;
//                }
//            }
//            System.out.println(res);
//        }
//    }
//
//    public static Boolean isHuiWei(String s) {
//        StringBuilder str = new StringBuilder(s);
//        if (str.reverse().toString().equals(s)) {
//            return true;
//        }
//        return false;
//    }


/**
 * 也可以直接采用递归的方法
 */
//public class FrogJumpDp {
//    public static void main(String[] args) {
//        Scanner sc = new Scanner(System.in);
//        System.out.println("请输入台阶的个数：");
//        int n = sc.nextInt();
//        System.out.println("总共有" + Recursion(n) + "种解法");
//    }
//
//    public static int Recursion(int n){
//        if (n == 0){
//            return 1;
//        }
//        if (n == 1){
//            return 1;
//        }
//        if (n == 2){
//            return 2;
//        }
//        return Recursion(n-2) + Recursion(n-1);
//    }
//}
