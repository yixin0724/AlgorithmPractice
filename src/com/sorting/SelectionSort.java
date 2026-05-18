package com.sorting;

import java.util.Arrays;
import java.util.Scanner;

/**
 * 选择排序（Selection Sort）
 *
 * ═══════════════════════════════════════════════════════════
 * 人为思考过程：
 * ═══════════════════════════════════════════════════════════
 * 1. 最简单直观的排序想法：每次从剩下的牌中选最小的那张，
 *    放到已排好的牌的最右边。
 * 2. 这就是选择排序：每一轮在未排序区间中找到最小值，
 *    把它交换到当前轮次的起始位置。
 * 3. 第 i 轮处理后，[0, i] 区间的元素就确定了它们的最终位置，
 *    需要执行 n-1 轮即可完成整体排序。
 * 4. 选择排序的核心操作是"选最小"和"交换"，每轮只发生至多一次交换，
 *    因此总交换次数为 O(n)，相比冒泡排序的 O(n^2) 交换要少很多。
 * 5. 但普通选择排序的交换是"远距离跳转"：
 *    假设 nums[i] 和很远的 nums[minIndex] 交换，
 *    中间的元素顺序被打乱，相等元素可能互相超越 —— 因此不稳定。
 * 6. 若想保持稳定性（stableSelectionSort），找到最小值后不直接交换，
 *    而是把中间元素整体右移一位，再把最小值插入到最前面。
 *    这种方式保持了相等元素的相对顺序，但增加了移动开销。
 * 7. 无论数据初始状态如何，选择排序的比较次数固定为 n(n-1)/2，
 *    无法提前终止，因此在已有序数据上也表现不佳。
 *
 * ═══════════════════════════════════════════════════════════
 * 具体措施（算法步骤）：
 * ═══════════════════════════════════════════════════════════
 *  1. 外层循环 i 从 0 到 n-2，维护已排序区间 [0, i-1]。
 *  2. 在未排序区间 [i, n) 中找到最小元素的下标 minIndex。
 *  3. 若 minIndex != i，交换 nums[i] 和 nums[minIndex]。
 *  4. 重复步骤 2-3，直到所有元素各就各位。
 *  5. stableSelectionSort：找到最小值后不交换，
 *     而是将 [i, minIndex) 的元素整体右移，再把最小值插入到 i。
 *
 * ═══════════════════════════════════════════════════════════
 * 复杂度分析：
 * ═══════════════════════════════════════════════════════════
 * 时间复杂度：O(n^2)
 *   - 最好情况：O(n^2) —— 每次仍需遍历完未排序区间找最小值
 *   - 最坏情况：O(n^2)
 *   - 平均情况：O(n^2)
 * 空间复杂度：O(1)
 *   - 原地排序，仅使用常数辅助空间
 * 稳定性：普通版否 / 稳定版是
 *   - 普通版跨距离交换会破坏相等元素的相对顺序
 *   - 稳定版通过整体右移插入保持相对顺序
 */
public class SelectionSort {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入数组长度：");
        int length = scanner.nextInt();
        int[] nums = new int[length];

        System.out.println("请依次输入数组元素：");
        for (int i = 0; i < nums.length; i++) {
            nums[i] = scanner.nextInt();
        }

        selectionSort(nums);
        System.out.println("选择排序结果为：" + Arrays.toString(nums));
    }

    /**
     * 普通选择排序：每轮从未排序区间选最小值，交换到当前位置。
     */
    public static void selectionSort(int[] nums) {
        if (nums == null || nums.length < 2) {
            return;
        }

        // 共进行 n-1 轮，第 i 轮确定 nums[i] 位置的最终元素
        for (int i = 0; i < nums.length - 1; i++) {
            int minIndex = i;                   // 假设当前位置是最小值
            // 在未排序区间 [i+1, n) 找真正的最小值
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[j] < nums[minIndex]) {
                    minIndex = j;
                }
            }
            // 若最小值不在当前位置，交换
            if (minIndex != i) {
                swap(nums, i, minIndex);
            }
        }
    }

    /**
     * 稳定选择排序：通过右移插入替代交换，保持相等元素相对顺序。
     */
    public static void stableSelectionSort(int[] nums) {
        if (nums == null || nums.length < 2) {
            return;
        }

        for (int i = 0; i < nums.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[j] < nums[minIndex]) {
                    minIndex = j;
                }
            }

            // 不直接交换，而是将 i 到 minIndex-1 整体右移后插入最小值
            int minValue = nums[minIndex];
            while (minIndex > i) {
                nums[minIndex] = nums[minIndex - 1];  // 逐位右移
                minIndex--;
            }
            nums[i] = minValue;
        }
    }

    public void selectSort(int[] nums) {
        selectionSort(nums);
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
