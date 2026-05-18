package com.twopointers;

import java.util.*;

/**
 * LeetCode Hot 100 - 15. 三数之和 (3Sum)
 * 思考：
 * 1. 暴力枚举是 O(n^3)，面对 LeetCode 的数据规模必然超时。
 *    怎么优化？——想想我们已经掌握的技巧。
 * 2. 回想"两数之和 II"（有序数组版），我们在有序数组上用双指针，
 *    从两端向中间夹逼，O(n) 就能找到两个和为 target 的数。
 * 3. 那三数之和能不能转化一下？
 *    如果把题目看成：固定一个数 a，然后在剩下的数中找两个数 b + c = -a，
 *    这就化归成了"两数之和"问题！
 * 4. 但是，要使用双指针的前提是——数组有序。
 *    所以第一步应该对数组排序（O(n log n)）。
 * 5. 排序之后还要解决去重问题。好在排序后，相等的元素会聚在一起，
 *    我们可以在代码中"跳过"相等的元素，避免生成重复的三元组。
 * 6. 于是整体策略成形：
 *    ① 排序数组
 *    ② 遍历每个元素 nums[i]，固定为第一个数
 *    ③ 在 nums[i+1 .. n-1] 范围内用双指针找两数之和为 -nums[i]
 *    ④ 找到后，左右指针各跳过重复元素，继续搜索
 * 7. 还有一个剪枝优化：如果 nums[i] > 0，那么固定数已经大于0，
 *    三数之和不可能再等于0（因为后面都是正数），可以直接结束。
 *
 * 具体措施（算法步骤）：
 * 1. 对数组进行升序排序。
 * 2. 遍历数组，以 nums[i] 作为三元组的第一个数：
 *    a. 若 nums[i] > 0，直接结束循环（剪枝）。
 *    b. 若 i > 0 且 nums[i] == nums[i-1]，跳过（去重：第一个数相同会重复）。
 *    c. 设置左指针 left = i + 1，右指针 right = n - 1。
 *    d. 当 left < right 时：
 *       - 计算 sum = nums[i] + nums[left] + nums[right]
 *       - 若 sum == 0：记录结果，然后 left 右移跳过重复，right 左移跳过重复。
 *       - 若 sum < 0：left 右移（需要更大的和）。
 *       - 若 sum > 0：right 左移（需要更小的和）。
 *
 * 时间复杂度：O(n^2)
 *   - 排序：O(n log n)
 *   - 外层循环 n 次，内层双指针约 O(n) 次移动
 *   - 总体：O(n^2)
 * 空间复杂度：O(1)（不计排序栈空间和返回结果）
 */
public class ThreeSumTwoPointers {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("请输入数组长度：");
        int n = scanner.nextInt();

        int[] nums = new int[n];
        System.out.println("请输入数组元素：");
        for (int i = 0; i < n; i++) {
            nums[i] = scanner.nextInt();
        }

        List<List<Integer>> result = threeSum(nums);

        System.out.println("所有和为0且不重复的三元组：");
        if (result.isEmpty()) {
            System.out.println("[]");
        } else {
            for (List<Integer> triplet : result) {
                System.out.println(triplet);
            }
        }
    }

    /**
     * 排序 + 双指针 求解三数之和。
     * @param nums 输入整数数组
     * @return 所有和为0且不重复的三元组
     */
    public static List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        int n = nums.length;
        if (n < 3) {
            return res; // 数组长度不足3，直接返回空列表
        }

        // ── 步骤1：排序，这是使用双指针的前提 ──
        Arrays.sort(nums);

        // ── 步骤2：遍历数组，固定第一个数 nums[i] ──
        for (int i = 0; i < n - 2; i++) {

            // ── 剪枝优化：如果最小的数已经 > 0，三数之和不可能为 0 ──
            if (nums[i] > 0) {
                break;
            }

            // ── 去重：跳过重复的第一个数（和前一个元素相同） ──
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }

            // ── 步骤3：双指针在 [i+1, n-1] 范围内寻找两数之和为 -nums[i] ──
            int left = i + 1;
            int right = n - 1;
            int target = -nums[i]; // 我们需要 nums[left] + nums[right] == target

            while (left < right) {
                int sum = nums[left] + nums[right];

                if (sum == target) {
                    // 找到一个解，加入结果集
                    res.add(Arrays.asList(nums[i], nums[left], nums[right]));

                    // ── 去重：跳过左侧重复元素 ──
                    while (left < right && nums[left] == nums[left + 1]) {
                        left++;
                    }
                    // ── 去重：跳过右侧重复元素 ──
                    while (left < right && nums[right] == nums[right - 1]) {
                        right--;
                    }

                    // 两指针同时向中间移动，继续寻找下一组解
                    left++;
                    right--;
                } else if (sum < target) {
                    // 两数之和太小，左指针右移以增大和
                    left++;
                } else {
                    // 两数之和太大，右指针左移以减小和
                    right--;
                }
            }
        }

        return res;
    }
}
