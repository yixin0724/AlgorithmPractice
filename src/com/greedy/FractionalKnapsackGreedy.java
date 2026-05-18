package com.greedy;

import java.util.Scanner;

class Item {
    public int weight;//重量
    public int value;//价值
    public double wi;//权重
    public String pid;//背包名称

    public Item(int w, int v, String pid) {
        this.weight = w;
        this.value = v;
        this.pid = pid;
        this.wi = (double) value / weight;
    }
}

/**
 * 分数背包问题（Fractional Knapsack）
 *
 * ═══════════════════════════════════════════════════════════
 * 人为思考过程：
 * ═══════════════════════════════════════════════════════════
 * 1. 问题本质：给定若干物品（有重量和价值）和一个背包容量，物品可以按任意
 *    比例切割装入背包，目标是总价值最大。这不同于 0/1 背包，因为物品可分割。
 * 2. 直觉告诉我们：既然能切割，那应该优先拿"性价比"最高的东西 —— 也就是
 *    单位重量价值（value/weight）最高的物品。
 * 3. 如果最高性价比物品不足以填满背包，就全部拿完，然后考虑次高性价比的，
 *    以此类推。这就是贪心策略：每一步都做当前看来最优的选择。
 * 4. 为什么贪心正确？因为物品可任意切割，不存在"选了当前最优就错过了未来
 *    更优组合"的问题。任何最优解都可以通过把低性价比部分替换成高性价比部分
 *    来改进，所以按单位价值排序的贪心一定得到全局最优。
 * 5. 操作步骤就是：计算每个物品的单位价值、按单位价值降序排序、依次装上
 *    单位价值最高的物品，直到背包装满或物品耗尽。
 *
 * ═══════════════════════════════════════════════════════════
 * 具体措施（算法步骤）：
 * ═══════════════════════════════════════════════════════════
 * 1. 对每个物品计算单位重量价值 wi = value / weight。
 * 2. 将所有物品按单位重量价值降序排序（当前实现使用选择排序）。
 * 3. 初始化当前背包剩余容量 w = 总容量，当前价值 v = 0。
 * 4. 从单位价值最高的物品开始遍历：
 *    a. 若该物品重量 <= 剩余容量，则全部装入，更新价值和剩余容量。
 *    b. 否则，按剩余容量装入该物品的一部分（装入比例 = 剩余容量/物品重量），
 *       更新价值后结束循环。
 * 5. 输出装入过程和最终价值。
 *
 * ═══════════════════════════════════════════════════════════
 * 复杂度分析：
 * ═══════════════════════════════════════════════════════════
 * 时间复杂度：O(n^2)
 *   - 排序步骤：当前实现使用选择排序，复杂度 O(n^2)。
 *   - 贪心装入步骤：只需一次线性扫描，O(n)。
 *   - 若使用 O(n log n) 排序（如快速排序），总复杂度可降至 O(n log n)。
 * 空间复杂度：O(1)
 *   - 除输入的物品数组外，仅使用常数个辅助变量（t, max 等）。
 *   - 排序在原数组上进行，不需要额外空间。
 */
public class FractionalKnapsackGreedy {
    //选择排序将数组中的bag按权重排序(使用了选择排序)
    //5,2,6,3,7
    public static void sort(Item[] p) {
        Item t;
        for (int i = 0; i < p.length; i++) {
            int max = i;
            t = p[i];
            // 寻找剩余元素中单位价值最高的物品下标
            for (int j = i; j < p.length; j++) {
                if (t.wi < p[j].wi) {
                    t = p[j];
                    max = j;
                }
            }
            // 将当前最高单位价值的物品交换到第 i 位
            t = p[i];
            p[i] = p[max];
            p[max] = t;

        }
    }

    //算法核心：贪心装入背包
    public static void bG(Item[] p, int w, double v) {
        for (int i = 0; i < p.length; i++) {
            if (p[i].weight <= w) {
                // 剩余容量足够，全部装入
                v = v + p[i].value;
                System.out.println(p[i].pid + "全部装入,当前背包价值为" + v);
                w = w - p[i].weight;
            } else {
                // 剩余容量不足，装入部分
                double a = w * p[i].wi;//当前价值
                v = v + a;
                System.out.println(p[i].pid + "装入了" + ((double) w / p[i].weight) + ",当前背包价值为" + v);
                break;
            }
        }
    }


    public static void main(String args[]) {
        System.out.println("请输入背包的容量w和物品的个数n");
        Scanner sc = new Scanner(System.in);
        int w = sc.nextInt();//背包的容量
        int n = sc.nextInt();//物品的个数
        Item[] p = new Item[n];
        //10 10 a 10 10 b 10 15 c
        System.out.println("请依次输入各个物品的重量w和价值v和名称s");
        int weigth;
        int value;
        String pid;
        // 读入所有物品信息
        for (int i = 0; i < n; i++) {
            weigth = sc.nextInt();
            value = sc.nextInt();
            pid = sc.next();
            p[i] = new Item(weigth, value, pid);
        }
        // 按单位重量价值降序排序
        sort(p);
        System.out.println("各物品的权重为：");
        for (int i = 0; i < n; i++) {
            System.out.println(p[i].wi + " " + p[i].pid);
        }

//        bG(p, 0, w, 0.0);
        // 贪心算法装入背包
        bG(p, w, 0.0);

    }

}
