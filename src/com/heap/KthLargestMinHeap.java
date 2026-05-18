package com.heap;

import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * LeetCode Hot 100 - 215. Kth Largest Element in an Array（数组中的第K个最大元素）
 *
 * ═══════════════════════════════════════════════════════════
 * 人为思考过程：
 * ═══════════════════════════════════════════════════════════
 * 1. 题目要求找出数组中第 k 大的元素。
 *    例如数组 [3,2,1,5,6,4] 中第 2 大的元素是 5。
 * 2. 最直接的想法是排序后取 nums[n-k]，但排序是 O(n log n)，
 *    有没有更高效的方法？
 * 3. 换个思路：我只需要知道前 k 大的元素中"最小的那个"。
 *    如果能维护一个"当前已经看过的元素中最大的 k 个"的集合，
 *    那么集合中最小的那个就是第 k 大的元素。
 * 4. 什么数据结构适合"维护最大 k 个元素中的最小值"？
 *    最小堆（小顶堆）！堆顶总是堆中最小的元素。
 * 5. 具体做法：遍历数组，每遇到一个元素就把它放入小顶堆。
 *    如果堆的大小超过了 k，就把堆顶（当前 k+1 个元素中最小的）
 *    弹出，这样堆始终保持着当前看到的最大 k 个元素。
 * 6. 遍历结束后，堆顶就是这最大 k 个元素中最小的那个，
 *    也就是整个数组中第 k 大的元素。
 * 7. 这种方法的时间复杂度是 O(n log k)，当 k 远小于 n 时，
 *    比排序的 O(n log n) 更优。
 *
 * ═══════════════════════════════════════════════════════════
 * 具体措施（算法步骤）：
 * ═══════════════════════════════════════════════════════════
 * 1. 创建小顶堆 PriorityQueue<Integer>（Java 默认就是小顶堆）。
 * 2. 遍历数组的每个元素 x：
 *    a. heap.offer(x)（将 x 加入堆）。
 *    b. 若 heap.size() > k，执行 heap.poll()（弹出堆顶最小值）。
 * 3. 遍历结束后，heap.peek() 即为第 k 大的元素。
 *
 * ═══════════════════════════════════════════════════════════
 * 复杂度分析：
 * ═══════════════════════════════════════════════════════════
 * 时间复杂度：O(n log k)
 *   - 遍历 n 个元素，每次堆操作（offer/poll）为 O(log k)。
 * 空间复杂度：O(k)
 *   - 堆中最多保存 k 个元素。
 */
public class KthLargestMinHeap {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入数组长度和 k：");
        int n = scanner.nextInt();
        int k = scanner.nextInt();
        // Java PriorityQueue 默认是小顶堆，堆顶是最小元素
        PriorityQueue<Integer> heap = new PriorityQueue<Integer>();
        System.out.println("请输入数组元素：");
        for (int i = 0; i < n; i++) {
            // 将元素加入小顶堆
            heap.offer(scanner.nextInt());
            // 堆大小超过 k 时，弹出堆顶（最小值），保持堆中只有最大的 k 个元素
            if (heap.size() > k) {
                heap.poll();
            }
        }
        // 堆顶就是第 k 大的元素
        System.out.println("第 " + k + " 大元素为：" + heap.peek());
    }
}
