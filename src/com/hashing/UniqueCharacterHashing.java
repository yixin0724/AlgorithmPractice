package com.hashing;
/**
 * 题⽬⼀：找字符
 * 描述
 *  在字符串s中找出只出现⼀次的字符，s只包含⼩写字⺟。
 * 解析
 *  我们可以采⽤数组来模拟哈希表，定义⼀个⻓度为26的数组map，来记录每个字符出现的次数，
 * ⽐如数组下标0，表示字符 a，⽽map[0]的值则表示字符a出现的次数，依次类推⾄下标25 -> 字符 z。
 * 时间复杂度O(n)
 * 空间复杂度O(n)
 */

import java.util.Scanner;

/**
 * 问题：找出字符串中只出现一次的小写字母。
 * 方法：使用数组模拟哈希表统计 26 个小写字母的出现次数。
 * 解题思路：第一次遍历字符串，将每个字符映射到 0 到 25 的数组下标并累加出现次数。
 * 第二次遍历计数数组，输出出现次数为 1 的字符。
 * 时间复杂度：O(n)，n 为字符串长度。
 * 空间复杂度：O(1)，计数数组固定为 26 个元素。
 */
public class UniqueCharacterHashing {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("输入你要查找的字符串：");
        String s = scanner.nextLine();
        int[] map = new int[26];
        for (char c : s.toCharArray()) {
            map[c - 97]++;
        }
        // ⼆次遍历，找到值为1的下标，映射为字⺟输出。
        System.out.println("出现次数为1的字母有：");
        for (int i = 0; i < 26; i++) {
            if (map[i] == 1) {
                System.out.println((char) (i + 97));
            }
        }
    }
}
