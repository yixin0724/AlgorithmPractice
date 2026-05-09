package com.sorting;

import java.util.Scanner;

/**
 * 问题：对数组进行升序排序。
 * 方法：使用选择排序，每轮从未排序区间选择最小元素放到前面。
 * 解题思路：第 i 轮在区间 [i,n) 中扫描最小元素下标，并与位置 i 交换。
 * 每轮确定一个最终位置，重复 n-1 轮后数组有序。
 * 时间复杂度：O(n^2)，无论初始数组是否有序都需要扫描未排序区间。
 * 空间复杂度：O(1)，原地排序。
 */
public class SelectionSort {
    public static void main(String[] args) {
        SelectionSort sls = new SelectionSort();
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入数组的长度：");
        int length = sc.nextInt();
        int a[] = new int[length];
        System.out.println("请依次为数组赋值：");
        for (int i=0;i<a.length;i++){      //为数组赋值
            a[i] = sc.nextInt();
        }
        sls.selectSort(a);
    }

    /**
     * 使用选择排序算法对数组进行排序
     * 选择排序的工作原理是每次从未排序的部分中选出最小的元素，然后将其放到已排序部分的末尾
     * 这个过程重复直到所有元素都排序完毕
     *
     * @param a 待排序的整型数组
     */
    public void selectSort(int a[]){
        // 初始化比较计数器，用于输出排序过程
        int flag = 0;
        // 外层循环控制排序的轮数，n个元素需要进行n-1次选择排序
        for (int i=0;i<a.length-1;i++){
            // 临时变量用于交换操作
            int temp = 0;
            // 记录当前轮最小值的索引，初始为未排序部分的第一个元素
            int index = i;
            // 内层循环用于在未排序部分中寻找最小值的索引
            for (int j=i+1;j<a.length;j++){
                // 如果找到更小的元素，则更新最小值的索引
                if (a[j]<a[index]){
                    index = j;
                }
            }
            // 如果最小值的索引不是未排序部分的第一个元素，则进行交换
            if (index != i){
                temp = a[i];
                a[i] = a[index];
                a[index] = temp;
            }
//            // 输出每轮排序后的数组状态
//            System.out.println("经过第" + ++flag + "躺排序后的数组为：");
//            // 遍历并打印当前数组状态
//            for (int x=0;x<a.length;x++){
//                // 对每一趟最后一个数组元素输出进行一个换行
//                if (x == a.length-1){
//                    System.out.print(a[x] + "\n");
//                }else {
//                    System.out.print(a[x] + ",");
//                }
//            }
        }
    }
}
