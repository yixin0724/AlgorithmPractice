package com.dp;

import java.util.Scanner;

/**
 * 0/1 背包问题（经典动态规划）
 *
 * ═══════════════════════════════════════════════════════════
 * 人为思考过程：
 * ═══════════════════════════════════════════════════════════
 * 1. 先理解问题：有 n 件物品，每件物品有重量 weight[i] 和价值 value[i]，一个容量为 capacity
 *    的背包。每件物品只能选择放（1）或不放（0），求在不超过背包容量的前提下能获得的最大价值。
 * 2. 这个问题的难点在于，选择一件物品不仅要考虑它的价值，还要考虑它占用的容量是否会挤掉
 *    其他更优组合。
 * 3. 引入二维状态：设 V[i][j] 表示"从前 i 件物品中选，且背包容量为 j 时能获得的最大价值"。
 *    这个定义把问题从"选择哪些物品"转化为"逐步考虑每一件物品"。
 * 4. 当考虑第 i 件物品时，分为两种情况：
 *    - 物品重量超过当前容量 j：放不下，只能从前 i-1 件中选，即 V[i][j] = V[i-1][j]。
 *    - 物品重量不超过容量 j：可以放，但放了不一定是更好的选择，需要在"不放"和"放"之间取
 *      最大值：V[i][j] = max(V[i-1][j], V[i-1][j-weight[i-1]] + value[i-1])。
 * 5. 这个递推式就是 0/1 背包问题的核心。从小到大依次填表，V[n][capacity] 即为最终答案。
 * 6. 为还原具体选择了哪些物品，额外使用 path[i][j] 记录在状态 (i,j) 时是否选择了第 i 件物品，
 *    填表完毕后从右下角向前回溯即可。
 *
 * ═══════════════════════════════════════════════════════════
 * 具体措施（算法步骤）：
 * ═══════════════════════════════════════════════════════════
 * 1. 读入物品数量 n、每件物品的重量 weight[] 和价值 value[]，以及背包容量 capacity。
 * 2. 创建二维表 V[n+1][capacity+1]（价值表）和 path[n+1][capacity+1]（选择记录表）。
 * 3. 双重循环填表（i 从 1 到 n，j 从 1 到 capacity）：
 *    a. 如果 weight[i-1] > j，放不下：V[i][j] = V[i-1][j]。
 *    b. 否则，比较不选和选的收益，取较大值，若选择则在 path 中标记为 1。
 * 4. 输出最多价值 V[n][capacity]。
 * 5. 回溯输出具体放入的物品：从 path 的右下角出发，遇到标记 1 则输出物品编号并减去对应重量。
 *
 * ═══════════════════════════════════════════════════════════
 * 复杂度分析：
 * ═══════════════════════════════════════════════════════════
 * 时间复杂度：O(n*C)
 *   - 填表需要两重循环：物品数 n * 背包容量 C，每个状态进行常数次比较和加法。
 * 空间复杂度：O(n*C)
 *   - 价值表 V 占用 (n+1)*(C+1) 空间，路径表 path 同样大小。可优化为一维滚动数组。
 */

public class ZeroOneKnapsackDp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("输入物品的数量：");
        int n = sc.nextInt();
        int weight[] = new int[n];
        int value[] = new int[n];

        // 依次赋值物品重量
        System.out.println("请依次为物品赋值重量：");
        for (int i = 0; i < n; i++) {
            weight[i] = sc.nextInt();
        }

        // 依次赋值物品价值
        System.out.println("请依次为物品赋值价值：");
        for (int j = 0; j < n; j++) {
            value[j] = sc.nextInt();
        }

        System.out.println("请输入最大容量：");
        int capacity = sc.nextInt();
        System.out.println("最大价值为：" + knapSack(weight, value, n, capacity));
    }

    public static int knapSack(int weight[], int value[], int n, int capacity) {

        // V[i][j] 表示前 i 个物品在容量 j 下能获得的最大价值
        int[][] V = new int[n + 1][capacity + 1];
        // path[i][j] 用于记录在状态 (i,j) 时是否选择了第 i 件物品（1=选择，0=未选）
        int[][] path = new int[n + 1][capacity + 1];

        // 双重循环填表
        for (int i = 1; i < V.length; i++) {
            for (int j = 1; j < V[0].length; j++) {
                // 当前物品重量大于背包容量，放不下，价值不变
                if (weight[i - 1] > j) {
                    V[i][j] = V[i - 1][j];
                } else {
                    // 能放下，在"不选"与"选"之间取最大值
                    if (V[i - 1][j] < value[i - 1] + V[i - 1][j - weight[i - 1]]) {
                        V[i][j] = value[i - 1] + V[i - 1][j - weight[i - 1]];
                        path[i][j] = 1;     // 标记选择了第 i 件物品
                    } else {
                        V[i][j] = V[i - 1][j];
                    }
                }
            }
        }

        // 输出价值表 V（调试用，方便观察填表过程）
        for (int i = 0; i < V.length; i++) {
            for (int j = 0; j < V[i].length; j++) {
                System.out.print(V[i][j] + " ");
            }
            System.out.println();
        }

        // 回溯输出具体放入的物品
        int i = path.length - 1;
        int j = path[0].length - 1;
        while (i > 0 && j > 0) {
            if (path[i][j] == 1) {
                System.out.println("第" + i + "个商品放入背包");
                j = j - weight[i - 1];      // 减去已选物品的重量，继续向前回溯
            }
            i--;
        }
        return V[n][capacity];
    }
}

