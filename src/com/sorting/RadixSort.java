package com.sorting;

import java.util.Arrays;
import java.util.Scanner;

/**
 * 基数排序（Radix Sort）
 *
 * ═══════════════════════════════════════════════════════════
 * 人为思考过程：
 * ═══════════════════════════════════════════════════════════
 * 1. 想象你要给一叠日期卡片排序，按年-月-日：先按日排序，再按月排序，
 *    最后按年排序。只要每步排序是稳定的，最终顺序就正确。
 * 2. 基数排序正是利用了这种"多关键字排序"思想：
 *    对数位上的数字，从最低位（LSD）到最高位（MSD）依次做稳定排序。
 * 3. 为什么从最低位开始？因为最低位的权重最小，
 *    高位排序后才具有最终决定权，这要求低位排序必须是稳定的。
 * 4. 每一位的排序可以使用计数排序（因为数字只有 0-9，值域极小），
 *    从而达到 O(n) 级别的单趟复杂度。
 * 5. 处理负数：将所有值平移到非负范围（key = num - min），
 *    对 key 做基数排序后再通过映射恢复原值。平移不改变相对顺序。
 * 6. 整体排序的正确性来自"稳定排序的逐位组合"：
 *    高位相同时，低位排序的结果决定了相对顺序。
 * 7. 基数排序不依赖元素间比较，突破了 O(n log n) 的理论下界，
 *    但需要数据可分解为独立位且每位值域有限。
 *
 * ═══════════════════════════════════════════════════════════
 * 具体措施（算法步骤）：
 * ═══════════════════════════════════════════════════════════
 *  1. 遍历数组找到 min 和 max，计算 maxKey = max - min（非负值）。
 *  2. 为每个元素生成非负 key：key[i] = nums[i] - min。
 *  3. 从 exp = 1 开始（个位），每次 exp *= 10，对 key 按当前位做计数排序：
 *     a. 创建 count[10]，统计当前位 0-9 各出现次数。
 *     b. count 做前缀和。
 *     c. 从右向左遍历，根据当前位数字将 nums 和 keys 放入 output 数组。
 *     d. 将 output 拷贝回原数组。
 *  4. 当 exp > maxKey 时，所有位处理完毕，数组已按原始值升序排列。
 *  5. radixSortNonNegative：只接受非负整数的简化版。
 *
 * ═══════════════════════════════════════════════════════════
 * 复杂度分析：
 * ═══════════════════════════════════════════════════════════
 * 时间复杂度：O(d * (n + r))
 *   - d = 最大 key 的位数（十进制下约等于 log10(maxKey)）
 *   - r = 基数（十进制下 r = 10）
 *   - 最好情况：O(d * (n + r))
 *   - 最坏情况：O(d * (n + r))
 *   - 平均情况：O(d * (n + r))
 * 空间复杂度：O(n + r)
 *   - count 数组占用 O(r)，output 数组占用 O(n)
 *   - key 数组占用 O(n)（long 类型以支持大范围）
 * 稳定性：是 —— 每一位排序使用稳定计数排序，保证多轮排序后
 *         相等元素的相对顺序不被破坏
 */
public class RadixSort {
    private static final int RADIX = 10;  // 十进制基数

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入数组长度：");
        int length = scanner.nextInt();
        int[] nums = new int[length];

        System.out.println("请依次输入数组元素：");
        for (int i = 0; i < nums.length; i++) {
            nums[i] = scanner.nextInt();
        }

        radixSort(nums);
        System.out.println("基数排序结果为：" + Arrays.toString(nums));
    }

    /**
     * 支持所有 int（含负数）的 LSD 基数排序。
     * 通过 key = num - min 将所有值平移到非负范围。
     */
    public static void radixSort(int[] nums) {
        if (nums == null || nums.length < 2) {
            return;
        }

        // 1. 确定数据范围
        int min = nums[0];
        int max = nums[0];
        for (int num : nums) {
            min = Math.min(min, num);
            max = Math.max(max, num);
        }

        // 2. 将原始值映射为非负 key（保持相对顺序）
        long maxKey = (long) max - min;
        long[] keys = new long[nums.length];
        for (int i = 0; i < nums.length; i++) {
            keys[i] = (long) nums[i] - min;
        }

        // 3. 对 key 做按位 LSD 基数排序，同时移动原始 nums
        radixSortByKeys(nums, keys, maxKey);
    }

    /**
     * 仅支持非负整数的 LSD 基数排序（教科书写法）。
     */
    public static void radixSortNonNegative(int[] nums) {
        if (nums == null || nums.length == 0) {
            return;
        }

        int max = nums[0];
        for (int num : nums) {
            if (num < 0) {
                throw new IllegalArgumentException("该方法只支持非负整数");
            }
            max = Math.max(max, num);
        }
        if (nums.length < 2) {
            return;
        }

        long[] keys = new long[nums.length];
        for (int i = 0; i < nums.length; i++) {
            keys[i] = nums[i];
        }

        radixSortByKeys(nums, keys, max);
    }

    /**
     * 对 keys 从低位到高位逐位做稳定计数排序，同步移动 nums。
     */
    private static void radixSortByKeys(int[] nums, long[] keys, long maxKey) {
        int[] outputNums = new int[nums.length];
        long[] outputKeys = new long[keys.length];

        // exp：当前处理的位权（1 = 个位，10 = 十位，...）
        for (long exp = 1; maxKey / exp > 0; exp *= RADIX) {
            int[] count = new int[RADIX];

            // 统计当前位各数字的出现次数
            for (long key : keys) {
                count[(int) ((key / exp) % RADIX)]++;
            }
            // 前缀和：count[i] 变成位置右边界
            for (int i = 1; i < RADIX; i++) {
                count[i] += count[i - 1];
            }
            // 从右向左放置，保证稳定性
            for (int i = nums.length - 1; i >= 0; i--) {
                int digit = (int) ((keys[i] / exp) % RADIX);
                int outputIndex = --count[digit];
                outputNums[outputIndex] = nums[i];
                outputKeys[outputIndex] = keys[i];
            }

            // 将本趟排序结果拷贝回原数组
            System.arraycopy(outputNums, 0, nums, 0, nums.length);
            System.arraycopy(outputKeys, 0, keys, 0, keys.length);
        }
    }
}
