package com.sorting;

import java.util.Arrays;
import java.util.Scanner;

/**
 * 堆排序（Heap Sort）
 *
 * ═══════════════════════════════════════════════════════════
 * 人为思考过程：
 * ═══════════════════════════════════════════════════════════
 * 1. 如果让你在一堆数中反复找出最大值，你会想到用什么数据结构？
 *    自然会想到大顶堆 —— 堆顶永远是当前最大值。
 * 2. 把数组看成一棵完全二叉树，利用数组下标关系表示父子节点：
 *    节点 i 的左孩子 = 2i+1，右孩子 = 2i+2，父节点 = (i-1)/2。
 * 3. 排序的核心思路：先建一个大顶堆，此时堆顶就是全局最大值；
 *    把堆顶（最大值）交换到数组末尾，然后从堆中"排除"这个末尾元素。
 * 4. 交换后，堆顶可能不再是最大值，需要对新的堆顶做"下沉"操作，
 *    将它逐步向下与较大子节点交换，直到满足大顶堆性质。
 * 5. 重复 n-1 次"交换堆顶 + 下沉"，每次确定一个最大值的位置，
 *    最终数组就变成升序排列。
 * 6. 建堆的关键是从最后一个非叶子节点（n/2 - 1）开始向前，
 *    对每个节点做下沉操作，这样 O(n) 即可完成建堆。
 * 7. 下沉可用循环（heapifyIterative）或递归（heapifyRecursive）实现，
 *    循环版空间更省（无调用栈），递归版更直观。
 * 8. 由于交换会打乱相等元素的相对顺序，堆排序是不稳定的。
 *
 * ═══════════════════════════════════════════════════════════
 * 具体措施（算法步骤）：
 * ═══════════════════════════════════════════════════════════
 *  1. 从最后一个非叶子节点（n/2 - 1）开始向前，对每个节点做下沉建大顶堆。
 *  2. 建堆完成后，堆顶 nums[0] 为当前最大值。
 *  3. 进入排序循环（end 从 n-1 到 1）：
 *     a. 交换 nums[0] 与 nums[end]，最大值归位。
 *     b. 将堆大小缩小为 end，对新的堆顶下沉调整。
 *  4. 循环结束后，数组升序排列。
 *  5. heapSortRecursiveHeapify：使用递归实现 heapify 作为对比。
 *
 * ═══════════════════════════════════════════════════════════
 * 复杂度分析：
 * ═══════════════════════════════════════════════════════════
 * 时间复杂度：O(n log n)
 *   - 最好情况：O(n log n)
 *   - 最坏情况：O(n log n)
 *   - 平均情况：O(n log n)
 * 空间复杂度：O(1)（循环版） / O(log n)（递归版，调用栈深度）
 *   - 循环版原地排序，仅使用常数辅助空间
 *   - 递归版的 heapify 调用栈深度为树高 O(log n)
 * 稳定性：否 —— 建堆和下沉过程中的交换会破坏相等元素的相对顺序
 */
public class HeapSort {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入数组长度：");
        int length = scanner.nextInt();
        int[] nums = new int[length];

        System.out.println("请依次输入数组元素：");
        for (int i = 0; i < nums.length; i++) {
            nums[i] = scanner.nextInt();
        }

        heapSort(nums);
        System.out.println("堆排序结果为：" + Arrays.toString(nums));
    }

    /**
     * 循环下沉版堆排序：建大顶堆后反复交换堆顶与末尾并下沉调整。
     */
    public static void heapSort(int[] nums) {
        if (nums == null || nums.length < 2) {
            return;
        }

        // 1. 从最后一个非叶子节点起向前建大顶堆
        buildMaxHeap(nums);
        // 2. 反复交换堆顶（最大值）到末尾，并下沉恢复堆结构
        for (int end = nums.length - 1; end > 0; end--) {
            swap(nums, 0, end);               // 最大值放到末尾
            heapifyIterative(nums, end, 0);   // 缩小堆并下沉调整
        }
    }

    /**
     * 递归下沉版堆排序：heapify 使用递归实现。
     */
    public static void heapSortRecursiveHeapify(int[] nums) {
        if (nums == null || nums.length < 2) {
            return;
        }

        // 递归建堆
        for (int i = nums.length / 2 - 1; i >= 0; i--) {
            heapifyRecursive(nums, nums.length, i);
        }
        // 排序循环
        for (int end = nums.length - 1; end > 0; end--) {
            swap(nums, 0, end);
            heapifyRecursive(nums, end, 0);
        }
    }

    /**
     * 建大顶堆：从最后一个非叶子节点开始向前逐个下沉。
     */
    private static void buildMaxHeap(int[] nums) {
        for (int i = nums.length / 2 - 1; i >= 0; i--) {
            heapifyIterative(nums, nums.length, i);
        }
    }

    /**
     * 循环版下沉：将 root 向下沉到合适位置，使子树满足大顶堆性质。
     */
    private static void heapifyIterative(int[] nums, int heapSize, int root) {
        while (true) {
            int largest = root;
            int left = root * 2 + 1;          // 左孩子下标
            int right = root * 2 + 2;         // 右孩子下标

            // 找 root, left, right 三者中的最大值
            if (left < heapSize && nums[left] > nums[largest]) {
                largest = left;
            }
            if (right < heapSize && nums[right] > nums[largest]) {
                largest = right;
            }
            // 若根已是最大，下沉结束
            if (largest == root) {
                return;
            }

            // 否则与较大孩子交换，继续向下
            swap(nums, root, largest);
            root = largest;
        }
    }

    /**
     * 递归版下沉：逻辑与循环版一致，递归处理交换后的子树。
     */
    private static void heapifyRecursive(int[] nums, int heapSize, int root) {
        int largest = root;
        int left = root * 2 + 1;
        int right = root * 2 + 2;

        if (left < heapSize && nums[left] > nums[largest]) {
            largest = left;
        }
        if (right < heapSize && nums[right] > nums[largest]) {
            largest = right;
        }
        if (largest != root) {
            swap(nums, root, largest);
            heapifyRecursive(nums, heapSize, largest);  // 递归下沉
        }
    }

    /**
     * 交换数组中两个位置的元素。
     */
    private static void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
