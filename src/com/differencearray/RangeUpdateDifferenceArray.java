package com.differencearray;

import java.util.Arrays;
import java.util.Scanner;

/**
 * 差分数组——区间更新
 *
 * ═══════════════════════════════════════════════════════════
 * 人为思考过程：
 * ═══════════════════════════════════════════════════════════
 * 面对"对数组做多次区间加法后输出最终结果"这个问题，最朴素的想法就是每次区间加都去遍历区间内每一个元素，但这样效率太低。
 *
 * 1. 思考：能不能只记录"变化"，而不在每次操作时真的去改区间里每个元素？
 * 2. 联想：给区间 [l, r] 每个元素加 v，相当于从位置 l 开始每个位置都多了 v，而从位置 r+1 开始这个增量要取消。
 * 3. 如果把相邻元素之间的差值单独存为一个数组（差分数组），那么：
 *    - 在 l 位置上加上 v，就代表从 l 开始所有后续元素在求前缀和时都会累加这个 v。
 *    - 在 r+1 位置上减去 v，就代表从 r+1 开始这个增量被抵消。
 * 4. 这样每次区间更新只需要 O(1) 的两步操作——改变了问题的处理方式。
 * 5. 所有更新做完后，对差分数组求一次前缀和，就能恢复出最终数组中每个位置的值。
 * 6. 这本质上就是把"区间修改"转换成了"端点标记"问题，利用前缀和天然的回推特性。
 *
 * ═══════════════════════════════════════════════════════════
 * 具体措施（算法步骤）：
 * ═══════════════════════════════════════════════════════════
 * 1. 初始化长度为 n+1 的差分数组 diff（多一位用于处理 r+1 的越界情况）。
 * 2. 对于每次区间更新操作 [l, r, value]：
 *    a) diff[l] += value      —— 从 l 开始累加 value。
 *    b) 若 r+1 < n，diff[r+1] -= value —— 从 r+1 开始抵消。
 * 3. 所有操作完成后，对 diff 求前缀和，依次填入结果数组。
 * 4. 输出结果数组。
 *
 * ═══════════════════════════════════════════════════════════
 * 复杂度分析：
 * ═══════════════════════════════════════════════════════════
 * 时间复杂度：O(n + q)
 *   - n 为数组长度，q 为区间更新次数。
 *   - 每次区间更新 O(1)，最终前缀和恢复 O(n)。
 * 空间复杂度：O(n)
 *   - 需要一个长度为 n+1 的差分数组。
 */
public class RangeUpdateDifferenceArray {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入数组长度和操作次数：");
        int n = scanner.nextInt();
        int q = scanner.nextInt();

        // 差分数组，多开一位防止 r+1 越界
        int[] diff = new int[n + 1];

        System.out.println("请输入每次操作的左端点、右端点和增加值，端点从 0 开始：");
        for (int i = 0; i < q; i++) {
            int left = scanner.nextInt();
            int right = scanner.nextInt();
            int value = scanner.nextInt();

            // 差分操作：左端点加 value，右端点后一位减 value
            diff[left] += value;
            if (right + 1 < n) {
                diff[right + 1] -= value;
            }
        }

        // 前缀和还原最终数组
        int[] result = new int[n];
        int current = 0;
        for (int i = 0; i < n; i++) {
            current += diff[i];
            result[i] = current;
        }

        System.out.println("更新后的数组为：" + Arrays.toString(result));
    }
}
