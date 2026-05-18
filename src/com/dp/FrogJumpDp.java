package com.dp;

import java.util.Scanner;

/**
 * 青蛙跳台阶问题（经典动态规划）
 *
 * ═══════════════════════════════════════════════════════════
 * 人为思考过程：
 * ═══════════════════════════════════════════════════════════
 * 1. 先考虑简单情况：n=0 时，不动就是一种方案，1 种；n=1 时只能跳 1 级，1 种；n=2 时可以
 *    一次跳 2 级或分两次各跳 1 级，共 2 种方案。
 * 2. 观察规律：要跳到第 n 级台阶，最后一步只能从第 n-1 级跳 1 级上来，或者从第 n-2 级跳
 *    2 级上来。这说明第 n 级的方案数取决于前面两级。
 * 3. 因此，设 f(n) 为跳到第 n 级的方案数，则递推关系为：f(n) = f(n-1) + f(n-2)。
 * 4. 这正是斐波那契数列的递推式，说明该问题本质上就是求斐波那契数列的第 n 项。
 * 5. 由于计算 f(n) 只需要知道 f(n-1) 和 f(n-2)，不必保存整个数组，可以用三个变量滚动更新
 *    来节省空间。
 * 6. 注意边界：n=0 和 n=1 需要提前处理，因为滚动计算从 n>=2 开始。
 *
 * ═══════════════════════════════════════════════════════════
 * 具体措施（算法步骤）：
 * ═══════════════════════════════════════════════════════════
 * 1. 读入台阶总数 n。
 * 2. 如果 n == 0，直接输出结果 1 并结束。
 * 3. 初始化两个变量 one = 1（f(1)）、two = 2（f(2)），以及结果变量 res。
 * 4. 从 i = 3 开始循环到 n，每次执行：
 *    a. res = one + two（当前台阶方案数 = 前两级方案数之和）
 *    b. one = two（向前滚动）
 *    c. two = res（向前滚动）
 * 5. 输出最终结果 res。
 *
 * ═══════════════════════════════════════════════════════════
 * 复杂度分析：
 * ═══════════════════════════════════════════════════════════
 * 时间复杂度：O(n)
 *   - 需要从 3 到 n 进行一次线性循环，每次循环执行常数级操作。
 * 空间复杂度：O(1)
 *   - 仅使用三个整型变量 one、two、res 进行滚动更新，不依赖输入规模。
 */
public class FrogJumpDp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入台阶的个数：");
        int n = scanner.nextInt();

        // 边界处理：0 级台阶，不跳也算一种方案
        if (n == 0) {
            System.out.println("总共有" + 1 + "种跳法");
        } else {
            // 初始化：f(1) = 1, f(2) = 2
            int one = 1;
            int two = 2;
            int res = 0;

            // 滚动迭代：从第 3 阶开始，用三个变量递推
            for (int i = 3; i <= n; i++) {
                res = one + two;   // f(i) = f(i-1) + f(i-2)
                one = two;         // 变量前移
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

