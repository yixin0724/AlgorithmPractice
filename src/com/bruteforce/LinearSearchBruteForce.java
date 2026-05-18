package com.bruteforce;

import java.util.Scanner;

/**
 * 经典算法 —— 线性查找（Linear Search）
 *
 * ═══════════════════════════════════════════════════════════
 * 人为思考过程：
 * ═══════════════════════════════════════════════════════════
 * 1. 查找问题的本质是：在一个数据集合中，找到满足条件的元素。
 *    线性查找是最朴素、最直接的方法。
 * 2. 想象你在翻一本书，要找其中某一页的内容，
 *    最自然的做法就是从第一页开始一页一页往后翻，直到找到为止。
 *    这就是线性查找的核心思想。
 * 3. 对于"查找某个元素的第一个位置"这个需求，
 *    从数组头部开始逐个比较，碰到相等的立刻返回下标，
 *    如果走到末尾都没找到则返回 -1 表示不存在。
 * 4. 对于"统计某个元素出现的次数和位置"这个需求，
 *    同样是逐个扫描，但遇到相等的元素时不立即返回，
 *    而是记录位置并继续向后扫描，直到遍历完整个数组。
 * 5. 线性查找不要求数据有序，适用性最广，
 *    是所有查找算法的基础。
 * 6. 虽然它的效率不是最高的，但面对无序数据时，
 *    在没有额外索引结构的情况下，这是唯一通用的方法。
 *
 * ═══════════════════════════════════════════════════════════
 * 具体措施（算法步骤）：
 * ═══════════════════════════════════════════════════════════
 * 查找单元素位置（orderFind）：
 * 1. 从数组下标 0 开始，逐个访问元素。
 * 2. 若当前元素等于目标值 flag，立即返回当前下标 i。
 * 3. 遍历结束仍未找到，返回 -1。
 *
 * 统计元素出现次数和位置（count）：
 * 1. 初始化计数器 temp = 0。
 * 2. 遍历数组，每遇到一个等于目标值 b 的元素：
 *    temp++，输出"第 temp 次出现的位置为: i"。
 * 3. 遍历结束后，若 temp == 0 则输出"没有找到该数"。
 *
 * ═══════════════════════════════════════════════════════════
 * 复杂度分析：
 * ═══════════════════════════════════════════════════════════
 * 时间复杂度：O(n)
 *   - 最坏情况下需要遍历整个数组才能确定结果，
 *     其中 n 为数组长度。
 * 空间复杂度：O(1)
 *   - 只使用了常数个辅助变量（循环计数器、临时结果等）。
 */
public class LinearSearchBruteForce {
    public static void main(String[] args) {
        LinearSearchBruteForce pom = new LinearSearchBruteForce();
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入数组的长度");
        int length = sc.nextInt();
        int a[] = new int[length];
        System.out.println("请依次为数组赋值：");
        // 为数组赋值
        for (int i = 0; i < a.length; i++) {
            a[i] = sc.nextInt();
        }
        System.out.println("请输入你的选择：\n1.蛮力法顺序查找\n2.查找元素出现的个数");
        int c = sc.nextInt();
        // 根据用户选择执行查找或统计
        switch (c) {
            case 1:
                System.out.println("请输入你要查找的数(如果出现多个则会返回第一个的下标)：");
                int flag = sc.nextInt();
                System.out.println("你要找的数，数组下标为(-1代表没有找到该数)：" + pom.orderFind(a, flag));
                break;
            case 2:
                System.out.println("请输入你要查找的数：");
                int b = sc.nextInt();
                pom.count(a, b);
        }
    }

    /**
     * 线性查找：在数组中寻找目标值，返回其首次出现的下标。
     * 若未找到则返回 -1。
     */
    public int orderFind(int a[], int flag) {
        // 从头到尾逐个比较
        for (int i = 0; i < a.length; i++) {
            if (a[i] == flag) {
                return i;       // 找到，返回下标
            }
        }
        return -1;              // 未找到
    }

    /**
     * 统计目标值在数组中出现的次数，并输出每次出现的位置。
     */
    public void count(int a[], int b) {
        int temp = 0;           // 出现次数计数器
        for (int i = 0; i < a.length; i++) {
            if (a[i] == b) {
                temp++;
                System.out.println("第" + temp + "次出现的位置为:" + i);
            }
        }
        if (temp == 0) {
            System.out.println("没有找到该数");
        }
    }
}
