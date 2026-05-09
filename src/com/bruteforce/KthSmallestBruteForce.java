package com.bruteforce;

import java.util.Scanner;

/**
 * 问题：查找数组中的第 k 小元素。
 * 方法：使用暴力枚举，每轮扫描当前最小值并剔除，重复 k 轮得到答案。
 * 解题思路：每轮遍历整个数组找到当前最小值，将其替换为较大的哨兵值，避免下一轮再次选中。
 * 重复执行 k 次后，最后一次找到的最小值就是第 k 小元素。
 * 时间复杂度：O(kn)，最坏情况下 k=n 时为 O(n^2)。
 * 空间复杂度：O(1)，只使用常数个临时变量。
 */
public class KthSmallestBruteForce {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入数组的长度：");
        int length = sc.nextInt();
        int[] arr = new int[length];
        System.out.println("请依次为数组赋值：");
        for (int i=0;i<length;i++) {
            arr[i] = sc.nextInt();
        }
        System.out.println("请输入你要选择第几个最小的数：");
        int k = sc.nextInt();
        System.out.println("你要查找的数为：" + selectMin(arr,k));
    }

    public static int selectMin(int[] arr,int k){
        int flag=0;
        int min =0;
        int index = 0;
        while (flag != k){                              //循环k次，找第k个最小的
            min =999;
            for (int j = 0; j < arr.length; j++) {
                if (arr[j]<min){                        //判断，把最小的存到min里面
                    min=arr[j];
                    index=j;                            //用index来记录索引，表示哪个元素是最小的
                }
            }
            arr[index]=9999;            //每次找到最小的如果不是要他的话把他赋值为9999，方便后面继续找最小
            flag++;                     //用于记录要找第几个最小的
        }
        return min;                        //将第k个最小的返回
    }
}
