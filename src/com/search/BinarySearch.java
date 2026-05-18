package com.search;

import java.util.Scanner;

/**
 * 二分查找
 *
 * ═══════════════════════════════════════════════════════════
 * 人为思考过程：
 * ═══════════════════════════════════════════════════════════
 * 面对"在有序数组中查找一个数"这个问题，暴力遍历 O(n) 太慢了，尤其是在数据量很大的时候。
 *
 * 1. 思考数组的核心特征——有序，这意味着元素之间存在大小关系可以利用。
 * 2. 直觉：像翻字典一样，先翻到中间，如果目标字母比当前页小就往左翻，大就往右翻，每次排除一半。
 * 3. 具体来说：取数组中间位置的元素与目标值比较：
 *    - 如果中间元素正好等于目标值，找到了，直接返回。
 *    - 如果目标值小于中间元素，说明目标只能在左半部分，将搜索区间缩小到 [low, mid-1]。
 *    - 如果目标值大于中间元素，说明目标只能在右半部分，将搜索区间缩小到 [mid+1, high]。
 * 4. 每次比较都排除一半的元素，以 2 的幂次速度缩小搜索范围。
 * 5. 当 low > high 时，区间为空，说明目标值不存在。
 * 6. 这就是二分查找——充分利用有序性的经典算法，也是分治思想的典型应用。
 *
 * ═══════════════════════════════════════════════════════════
 * 具体措施（算法步骤）：
 * ═══════════════════════════════════════════════════════════
 * 1. 初始化左右边界：low = 0，high = n - 1。
 * 2. 循环，当 low <= high 时：
 *    a) 计算中点 mid = (low + high) / 2。
 *    b) 若 arr[mid] == target，返回 mid。
 *    c) 若 target < arr[mid]，high = mid - 1（搜索左半区间）。
 *    d) 若 target > arr[mid]，low = mid + 1（搜索右半区间）。
 * 3. 循环结束未返回，说明未找到，返回 -1。
 *
 * ═══════════════════════════════════════════════════════════
 * 复杂度分析：
 * ═══════════════════════════════════════════════════════════
 * 时间复杂度：O(log n)
 *   - n 为数组长度，每次比较搜索区间减半，最多比较 log2(n) 次。
 * 空间复杂度：O(1)
 *   - 仅使用 low、high、mid 三个边界变量。
 */
public class BinarySearch {
    public static void main(String[] args) {
        BinarySearch binaryFind = new BinarySearch();
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入数组的长度：");
        int length = sc.nextInt();
        int[] arr = new int[length];
        System.out.println("请依次为数组赋值(使用折半数组必须是有序的)：");
        for (int i = 0; i < length; i++) {
            arr[i] = sc.nextInt();
        }
        System.out.println("请输入你要查找的数(-1代表没有找到该数)：");
        int i = sc.nextInt();
        int res = binaryFind.binarySearch(arr, i);
        System.out.println("查找的数索引为：" + res);
    }

    /**
     * 二分查找
     *
     * @param arr     有序数组（升序）
     * @param findElem 要查找的元素
     * @return 元素在数组中的索引位置，返回 -1 表示未找到
     */
    public int binarySearch(int[] arr, int findElem) {
        int low = 0;
        int high = arr.length - 1;
        int mid;

        // 搜索区间 [low, high]，当区间非空时持续二分
        while (low <= high) {
            mid = (low + high) / 2;

            // 目标值小于中点，搜索左半区间
            if (findElem < arr[mid]) {
                high = mid - 1;
            }
            // 目标值大于中点，搜索右半区间
            if (findElem > arr[mid]) {
                low = mid + 1;
            }
            // 命中目标值，返回索引
            if (arr[mid] == findElem) {
                return mid;
            }
        }

        // 区间为空，未找到
        return -1;
    }
}
