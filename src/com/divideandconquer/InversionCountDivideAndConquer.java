package com.divideandconquer;

import java.util.Scanner;

/**
 * 数组中的逆序对（Inversion Count）
 *
 * ═══════════════════════════════════════════════════════════
 * 人为思考过程：
 * ═══════════════════════════════════════════════════════════
 * 1. 问题本质：在数组中，如果前一个数字大于后一个数字，则这两个数字组成
 *    一个逆序对。任务是统计数组中所有逆序对的总数。
 * 2. 暴力思路：双重循环枚举所有 (i, j) 对其中 i < j，检查 nums[i] > nums[j]，
 *    计数即可。但这是 O(n^2)，对于大数据量不可接受。
 * 3. 能否利用"已有序"来加速？如果数组已经被分成两个有序部分，那么跨区间
 *    的逆序对可以快速统计：在归并排序的合并阶段，当左侧剩余元素大于右侧
 *    当前元素时，左侧所有剩余元素都能与右侧当前元素形成逆序对。
 * 4. 这正好是归并排序的一个变形：在合并两个有序子数组时，统计跨区间逆序对，
 *    内部逆序对则由递归直接计算。
 * 5. 分治策略：递归统计左半区间逆序对 + 右半区间逆序对 + 合并阶段的跨区间
 *    逆序对，三者之和即总逆序对数。
 *
 * ═══════════════════════════════════════════════════════════
 * 具体措施（算法步骤）：
 * ═══════════════════════════════════════════════════════════
 * 1. 实现 mergeSort(l, r) 递归函数，对 [l, r] 区间进行归并排序并返回
 *    该区间内的逆序对个数。
 * 2. 终止条件：若 l >= r，区间长度 <= 1，逆序对为 0。
 * 3. 分：计算中点 m = (l + r) / 2，分别递归计算左半 [l, m] 和右半 [m+1, r]。
 * 4. 治（合并）：
 *    a. 将原数组 [l, r] 拷贝到临时数组 tmp。
 *    b. 用双指针 i（左起点）和 j（右起点）进行归并。
 *    c. 当 tmp[i] > tmp[j] 时，左侧剩余元素（共 m - i + 1 个）都能与
 *       tmp[j] 形成逆序对，累加到结果中。
 *    d. 将较小元素放回 nums 数组。
 * 5. 返回左区间逆序对 + 右区间逆序对 + 合并阶段跨区间逆序对的总和。
 *
 * ═══════════════════════════════════════════════════════════
 * 复杂度分析：
 * ═══════════════════════════════════════════════════════════
 * 时间复杂度：O(n log n)
 *   - 递归树深度 O(log n)，每层合并操作 O(n)。
 *   - 同步完成归并排序和逆序对统计，没有额外开销。
 * 空间复杂度：O(n)
 *   - 需要一个与输入数组等长的临时数组 tmp 辅助归并。
 *   - 递归调用栈深度最大 O(log n)。
 */
public class InversionCountDivideAndConquer {
    static int[] tmp, nums;

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("请输入数组的长度：");
        int n = scan.nextInt();
        nums = new int[n];
        tmp = new int[n];
        System.out.println("请为数组赋值：");
        for (int i = 0; i < n; i++) {
            nums[i] = scan.nextInt();
        }
        // 分治归并排序统计逆序对
        System.out.println("所得到的逆序对的个数为：" + mergeSort(0, nums.length - 1));
    }

    static int mergeSort(int l, int r) {
        // 终止条件：区间长度 <= 1 时没有逆序对
        if (l >= r) {
            return 0;
        }
        // 递归划分：将问题一分为二
        int m = (l + r) / 2;
        int res = mergeSort(l, m) + mergeSort(m + 1, r);
        // 合并阶段：归并两个有序子数组并统计跨区间逆序对
        int i = l, j = m + 1;
        for (int k = l; k <= r; k++) {
            tmp[k] = nums[k];
        }
        for (int k = l; k <= r; k++) {
            if (i > m) {
                nums[k] = tmp[j++];
            } else if (j > r) {
                nums[k] = tmp[i++];
            } else if (tmp[i] <= tmp[j]) {
                nums[k] = tmp[i++];
            } else {
                // tmp[i] > tmp[j]：左侧剩余元素都与 tmp[j] 形成逆序对
                nums[k] = tmp[j++];
                // 4 5 6 合并 2 3 4
                res += m - i + 1; // 统计逆序对
            }
        }
        return res;
    }
}

/**
 * 这是两个for循环解答，时间复杂度为O(n平方)
 */

//class Main {
//    public static void main(String[] args) {
//        Scanner scan = new Scanner(System.in);
//        int n = scan.nextInt();
//        int[] nums = new int[n];
//        for (int i = 0; i < n; i++) {
//            nums[i] = scan.nextInt();
//        }
//        int res = 0;
//        for (int i = 0; i < nums.length; i++) {
//            for (int j = i + 1; j < nums.length; j++) {
//                if (nums[i] > nums[j]) {
//                    res++;
//                }
//            }
//        }
//        System.out.println(res);
//    }
//}
