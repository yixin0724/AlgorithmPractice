package com.sorting;

import java.util.Arrays;
import java.util.Scanner;

/**
 * 快速排序（Quick Sort）
 *
 * ═══════════════════════════════════════════════════════════
 * 人为思考过程：
 * ═══════════════════════════════════════════════════════════
 * 1. 想象你要给一群学生按身高排队：先挑一个人当"标杆"（pivot），
 *    比他矮的站左边，比他高的站右边。然后左右两队各自再用同样方法排。
 * 2. 这就是快速排序的"分治"思想：选一个 pivot，把数组分成
 *    小于 pivot 和大于 pivot 两段，然后递归处理。
 * 3. 最简单的分区方法（双边分区）：左右指针向中间移动，
 *    左边找到大于 pivot 的，右边找到小于 pivot 的，交换它们。
 * 4. 但如果存在大量重复元素，双边分区会产生很多不必要的递归比较。
 *    三路分区（Dutch National Flag）能更好地处理：把区间分成
 *    < pivot、= pivot、> pivot 三段，等于 pivot 的部分直接跳过不用递归。
 * 5. 三路分区用三个指针：less 左边放小于 pivot 的，greater 右边放大于 pivot 的，
 *    index 扫描未知区间，遇小值换到 less 区，遇大值换到 greater 区，遇等值直接推进。
 * 6. pivot 的选择影响效率：选中间值可降低遇到最坏情况的概率，
 *    但极端数据下仍可能退化为 O(n^2)。
 * 7. 分区过程中的交换会破坏相等元素的相对顺序，因此快速排序不稳定。
 *
 * ═══════════════════════════════════════════════════════════
 * 具体措施（算法步骤）：
 * ═══════════════════════════════════════════════════════════
 *  【三路分区版 quickSort】
 *  1. 选取中间元素作为 pivot。
 *  2. 设置三个指针：less = left, index = left, greater = right。
 *  3. while (index <= greater)：
 *     a. nums[index] < pivot：交换 nums[less] 和 nums[index]，less++, index++。
 *     b. nums[index] > pivot：交换 nums[index] 和 nums[greater]，greater--。
 *     c. nums[index] == pivot：index++。
 *  4. 递归对 [left, less-1] 和 [greater+1, right] 执行同样操作。
 *  【双边分区版 quickSortTwoWay】
 *  1. 选取中间元素作为 pivot。
 *  2. i 从左向右找 >= pivot 的元素，j 从右向左找 <= pivot 的元素。
 *  3. 若 i <= j，交换 nums[i] 和 nums[j]，i++, j--。
 *  4. 递归对 [left, j] 和 [i, right] 执行同样操作。
 *
 * ═══════════════════════════════════════════════════════════
 * 复杂度分析：
 * ═══════════════════════════════════════════════════════════
 * 时间复杂度：O(n log n)
 *   - 最好情况：O(n log n) —— 每次分区均匀
 *   - 最坏情况：O(n^2) —— 每次 pivot 都是极值，退化为冒泡
 *   - 平均情况：O(n log n)
 * 空间复杂度：O(log n)（平均） / O(n)（最坏）
 *   - 递归调用栈深度，平均 log n 层，最坏 n 层
 *   - 原地排序，不需要额外数组空间
 * 稳定性：否 —— 分区过程中跨距离交换元素会破坏相对顺序
 */
public class QuickSort {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入数组长度：");
        int length = scanner.nextInt();
        int[] nums = new int[length];

        System.out.println("请依次输入数组元素：");
        for (int i = 0; i < nums.length; i++) {
            nums[i] = scanner.nextInt();
        }

        quickSort(nums);
        System.out.println("快速排序结果为：" + Arrays.toString(nums));
    }

    /**
     * 三路分区快排：将数组分成 < pivot、= pivot、> pivot 三段。
     */
    public static void quickSort(int[] nums) {
        if (nums == null || nums.length < 2) {
            return;
        }
        quickSortThreeWay(nums, 0, nums.length - 1);
    }

    /**
     * 双边分区快排：左右指针向中间移动交换不符合条件的元素。
     */
    public static void quickSortTwoWay(int[] nums) {
        if (nums == null || nums.length < 2) {
            return;
        }
        quickSortTwoWay(nums, 0, nums.length - 1);
    }

    public void quickSort(int[] nums, int left, int right) {
        if (nums == null || nums.length < 2 || left >= right) {
            return;
        }
        if (left < 0 || right >= nums.length) {
            throw new IllegalArgumentException("排序区间越界");
        }
        quickSortThreeWay(nums, left, right);
    }

    /**
     * 三路分区递归核心。
     */
    private static void quickSortThreeWay(int[] nums, int left, int right) {
        if (left >= right) {
            return;
        }

        // 选取中间元素作为 pivot，降低遇到最坏情况的概率
        int pivot = nums[left + (right - left) / 2];
        int less = left;     // less 左边全部 < pivot
        int index = left;    // 当前扫描位置，less ~ index-1 全部 = pivot
        int greater = right; // greater 右边全部 > pivot

        // 三路分区（Dutch National Flag）
        while (index <= greater) {
            if (nums[index] < pivot) {
                swap(nums, less++, index++);    // 小值换到左边
            } else if (nums[index] > pivot) {
                swap(nums, index, greater--);   // 大值换到右边
            } else {
                index++;                        // 等值跳过
            }
        }

        // 只递归 < pivot 和 > pivot 的部分，= pivot 部分已经就位
        quickSortThreeWay(nums, left, less - 1);
        quickSortThreeWay(nums, greater + 1, right);
    }

    /**
     * 双边分区递归核心。
     */
    private static void quickSortTwoWay(int[] nums, int left, int right) {
        if (left >= right) {
            return;
        }

        int pivot = nums[left + (right - left) / 2];
        int i = left;
        int j = right;

        // 双边分区：i 找大值，j 找小值，交换
        while (i <= j) {
            while (nums[i] < pivot) {
                i++;
            }
            while (nums[j] > pivot) {
                j--;
            }
            if (i <= j) {
                swap(nums, i++, j--);
            }
        }

        // 递归左右子区间
        quickSortTwoWay(nums, left, j);
        quickSortTwoWay(nums, i, right);
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
