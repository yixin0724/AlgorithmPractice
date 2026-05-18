package com.backtracking;

import java.util.Scanner;

/**
 * 0/1 背包问题 - 回溯法（0/1 Knapsack - Backtracking）
 *
 * ═══════════════════════════════════════════════════════════
 * 人为思考过程：
 * ═══════════════════════════════════════════════════════════
 * 1. 问题本质：给定 n 个物品（每个有重量和价值）和一个容量为 W 的背包，
 *    每个物品只能选或不选（0 或 1），求总重量不超过 W 的前提下总价值最大。
 * 2. 最直接的想法：枚举所有 2^n 种选择方案，选出满足容量约束且价值
 *    最大的那个。这就是回溯法的基本思路 —— 系统地搜索解空间。
 * 3. 用决策树来理解：每个物品是一个决策层，有"选"和"不选"两个分支。
 *    从根节点走到叶子节点就得到一个完整方案。深度优先遍历整棵树即可
 *    穷举所有方案。
 * 4. 约束剪枝：选择某个物品前，先检查当前重量 + 该物品重量是否超过容量，
 *    超过则跳过该分支（不往下递归），避免无效搜索。
 * 5. 回溯的精髓：选择物品后递归深入，递归返回后必须"撤销"选择
 *    （恢复重量和价值），这样才能正确尝试下一个分支。这是回溯法区别于
 *    普通递归的关键特征。
 *
 * ═══════════════════════════════════════════════════════════
 * 具体措施（算法步骤）：
 * ═══════════════════════════════════════════════════════════
 * 1. 初始化物品重量数组 weight[]、价值数组 value[]、当前选择 take[]。
 * 2. 实现递归函数 maxValue(x)，x 为当前决策的物品下标：
 *    a. 若 x > count - 1（所有物品已决策完成，到达叶子节点）：
 *       - 若当前价值 curValue > 历史最优 bestValue，则更新最优解。
 *    b. 否则，对当前物品尝试两种选择：
 *       - i = 0（不选）：设置 take[x] = 0，递归进入下一层 maxValue(x+1)。
 *       - i = 1（选）：
 *         - 约束检查：若 curWeight + weight[x] <= maxWeight，
 *           则更新 curWeight 和 curValue，递归 maxValue(x+1)，
 *           递归返回后回溯：curWeight -= weight[x], curValue -= value[x]。
 * 3. 从 x = 0 开始调用 maxValue(0)，得到最优选择。
 * 4. 输出最优选择方案和最优价值。
 *
 * ═══════════════════════════════════════════════════════════
 * 复杂度分析：
 * ═══════════════════════════════════════════════════════════
 * 时间复杂度：O(2^n)
 *   - 每个物品有 2 种选择，总状态数为 2^n。
 *   - 虽有约束剪枝，但最坏情况下（容量极大）仍需探索所有分支。
 * 空间复杂度：O(n)
 *   - 递归调用栈最大深度为 n（决策树高度）。
 *   - take[] 和 bestChoice[] 数组各 O(n)。
 */
public class ZeroOneKnapsackBacktracking {
    public int[] weight;
    public int[] value;
    public int[] take;

    int curWeight = 0;
    int curValue = 0;

    int bestValue = 0;
    int[] bestChoice;
    int count;

    int maxWeight = 0;

    public void init(int[] weight, int[] value, int maxWeight) {
        if (weight == null || weight.length == 0
                || value == null || value.length == 0
                || weight.length != value.length || maxWeight <= 0) {
            System.out.println("args wrong!");
            return;
        }
        this.value = value;
        this.weight = weight;
        this.maxWeight = maxWeight;
        count = value.length;
        take = new int[count];
        bestChoice = new int[count];
        System.out.println("物品各个重量为：");
        for (int i = 0; i < weight.length; i++) {
            System.out.printf(weight[i] + " ");
        }
        System.out.println();
        System.out.println("物品各个重量为：");
        for (int i = 0; i < value.length; i++) {
            System.out.printf(value[i] + " ");
        }
        System.out.println();
        System.out.println("背包容量为"+maxWeight);
    }

    /**
     * 回溯递归函数：在决策树中搜索最优方案
     * @param x 当前正在决策的物品下标
     * @return 最优选择数组
     */
    public int[] maxValue(int x) {
        //走到了叶子节点
        if (x > count - 1) {
            //更新最优解
            if (curValue > bestValue) {
                bestValue = curValue;
                for (int i = 0; i < take.length; i++) {
                    bestChoice[i] = take[i];
                }
            }
        } else {
            //遍历当前节点（物品）的子节点：0 不放入背包 1：放入背包
            for (int i = 0; i < 2; i++) {
                take[x] = i;
                if (i == 0) {
                    //不放入背包，接着往下走
                    maxValue(x + 1);
                } else {
                    //约束条件，如果小于背包容量
                    if (curWeight + weight[x] <= maxWeight) {
                        //更新当前重量和价值
                        curWeight += weight[x];
                        curValue += value[x];
                        //继续向下深入
                        maxValue(x + 1);
                        //回溯法重要步骤，个人感觉也是精华所在。
                        // 当从上一行代码maxValue出来后，需要回溯容量和值
                        curWeight -= weight[x];
                        curValue -= value[x];
                    }
                }
            }
        }
        return bestChoice;
    }

    public static void main(String[] args) {
        ZeroOneKnapsackBacktracking bt=new ZeroOneKnapsackBacktracking();
        bt.init(new int[]{2,2,6,5,4},new int[]{6,3,5,4,6},10);
        // 启动回溯搜索
        int[] result = bt.maxValue(0);
        System.out.print("最佳选择为：[");
        for(int i=0;i<bt.bestChoice.length;i++) {
            if(i==bt.bestChoice.length-1) {
                System.out.print(bt.bestChoice[i]+"]");
            }else {
                System.out.print(bt.bestChoice[i]+",");
            }
        }
        System.out.print("\n此时价值最大，即"+bt.bestValue);
    }
}
