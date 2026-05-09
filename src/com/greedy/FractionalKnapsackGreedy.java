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
 * 问题：分数背包问题，物品可以按比例装入背包，求最大价值。
 * 方法：使用贪心算法，按单位重量价值从高到低选择物品。
 * 解题思路：先计算每个物品的单位重量价值，然后按该值从高到低排序。
 * 依次装入单位价值最高的物品；若剩余容量不足以装完整物品，则按剩余容量装入该物品的一部分。
 * 时间复杂度：O(n^2)，当前实现使用选择排序按单位价值排序。
 * 空间复杂度：O(1)，除输入物品数组外只使用常数个辅助变量。
 */
public class FractionalKnapsackGreedy {
    //选择排序将数组中的bag按权重排序(使用了选择排序)
    //5,2,6,3,7
    public static void sort(Item[] p) {
        Item t;
        for (int i = 0; i < p.length; i++) {
            int max = i;
            t = p[i];
            for (int j = i; j < p.length; j++) {
                if (t.wi < p[j].wi) {
                    t = p[j];
                    max = j;
                }
            }
            t = p[i];
            p[i] = p[max];
            p[max] = t;

        }
    }

    //算法核心
    public static void bG(Item[] p, int w, double v) {
        for (int i = 0; i < p.length; i++) {
            if (p[i].weight <= w) {
                v = v + p[i].value;
                System.out.println(p[i].pid + "全部装入,当前背包价值为" + v);
                w = w - p[i].weight;
            } else {
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
        for (int i = 0; i < n; i++) {
            weigth = sc.nextInt();
            value = sc.nextInt();
            pid = sc.next();
            p[i] = new Item(weigth, value, pid);
        }
        sort(p);
        System.out.println("各物品的权重为：");
        for (int i = 0; i < n; i++) {
            System.out.println(p[i].wi + " " + p[i].pid);
        }

//        bG(p, 0, w, 0.0);
        bG(p, w, 0.0);

    }

}



