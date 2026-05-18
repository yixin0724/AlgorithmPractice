package com.sorting;

import java.util.Arrays;
import java.util.Scanner;

/**
 * 归并排序（Merge Sort）
 *
 * ═══════════════════════════════════════════════════════════
 * 人为思考过程：
 * ═══════════════════════════════════════════════════════════
 * 1. 如果给你两叠已经排好序的扑克牌，要把它们合并成一叠有序的牌，
 *    你会怎么做？很简单：每次比较两叠牌的最上面一张，取小的放结果堆。
 * 2. 这个"合并两个有序序列"的操作非常高效（O(n)），
 *    那问题就变成了：怎么把一个大数组变成两个有序子数组？
 * 3. 答案就是"分治"：不断把数组对半切分，直到每个子数组只有一个元素时天然有序。
 * 4. 然后从底向上，逐层把两个有序子数组合并成一个更大的有序数组，
 *    最终整棵递归树的根节点就是完全有序的原数组。
 * 5. 递归版（mergeSort）从顶部向下拆分再自底向上合并，最符合直觉；
 *    迭代版（mergeSortBottomUp）直接从底部 size=1 开始逐层合并，省略了递归开销。
 * 6. 优化：如果左右两个子区间已经整体有序（nums[mid] <= nums[mid+1]），
 *    可以跳过本次合并，这在部分有序的数据上能显著减少操作。
 * 7. 合并时，当两个指针指向相等元素时优先取左侧元素，
 *    从而保持稳定性 —— 左侧原本就在前面。
 *
 * ═══════════════════════════════════════════════════════════
 * 具体措施（算法步骤）：
 * ═══════════════════════════════════════════════════════════
 *  1. 递归拆分：将数组区间 [left, right] 递归分成 [left, mid] 和 [mid+1, right]。
 *  2. 递归终止条件：left >= right（区间长度为 0 或 1）时自然有序。
 *  3. 合并：对两个有序子区间使用双指针合并到临时数组 temp。
 *     a. i 指向左区间起点，j 指向右区间起点。
 *     b. 比较 nums[i] 和 nums[j]，取较小者放入 temp，相等时优先左区间。
 *     c. 某区间耗尽后，将另一区间剩余元素直接复制到 temp。
 *  4. 将 temp 中合并结果拷贝回原数组。
 *  5. mergeSortBottomUp：从 size=1 开始，每次 size 翻倍，迭代合并相邻区间。
 *
 * ═══════════════════════════════════════════════════════════
 * 复杂度分析：
 * ═══════════════════════════════════════════════════════════
 * 时间复杂度：O(n log n)
 *   - 最好情况：O(n log n)
 *   - 最坏情况：O(n log n)
 *   - 平均情况：O(n log n)
 * 空间复杂度：O(n)
 *   - 需要临时数组 temp 存放合并结果，大小与原数组相同
 *   - 递归版额外占用 O(log n) 调用栈
 * 稳定性：是 —— 合并时相等元素优先取左侧，保持原相对顺序
 */
public class MergeSort {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入数组长度：");
        int length = scanner.nextInt();
        int[] nums = new int[length];

        System.out.println("请依次输入数组元素：");
        for (int i = 0; i < nums.length; i++) {
            nums[i] = scanner.nextInt();
        }

        mergeSort(nums);
        System.out.println("归并排序结果为：" + Arrays.toString(nums));
    }

    /**
     * 自顶向下递归归并排序：复用同一个 temp 数组减少分配开销。
     */
    public static void mergeSort(int[] nums) {
        if (nums == null || nums.length < 2) {
            return;
        }

        // 一次性分配临时数组，所有递归层级共用
        int[] temp = new int[nums.length];
        mergeSort(nums, 0, nums.length - 1, temp);
    }

    /**
     * 自底向上迭代归并排序：从 size=1 开始逐层合并。
     */
    public static void mergeSortBottomUp(int[] nums) {
        if (nums == null || nums.length < 2) {
            return;
        }

        int[] temp = new int[nums.length];
        // size: 当前每段已有序的长度
        for (int size = 1; size < nums.length; size *= 2) {
            // 每轮合并相邻两段 [left, mid] 和 [mid+1, right]
            for (int left = 0; left < nums.length - size; left += size * 2) {
                int mid = left + size - 1;
                int right = Math.min(left + size * 2 - 1, nums.length - 1);
                // 若两段已经整体有序，跳过合并
                if (nums[mid] > nums[mid + 1]) {
                    merge(nums, left, mid, right, temp);
                }
            }
            // 防止整数溢出导致的死循环
            if (size > nums.length / 2) {
                break;
            }
        }
    }

    public void Merge_Sort(int[] nums, int start, int end) {
        if (nums == null || nums.length < 2 || start >= end) {
            return;
        }
        if (start < 0 || end >= nums.length) {
            throw new IllegalArgumentException("排序区间越界");
        }

        int[] temp = new int[nums.length];
        mergeSort(nums, start, end, temp);
    }

    public void Merge(int[] nums, int start, int mid, int end) {
        if (nums == null || nums.length == 0) {
            return;
        }
        if (start < 0 || start > mid || mid >= end || end >= nums.length) {
            throw new IllegalArgumentException("合并区间不合法");
        }

        int[] temp = new int[nums.length];
        merge(nums, start, mid, end, temp);
    }

    /**
     * 递归分治核心：拆分并合并。
     */
    private static void mergeSort(int[] nums, int left, int right, int[] temp) {
        // 递归终止：区间长度 <= 1，已经有序
        if (left >= right) {
            return;
        }

        int mid = left + (right - left) / 2;    // 避免整数溢出
        mergeSort(nums, left, mid, temp);        // 排序左半
        mergeSort(nums, mid + 1, right, temp);   // 排序右半

        // 优化：若左右已经整体有序，跳过合并
        if (nums[mid] <= nums[mid + 1]) {
            return;
        }
        merge(nums, left, mid, right, temp);     // 合并两个有序子区间
    }

    /**
     * 合并两个有序子区间 [left, mid] 和 [mid+1, right] 到临时数组。
     */
    private static void merge(int[] nums, int left, int mid, int right, int[] temp) {
        int i = left;      // 左区间指针
        int j = mid + 1;   // 右区间指针
        int index = left;  // 临时数组写入指针

        // 双指针比较，取较小者，相等时优先左区间（保证稳定性）
        while (i <= mid && j <= right) {
            if (nums[i] <= nums[j]) {
                temp[index++] = nums[i++];
            } else {
                temp[index++] = nums[j++];
            }
        }
        // 左区间剩余元素
        while (i <= mid) {
            temp[index++] = nums[i++];
        }
        // 右区间剩余元素
        while (j <= right) {
            temp[index++] = nums[j++];
        }
        // 拷贝回原数组
        for (int k = left; k <= right; k++) {
            nums[k] = temp[k];
        }
    }
}
