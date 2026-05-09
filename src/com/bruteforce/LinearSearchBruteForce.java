package com.bruteforce;

import java.util.Scanner;

/**
 * 问题：在线性数组中查找指定元素，或统计指定元素出现的位置和次数。
 * 方法：使用暴力线性扫描逐个比较元素。
 * 解题思路：从数组头部开始逐个访问元素；查找下标时遇到第一个相等元素立即返回，统计次数时遍历完整数组累计匹配次数。
 * 该方法不要求数组有序，适合最基础的顺序查找场景。
 * 时间复杂度：O(n)，n 为数组长度。
 * 空间复杂度：O(1)，只使用常数个辅助变量。
 */
public class LinearSearchBruteForce {
    public static void main(String[] args) {
        LinearSearchBruteForce pom = new LinearSearchBruteForce();
        Scanner sc =new Scanner(System.in);
        System.out.println("请输入数组的长度");
        int length = sc.nextInt();
        int a[] = new int[length];
        System.out.println("请依次为数组赋值：");
        for (int i=0;i<a.length;i++){      //为数组赋值
            a[i] = sc.nextInt();
        }
        System.out.println("请输入你的选择：\n1.蛮力法顺序查找\n2.查找元素出现的个数");
        int c = sc.nextInt();
        switch (c) {            //进行选择查找数还是出现次数
            case 1:
                System.out.println("请输入你要查找的数(如果出现多个则会返回第一个的下标)：");
                int flag = sc.nextInt();
                System.out.println("你要找的数，数组下标为(-1代表没有找到该数)：" + pom.orderFind(a, flag));
                break;
            case 2:
                System.out.println("请输入你要查找的数：");
                int b = sc.nextInt();
                pom.count(a,b);
        }
    }

    public int orderFind(int a[], int flag) {   //该方法用于寻找元素
        for (int i = 0; i < a.length; i++) {
            if (a[i] == flag) {
                return i;
            }
        }
        return -1;
    }

    public void count(int a[], int b) {         //该方法用于查找一个数出现的次数
        int temp=0;             //该数字用与返回出现的次数
        for (int i=0;i<a.length;i++){
            if(a[i] == b){
                temp++;
                System.out.println("第" + temp + "次出现的位置为:" + i);
            }if (temp==0){
                System.out.println("没有找到该数");
            }
        }
    }
}
