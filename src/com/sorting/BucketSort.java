package com.sorting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * 桶排序（Bucket Sort）
 *
 * ═══════════════════════════════════════════════════════════
 * 人为思考过程：
 * ═══════════════════════════════════════════════════════════
 * 1. 假设手里有一大把不同面值的硬币，想把它们从小到大排好：
 *    可以先把硬币按面值范围扔进不同的小盒子（桶），
 *    再把每个小盒子里的硬币单独整理好，最后按盒子顺序倒出来即可。
 * 2. 核心思想是"分而治之"：把大范围分散到多个小范围，
 *    每个小范围的排序代价远小于全局排序。
 * 3. 先扫描一遍数据找到最小值和最大值，确定整个值域范围。
 * 4. 根据值域范围和桶大小计算需要多少个桶：
 *    bucketCount = (max - min) / BUCKET_SIZE + 1。
 * 5. 对于每个元素，计算它落入哪个桶：(num - min) / BUCKET_SIZE，
 *    值域内均匀分布时，元素会均匀散布到各桶中。
 * 6. 每个桶内部排序可以使用任何稳定的排序方法（本实现用 Collections.sort），
 *    因为桶内元素数量通常远小于总数，排序开销可控。
 * 7. 最终按桶的编号顺序，依次取出每个桶内已排好的元素写回原数组。
 *
 * ═══════════════════════════════════════════════════════════
 * 具体措施（算法步骤）：
 * ═══════════════════════════════════════════════════════════
 *  1. 检查数组长度：若为空或长度小于 2，直接返回。
 *  2. 遍历数组，找到 min 和 max。
 *  3. 计算桶数量 bucketCount = (max - min) / BUCKET_SIZE + 1。
 *  4. 创建 bucketCount 个空桶（List）。
 *  5. 遍历每个元素，按 (num - min) / BUCKET_SIZE 放入对应桶。
 *  6. 对每个桶内部排序（Collections.sort）。
 *  7. 按桶顺序把所有元素写回原数组。
 *  8. bucketSortByBucketCount：固定桶数版本，按比例映射元素到桶。
 *
 * ═══════════════════════════════════════════════════════════
 * 复杂度分析：
 * ═══════════════════════════════════════════════════════════
 * 时间复杂度：O(n + k) ~ O(n^2)
 *   - 最好情况：O(n + k) —— 元素均匀分布，每个桶内元素很少
 *   - 最坏情况：O(n^2) —— 所有元素落入同一桶，退化为桶内排序（O(n^2)）
 *   - 平均情况：O(n + k) —— 数据均匀分布时接近线性
 * 空间复杂度：O(n + k)
 *   - k 个桶的 List 开销 + 存储 n 个元素的节点开销
 * 稳定性：是 —— 桶内使用 Collections.sort，对 List 是稳定排序；
 *          且桶间按固定顺序合并，不会打乱等值元素的相对顺序
 */
public class BucketSort {
    // 每个桶覆盖的数值区间大小
    private static final int BUCKET_SIZE = 5;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入数组长度：");
        int length = scanner.nextInt();
        int[] nums = new int[length];

        System.out.println("请依次输入数组元素：");
        for (int i = 0; i < nums.length; i++) {
            nums[i] = scanner.nextInt();
        }

        bucketSort(nums);
        System.out.println("桶排序结果为：" + Arrays.toString(nums));
    }

    /**
     * 固定桶宽版本：每个桶负责固定大小的数值区间。
     */
    public static void bucketSort(int[] nums) {
        if (nums == null || nums.length < 2) {
            return;
        }

        // 1. 确定数据范围
        Range range = findRange(nums);
        // 2. 计算所需桶数
        long bucketCountLong = ((long) range.max - range.min) / BUCKET_SIZE + 1;
        if (bucketCountLong > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("数值范围过大，不适合使用当前桶排序实现");
        }

        // 3. 创建桶并分配元素
        List<List<Integer>> buckets = createBuckets((int) bucketCountLong);
        for (int num : nums) {
            int bucketIndex = (int) (((long) num - range.min) / BUCKET_SIZE);
            buckets.get(bucketIndex).add(num);
        }
        // 4. 每桶排序并按桶顺序收集回原数组
        collectBuckets(nums, buckets);
    }

    /**
     * 固定桶数版本：调用者指定桶数量，元素按值域比例映射到桶。
     */
    public static void bucketSortByBucketCount(int[] nums, int bucketCount) {
        if (nums == null || nums.length < 2) {
            return;
        }
        if (bucketCount <= 0) {
            throw new IllegalArgumentException("桶数量必须大于 0");
        }

        Range range = findRange(nums);
        List<List<Integer>> buckets = createBuckets(bucketCount);
        long valueRange = (long) range.max - range.min + 1;
        // 按比例映射元素到桶，(num - min) / valueRange * bucketCount
        for (int num : nums) {
            int bucketIndex = (int) (((long) num - range.min) * bucketCount / valueRange);
            // 处理边界：最大值恰好落入第 bucketCount 号桶时回退到最后一个桶
            if (bucketIndex == bucketCount) {
                bucketIndex--;
            }
            buckets.get(bucketIndex).add(num);
        }
        collectBuckets(nums, buckets);
    }

    /**
     * 创建指定数量的空桶。
     */
    private static List<List<Integer>> createBuckets(int bucketCount) {
        List<List<Integer>> buckets = new ArrayList<List<Integer>>(bucketCount);
        for (int i = 0; i < bucketCount; i++) {
            buckets.add(new ArrayList<Integer>());
        }
        return buckets;
    }

    /**
     * 对每个桶内部排序，然后按桶顺序写回原数组。
     */
    private static void collectBuckets(int[] nums, List<List<Integer>> buckets) {
        int index = 0;
        for (List<Integer> bucket : buckets) {
            Collections.sort(bucket);          // 桶内排序
            for (int num : bucket) {
                nums[index++] = num;           // 按桶顺序收集
            }
        }
    }

    /**
     * 遍历数组，找到最小值和最大值。
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
     * 数据范围值对象。
     */
    private static class Range {
        private final int min;
        private final int max;

        private Range(int min, int max) {
            this.min = min;
            this.max = max;
        }
    }
}
