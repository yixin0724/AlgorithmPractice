package com.sorting;

import java.util.Arrays;
import java.util.Scanner;

/**
 * 冒泡排序（Bubble Sort）
 *
 * ═══════════════════════════════════════════════════════════
 * 人为思考过程：
 * ═══════════════════════════════════════════════════════════
 * 1. 面对一堆无序数字，最朴素的想法就是"大的往后浮，小的往前沉"。
 * 2. 想象一列竖直排列的气泡：轻的气泡自然会上升，重的会下沉 ——
 *    把数组想象成竖排，较大的元素像重气泡一样慢慢"沉"到底部。
 * 3. 每一轮从左到右扫一遍，只要遇到相邻的两个元素顺序不对，就交换它们。
 * 4. 扫描完一轮后，当前区间里最大的元素一定被交换到了最右侧，
 *    就像最大的气泡沉到了最底部，不再参与后续比较。
 * 5. 但会发现：可能数组在某一轮就已经完全有序了，后续扫描都是白跑。
 *    如果能感知"本轮没有任何交换"，就可以提前结束 —— 这就是优化版的核心灵感。
 * 6. 基础版（bubbleSortBasic）省略了提前退出判断，固定执行 n-1 轮，
 *    逻辑更直白，适合理解算法的原始形态。
 * 7. 因为只交换相邻元素，且相等时不交换，所以冒泡排序天然具备稳定性。
 *
 * ═══════════════════════════════════════════════════════════
 * 具体措施（算法步骤）：
 * ═══════════════════════════════════════════════════════════
 *  1. 从 i = 0 开始，共进行 n-1 轮（n 为数组长度）。
 *  2. 每轮从 j = 0 向右比较 nums[j] 和 nums[j+1]。
 *  3. 若 nums[j] > nums[j+1]，交换两者。
 *  4. 维护一个 swapped 标志，若整轮无交换，说明数组已有序，提前返回。
 *  5. 优化版 bubbleSort：每轮边界缩小（nums.length - 1 - i），且支持提前退出。
 *  6. 基础版 bubbleSortBasic：固定 n-1 轮，每轮边界缩小，但不检测提前退出。
 *
 * ═══════════════════════════════════════════════════════════
 * 复杂度分析：
 * ═══════════════════════════════════════════════════════════
 * 时间复杂度：O(n^2)
 *   - 最好情况：O(n) —— 优化版在数组已有序时仅需一轮扫描
 *   - 最坏情况：O(n^2) —— 完全逆序时每轮都需要完整遍历
 *   - 平均情况：O(n^2)
 * 空间复杂度：O(1)
 *   - 仅使用常数个临时变量进行原地交换，无额外数组分配
 * 稳定性：是 —— 相等元素不会触发交换，相对顺序得以保持
 */
public class BubbleSort {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入数组长度：");
        int length = scanner.nextInt();
        int[] nums = new int[length];

        System.out.println("请依次输入数组元素：");
        for (int i = 0; i < nums.length; i++) {
            nums[i] = scanner.nextInt();
        }

        bubbleSort(nums);
        System.out.println("冒泡排序结果为：" + Arrays.toString(nums));
    }

    /**
     * 优化版冒泡排序：通过 swapped 标志提前终止，最好可达 O(n)。
     */
    public static void bubbleSort(int[] nums) {
        // 空数组或单元素数组无需排序
        if (nums == null || nums.length < 2) {
            return;
        }

        // 外层循环控制排序轮数，共 n-1 轮
        for (int i = 0; i < nums.length - 1; i++) {
            // swapped 标志：若本轮无交换，说明数组已有序
            boolean swapped = false;
            // 内层循环：每轮右边界左移（已排序的元素不再参与）
            for (int j = 0; j < nums.length - 1 - i; j++) {
                if (nums[j] > nums[j + 1]) {
                    swap(nums, j, j + 1);
                    swapped = true;
                }
            }
            // 若本轮未发生任何交换，数组已整体有序，提前结束
            if (!swapped) {
                return;
            }
        }
    }

    /**
     * 基础版冒泡排序：固定 n-1 轮，不做提前退出判断。
     */
    public static void bubbleSortBasic(int[] nums) {
        if (nums == null || nums.length < 2) {
            return;
        }

        for (int i = 0; i < nums.length - 1; i++) {
            for (int j = 0; j < nums.length - 1 - i; j++) {
                if (nums[j] > nums[j + 1]) {
                    swap(nums, j, j + 1);
                }
            }
        }
    }

    public void sort(int[] nums) {
        bubbleSort(nums);
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
