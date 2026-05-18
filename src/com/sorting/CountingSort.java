package com.sorting;

import java.util.Arrays;
import java.util.Scanner;

/**
 * 计数排序（Counting Sort）
 *
 * ═══════════════════════════════════════════════════════════
 * 人为思考过程：
 * ═══════════════════════════════════════════════════════════
 * 1. 面对一堆考试分数（0-100），一个直觉是"画正字统计"：
 *    先统计每个分数出现了多少次，然后从低分到高分依次输出即可。
 * 2. 这是典型的"以空间换时间"策略：用一个计数数组记录每个值的出现次数，
 *    从而避免元素之间的比较操作。
 * 3. 对于非负整数，值可直接作为计数数组下标；对于含负数的场景，
 *    只需将所有值平移到「num - min」作为下标，顺序关系不变。
 * 4. 直接回填版（countingSortSimple）很简单：统计完次数后，
 *    从小到大遍历计数数组，按次数输出对应值。
 * 5. 但如果要进一步区分"两个相同值的元素谁先谁后"（稳定性），
 *    就需要引入前缀和技巧：把计数数组变成"该值最终出现位置的右边界"。
 * 6. 稳定版（countingSort）从右向左遍历原数组，
 *    每次将元素放置到 count[index] - 1 的位置，然后 count[index]--,
 *    确保后出现的相同元素排在更右侧，从而保持稳定性。
 * 7. 计数排序的限制在于只适合值域范围较小的场景，
 *    值域过大会导致计数数组过大，浪费内存甚至溢出。
 *
 * ═══════════════════════════════════════════════════════════
 * 具体措施（算法步骤）：
 * ═══════════════════════════════════════════════════════════
 *  1. 遍历数组，找到 min 和 max，检查值域范围是否可接受。
 *  2. 创建 count 数组，长度为 range = max - min + 1。
 *  3. 遍历 nums，count[num - min]++ 统计每个值的出现次数。
 *  4. （稳定版）对 count 做前缀和：count[i] += count[i-1]，
 *     此时 count[i] 表示值为 (i + min) 的元素在输出数组中的右边界位置。
 *  5. （稳定版）从右向左遍历 nums，
 *     将元素放入 output[count[num - min] - 1]，再让 count[num - min]--。
 *  6. （稳定版）将 output 拷贝回 nums。
 *  7. （直接回填版）从小到大遍历 count，按次数直接往 nums 回填对应值。
 *
 * ═══════════════════════════════════════════════════════════
 * 复杂度分析：
 * ═══════════════════════════════════════════════════════════
 * 时间复杂度：O(n + k)
 *   - 最好情况：O(n + k) —— k 为值域大小（max - min + 1）
 *   - 最坏情况：O(n + k) —— 不论数据如何分布，复杂度只取决于 n 和 k
 *   - 平均情况：O(n + k)
 * 空间复杂度：O(n + k)（稳定版） / O(k)（直接回填版）
 *   - count 数组占用 O(k)，output 数组（稳定版）占用 O(n)
 * 稳定性：稳定版是 / 直接回填版否
 *   - 稳定版使用前缀和 + 从右向左放置，保证相等元素的相对顺序
 */
public class CountingSort {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入数组长度：");
        int length = scanner.nextInt();
        int[] nums = new int[length];

        System.out.println("请依次输入数组元素：");
        for (int i = 0; i < nums.length; i++) {
            nums[i] = scanner.nextInt();
        }

        countingSort(nums);
        System.out.println("计数排序结果为：" + Arrays.toString(nums));
    }

    /**
     * 稳定版计数排序：使用前缀和 + 从右向左放置。
     */
    public static void countingSort(int[] nums) {
        if (nums == null || nums.length < 2) {
            return;
        }

        // 1. 查找数据范围
        Range range = findRange(nums);
        // 2. 创建计数数组，统计各值出现次数
        int[] count = new int[checkedRangeLength(range)];
        for (int num : nums) {
            count[num - range.min]++;
        }
        // 3. 前缀和：count[i] 变为值为 (i + min) 的元素的右边界位置
        for (int i = 1; i < count.length; i++) {
            count[i] += count[i - 1];
        }

        // 4. 从右向左放置元素，保证稳定性
        int[] output = new int[nums.length];
        for (int i = nums.length - 1; i >= 0; i--) {
            int countIndex = nums[i] - range.min;
            output[count[countIndex] - 1] = nums[i];
            count[countIndex]--;
        }

        // 5. 拷贝回原数组
        System.arraycopy(output, 0, nums, 0, nums.length);
    }

    /**
     * 直接回填版计数排序：仅统计次数后按值从小到大写回。
     */
    public static void countingSortSimple(int[] nums) {
        if (nums == null || nums.length < 2) {
            return;
        }

        Range range = findRange(nums);
        int[] count = new int[checkedRangeLength(range)];
        // 统计各值出现次数
        for (int num : nums) {
            count[num - range.min]++;
        }

        // 从小到大按次数回填
        int index = 0;
        for (int i = 0; i < count.length; i++) {
            while (count[i] > 0) {
                nums[index++] = i + range.min;
                count[i]--;
            }
        }
    }

    /**
     * 查找数据的最小值和最大值。
     */
    private static Range findRange(int[] nums) {
        int min = nums[0];
        int max = nums[0];
        for (int num : nums) {
            min = Math.min(min, num);
            max = Math.max(max, num);
        }
        return new Range(min, max);
    }

    /**
     * 检查值域范围是否在 int 可表示范围内。
     */
    private static int checkedRangeLength(Range range) {
        long length = (long) range.max - range.min + 1;
        if (length > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("数值范围过大，不适合使用计数排序");
        }
        return (int) length;
    }

    private static class Range {
        private final int min;
        private final int max;

        private Range(int min, int max) {
            this.min = min;
            this.max = max;
        }
    }
}
