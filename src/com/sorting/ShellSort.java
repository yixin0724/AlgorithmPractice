package com.sorting;

import java.util.Arrays;
import java.util.Scanner;

/**
 * 希尔排序（Shell Sort）
 *
 * ═══════════════════════════════════════════════════════════
 * 人为思考过程：
 * ═══════════════════════════════════════════════════════════
 * 1. 插入排序有个明显的弱点：如果一个很小的元素在数组末尾，
 *    它需要一步一步向左挪，非常慢。能不能让它"跳着走"？
 * 2. 希尔排序的灵感：先用较大的步长（gap）把数组分成若干子序列，
 *    每个子序列的元素间隔相同，对每个子序列分别做插入排序。
 * 3. gap 比较大时，元素可以"远距离跳跃"，迅速把大局调整到接近有序，
 *    解决了插入排序效率受初始逆序度影响的瓶颈。
 * 4. 然后逐步缩小 gap（如 gap /= 2 或 gap /= 3），
 *    当 gap = 1 时退化为普通的插入排序。
 * 5. 但此时数组已经基本有序，插入排序的 O(n) 最好情况特性被充分激活，
 *    整体效率远优于直接对整个数组做插入排序。
 * 6. gap 序列的选择至关重要：Knuth 增量（gap = gap * 3 + 1）
 *    在实践中表现优秀，而折半增量（gap = n/2, n/4, ...）最简单直观。
 * 7. 由于分组跳跃式的插入会跨越相等元素，希尔排序是不稳定的。
 *
 * ═══════════════════════════════════════════════════════════
 * 具体措施（算法步骤）：
 * ═══════════════════════════════════════════════════════════
 *  1. 确定初始 gap（Knuth 版：最大的 gap = gap*3+1 < n/3；
 *     折半版：gap = n / 2）。
 *  2. 在当前 gap 下进行分组插入排序：
 *     a. 每组元素的下标相差 gap。
 *     b. 对每组从第二个元素起做插入排序（只与同组前序元素比较）。
 *  3. 缩小 gap（Knuth 版：gap /= 3；折半版：gap /= 2）。
 *  4. 重复步骤 2-3，直到 gap = 1 完成最后一趟插入排序。
 *  5. shellSortHalfGap：使用折半 gap 序列的简化版。
 *
 * ═══════════════════════════════════════════════════════════
 * 复杂度分析：
 * ═══════════════════════════════════════════════════════════
 * 时间复杂度：依赖 gap 序列，通常为 O(n^(3/2)) ~ O(n^2)
 *   - 最好情况：O(n log n) —— 取决于 gap 序列的质量
 *   - 最坏情况：O(n^2) —— 差 gap 序列下可能退化为插入排序级别
 *   - 平均情况：O(n^(3/2))（Knuth 序列的经验值）
 * 空间复杂度：O(1)
 *   - 原地排序，仅使用常数辅助空间
 * 稳定性：否 —— 分组跳跃式插入会跨越相等元素，破坏相对顺序
 */
public class ShellSort {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入数组长度：");
        int length = scanner.nextInt();
        int[] nums = new int[length];

        System.out.println("请依次输入数组元素：");
        for (int i = 0; i < nums.length; i++) {
            nums[i] = scanner.nextInt();
        }

        shellSort(nums);
        System.out.println("希尔排序结果为：" + Arrays.toString(nums));
    }

    /**
     * Knuth 增量序列版希尔排序：gap = 1, 4, 13, 40, 121, ...
     * 通过初始大 gap 远距离跳跃快速接近有序。
     */
    public static void shellSort(int[] nums) {
        if (nums == null || nums.length < 2) {
            return;
        }

        // 1. 计算最大 gap（Knuth 序列：gap = gap * 3 + 1）
        int gap = 1;
        while (gap < nums.length / 3) {
            gap = gap * 3 + 1;
        }

        // 2. 逐步缩小 gap，对每个 gap 做分组插入排序
        while (gap >= 1) {
            shellInsert(nums, gap);
            gap /= 3;     // 按 Knuth 序列递减
        }
    }

    /**
     * 折半增量版希尔排序：gap 从 n/2 开始逐次折半。
     */
    public static void shellSortHalfGap(int[] nums) {
        if (nums == null || nums.length < 2) {
            return;
        }

        for (int gap = nums.length / 2; gap > 0; gap /= 2) {
            shellInsert(nums, gap);
        }
    }

    /**
     * 对给定的 gap 做一趟分组插入排序。
     * 每个元素与同组内前方元素比较和移动。
     */
    private static void shellInsert(int[] nums, int gap) {
        // 从 gap 开始，每个元素作为其所在组的插入排序元素
        for (int i = gap; i < nums.length; i++) {
            int current = nums[i];          // 当前待插入元素
            int j = i - gap;
            // 在同组内向前移动所有大于 current 的元素
            while (j >= 0 && nums[j] > current) {
                nums[j + gap] = nums[j];
                j -= gap;
            }
            // 将 current 插入到同组内合适位置
            nums[j + gap] = current;
        }
    }
}
