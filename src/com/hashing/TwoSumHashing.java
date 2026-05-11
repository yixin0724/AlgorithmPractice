package com.hashing;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * LeetCode Hot 100 - 1. Two Sum
 * 思路：
 * 在不采用双重遍历的暴力枚举情况下，如何仅用一次遍历解决问题？
 * 用哈希表，关键在于定义key和value。
 * 如果用value表示下标，这样的含义就是：key + nums[value] = target
 * 但value只能表达1个下标，而答案需要两个元素的下标，这时考虑两个元素之间的关系，也就是 x = target - y。
 * 那这时就转变为了：当前数是 x，需要找 target - x
 * 所以我们先创建好HashMap集合，使用for循环遍历列表，用need变量存储要要找的target - x。
 * 此时先判断map集合中是否已经存在need的值，若存在则直接用get获取该need值的下标后，直接返回[map.get(need),i]就是答案
 * 若不存在，则将当前的元素值和对应的下标存入map，直到map集合中存在need元素值。
 */
public class TwoSumHashing {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter array length and target:");
        int n = scanner.nextInt();
        int target = scanner.nextInt();

        int[] nums = new int[n];
        System.out.println("Please enter array elements:");
        for (int i = 0; i < n; i++) {
            nums[i] = scanner.nextInt();
        }

        int[] result = twoSum(nums, target);
        if (result[0] == -1 && result[1] == -1) {
            System.out.println("No answer found");
        } else {
            System.out.println(result[0] + " " + result[1]);
        }

        scanner.close();
    }

    public static int[] twoSum(int[] nums, int target) {
        Map<Integer,Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int need = target - nums[i];
            if (map.containsKey(need)){
                return new int[]{map.get(need),i};
            }
            map.put(nums[i], i);
        }

        return new int[]{-1, -1};
    }
}
