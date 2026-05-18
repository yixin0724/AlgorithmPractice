package com.sorting;

import java.util.Arrays;
import java.util.Scanner;

/**
 * 插入排序（Insertion Sort）
 *
 * ═══════════════════════════════════════════════════════════
 * 人为思考过程：
 * ═══════════════════════════════════════════════════════════
 * 1. 想象你在打扑克牌，手里已经握着几张牌排好了顺序，
 *    这时摸到一张新牌，你会怎么做？从右向左扫，找到合适的位置插进去。
 * 2. 这就是插入排序的直觉来源：维护左侧的"已排序区间"，
 *    每次从右侧"未排序区间"取第一个元素，插入到左侧的合适位置。
 * 3. 默认第一个元素 nums[0] 自身已有序，从 i = 1 开始处理。
 * 4. 对于待插入的 current = nums[i]，在已排序区间从右向左扫描，
 *    每遇到一个大于 current 的元素，就把它右移一格，为 current 腾位置。
 * 5. 当扫描到不大于 current 的元素（或越过左边界），
 *    该位置右侧的空位就是 current 的插入位置 —— 这就是直接插入排序。
 * 6. 如果能缩小比较次数，可以用二分查找定位插入位置
 *    （binaryInsertionSort），但元素移动次数仍是 O(n^2)，
 *    整体时间复杂度没有改变。
 * 7. 因为只有在 nums[j] > current 时才移动，相等元素不会互相超越，
 *    所以插入排序是稳定的。
 *
 * ═══════════════════════════════════════════════════════════
 * 具体措施（算法步骤）：
 * ═══════════════════════════════════════════════════════════
 *  1. 从 i = 1 开始遍历数组，nums[0] 视为已排序。
 *  2. 保存 current = nums[i]（待插入元素）。
 *  3. j 从 i-1 向左扫描：若 nums[j] > current，将 nums[j] 右移一位。
 *  4. 当 j < 0 或 nums[j] <= current 时停止，将 current 放入 j+1 位置。
 *  5. 重复步骤 2-4，直到所有元素处理完毕。
 *  6. binaryInsertionSort：用二分查找替代步骤 3 的顺序扫描定位插入位置。
 *
 * ═══════════════════════════════════════════════════════════
 * 复杂度分析：
 * ═══════════════════════════════════════════════════════════
 * 时间复杂度：O(n^2)
 *   - 最好情况：O(n) —— 数组已有序，每轮仅比较一次就确定位置
 *   - 最坏情况：O(n^2) —— 完全逆序，每轮需移动所有已排序元素
 *   - 平均情况：O(n^2)
 * 空间复杂度：O(1)
 *   - 原地排序，仅使用常数个临时变量
 * 稳定性：是 —— 遇到相等元素时不移动（nums[j] > current），
 *         保证相等元素的相对顺序不变
 */
public class InsertionSort {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入数组长度：");
        int length = scanner.nextInt();
        int[] nums = new int[length];

        System.out.println("请依次输入数组元素：");
        for (int i = 0; i < nums.length; i++) {
            nums[i] = scanner.nextInt();
        }

        insertionSort(nums);
        System.out.println("插入排序结果为：" + Arrays.toString(nums));
    }

    /**
     * 直接插入排序：边找位置边移动元素，适合小规模或基本有序的数据。
     */
    public static void insertionSort(int[] nums) {
        if (nums == null || nums.length < 2) {
            return;
        }

        // 从第二个元素开始，逐个插入到已排序区间
        for (int i = 1; i < nums.length; i++) {
            int current = nums[i];          // 待插入元素
            int j = i - 1;
            // 从右向左移动大于 current 的元素
            while (j >= 0 && nums[j] > current) {
                nums[j + 1] = nums[j];
                j--;
            }
            // 插入到合适位置
            nums[j + 1] = current;
        }
    }

    /**
     * 折半插入排序：用二分查找定位插入位置，减少比较次数。
     */
    public static void binaryInsertionSort(int[] nums) {
        if (nums == null || nums.length < 2) {
            return;
        }

        for (int i = 1; i < nums.length; i++) {
            int current = nums[i];
            // 二分查找插入位置
            int insertIndex = findInsertIndex(nums, 0, i - 1, current);

            // 将 [insertIndex, i-1] 范围内的元素整体右移
            for (int j = i - 1; j >= insertIndex; j--) {
                nums[j + 1] = nums[j];
            }
            nums[insertIndex] = current;
        }
    }

    /**
     * 在已排序区间 [left, right] 中用二分查找 target 的插入位置。
     * 返回第一个 > target 的位置（保证稳定插入）。
     */
    private static int findInsertIndex(int[] nums, int left, int right, int target) {
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] <= target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return left;  // left 即第一个 > target 的位置
    }
}
