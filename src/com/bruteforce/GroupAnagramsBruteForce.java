package com.bruteforce;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * LeetCode Hot 100 - 49. Group Anagrams
 * 上来可以先把题目拆分为两个：第一个是判断两个字符串是不是异位词，第二个是将是异位词的单词放到一组List中。
 * 首先如何判断异位词，这里首先能想到的第一方法，将两个字符串进行排序，若排序后相等，则是异位词。
 * 第二个方法字符计数判断，判断26个字母出现的次数相同
 * 先考虑暴力枚举，依次遍历每一个字符串 i ，如果 i 还没有被分组，则创建一个 group ，然后再遍历后面的每个字符串 j ，
 * 判断 字符串 i 和 j 是否是异位词，若是则将 j 加入到 group。
 * 暴力结构是：两层循环 + 一个判断异位词函数
 */
public class GroupAnagramsBruteForce {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter string count:");
        int n = scanner.nextInt();

        String[] strs = new String[n];
        System.out.println("Please enter strings:");
        for (int i = 0; i < n; i++) {
            strs[i] = scanner.next();
        }

        List<List<String>> result = groupAnagrams(strs);
        if (result.isEmpty()) {
            System.out.println("No groups found");
        } else {
            for (List<String> group : result) {
                System.out.println(group);
            }
        }

        scanner.close();
    }

    public static List<List<String>> groupAnagrams(String[] strs) {
        List<List<String>> result = new ArrayList<>();
        // used 用来记录某个字符串是否已经被分过组，避免重复分组
        boolean[] used = new boolean[strs.length];

        // 依次尝试用每个字符串作为一个新组的代表
        for (int i = 0; i < strs.length; i++) {
            if (used[i]) {
                continue;
            }

            // 没有分过组的，先创建新组
            List<String> group = new ArrayList<>();
            group.add(strs[i]);
            used[i] = true;

            // 从后面的字符串里找异位词
            for (int j = i + 1; j < strs.length; j++) {
                // 必须是还没有被放进任何组的字符串，且是异位词
                if (!used[j] && isAnagram(strs[i], strs[j])) {
                    group.add(strs[j]);
                    used[j] = true;
                }
            }

            result.add(group);
        }

        return result;
    }

    public static boolean isAnagram(String first, String second) {
        if (first.length() != second.length()) {
            return false;
        }

        char[] firstChars = first.toCharArray();
        char[] secondChars = second.toCharArray();

        Arrays.sort(firstChars);
        Arrays.sort(secondChars);

        return new String(firstChars).equals(new String(secondChars));
    }
}
