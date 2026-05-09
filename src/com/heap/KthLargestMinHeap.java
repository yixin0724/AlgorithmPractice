package com.heap;

import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * 问题：查找数组中的第 k 大元素。
 * 方法：使用小顶堆维护当前最大的 k 个元素。
 * 解题思路：依次遍历数组，把元素加入小顶堆；当堆大小超过 k 时弹出堆顶最小值。
 * 遍历结束后，堆顶就是当前最大的 k 个元素中最小的那个，也就是第 k 大元素。
 * 时间复杂度：O(nlogk)，n 为数组长度。
 * 空间复杂度：O(k)，堆中最多保存 k 个元素。
 */
public class KthLargestMinHeap {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入数组长度和 k：");
        int n = scanner.nextInt();
        int k = scanner.nextInt();
        PriorityQueue<Integer> heap = new PriorityQueue<Integer>();
        System.out.println("请输入数组元素：");
        for (int i = 0; i < n; i++) {
            heap.offer(scanner.nextInt());
            if (heap.size() > k) {
                heap.poll();
            }
        }
        System.out.println("第 " + k + " 大元素为：" + heap.peek());
    }
}
